package com.snow.gotit.product.dto

import com.snow.gotit.base.utils.toKRW
import java.math.BigDecimal

data class ProductDto(
    val productId: Long?,
    private val _price: BigDecimal,
    val categoryName: String,
    val brandName: String,
) {
    val price: BigDecimal get() = _price.toKRW()
}