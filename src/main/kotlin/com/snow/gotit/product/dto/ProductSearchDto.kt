package com.snow.gotit.product.dto

import com.snow.gotit.base.utils.toKRW
import java.math.BigDecimal

data class ProductSearchDto(
    val productId: Long?,
    private val _price: BigDecimal,
    val categoryId: Long?,
    val categoryName: String,
    val brandId: Long?,
    val brandName: String,
) {
    val price: BigDecimal get() = _price.toKRW()
}