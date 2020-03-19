package com.zy.smps.servicefallback;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Component
@Slf4j
public class MessServiceFallBack implements FallbackProvider {
    // 指定要处理的 service。
    @Override
    public String getRoute() {
        return "message-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        String reason="";
        if (cause != null && cause.getCause() != null) {
            reason = cause.getCause().getMessage();
            log.info("message-service exception： ", reason);
        }
        return ServiceFallBackResponse.fallbackResponse(reason);
    }
}
