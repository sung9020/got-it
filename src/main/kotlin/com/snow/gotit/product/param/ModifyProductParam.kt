package com.snow.gotit.product.param

import java.math.BigDecimal

data class ModifyProductParam(
    val brandId: Long? = null,
    val categoryId: Long? = null,
    val price: BigDecimal?,
) {

}