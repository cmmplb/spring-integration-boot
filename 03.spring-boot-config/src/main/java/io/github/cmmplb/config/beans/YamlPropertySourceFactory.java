package io.github.cmmplb.config.beans;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * YAML配置文件加载工厂
 * @author penglibo
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @NonNull
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        try {
            return new YamlPropertySourceLoader()
                    .load(resource.getResource().getFilename(), resource.getResource())
                    .get(0);
        } catch (IllegalStateException e) {
            // 如果YAML配置文件不存在，希望能忽略该文件，而不是引发异常导致Spring容器启动失败
            // 需要往外抛FileNotFoundException，Spring捕捉到后会忽略该异常（当 ignoreResourceNotFound = true 时）
            if (e.getCause() instanceof FileNotFoundException) {
                throw (FileNotFoundException) e.getCause();
            } else {
                throw e;
            }
        }
    }
}