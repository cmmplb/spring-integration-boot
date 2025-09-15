# spring-boot-xss

https://blog.csdn.net/wly55690/article/details/148329969

XSS攻击，全称为跨站脚本攻击（Cross-Site Scripting），是一种常见的网络攻击手段。它主要利用了Web应用程序对用户输入验证的不足，允许攻击者将恶意脚本注入到其他用户浏览的网页中。

## Spring Boot 中的 XSS 防御手段

在 Spring Boot 中，可以采用使用注解和使用过滤器。

### 使用注解进行 XSS 防御

````xml
<!--JSR-303/JSR-380用于验证的注解 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>3.2.0</version>
</dependency>
````

- 自定义 @XSS 注解进行参数校验

````java

@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XssValidator.class)
public @interface Xss {
    String message() default "非法输入, 检测到潜在的XSS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
````

- 自定义注解处理器

````java
public class XssValidator implements ConstraintValidator<Xss, String> {
    /**
     * 使用自带的 basicWithImages 白名单
     */
    private static final Safelist WHITE_LIST = Safelist.relaxed();
    /**
     * 定义输出设置，关闭prettyPrint（prettyPrint=false），目的是避免在清理过程中对代码进行格式化
     * 从而保持输入和输出内容的一致性。
     */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 验证输入值是否有效，即是否包含潜在的XSS攻击脚本。
     *
     * @param value 输入值，需要进行XSS攻击脚本清理。
     * @param context 上下文对象，提供关于验证环境的信息，如验证失败时的错误消息定制。
     * @return 如果清理后的值与原始值相同，则返回true，表示输入值有效；否则返回false，表示输入值无效。
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 使用Jsoup库对输入值进行清理，以移除潜在的XSS攻击脚本。
        // 使用预定义的白名单和输出设置来确保只保留安全的HTML元素和属性。
        String cleanedValue = Jsoup.clean(value, "", WHITE_LIST, OUTPUT_SETTINGS);

        // 比较清理后的值与原始值是否相同，用于判断输入值是否有效。
        return cleanedValue.equals(value);
    }

}
````

- 使用注解

````java

@Data
public class UserLoginDTO {

    @Xss
    @NotBlank(message = "账号不能为空")
    private String userAccount;

    @Xss
    @Size(min = 6, max = 18, message = "用户密码长度需在6-18位")
    private String password;

    @Xss
    @NotBlank(message = "邮箱验证码内容不能为空")
    private String emailCaptcha;
}
````

- 在 Controller 中的接口添加 @Validated 注解：

````java

@PostMapping("/login")
public Result<String> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
    return Result.success();
}
````

### 使用过滤器进行 XSS 防御

````xml
<!-- Jsoup依赖 -->
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.17.2</version>
</dependency>
````

- 自定义 json 消息解析器

类似于普通的参数 parameter，attribute，header 一类的，可以直接使用过滤器来过滤。而前端发送回来的json字符串就没那么方便过滤了。可以考虑用自定义json消息解析器来过滤前端传递的json

````java
/**
 * 在读取和写入JSON数据时特殊字符避免xss攻击的消息解析器
 *
 */
public class XSSMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    /**
     * 从HTTP输入消息中读取对象，同时应用XSS防护。
     * 
     * @param type        类型令牌，表示要读取的对象类型。
     * @param contextClass    上下文类，提供类型解析的上下文信息。
     * @param inputMessage HTTP输入消息，包含要读取的JSON数据。
     * @return 从输入消息中解析出的对象，经过XSS防护处理。
     * @throws IOException 如果发生I/O错误。
     * @throws HttpMessageNotReadableException 如果消息无法读取。
     */
    @Override
    public Object read(Type type, Class contextClass,
                       HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        JavaType javaType = getJavaType(type, contextClass);
        Object obj = readJavaType(javaType, inputMessage);
        //得到请求json
        String json = super.getObjectMapper().writeValueAsString(obj);
        //过滤特殊字符
        String result = XssUtil.clean(json);
        Object resultObj = super.getObjectMapper().readValue(result, javaType);
        return resultObj;
    }

    /**
     * 从HTTP输入消息中读取指定Java类型的对象，内部使用。
     * 
     * @param javaType    要读取的对象的Java类型。
     * @param inputMessage HTTP输入消息，包含要读取的JSON数据。
     * @return 从输入消息中解析出的对象。
     * @throws IOException 如果发生I/O错误。
     * @throws HttpMessageNotReadableException 如果消息无法读取。
     */
    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
        try {
            return super.getObjectMapper().readValue(inputMessage.getBody(), javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * 将对象写入HTTP输出消息，同时应用XSS防护。
     * 
     * @param object 要写入的对象。
     * @param outputMessage HTTP输出消息，对象将被序列化为JSON并写入此消息。
     * @throws IOException 如果发生I/O错误。
     * @throws HttpMessageNotWritableException 如果消息无法写入。
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        //得到要输出的json
        String json = super.getObjectMapper().writeValueAsString(object);
        //过滤特殊字符
        String result = XssUtil.clean(json);
        // 输出
        outputMessage.getBody().write(result.getBytes());
    }
}
````

- 注册 bean

````java
@Bean
public HttpMessageConverters xssHttpMessageConverters() {
    XSSMappingJackson2HttpMessageConverter xssMappingJackson2HttpMessageConverter = new XSSMappingJackson2HttpMessageConverter();
    HttpMessageConverter converter = xssMappingJackson2HttpMessageConverter;
    return new HttpMessageConverters(converter);
}
````