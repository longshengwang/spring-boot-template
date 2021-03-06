package com.github.shepherdviolet.webdemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 入口
 *
 * @author Sviolet
 */
@SpringBootApplication(
        //这个方法只能排除spring.factories里声明的自动配置类, 对@Import导入或者@Enable注解启用的无效!
//        exclude = {
//                DataSourceAutoConfiguration.class//排除数据库配置(可选)
//        }
)
//扫包路径, 等同于XML中的<context:component-scan base-package="com.aaa.**.xxx;com.bbb.**.xxx"/>
@ComponentScan({
        "com.github.shepherdviolet.webdemo.basic.config",
        "com.github.shepherdviolet.webdemo.demo.common.config",
        "com.github.shepherdviolet.webdemo.demo.validate.config",
        "com.github.shepherdviolet.webdemo.demo.fileupload.config",
        "com.github.shepherdviolet.webdemo.demo.properties.config",
        "com.github.shepherdviolet.webdemo.demo.xmlconfig.config",
        "com.github.shepherdviolet.webdemo.demo.wechatpush.config",
        "com.github.shepherdviolet.webdemo.demo.annoproxy.config",
        "com.github.shepherdviolet.webdemo.demo.aspectj.config",
        "com.github.shepherdviolet.webdemo.demo.mockito.config",
        "com.github.shepherdviolet.webdemo.demo.mybatis.config",
        "com.github.shepherdviolet.webdemo.demo.micrometer.config",
        "com.github.shepherdviolet.webdemo.demo.schedule.config",
//        "com.github.shepherdviolet.webdemo.demo.sentinel.config",
//        "com.github.shepherdviolet.webdemo.demo.apollo.config",
//        "com.github.shepherdviolet.webdemo.demo.rocketmq.config",
})
//Spring Boot Admin server (控制台服务端, 容器需改为Tomcat, 控制台地址: http://localhost:8080/admin, 改过URL(默认没/admin), 见application.yaml)
@EnableAdminServer
public class BootApplication {

//    private static volatile boolean shutdown = false;
//    private static final Logger logger = LoggerFactory.getLogger(BootApplication.class);


    public static void main(String[] args) {

        //Sentinel启动参数
        sentinelSettings();

        //WEB项目启动
        SpringApplication.run(BootApplication.class, args);

        //非WEB项目启动
        //Spring会根据Classpath下是否存在Servlet等类来判断是否WEB模式, 判断为WEB模式时, 如果未依赖spring-boot-starter-web, 会直接结束进程(不会进入后面的自旋循环)
        //所以强制设置为非WEB模式, 然后再用自旋循环可以解决启动后立刻结束的问题
//        Thread thread = Thread.currentThread();
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                shutdown = true;
//                thread.interrupt();
//            }
//        }));
//        try {
//            new SpringApplicationBuilder(ProviderBoot.class)
//                    .web(WebApplicationType.NONE)
//                    .run(args);
//        } catch (Throwable t) {
//            logger.error("Error while application starting", t);
//            return;
//        }
//        while (!shutdown) {
//            try {
//                Thread.sleep(60000L);
//            } catch (InterruptedException ignored) {
//            }
//        }
    }

//    /**
//     * 配置Slate监听器(可选)
//     * 依赖了com.github.shepherdviolet:slate-springboot后, 无需手动配置
//     */
//    @Bean
//    public ServletContextListener slateServletContextListener() {
//        return new SlateServletContextListener();
//    }

    /**
     * Tomcat调优
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            factory.addConnectorCustomizers(connector -> {
                connector.setAttribute("acceptorThreadCount", "2");
                connector.setAttribute("connectionTimeout", "30000");
                connector.setAttribute("asyncTimeout", "30000");
                connector.setAttribute("enableLookups", "false");
                connector.setAttribute("compression", "on");
                connector.setAttribute("compressionMinSize", "2048");
                connector.setAttribute("redirectPort", "8443");
            });
        };
    }

    /**
     * Undertow调优
     */
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableUndertowWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> {
//            factory.addDeploymentInfoCustomizers(deploymentInfo -> {
//                //禁用HTTP TRACE, 测试:curl -v -X TRACE http://localhost:8080/
//                deploymentInfo.addSecurityConstraint(new SecurityConstraint()
//                        .addWebResourceCollection(
//                                new WebResourceCollection()
//                                        .addUrlPattern("/*")
//                                        .addHttpMethod(HttpMethod.TRACE.toString())
//                        )
//                );
//            });
//        };
//    }

    private static void sentinelSettings(){
        //将RT最大值从4.9秒改成120秒
        System.setProperty("csp.sentinel.statistic.max.rt", "120000");
    }

}
