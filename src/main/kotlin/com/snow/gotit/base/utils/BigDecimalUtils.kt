package com.snow.gotit.base.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.halfEvenRound(scale: Int = 2): BigDecimal {
    return this.setScale(scale, RoundingMode.HALF_EVEN)
}

fun BigDecimal.toKRW(): BigDecimal {
    return this.halfEvenRound(0)
}