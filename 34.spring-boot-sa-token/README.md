# sa-token

官网：https://sa-token.cc/doc.html

## 登录认证

**登录注销**

````
// 当前会话注销登录
StpUtil.logout();

// 获取当前会话是否已经登录，返回true=已登录，false=未登录
StpUtil.isLogin();

// 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
StpUtil.checkLogin();
````

**会话查询**

````
// 获取当前会话账号id, 如果未登录，则抛出异常：`NotLoginException`
StpUtil.getLoginId();

// 类似查询API还有：
StpUtil.getLoginIdAsString();    // 获取当前会话账号id, 并转化为`String`类型
StpUtil.getLoginIdAsInt();       // 获取当前会话账号id, 并转化为`int`类型
StpUtil.getLoginIdAsLong();      // 获取当前会话账号id, 并转化为`long`类型

// ---------- 指定未登录情形下返回的默认值 ----------

// 获取当前会话账号id, 如果未登录，则返回 null 
StpUtil.getLoginIdDefaultNull();

// 获取当前会话账号id, 如果未登录，则返回默认值 （`defaultValue`可以为任意类型）
StpUtil.getLoginId(T defaultValue);
````

**token查询**

````
// 获取当前会话的 token 值
StpUtil.getTokenValue();

// 获取当前`StpLogic`的 token 名称
StpUtil.getTokenName();

// 获取指定 token 对应的账号id，如果未登录，则返回 null
StpUtil.getLoginIdByToken(String tokenValue);

// 获取当前会话剩余有效期（单位：s，返回-1代表永久有效）
StpUtil.getTokenTimeout();

// 获取当前会话的 token 信息参数
StpUtil.getTokenInfo();
````

## 权限认证

**获取当前账号权限码集合**

````
/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展 
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();    
        list.add("101");
        list.add("userDetails.add");
        list.add("userDetails.update");
        list.add("userDetails.get");
        // list.add("userDetails.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();    
        list.add("admin");
        list.add("super-admin");
        return list;
    }

}
````

**权限校验**

扩展：NotPermissionException 对象可通过 getLoginType() 方法获取具体是哪个 StpLogic 抛出的异常

````

// 获取：当前账号所拥有的权限集合
StpUtil.getPermissionList();

// 判断：当前账号是否含有指定权限, 返回 true 或 false
StpUtil.hasPermission("userDetails.add");        

// 校验：当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException 
StpUtil.checkPermission("userDetails.add");        

// 校验：当前账号是否含有指定权限 [指定多个，必须全部验证通过]
StpUtil.checkPermissionAnd("userDetails.add", "userDetails.delete", "userDetails.get");        

// 校验：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
StpUtil.checkPermissionOr("userDetails.add", "userDetails.delete", "userDetails.get");
````

**角色校验**

扩展：NotRoleException 对象可通过 getLoginType() 方法获取具体是哪个 StpLogic 抛出的异常

````
// 获取：当前账号所拥有的角色集合
StpUtil.getRoleList();

// 判断：当前账号是否拥有指定角色, 返回 true 或 false
StpUtil.hasRole("super-admin");        

// 校验：当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
StpUtil.checkRole("super-admin");        

// 校验：当前账号是否含有指定角色标识 [指定多个，必须全部验证通过]
StpUtil.checkRoleAnd("super-admin", "shop-admin");        

// 校验：当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可] 
StpUtil.checkRoleOr("super-admin", "shop-admin");    
````

**拦截全局异常**

````
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常拦截 
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace(); 
        return SaResult.error(e.getMessage());
    }
}
````

**权限通配符**

上帝权限：当一个账号拥有 "*" 权限时，他可以验证通过任何权限码 （角色认证同理）

````
// 当拥有 art.* 权限时
StpUtil.hasPermission("art.add");        // true
StpUtil.hasPermission("art.update");     // true
StpUtil.hasPermission("goods.add");      // false

// 当拥有 *.delete 权限时
StpUtil.hasPermission("art.delete");      // true
StpUtil.hasPermission("userDetails.delete");     // true
StpUtil.hasPermission("userDetails.update");     // false

// 当拥有 *.js 权限时
StpUtil.hasPermission("index.js");        // true
StpUtil.hasPermission("index.css");       // false
StpUtil.hasPermission("index.html");      // false

