package com.snow.gotit.product.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.snow.gotit.base.utils.toKRW
import com.snow.gotit.product.dto.ProductFinderDto
import java.math.BigDecimal

data class MinPriceProductByCategoryResponse(
    @JsonProperty("상품")
    val productList: List<ProductFinderDto>,
    private val _totalPrice: BigDecimal
) {
    @get:JsonProperty("총액")
    val totalPrice: BigDecimal get() = _totalPrice.toKRW()
}