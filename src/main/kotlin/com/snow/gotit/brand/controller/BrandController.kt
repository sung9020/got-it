package com.snow.gotit.brand.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.BrandManagerService
import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.param.CreateBrandParam
import com.snow.gotit.brand.param.ModifyBrandParam
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
    fun createBrand(@RequestBody param: CreateBrandParam): ResponseEntity<ResultResponse<BrandDto>> {
        return ResponseEntity.ok(brandManagerService.createBrand(param.brandName))
    }

    @PutMapping("/{brandId}")
    fun modifyBrand(
        @PathVariable brandId: Long,
        @RequestBody param: ModifyBrandParam
    ): ResponseEntity<ResultResponse<BrandDto>> {
        return ResponseEntity.ok(brandManagerService.modifyBrand(brandId, param.brandName))
    }

    @DeleteMapping("/{brandId}")
    fun deleteBrand(@PathVariable brandId: Long): ResponseEntity<ResultResponse<String>> {
        brandManagerService.deleteBrand(brandId)
        return ResponseEntity.noContent().build<ResultResponse<String>>()
    }
}