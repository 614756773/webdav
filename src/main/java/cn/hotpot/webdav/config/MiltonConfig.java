package cn.hotpot.webdav.config;

import io.milton.config.HttpManagerBuilder;
import io.milton.http.ResourceFactory;
import io.milton.http.fs.FileSystemResourceFactory;
import io.milton.http.fs.NullSecurityManager;
import io.milton.http.http11.DefaultHttp11ResponseHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;


@Configuration
@EnableConfigurationProperties(MiltonProperties.class)
public class MiltonConfig {
    private MiltonProperties miltonProperties;

    public MiltonConfig(MiltonProperties miltonProperties) {
        this.miltonProperties = miltonProperties;
    }

    @Bean
    FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(springMiltonFilterBean());
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    SpringMiltonFilterBean springMiltonFilterBean() {
        return new SpringMiltonFilterBean();
    }

    @Bean
    @ConditionalOnProperty("milton.filesystem_root")
    ResourceFactory resourceFactory() {
        FileSystemResourceFactory factory = new FileSystemResourceFactory();
        factory.setAllowDirectoryBrowsing(true);
        factory.setRoot(new File(miltonProperties.getFilesystemRoot()));
        factory.setSecurityManager(new NullSecurityManager());
        ensureDataDirectory(miltonProperties.getFilesystemRoot());
        return factory;
    }

    @Bean
    HttpManagerBuilder httpManagerBuilder() {
        HttpManagerBuilder builder = new HttpManagerBuilder();
        builder.setResourceFactory(resourceFactory());
        builder.setBuffering(DefaultHttp11ResponseHandler.BUFFERING.whenNeeded);
        builder.setEnableCompression(false);
        return builder;
    }

    /**
     * 确保存放数据的目录存在
     */
    private static void ensureDataDirectory(String path) {
        File file = new File(path);
        if (file.exists()) {
            return;
        }
        boolean b = file.mkdir();
        if (!b) {
            throw new IllegalStateException("create directory fail");
        }
    }
}
