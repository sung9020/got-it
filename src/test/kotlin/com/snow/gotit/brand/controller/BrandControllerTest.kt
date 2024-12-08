package com.snow.gotit.brand.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.BrandManagerService
import com.snow.gotit.product.dto.ProductDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(value = [BrandController::class])
class BrandControllerTest {
    @Test
    fun createProduct_파라미터_valid(){
        //given
        val param = """
        {
            "categoryId": 2,
            "price": 10000
        }
    """.trimIndent()
        //when
        `when`(productManagerService.createProduct(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(ProductDto(null, BigDecimal.ZERO, "","")))

        //then
        mockMvc.perform(
            post("/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun createProduct_파라미터_valid_2(){
        //given
        val param = """
        {
            "brandId": 1,
            "price": 10000
        }
    """.trimIndent()
        //when
        `when`(productManagerService.createProduct(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(ProductDto(null, BigDecimal.ZERO, "","")))

        //then
        mockMvc.perform(
            post("/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun createProduct_성공(){
        //given
        val param = """
        {
            "brandId": 1,
            "categoryId": 2,
            "price": 10000
        }
    """.trimIndent()
        //when
        `when`(productManagerService.createProduct(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(ProductDto(null, BigDecimal.ZERO, "","")))

        //then
        mockMvc.perform(
            post("/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun modifyProduct_파라미터_valid(){
        //given
        val param = """
        {
            "brandId": 1,
            "price": 10000
        }
    """.trimIndent()
        //when
        `when`(productManagerService.createProduct(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(ProductDto(null, BigDecimal.ZERO, "","")))

        //then
        mockMvc.perform(
            post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
            .andExpect(status().isBadRequest)
    }
}