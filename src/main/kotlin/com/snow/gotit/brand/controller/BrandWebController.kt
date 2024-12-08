package com.snow.gotit.brand.controller

import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.repository.BrandRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

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