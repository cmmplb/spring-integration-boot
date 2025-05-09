# SpringBoot整合Mockito进行单元测试

文档：https://github.com/hehonghui/mockito-doc-zh#0

Mockito是Java单元测试开发框架。在写测试单元时它可以Mock（Mock的中文释义是模拟，所以Mockito从名字上可以看出是要模拟一种场景）。

它可以模拟任何 Spring 管理的 Bean、模拟方法的返回值、模拟抛出异常等，避免为了测试一个方法，却要自行构建整个bean的依赖链。

Mock 测试主要是用来进行开发中一些未完成的接口或者网络断开、数据库连接错误等方法调用。

## 常用的Mock技术

- PowerMock
- EasyMock
- Mockito
- JMock

**Mock测试环境和Spring上下文环境**

使用Mock进行测试时候，可以仅仅使用Mock环境，不添加@SpringBootTest，这个时候不会加载Spring上下文（@Autowired等不会起作用），需要手动处理使用@Mock和@InjectMock来处理类之间的依赖关系。

**集成测试和单元测试区别**

- 集成测试：

  测试过程中，会启动整个Spring容器，调用DB 或者 依赖的外部接口等。只不过访问的环境是测试环境。这个过程最大程度还原生产环境过程，但是耗时长。

- 单元测试：

  不启动整个应用，只对单个接口/类进行测试。不调用DB 、外部接口，依赖的服务都Mock掉，只测试代码逻辑。这个过程，测试用例耗时短。

# API

**1、Mockito的API**

- mock：构建一个我们需要的对象；可以mock具体的对象，也可以mock接口
- spy：构建监控对象
- verify：验证某种行为
- when：当执行什么操作的时候，一般配合thenXXX 一起使用。表示执行了一个操作之后产生什么效果
- doReturn：返回什么结果
- doThrow：抛出一个指定异常
- doAnswer：做一个什么相应，需要我们自定义Answer
- times：某个操作执行了多少次
- atLeastOnce：某个操作至少执行一次
- atLeast：某个操作至少执行指定次数
- atMost：某个操作至多执行指定次数
- atMostOnce：某个操作至多执行一次
- doNothing：不做任何处理
- doReturn：返回一个结果
- doThrow：抛出一个指定异常
- doAnswer：指定一个操作，传入Answer
- doCallRealMethod：返回真实业务执行的结果，只能用于监控对象

**2、ArgumentMatchers参数匹配**

- anyInt：任何int类型的参数，类似的还有anyLong/anyByte等等。
- eq：等于某个值的时候，如果是对象类型的，则看toString方法
- isA：匹配某种类型
- matches：使用正则表达式进行匹配

**3、OngoingStubbing返回操作**

- thenReturn：指定一个返回的值
- thenThrow：抛出一个指定异常
- then：指定一个操作，需要传入自定义Answer
- thenCallRealMethod：返回真实业务执行的结果，只能用于监控对象

## 依赖

core依赖

````xml

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
````

starter依赖

````xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
````

**如果集成了MyBatis，因Mybatis中Mapper基于动态代理实现，则其对应的mapper接口只能基于xml文件配置方式注入mock或spy。**

## @InjectMocks、@Mock使用

博客文档：https://blog.csdn.net/wx17343624830/article/details/140832413

- @Mock: 用于代替Mockito.mock创建mock对象,创建一个Mock实例，需要基于JUnit5环境。

- @InjectMocks: 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中。

要测试哪个类（如TUserServiceImpl），那么就用 @InjectMocks注解；被测试的类中通过 @Autowired注解注入了几个，那么测试类里面就用@Mock注解创建几个实例！

使用Mockito的注解，需要让注解生效，让注解生效的方法有两个：

1.给被测类添加 @RunWith(MockitoJUnitRunner.class) 或者 @RunWith(SpringJUnit4ClassRunner.class) 注解

````java

@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest {
    // ...
}
````

2.在初始化方法中使用MockitoAnnotations.openMocks(this)

````java

@Before
public void init() {
    MockitoAnnotations.openMocks(this);
}
````

**注意：新版spring-boot-starter-test不再集成junit，而是junit-jupiter,找不到@RunWith注解：**

spring-boot-starter-test 2.5.5 版本只需要在类上加上@SpringBootTest即可，不需要再加@RunWith()注解了。

spring-boot-starter-test 2.4.x 版本的也没有@RunWith()注解，至于从哪个版本开始没有@RunWith()注解的，请自行查阅相关文档。

一些较低版本也没有 openMocks 方法，而是 initMocks。

## SpringbootTest 注解和 RunWith 注解在测试类的作用

- **@SpringbootTest**

  这个注解相当于启动类的作用，加了这个注解后，当使用加了@Test注解的方法时，会加载Spring上下文，跟SpringbootApplication这个启动类一样，把bean加载进IOC容器。

  其中参数classes 需指明启动类.class，如果不指明，需要保证启动类所在的包和加了SpringbootTest注解的类
  在同一个包或者是启动类的子包下,否则注入到（
  @Autowired / @Resource）会报空指针异常。

  如下：

  `@SpringBootTest(classes = MySpringbootApplication.class)`

- **@RunWith**

  @RunWith（SpringRunner.class），作用是与Spring环境整合，因为在测试类中我们可以需要用@Autowired自动装配IOC容器中的bean，所以需要与Spring环境进行整合，才能实现自动装配，否则会装配失败，导致bean为null。

  有时候会发现，有的测试类不添加@RunWith也能注入成功，这是因为，如果导入@Test注解的包是org.junit.jupiter.api.Test，则不需要添加@RunWith注解，如果导入的是org.junit.Test,则需要添加，这点需要注意。

## 模拟对象有两种方式：

对注解了 @Mock 的对象进行模拟 MockitoAnnotations.openMocks(this);

对单个对象手动 mock ：xxx= Mockito.mock(xxx.class);

````java

Userservice userService = Mockito.mock(Userservice.class);
````

## Mock 测试常用注解

**全部 Mock**

````java

@Mock
private ServiceA serviceA;
````

**依赖注入**

ServiceA 依赖了 ServiceC 和 DaoA，使用InjectMocks可以自动注入。

````java

@InjectMocks
private ServiceA serviceA;
````

**真实调用**

````java

@Spy
private ServiceC serviceC;
````

这种方式，调用serviceC的方法，会被真实调用。






