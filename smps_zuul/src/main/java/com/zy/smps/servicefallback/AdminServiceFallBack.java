package com.zy.smps.servicefallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AdminServiceFallBack implements FallbackProvider {
    // 指定要处理的 service。
    @Override
    public String getRoute() {
        return "admin-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        String reason="";
        if (cause != null && cause.getCause() != null) {
            reason = cause.getCause().getMessage();
            log.info("view-service exception： ", reason);
        }
        return ServiceFallBackResponse.fallbackResponse(reason);
    }
}
