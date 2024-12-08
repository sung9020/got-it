package com.snow.gotit.product.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.snow.gotit.base.utils.toKRW
import java.math.BigDecimal

data class ProductFinderDto(
    @JsonIgnore
    var productId: Long?,
    private val _price: BigDecimal,
    @JsonProperty("카테고리")
    var categoryName: String,
    @JsonProperty("브랜드")
    var brandName: String,
){
    @get:JsonProperty("가격")
    val price: BigDecimal get() = _price.toKRW()
}
