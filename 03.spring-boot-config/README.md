## 宽松绑定/松散绑定

在ServerConfig中的ipAddress属性名

```JAVA

@Component
@Data
@ConfigurationProperties(prefix = "servers")
public class ServerConfig {
    private String ipAddress;
}
```

可以与下面的配置属性名规则全兼容

```YML
servers:
  ipAddress: 192.168.0.2       # 驼峰模式
  ip_address: 192.168.0.2      # 下划线模式
  ip-address: 192.168.0.2      # 烤肉串模式
  IP_ADDRESS: 192.168.0.2      # 常量模式
```

以上规则仅针对springboot中@ConfigurationProperties注解进行属性绑定时有效, 对@Value注解进行属性映射无效。

````yaml
0127八进制的格式, 最终以十进制数字87
````

yaml文件中对于数字的定义支持进制书写格式, 如需使用字符串请使用引号明确标注