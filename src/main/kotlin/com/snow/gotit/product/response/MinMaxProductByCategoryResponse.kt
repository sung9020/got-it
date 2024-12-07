package com.snow.gotit.product.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.snow.gotit.base.utils.toKRW
import java.math.BigDecimal

data class MinMaxProductByCategoryResponse(
    @JsonProperty("카테고리")
    val categoryName: String,
    @JsonProperty("최저가")
    val minProductList: List<MinMaxProductResponse>,
    @JsonProperty("최고가")
    val maxProductList: List<MinMaxProductResponse>
){
    data class MinMaxProductResponse(
        @JsonProperty("브랜드")
        val brandName: String,
        private val _price: BigDecimal
    ){
        @get:JsonProperty("가격")
        val price: BigDecimal get() = _price.toKRW()
    }
}
