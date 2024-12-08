package com.snow.gotit.brand.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.BrandManagerService
import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.param.CreateBrandParam
import com.snow.gotit.brand.param.ModifyBrandParam
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.dto.CategoryDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("brands/w")
class BrandWebController(
    private val brandRepository: BrandRepository,
) {
    @GetMapping("manager")
    fun getManager(model: Model): String {
        val brandDtoList = brandRepository.findAll().map { brand ->
            BrandDto(
                brandId = brand.id,
                brandName = brand.name
            )
        }

        model.addAttribute("brandList", brandDtoList)
        return "brand_manager"
    }
}