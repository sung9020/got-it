package com.snow.gotit.brand.param

import jakarta.validation.constraints.NotBlank

data class CreateBrandParam(
    @field:NotBlank(message = "브랜드 이름은 필수입니다")
    val brandName: String? = null,
) {
}