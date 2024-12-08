package com.snow.gotit.product.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.product.ProductManagerService
import com.snow.gotit.product.dto.ProductDto
import com.snow.gotit.product.dto.ProductSearchDto
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import com.snow.gotit.product.param.SearchProductParam
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productManagerService: ProductManagerService
) {
    @GetMapping("search")
    fun searchProduct(@RequestParam brandName: String?,
                      @RequestParam categoryName: String?
    ): ResponseEntity<ResultResponse<List<ProductSearchDto>>> {
        return ResponseEntity.ok(productManagerService.searchProduct(brandName, categoryName))
    }

    @PostMapping
    fun createProduct(@Valid @RequestBody param: CreateProductParam): ResponseEntity<ResultResponse<ProductDto>> {
        return ResponseEntity.ok(productManagerService.createProduct(param.toValid()))
    }

    @PutMapping("/{productId}")
    fun modifyProduct(
        @PathVariable productId: Long,
        @RequestBody param: ModifyProductParam
    ): ResponseEntity<ResultResponse<ProductDto>> {
        return ResponseEntity.ok(productManagerService.modifyProduct(productId, param))
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<ResultResponse<String>> {
        productManagerService.deleteProduct(productId)
        return ResponseEntity.noContent().build()
    }
}