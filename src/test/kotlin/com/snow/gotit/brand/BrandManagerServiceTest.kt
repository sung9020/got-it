package com.snow.gotit.brand

import com.snow.gotit.base.exception.GotItException
import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.base.utils.DatabaseCleaner
import com.snow.gotit.brand.entity.Brand
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.entity.Category
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.ProductManagerService
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import com.snow.gotit.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
class BrandManagerServiceTest(
    @Autowired
    val brandRepository: BrandRepository,
    @Autowired
    val databaseCleaner: DatabaseCleaner,
    @Autowired
    val brandManagerService: BrandManagerService,
    ) {

    @BeforeEach
    fun init(){
        databaseCleaner.clean()
    }

    @Test
    fun 브랜드_생성_테스트() {
        //given
        val brandName = "CAAA"

        //when
        val response = brandManagerService.createBrand(brandName)

        // when
        assertTrue(response is ResultResponse.Success)

        if(response is ResultResponse.Success){
            val brand = response.value
            val brandId = brand.brandId ?: 0

            val resultBrand = brandRepository.findById(brandId)
            assertNotNull(resultBrand.getOrNull())
        }
    }


    @Test
    fun 브랜드_수정_테스트() {
        //given
        val brandName = "CAAA"
        val response = brandManagerService.createBrand(brandName)
        val createdBrandId = if(response is ResultResponse.Success) response.value.brandId else 0L

        // when && then

        val brandName2 = "BBBB"

        assertNotNull(createdBrandId)
        createdBrandId?.let {
            brandManagerService.modifyBrand(createdBrandId, brandName2)

            val resultBrand = brandRepository.findById(createdBrandId).getOrNull()
            assertEquals(resultBrand?.name, brandName2 )
        }
    }

    @Test
    fun 브랜드_삭제_테스트(){
        //given
        val brandName = "CAAA"
        val response = brandManagerService.createBrand(brandName)
        val createdBrandId = if(response is ResultResponse.Success) response.value.brandId else 0L



        // when && then
        assertNotNull(createdBrandId)

        createdBrandId?.let {
            val resultBrand = brandRepository.findById(createdBrandId).getOrNull()
            assertNotNull(resultBrand)

            brandManagerService.deleteBrand(createdBrandId)
            val resultBrand2 = brandRepository.findById(createdBrandId).getOrNull()
            assertNull(resultBrand2)
        }
    }
}