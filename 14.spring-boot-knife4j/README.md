文档地址：https://doc.xiaominfo.com/knife4j/documentation/

### Springfox和SpringDoc注解对照表

官方文档

https://springdoc.org/index.html#migrating-from-springfox

| Springfox                                          | SpringDoc                                                      | 解释说明              |
|----------------------------------------------------|----------------------------------------------------------------|-------------------|
| @Api                                               | @Tag                                                           | 描述接口信息            |
| @ApiIgnore                                         | @Parameter(hidden = true) 或 @Operation(hidden = true) 或@Hidden | 隐藏字段              |
| @ApiImplicitParam                                  | @Parameter                                                     | 描述单个参数            |
| @ApiImplicitParams                                 | @Parameters                                                    | 描述多个参数            |
| @ApiModel                                          | @Schema                                                        | 描述数据模型            |
| @ApiModelProperty(hidden = true)                   | @Schema(accessMode = READ_ONLY)                                | 描述属性，可隐藏          |
| @ApiModelProperty                                  | @Schema                                                        | 描述属性              |
| @Operation(summary = value = "foo", notes = "bar") | @Operation(summary = "foo", description = "bar")               | 描述接口操作，包括标题和注释    |
| @ApiParam                                          | @Parameter                                                     | 描述接口方法参数          |
| @ApiResponse(code = 404, message = "foo")          | @ApiResponse(responseCode = "404", description = "foo")        | 描述接口响应信息，包括状态码和消息 |

### SpringDoc核心注解

| 注解                    | 描述                                                              | 
|-----------------------|-----------------------------------------------------------------|
| @OpenAPIDefinition    | 定义 OpenAPI 规范的基本信息，如 API 的标题、版本、服务器等                            | 
| @Operation            | 用于描述 API 操作（端点），包括操作的摘要、描述、请求和响应等信息                             |
| @ApiResponse          | 定义操作的响应，包括响应的状态码、描述和响应模型等信息                                     |
| @Parameter            | 定义操作的参数，包括路径参数、查询参数、请求头参数等                                      |
| @PathVariable         | 定义路径参数，用于提取 URL 中的变量                                            |
| @RequestParam         | 定义查询参数，用于从请求中获取参数的值                                             |
| @ApiParam             | 在方法参数上使用，用于描述参数的含义和约束                                           |
| @ApiResponses         | 在控制器类上使用，为多个操作定义通用的响应规范                                         |
| @ApiResponseExtension | 在 @Operation 或 @ApiResponse 上使用，用于扩展响应信息                        |
| @SecurityRequirement  | 定义操作所需的安全要求，如需要的身份验证方案、安全范围等                                    |
| @SecurityScheme       | 定义安全方案，包括认证方式（如 Basic、OAuth2 等）、令牌 URL、授权 URL 等                 |
| @Tags                 | 定义操作的标签，用于对操作进行分类和组织                                            |
| @Hidden               | 在文档中隐藏标记的操作或参数，可以用于隐藏一些内部或不需要在文档中展示的部分                          |
| @Extension            | 用于为生成的 OpenAPI 文档添加自定义的扩展信息，可以在文档中增加额外的元数据或自定义字段                |
| @RequestBodySchema    | 定义请求体的数据模型，允许对请求体进行更细粒度的描述和约束，如属性的名称、类型、格式、必填性等                 |
| @ApiResponseSchema    | 定义响应的数据模型，允许对响应体进行更细粒度的描述和约束，如属性的名称、类型、格式、必填性等                  |
| @ExtensionProperty    | 在 @Extension 注解上使用，用于定义自定义扩展的属性，可以添加额外的元数据或自定义字段到生成的 OpenAPI文档中 |

### 从 Swagger 2 升级为 Swagger 3

下面是将 Swagger 2 注解替换为 Swagger 3 注解的翻译：

````
@Api → @Tag
@ApiIgnore → @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden
@ApiImplicitParam → @Parameter
@ApiImplicitParams → @Parameters
@ApiModel → @Schema
@ApiModelProperty(hidden = true) → @Schema(accessMode = READ_ONLY)
@ApiModelProperty → @Schema
@Operation(summary = value = “foo”, notes = “bar”) → @Operation(summary = “foo”, description = “bar”)
@ApiParam → @Parameter
@ApiResponse(code = 404, message = “foo”) → @ApiResponse(responseCode = “404”, description = “foo”)
````

