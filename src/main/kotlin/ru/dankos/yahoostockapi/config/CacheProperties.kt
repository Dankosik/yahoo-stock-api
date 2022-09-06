package ru.dankos.yahoostockapi.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "caching")
class CacheProperties(
    val cacheNames: Map<String, CacheSpec>
)

class CacheSpec(
    val ttl: Long,
)