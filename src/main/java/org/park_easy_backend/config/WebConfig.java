package org.park_easy_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Value("${ENV}")
    private String ENV;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (ENV != null && ENV.equals("dev")) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET","POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                    .allowCredentials(true)
            ;
        } else {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "*")
                    .allowedMethods("GET","POST", "PUT", "DELETE", "OPTIONS", "HEAD")
            ;
        }
    }
}