````

## 踢人下线

**强制注销**

````
StpUtil.logout(10001);                    // 强制指定账号注销下线
StpUtil.logout(10001, "PC");              // 强制指定账号指定端注销下线
StpUtil.logoutByTokenValue("token");      // 强制指定 Token 注销下线 
````

**踢人下线**

````
StpUtil.kickout(10001);                    // 将指定账号踢下线 
StpUtil.kickout(10001, "PC");              // 将指定账号指定端踢下线
StpUtil.kickoutByTokenValue("token");      // 将指定 Token 踢下线
````

## 注解鉴权

**注解鉴权**

注解鉴权 —— 优雅的将鉴权与业务代码分离！

- @SaCheckLogin: 登录校验 —— 只有登录之后才能进入该方法。
- @SaCheckRole("admin"): 角色校验 —— 必须具有指定角色标识才能进入该方法。
- @SaCheckPermission("userDetails:add"): 权限校验 —— 必须具有指定权限才能进入该方法。
- @SaCheckSafe: 二级认证校验 —— 必须二级认证之后才能进入该方法。
- @SaCheckHttpBasic: HttpBasic校验 —— 只有通过 HttpBasic 认证后才能进入该方法。
- @SaCheckHttpDigest: HttpDigest校验 —— 只有通过 HttpDigest 认证后才能进入该方法。
- @SaIgnore：忽略校验 —— 表示被修饰的方法或类无需进行注解鉴权和路由拦截器鉴权。
- @SaCheckDisable("comment")：账号服务封禁校验 —— 校验当前账号指定服务是否被封禁。

Sa-Token 使用全局拦截器完成注解鉴权功能，为了不为项目带来不必要的性能负担，拦截器默认处于关闭状态
因此，为了使用注解鉴权，你必须手动将 Sa-Token 的全局拦截器注册到你项目中

**注册拦截器**

````java

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}
````

**使用注解鉴权**

注解都可以加在类上，代表为这个类所有方法进行鉴权

````
// 登录校验：只有登录之后才能进入该方法 
@SaCheckLogin                        
@RequestMapping("info")
public String info() {
    return "查询用户信息";
}

// 角色校验：必须具有指定角色才能进入该方法 
@SaCheckRole("super-admin")        
@RequestMapping("add")
public String add() {
    return "用户增加";
}

// 权限校验：必须具有指定权限才能进入该方法 
@SaCheckPermission("userDetails-add")        
@RequestMapping("add")
public String add() {
    return "用户增加";
}

// 二级认证校验：必须二级认证之后才能进入该方法 
@SaCheckSafe()        
@RequestMapping("add")
public String add() {
    return "用户增加";
}

// Http Basic 校验：只有通过 Http Basic 认证后才能进入该方法 
@SaCheckHttpBasic(account = "sa:123456")
@RequestMapping("add")
public String add() {
    return "用户增加";
}

// Http Digest 校验：只有通过 Http Digest 认证后才能进入该方法 
@SaCheckHttpDigest(value = "sa:123456")
@RequestMapping("add")
public String add() {
    return "用户增加";
}

// 校验当前账号是否被封禁 comment 服务，如果已被封禁会抛出异常，无法进入方法 
@SaCheckDisable("comment")                
@RequestMapping("send")
public String send() {
    return "查询用户信息";
}
````

**设定校验模式**

mode有两种取值：

SaMode.AND，标注一组权限，会话必须全部具有才可通过校验。
SaMode.OR，标注一组权限，会话只要具有其一即可通过校验。

````
// 注解式鉴权：只要具有其中一个权限即可通过校验 
@RequestMapping("atJurOr")
@SaCheckPermission(value = {"userDetails-add", "userDetails-all", "userDetails-delete"}, mode = SaMode.OR)        
public SaResult atJurOr() {
    return SaResult.data("用户信息");
}
````

**角色权限双重 “or校验”**

写法一：orRole = "admin"，代表需要拥有角色 admin 。
写法二：orRole = {"admin", "manager", "staff"}，代表具有三个角色其一即可。
写法三：orRole = {"admin, manager, staff"}，代表必须同时具有三个角色。

