package com.snow.gotit.brand.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.BrandManagerService
import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.param.CreateBrandParam
import com.snow.gotit.brand.param.ModifyBrandParam
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/brands")
class BrandController(
    private val brandManagerService: BrandManagerService
) {
    @PostMapping
    fun createBrand(@Valid @RequestBody param: CreateBrandParam): ResponseEntity<ResultResponse<BrandDto>> {
        val brandName = param.brandName as String
        return ResponseEntity.ok(brandManagerService.createBrand(brandName))
    }

    @PutMapping("/{brandId}")
    fun modifyBrand(
        @PathVariable brandId: Long,
        @Valid @RequestBody param: ModifyBrandParam
    ): ResponseEntity<ResultResponse<BrandDto>> {
        val brandName = param.brandName as String
        return ResponseEntity.ok(brandManagerService.modifyBrand(brandId, brandName))
    }

    @DeleteMapping("/{brandId}")
    fun deleteBrand(@PathVariable brandId: Long): ResponseEntity<ResultResponse<String>> {
        brandManagerService.deleteBrand(brandId)
        return ResponseEntity.noContent().build()
    }
}