package com.snow.gotit.product.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.snow.gotit.base.utils.toKRW
import java.math.BigDecimal

data class MinPriceProductByBrandResponse(
    @JsonProperty("최저가")
    val lowestPrice: MinPriceResponse
){
    data class MinPriceResponse(
        @JsonProperty("브랜드")
        val brandName: String,

        @JsonProperty("카테고리")
        val categoryList: List<CategoryResponse>,

        private val _totalPrice: BigDecimal
    ){
        @get:JsonProperty("총액")
        val totalPrice: BigDecimal get() = _totalPrice.toKRW()
    }

    data class CategoryResponse(
        @JsonProperty("카테고리")
        val categoryName: String,

        private val _price: BigDecimal
    ){
        @get:JsonProperty("가격")
        val price: BigDecimal get() = _price.toKRW()
    }
}
