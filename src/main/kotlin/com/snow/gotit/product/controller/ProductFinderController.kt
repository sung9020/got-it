package com.snow.gotit.product.controller

import com.snow.gotit.product.ProductFinderService
import com.snow.gotit.product.response.MinMaxProductByCategoryResponse
import com.snow.gotit.product.response.MinPriceProductByBrandResponse
import com.snow.gotit.product.response.MinPriceProductByCategoryResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/finder")
class ProductFinderController(
    val productFinderService: ProductFinderService,
) {
    @GetMapping("/categories/products/min-price")
    fun getMinPriceProductsByCategory(): ResponseEntity<MinPriceProductByCategoryResponse>{
        return ResponseEntity.ok(productFinderService.getMinPriceProductsByCategory())
    }

    @GetMapping("/brands/products/min-price")
    fun getMinPriceProductByBrand(): ResponseEntity<MinPriceProductByBrandResponse> {
        return ResponseEntity.ok(productFinderService.getMinPriceProductByBrand())
    }

    @GetMapping("/categories/products/min-max-price")
    fun getMinMaxProductByCategory(@RequestParam categoryName: String): ResponseEntity<MinMaxProductByCategoryResponse> {
        val response = runBlocking {
            productFinderService.getMinMaxProductByCategory(categoryName)
        }
        return ResponseEntity.ok(response)
    }
}
