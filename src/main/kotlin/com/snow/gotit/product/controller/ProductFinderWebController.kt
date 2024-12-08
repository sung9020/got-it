package com.snow.gotit.product.controller

import com.snow.gotit.category.dto.CategoryDto
import com.snow.gotit.category.repository.CategoryRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("finder/w")
class ProductFinderWebController(
    val categoryRepository: CategoryRepository,
) {
    @GetMapping("/categories/products/min-price")
    fun getMinPriceProductsByCategory(model: Model): String {

        return "min_price_products_by_category"
    }

    @GetMapping("/brands/products/min-price")
    fun getMinPriceProductByBrand(model: Model): String  {
        return "min_price_product_by_brand"
    }

    @GetMapping("/categories/products/min-max-price")
    fun getMinMaxProductByCategory(model: Model): String{
        val categoryDtoList = categoryRepository.findAll().map { category ->
            CategoryDto(
                categoryId = category.id,
                categoryName = category.name
            )
        }
        model.addAttribute("categoryList", categoryDtoList)

        return "min_max_product_by_category"
    }
}
