package com.snow.gotit.product.param

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ModifyProductParam(
    val brandId: Long? = null,
    val categoryId: Long? = null,
    val price: BigDecimal?,
) {

}