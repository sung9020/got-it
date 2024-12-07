package com.snow.gotit.base.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class GotItException(
    val status: HttpStatus,
    override val message: String? = null, // 실패사유
    val data: Map<String, Any>? = null, // 실패한 값 모니터링 지원
): RuntimeException()
