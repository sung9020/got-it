package com.snow.gotit.product.param

import java.math.BigDecimal

data class ModifyProductParam(
    val brandId: Long?,
    val categoryId: Long?,
    val price: BigDecimal?,
) {

}