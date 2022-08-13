package ru.dankos.yahoostockapi.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.kotlin.coroutine.EnableCoroutine

@Configuration
@EnableCaching
@EnableCoroutine
class CacheConfiguration