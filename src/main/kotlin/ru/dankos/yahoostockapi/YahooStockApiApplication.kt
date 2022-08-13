package ru.dankos.yahoostockapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import reactivefeign.spring.config.EnableReactiveFeignClients
import ru.dankos.yahoostockapi.config.CaffeineProperties

@EnableCaching
@SpringBootApplication
@EnableReactiveFeignClients
@EnableConfigurationProperties(
    CaffeineProperties::class
)
class YahooStockApiApplication

fun main(args: Array<String>) {
    runApplication<YahooStockApiApplication>(*args)
}
