# cache2k

项目源码仓库：https://github.com/cache2k/cache2k

Ehcache是一种高性能、开源的Java缓存框架, 被广泛应用于许多大规模、高并发的分布式系统中。。

````
<dependency>
   <groupId>net.sf.ehcache</groupId>
   <artifactId>ehcache</artifactId>
   <version>2.10.9.2</version>
</dependency>
````

````
# Ehcache配置
spring:
  cache:
    type: ehcache
    ehcache:
      config: 'classpath:ehcache.xml'
````

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