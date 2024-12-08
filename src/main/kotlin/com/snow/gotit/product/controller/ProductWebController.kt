package com.snow.gotit.product.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.dto.CategoryDto
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.ProductManagerService
import com.snow.gotit.product.dto.ProductDto
import com.snow.gotit.product.dto.ProductSearchDto
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import com.snow.gotit.product.param.SearchProductParam
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("products/w")
class ProductWebController(
    private val categoryRepository: CategoryRepository,
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
        val categoryDtoList = categoryRepository.findAll().map { category ->
            CategoryDto(
                categoryId = category.id,
                categoryName = category.name
            )
        }

        model.addAttribute("brandList", brandDtoList)
        model.addAttribute("categoryList", categoryDtoList)
        return "product_manager"
    }
}