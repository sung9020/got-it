package com.snow.gotit.product.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.ProductFinderService
import com.snow.gotit.product.ProductManagerService
import com.snow.gotit.product.dto.ProductDto
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.repository.ProductRepository
import com.snow.gotit.product.response.MinPriceProductByCategoryResponse
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal


@WebMvcTest(value = [ProductController::class])
class ProductControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
){
    @MockitoBean
    private lateinit var productManagerService: ProductManagerService

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
        mockMvc.perform(post("/v1/products")
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
        mockMvc.perform(post("/v1/products")
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
        mockMvc.perform(post("/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param))
            .andExpect(status().is2xxSuccessful)
    }
}