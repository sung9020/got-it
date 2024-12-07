package com.snow.gotit.product.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.product.ProductManagerService
import com.snow.gotit.product.dto.ProductDto
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productManagerService: ProductManagerService
) {
    @PostMapping
    fun createProduct(@RequestBody param: CreateProductParam): ResponseEntity<ResultResponse<ProductDto>> {
        return ResponseEntity.ok(productManagerService.createProduct(param))
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
        return ResponseEntity.noContent().build<ResultResponse<String>>()
    }
}