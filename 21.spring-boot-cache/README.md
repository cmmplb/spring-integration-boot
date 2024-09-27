# spring-boot-cache

## ehcache

Ehcache的特点：

缓存数据有三级：内存、堆外缓存Off-Heap、Disk缓存，因此无需担心容量问题。还可以通过RMI、可插入API等方式进行分布式缓存。
缓存数据会在虚拟机重启的过程中写入磁盘，持久化。
具有缓存和缓存管理器的侦听接口。
支持多缓存管理器实例，以及一个实例的多个缓存区域。

Ehcache3支持堆、堆外、磁盘以及集群缓存；除了堆之外的三种缓存，缓存的键值对必须支持序列化和反序列化。我们在使用的时候，可以单独使用任意一个。

heap :堆上存储，利用Java的堆上RAM内存来存储缓存条目。该层使用与Java应用程序相同的堆内存
off-heap: 堆外内存，大小仅受可用RAM限制。不受Java垃圾回收（GC）的约束。与堆上存储相比，它速度较慢，因为在存储和重新访问JVM堆时必须将数据移入和移出JVM堆。
disk：利用磁盘（文件系统）存储缓存条目。存储空间充足，但比基于RAM的存储要慢得多。对于所有使用磁盘存储的应用程序，建议使用快速专用磁盘来优化吞吐量。
clustered:群集存储，该数据存储是远程服务器上的缓存。

使用

常被查询、最重要、数据量较小的数据存放在堆缓存，不用担心JVM的重启，有持久化机制；
常被查询、数据量中等的数据存放在堆外缓存，几个G就好了，不用担心服务器的重启，有持久化机制；
不常用、大量的数据、但又不想占用数据库IO的数据，放在Disk缓存，容量自便；

如果要使用多个层，则必须遵守一些约束条件：

必须始终有堆内存
disk和clusterd不能同时存在
层的大小应采用金字塔式的大小，即，金字塔较高的层配置为使用的内存比下方较低的层少。
可能出现的层组合：

heap + offheap
heap + offheap + disk
heap + offheap + clustered
heap + disk
heap + clustered
注意：

将值放入高速缓存时，它会直接进入最低层，比如heap + offheap + disk直接会存储在disk层。
当获取一个值，从最高层获取，如果没有继续向下一层获取，一旦获取到，会向上层推送，同时上层存储该值。
小知识：堆外缓存
堆外缓存（off-heap）既是独立的进程缓存，又是JVM堆外的系统缓存。JVM堆是非常宝贵的，如果占用过大会带来GC性能问题，堆外缓存很好的解决了这个问题。现在服务器内存越来越大，而磁盘缓存的io性能又比较低，off-heap缓存就是折中的方案，既保证了高速性能，又可以有一定的容量。off-heap缓存性能的占用主要是序列化、反序列化的过程。一旦对象被序列化，在返回Java堆的时候必需反序列化才可以使用。这是一笔较大的性能开销。但off-heap还是要比本地磁盘、网络存储、RDBMS数据库IO等记录数据的系统要快的多。还应指出的是，序列化/反序列化的性能开销远没有很多用户想象的那么大。off-heap已经针对字节缓冲区做了优化，本身也包含一些优化机制，可以对使用标准Java序列化的对象进行优化，能使复杂Java对象的性能提升两倍，使byte数组的性能提升四倍。

## spring-boot-starter-cache

SpringBoot提供spring-boot-starter-cache通过CacheAutoConfiguration配置类实现SpringCache的自动化配置。

**本地缓存：Guava LocalCache、Ehcache、Caffeine、cache2k**

Ehcache 的功能更加丰富，Caffeine 的性能要比 Guava LocalCache 好。

**分布式缓存：Redis、Memcached、Tair**

````
spring:
  # 存在多个缓存时需要指定缓存类型
  cache:
    type: redis
````

### 自定义KeyGenerator

特定的情况下SpringBoot自带的SimpleKeyGenerator和key生成器不能满足需求

````java

@Bean
public KeyGenerator customKeyGenerator() {
    return (target, method, params) -> {
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        } else {
            if (params.length == 1) {
                Object param = params[0];
                if (param != null && !param.getClass().isArray()) {
                    return param;
                }
            }
            return new SimpleKey(params);
        }
    };
}
````

### 过期时间

在SpringData使用Redis作为缓存方案时，默认情况下是永不过期的。

- 通过spring.cache.redis.time-to-live配置项设置过期时间，它是全局的统一的，无法满足不同的缓存使用不同的过期时间。

- @Cacheable注解不支持配置过期时间，可以通过配置CacheManager来配置默认的过期时间和针对每个类或者是方法进行缓存失效时间配置。

````
// 过期时间配置1分钟，@Cacheable(value = "UserCacheData", keyGenerator = "wiselyKeyGenerator")
redisCacheConfigurationMap.put("UserCacheData", this.getRedisCacheConfigurationWithTtl(60));
````

