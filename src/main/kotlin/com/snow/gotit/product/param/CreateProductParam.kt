package com.snow.gotit.product.param

import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateProductParam(
    val brandId: Long,
    val categoryId: Long,
    val price: BigDecimal,
){

}