### Knife4j配置项

| 属性                                                      | 默认值            | 说明值                                                                                   |
|---------------------------------------------------------|----------------|---------------------------------------------------------------------------------------|
| @knife4j.enable                                         | false          | 是否开启Knife4j增强模式                                                                       |
| knife4j.cors                                            | false          | 是否开启一个默认的跨域配置,该功能配合自定义Host使用                                                          |
| knife4j.production                                      | false          | 是否开启生产环境保护策略                                                                          |
| knife4j.basic                                           |                | 对Knife4j提供的资源提供BasicHttp校验,保护文档                                                       |
| knife4j.basic.enable                                    | false          | 关闭BasicHttp功能                                                                         |
| knife4j.basic.username                                  |                | basic用户名                                                                              |
| knife4j.basic.password                                  |                | basic密码                                                                               |
| knife4j.documents                                       |                | 自定义文档集合，该属性是数组                                                                        |
| knife4j.documents.group                                 |                | 所属分组                                                                                  |
| knife4j.documents.name                                  |                | 类似于接口中的tag,对于自定义文档的分组                                                                 |
| knife4j.documents.locations                             |                | markdown文件路径,可以是一个文件夹(classpath:markdowns/*)，也可以是单个文件(classpath:md/sign.md)           |
| knife4j.setting                                         |                | 前端Ui的个性化配置属性                                                                          |
| knife4j.setting.enable-after-script                     | true           | 调试Tab是否显示AfterScript功能,默认开启                                                           |
| knife4j.setting.language                                |                | zh-CN Ui默认显示语言,目前主要有两种:中文(zh-CN)、英文(en-US)                                            |
| knife4j.setting.enable-swagger-models                   | true           | 是否显示界面中SwaggerModel功能                                                                 |
| knife4j.setting.swagger-model-name                      | Swagger Models | 重命名SwaggerModel名称,默认                                                                  |
| knife4j.setting.enable-document-manage                  | true           | 是否显示界面中"文档管理"功能                                                                       |
| knife4j.setting.enable-reload-cache-parameter           | false          | 是否在每个Debug调试栏后显示刷新变量按钮,默认不显示                                                          |
| knife4j.setting.enable-version                          | false          | 是否开启界面中对某接口的版本控制,如果开启，后端变化后Ui界面会存在小蓝点                                                 |
| knife4j.setting.enable-request-cache                    | true           | 是否开启请求参数缓存                                                                            |
| knife4j.setting.enable-filter-multipart-apis            | false          | 针对RequestMapping的接口请求类型,在不指定参数类型的情况下,如果不过滤,默认会显示7个类型的接口地址参数,如果开启此配置,默认展示一个Post类型的接口地址 |
| knife4j.setting.enable-filter-multipart-api-method-type | POST           | 具体接口的过滤类型                                                                             |
| knife4j.setting.enable-host                             | false          | 是否启用Host                                                                              |
| knife4j.setting.enable-host-text                        | false          | HOST地址                                                                                |
| knife4j.setting.enable-home-custom                      | false          | 是否开启自定义主页内容                                                                           |
| knife4j.setting.home-custom-path                        |                | 主页内容Markdown文件路径                                                                      |
| knife4j.setting.enable-search                           | false          | 是否禁用Ui界面中的搜索框                                                                         |
| knife4j.setting.enable-footer                           | true           | 是否显示Footer                                                                            |
| knife4j.setting.enable-footer-custom                    | false          | 是否开启自定义Footer                                                                         |
| knife4j.setting.footer-custom-content                   | false          | 自定义Footer内容                                                                           |
| knife4j.setting.enable-dynamic-parameter                | false          | 是否开启动态参数调试功能                                                                          |
| knife4j.setting.enable-debug                            | true           | 启用调试                                                                                  |
| knife4j.setting.enable-open-api                         | true           | 显示OpenAPI规范                                                                           |
| knife4j.setting.enable-group                            | true           | 显示服务分组                                                                                |

排序失效：

开发者必须使用knife4j-openapi2-spring-boot-starter组件才生效

更改@Tag注解：

````
@Tag(name = "测试",description = "测试", extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2", parseValue = true)})})
````

description不能省略，为空则不会生成tags

````json
[
  {
    "name": "测试",
    "description": "测试",
    "x-order": 2
  },
  {
    "name": "测试接口",
    "description": "测试接口",
    "x-order": 3
  }
]
````
 

