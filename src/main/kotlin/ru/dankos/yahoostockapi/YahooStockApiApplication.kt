package ru.dankos.yahoostockapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import reactivefeign.spring.config.EnableReactiveFeignClients
import ru.dankos.yahoostockapi.config.CaffeineProperties

@SpringBootApplication
@EnableReactiveFeignClients
@EnableConfigurationProperties(
    CaffeineProperties::class
)
class YahooStockApiApplication

fun main(args: Array<String>) {
    runApplication<YahooStockApiApplication>(*args)
}
