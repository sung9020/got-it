package com.snow.gotit.product

import com.snow.gotit.base.exception.GotItException
import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.base.utils.DatabaseCleaner
import com.snow.gotit.brand.entity.Brand
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.entity.Category
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.entity.Product
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import com.snow.gotit.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
class ProductManagerServiceTest(
    @Autowired
    val productManagerService: ProductManagerService,
    @Autowired
    val categoryRepository: CategoryRepository,
    @Autowired
    val brandRepository: BrandRepository,
    @Autowired
    val databaseCleaner: DatabaseCleaner,
    @Autowired
    val productRepository: ProductRepository,
) {
    @BeforeEach
    fun init(){
        databaseCleaner.clean()
    }

    @Test
    fun 상품_생성_테스트() {
        //given
        val brand = Brand(name="CA")
        brandRepository.save(brand)
        val category = Category(name="바지")
        categoryRepository.save(category)

        val param = CreateProductParam(
            brandId = brand.id ?: 0,
            categoryId = category.id ?: 0,
            price = BigDecimal.valueOf(10000L)
        )

        // when
        val response = productManagerService.createProduct(param)

        // when
        assertTrue(response is ResultResponse.Success)

        if(response is ResultResponse.Success){
            val product = response.value
            val productId = product.productId ?: 0

            val resultProduct = productRepository.findById(productId)
            assertNotNull(resultProduct.getOrNull())
        }
    }

    @Test
    fun 상품_생성_테스트_오류() {
        //given
        val brand = Brand(name="CA")
        brandRepository.save(brand)
        val category = Category(name="바지")
        categoryRepository.save(category)


        val param = CreateProductParam(
            brandId = 555555L,
            categoryId = category.id ?: 0,
            price = BigDecimal.valueOf(10000L)
        )

        // when & then
        assertThrows<GotItException> {
            productManagerService.createProduct(param)
        }

        val param2 = CreateProductParam(
            brandId = brand.id ?: 0,
            categoryId = 333333L,
            price = BigDecimal.valueOf(10000L)
        )

        // when & then
        assertThrows<GotItException> {
            productManagerService.createProduct(param2)
        }
    }

    @Test
    fun 상품_수정_테스트() {
        //given
        val brand = Brand(name="CA")
        brandRepository.save(brand)
        val category = Category(name="바지")
        categoryRepository.save(category)

        val response = productManagerService.createProduct(
            CreateProductParam(
                brandId = brand.id ?: 0,
                categoryId = category.id ?: 0,
                price = BigDecimal.valueOf(10000L)
            )
        )
        val createdProductId = if(response is ResultResponse.Success) response.value.productId else 0L

        // when && then
        val brand2 = Brand(name="GGGG")
        brandRepository.save(brand2)

        val category2 = Category(name="GGGG")
        categoryRepository.save(category2)

        val price = BigDecimal.valueOf(20000L)
        val param = ModifyProductParam(
            brandId = brand2.id,
            categoryId = category2.id,
            price = price
        )

        assertNotNull(createdProductId)
        createdProductId?.let {
            productManagerService.modifyProduct(
                productId = createdProductId,
                param = param
            )


            val resultProduct = productRepository.findById(createdProductId).getOrNull()
            assertEquals(resultProduct?.brand?.id, brand2.id )
            assertEquals(resultProduct?.category?.id, category2.id )
            assertEquals(resultProduct?.price, price )
        }
    }

    @Test
    fun 상품_삭제_테스트() {
        //given
        val brand = Brand(name="CA")
        brandRepository.save(brand)
        val category = Category(name="바지")
        categoryRepository.save(category)

        val param = CreateProductParam(
            brandId = brand.id ?: 0,
            categoryId = category.id ?: 0,
            price = BigDecimal.valueOf(10000L)
        )

        val response = productManagerService.createProduct(param)

        //when && then
        val createdProductId = if(response is ResultResponse.Success) response.value.productId else 0L
        assertNotNull(createdProductId)

        createdProductId?.let {
            val resultProduct = productRepository.findById(createdProductId).getOrNull()
            assertNotNull(resultProduct)

            productManagerService.deleteProduct(createdProductId)
            val resultProduct2 = productRepository.findById(createdProductId).getOrNull()
            assertNull(resultProduct2)
        }
    }
}