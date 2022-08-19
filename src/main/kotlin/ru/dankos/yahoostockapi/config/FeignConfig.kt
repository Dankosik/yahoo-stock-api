package ru.dankos.yahoostockapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import reactivefeign.webclient.WebReactiveOptions


@Configuration
@EnableWebFlux
class FeignConfig {

    @Bean
    fun webReactiveOptions(): WebReactiveOptions? =
        WebReactiveOptions.Builder()
            .setMaxConnections(1000)
            .setPendingAcquireMaxCount(5000)
            .build()
}