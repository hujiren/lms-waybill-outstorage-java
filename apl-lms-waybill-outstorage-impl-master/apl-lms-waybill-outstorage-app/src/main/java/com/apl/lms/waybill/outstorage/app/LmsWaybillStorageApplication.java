package com.apl.lms.waybill.outstorage.app;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hjr start
 * @Classname LmsWaybillStorageApplication
 * @Date 2020/12/21 9:48
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.apl.lib", //APL基本工具类
                "com.apl.tenant", //多租户
                "com.apl.db.adb", // adb数据库操作助手
                "com.apl.cache", // redis代理
                "com.apl.shardingjdbc",
                "com.apl.lms.net",
                "com.apl.sys.lib",
                "com.apl.lms.waybill.outstorage",
                "com.apl.lms.common.lib",
                "com.apl.lms.price.exp.lib"},
        exclude= {DataSourceAutoConfiguration.class})
@EnableSwagger2
@EnableFeignClients(basePackages = {"com.apl.lms.common.lib.feign","com.apl.lms.price.exp.lib",
        "com.apl.sys.lib.feign"})
@EnableDiscoveryClient//可以被注册中心发现
@MapperScan(basePackages = "com.apl.lms.waybill.outstorage.mapper", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc")
public class LmsWaybillStorageApplication {

    public static void main(String[] args) {

        SpringApplication.run(LmsWaybillStorageApplication.class, args);
    }
}