````
// 角色权限双重 “or校验”：具备指定权限或者指定角色即可通过校验
@RequestMapping("userAdd")
@SaCheckPermission(value = "userDetails.add", orRole = "admin")        
public SaResult userAdd() {
    return SaResult.data("用户信息");
}
````

**忽略认证**

使用 @SaIgnore 可表示一个接口忽略认证

@SaIgnore 修饰方法时代表这个方法可以被游客访问，修饰类时代表这个类中的所有接口都可以游客访问。
@SaIgnore 具有最高优先级，当 @SaIgnore 和其它鉴权注解一起出现时，其它鉴权注解都将被忽略。
@SaIgnore 同样可以忽略掉 Sa-Token 拦截器中的路由鉴权。

````
@SaCheckLogin
@RestController
public class TestController {
    
    // ... 其它方法 
    
    // 此接口加上了 @SaIgnore 可以游客访问 
    @SaIgnore
    @RequestMapping("getList")
    public SaResult getList() {
        // ... 
        return SaResult.ok(); 
    }
}

````

**批量注解鉴权**

````
// 在 `@SaCheckOr` 中可以指定多个注解，只要当前会话满足其中一个注解即可通过验证，进入方法。
@SaCheckOr(
        login = @SaCheckLogin,
        role = @SaCheckRole("admin"),
        permission = @SaCheckPermission("userDetails.add"),
        safe = @SaCheckSafe("update-password"),
        httpBasic = @SaCheckHttpBasic(account = "sa:123456"),
        disable = @SaCheckDisable("submit-orders")
)
@RequestMapping("test")
public SaResult test() {
    // ... 
    return SaResult.ok(); 
}

````

每一项属性都可以写成数组形式，例如：

````
// 当前客户端只要有 [ login 账号登录] 或者 [userDetails 账号登录] 其一，就可以通过验证进入方法。
//         注意：`type = "login"` 和 `type = "userDetails"` 是多账号模式章节的扩展属性，此处你可以先略过这个知识点。
@SaCheckOr(
    login = { @SaCheckLogin(type = "login"), @SaCheckLogin(type = "userDetails") }
)
@RequestMapping("test")
public SaResult test() {
    // ... 
    return SaResult.ok(); 
}

````

疑问：既然有了 @SaCheckOr，为什么没有与之对应的 @SaCheckAnd 呢？

因为当你写多个注解时，其天然就是 and 校验关系，例如：

````
// 当你在一个方法上写多个注解鉴权时，其默认就是要满足所有注解规则后，才可以进入方法，只要有一个不满足，就会抛出异常
@SaCheckLogin
@SaCheckRole("admin")
@SaCheckPermission("userDetails.add")
@RequestMapping("test")
public SaResult test() {
    // ...
    return SaResult.ok();
}
````

## 路由拦截鉴权

**注册 Sa-Token 路由拦截器**

````
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/userDetails/doLogin"); 
    }
}

````

**校验函数详解**

自定义认证规则：new SaInterceptor(handle -> StpUtil.checkLogin()) 是最简单的写法，代表只进行登录校验功能。

我们可以往构造函数塞一个完整的 lambda 表达式，来定义详细的校验规则，例如：

````
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则 
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                .match("/**")    // 拦截的 path 列表，可以写多个 */
                .notMatch("/userDetails/doLogin")        // 排除掉的 path 列表，可以写多个 
                .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式
                
            // 根据路由划分模块，不同模块不同鉴权 
            SaRouter.match("/userDetails/**", r -> StpUtil.checkPermission("userDetails"));
            SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
            SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
            SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
            SaRouter.match("/notice/**", r -> StpUtil.checkPermission("notice"));
            SaRouter.match("/comment/**", r -> StpUtil.checkPermission("comment"));
        })).addPathPatterns("/**");
    }
}

````

## Session会话

在 Sa-Token 中，Session 分为三种，分别是：

Account-Session: 指的是框架为每个 账号id 分配的 Session
Token-Session: 指的是框架为每个 token 分配的 Session
Custom-Session: 指的是以一个 特定的值 作为SessionId，来分配的 Session

````
// 在登录时缓存 userDetails 对象 
StpUtil.getSession().set("userDetails", userDetails);

// 然后我们就可以在任意处使用这个 userDetails 对象
SysUser userDetails = (SysUser) StpUtil.getSession().get("userDetails");

````











