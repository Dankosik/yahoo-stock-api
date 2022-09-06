package ru.dankos.yahoostockapi.config

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import java.time.Duration

@Configuration
class RedisConfig(
    private val cacheProperties: CacheProperties
) {

    @Bean
    fun redisCacheManagerBuilderCustomizer() = RedisCacheManagerBuilderCustomizer { builder ->
        val configurationMap = mutableMapOf<String, RedisCacheConfiguration>()
        cacheProperties.cacheNames.entries.map { (key, value): Map.Entry<String, CacheSpec> ->
            configurationMap[key] =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(value.ttl))
        }.toList()
        builder.withInitialCacheConfigurations(configurationMap)
    }
}