- 设置单个key的缓存时间

SpringCache对redis进行处理，用的DefaultRedisCacheWriter

实现RedisCacheWriter接口，覆盖put和get方法，设置和获取时添加缓存的过期时间

### 相关注解

````
@Cacheable      // 用于标记一个类或方法，当被标记对象被访问，会先去缓存中查询相应的结果，如果存在则返回，不存在则指向方法
@CacheEvict     // 用于从缓存中移除数据。当使用@CacheEvict注解标记一个方法时，该方法执行后会触发缓存的清除操作
@CachePut       // 用于将方法的返回值存储到缓存中。与@Cacheable注解不同的是，@CachePut注解每次都会触发方法的执行，并将结果存储到缓存中
@Cache          // 作用在方法上，综合上面的各种操作，在有些场景下 ，调用业务会触发多种缓存操作。
@CacheConfig    // 常用于类级别的注解，用于统一配置类缓存的公共属性。
@Caching        // 可以在一个类或方法同时使用多个缓存相关注解，灵活配置缓存策略
@EnableCaching  // 开启缓存支持

````

**@Cacheable**

注解用于标记一个方法或类支持缓存。当该方法被调用时，Spring会先检查缓存中是否存在相应的结果，如果存在，则直接返回缓存中的结果，而不执行方法体。

如果缓存中不存在结果，则执行方法体，并将结果存入缓存中供下次使用

````java

@Cacheable(cacheNames = "users", key = "'UserInfo'+#id", sync = true)
public User getById(Long id) {
    return userMapper.selectById(id);
}
````

属性：

````
value：指定缓存的名称或缓存管理器的名称。可以使用cacheNames作为value的别名。如果指定了多个名称，将会使用多个缓存进行缓存操作。

key：指定缓存的键。默认情况下，Spring会根据方法的参数生成缓存键。可以使用SpEL表达式来自定义缓存键的生成方式。例如，key = "#param"表示使用方法的参数param作为缓存键。

condition：指定一个SpEL表达式，用于判断是否执行缓存操作。只有当表达式的结果为true时，才会执行缓存操作。

unless：指定一个SpEL表达式，用于判断是否不执行缓存操作。只有当表达式的结果为false时，才会执行缓存操作。与condition相反。

sync：指定是否使用同步模式进行缓存操作。默认值为false，表示使用异步模式。在异步模式下，如果多个线程同时访问同一个缓存项，可能会导致缓存穿透的问题。可以将sync设置为true来避免这个问题。

cacheManager：指定使用的缓存管理器的名称。可以通过该属性指定使用不同的缓存管理器。

cacheResolver：指定使用的缓存解析器的名称。可以通过该属性指定使用不同的缓存解析器。

keyGenerator：指定使用的缓存键生成器的名称。可以通过该属性指定使用不同的缓存键生成器。
````

**@CacheEvict**

@CacheEvict是Spring框架中的一个注解，用于从缓存中移除数据。当使用@CacheEvict注解标记一个方法时，该方法执行后会触发缓存的清除操作

@CacheEvict(cacheNames = "users", allEntries = true)

属性：

````

value：指定要清除的缓存名称或缓存管理器的名称。可以使用cacheNames作为value的别名。如果指定了多个名称，将会清除多个缓存。

key：指定要清除的缓存键。默认情况下，Spring会根据方法的参数生成缓存键。可以使用SpEL表达式来自定义缓存键的生成方式。例如，key = "#param"表示使用方法的参数param作为缓存键。

condition：指定一个SpEL表达式，用于判断是否执行缓存清除操作。只有当表达式的结果为true时，才会执行缓存清除操作。

allEntries：指定是否清除所有缓存项。默认值为false，表示只清除指定缓存键对应的缓存项。如果将allEntries设置为true，则会清除指定缓存名称或缓存管理器下的所有缓存项。

beforeInvocation：指定是否在方法执行之前清除缓存。默认值为false，表示在方法执行之后清除缓存。如果将beforeInvocation设置为true，则会在方法执行之前清除缓存，即使方法执行出现异常。
````

**@CachePut**

@CachePut是Spring框架中的一个注解，用于将方法的返回值存储到缓存中。与@Cacheable注解不同的是，@CachePut注解每次都会触发方法的执行，并将结果存储到缓存中。

@CachePut(cacheNames = "users", key = "'UserList'+#p0.id")

**@Caching**

@Caching是Spring框架中的一个注解，它允许我们在一个方法或类上同时指定多个Spring缓存相关的注解。通过使用@Caching注解，我们可以灵活地定义缓存策略。

````
@Caching(
    cacheable = {
        @Cacheable(value = "users", key = "#id")
    },
    put = {
        @CachePut(value = "users", key = "#p.id")
    },
    evict = {
        @CacheEvict(value = "userList", allEntries = true)
    }
)
public User getById(Long id) {
    return userMapper.selectById(id);
}
````
