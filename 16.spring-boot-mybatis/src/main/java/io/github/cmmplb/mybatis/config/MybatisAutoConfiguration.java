package io.github.cmmplb.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-04-10 16:51:11
 */

@Configuration
@MapperScan(basePackages = {"io.github.cmmplb.mybatis.dao"})
public class MybatisAutoConfiguration {

//    /**
//     * 把插件拦截器配置成一个bean就可以了, mybatis-starter会自动加载的. 
//     * @return SQLQueryParamInterceptor
//     */
//    @Bean
//    public SQLQueryParamInterceptor sqlQueryParamInterceptor() {
//        return new SQLQueryParamInterceptor();
//    }
//
//    /**
//     * 更新时注入参数插件
//     * @return SQLUpdateParamInterceptor
//     */
//    @Bean
//    public SQLUpdateParamInterceptor sqlUpdateParamInterceptor() {
//        return new SQLUpdateParamInterceptor();
//    }
//
//    /**
//     * 乐观锁插件
//     * @return SQLVersionLockerInterceptor
//     */
//    @Bean
//    public SQLVersionLockerInterceptor sqlVersionLockerInterceptor() {
//        return new SQLVersionLockerInterceptor();
//    }
//
//    /**
//     * 配置-Configuration
//     * @return
//     */
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                // 开启驼峰配置
//                configuration.setMapUnderscoreToCamelCase(true);
//                // 日志打印
//                configuration.setLogImpl(StdOutImpl.class);
//            }
//        };
//    }
//
//    @Autowired
//    private DataSource dataSource;
//
//    /**
//     * 配置SqlSessionFactory和Configuration-会替换上面的
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new SQLQueryParamInterceptor(), new SQLUpdateParamInterceptor()});
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("io.github.cmmplb.mybatis.entity");
//        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        // 开启驼峰配置
//        configuration.setMapUnderscoreToCamelCase(true);
//        // 日志打印
//        configuration.setLogImpl(StdOutImpl.class);
//        sqlSessionFactoryBean.setConfiguration(configuration);
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    public Resource[] resolveMapperLocations() {
//        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
//        List<String> mapperLocations = new ArrayList<>();
//        mapperLocations.add("classpath:mapper/*.xml");
//        mapperLocations.add("classpath:mapper/*/*.xml");
//        List<Resource> resources = new ArrayList<>();
//        for (String mapperLocation : mapperLocations) {
//            try {
//                Resource[] mappers = resourceResolver.getResources(mapperLocation);
//                resources.addAll(Arrays.asList(mappers));
//            } catch (IOException e) {
//                // ignore
//            }
//        }
//        return resources.toArray(new Resource[0]);
//    }
}