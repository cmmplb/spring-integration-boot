# cache2k

项目源码仓库：https://github.com/cache2k/cache2k

cache2k 是一个内存中的高性能 Java 缓存库。

````java

@EnableCaching
@Configuration
public class Cache2KConfig extends CachingConfigurerSupport {

    @Bean
    public Cache<String, String> cache() {
        return Cache2kBuilder.of(String.class, String.class)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }
}
````