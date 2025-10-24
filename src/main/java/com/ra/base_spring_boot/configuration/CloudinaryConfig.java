package com.ra.base_spring_boot.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dcg5wftdq");
        config.put("api_key", "375331864736173");
        config.put("api_secret", "UxY_F9vkYF3tFYsgAlazBqqGrWc");
        config.put("secure", "true");
        return new Cloudinary(config);
    }

}
