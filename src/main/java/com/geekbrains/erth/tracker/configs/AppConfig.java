package com.geekbrains.erth.tracker.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:liquibase.properties")
@ComponentScan("com.geekbrains.erth.tracker")
public class AppConfig implements WebMvcConfigurer {
}
