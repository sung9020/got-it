package com.snow.gotit.brand.controller

import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.BrandManagerService
import com.snow.gotit.brand.dto.BrandDto
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = [BrandController::class])
class BrandControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
) {
    @MockitoBean
    private lateinit var brandManagerService: BrandManagerService

    @Test
    fun createBrand_파라미터_valid(){
        //given
        val param = """
        {}
    """.trimIndent()
        //when
        `when`(brandManagerService.createBrand(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(BrandDto(null, "")))

        //then
        mockMvc.perform(
            post("/v1/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(param))
            .andExpect(status().isBadRequest)
    }


    @Test
    fun createBrand_성공(){
        //given
        val param = """
        {
            "brandName": "CC"
        }
    """.trimIndent()
        //when
        `when`(brandManagerService.createBrand(org.mockito.kotlin.any()))
            .thenReturn(ResultResponse.Success(BrandDto(null, "")))

        //then
        mockMvc.perform(
            post("/v1/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
            .andExpect(status().isOk)
    }
}