package com.davide.demo2;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelLoader loadModels() {
        return new ModelLoader();
    }
}
