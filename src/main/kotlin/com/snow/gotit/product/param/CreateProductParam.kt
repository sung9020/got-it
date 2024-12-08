package com.snow.gotit.product.param

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateProductParam(
    @field:NotNull(message = "브랜드 ID는 필수입니다")
    val brandId: Long? = null,
    @field:NotNull(message = "카테고리 ID는 필수입니다")
    val categoryId: Long? = null,
    @field:NotNull(message = "가격 정보는 필수입니다")
    val price: BigDecimal? = null,
){
    fun toValid(): ValidCreateProductParam {
        return ValidCreateProductParam(
            brandId = brandId ?: 0L,
            categoryId = categoryId ?: 0L,
            price = price ?: BigDecimal.ZERO
        )
    }

    data class ValidCreateProductParam(
        val brandId: Long,
        val categoryId: Long,
        val price: BigDecimal
    )
}
