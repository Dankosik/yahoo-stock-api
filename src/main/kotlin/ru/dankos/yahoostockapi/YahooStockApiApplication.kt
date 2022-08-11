package ru.dankos.yahoostockapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import reactivefeign.spring.config.EnableReactiveFeignClients

@EnableCaching
@SpringBootApplication
@EnableReactiveFeignClients
class YahooStockApiApplication

fun main(args: Array<String>) {
	runApplication<YahooStockApiApplication>(*args)
}
