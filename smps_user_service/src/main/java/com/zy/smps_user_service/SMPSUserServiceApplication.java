package com.zy.smps_user_service;

import com.zy.smps_user_service.convert.DateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class SMPSUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMPSUserServiceApplication.class, args);
    }

//    @PostConstruct
//    @Autowired  //注册自定义的日期转换器
    public void initEditableValidated(RequestMappingHandlerAdapter handlerAdapter){
        ConfigurableWebBindingInitializer initializer=(ConfigurableWebBindingInitializer)handlerAdapter
                .getWebBindingInitializer();
        ConversionService conversionService = initializer.getConversionService();
        if (conversionService!=null){
            ((GenericConversionService)conversionService).addConverter(new DateConvert());
        }
    }

    @Bean
    public ConversionServiceFactoryBean conversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean=new ConversionServiceFactoryBean();
        Set<Converter> converters= new HashSet<>();
        converters.add(new DateConvert());//日期转换器
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
