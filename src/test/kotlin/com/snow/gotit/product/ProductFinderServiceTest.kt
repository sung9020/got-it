package com.snow.gotit.product

import com.snow.gotit.base.utils.DatabaseCleaner
import com.snow.gotit.brand.entity.Brand
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.entity.Category
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.entity.Product
import com.snow.gotit.product.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import kotlin.collections.listOf
import kotlin.collections.mapOf
import kotlin.collections.set
import kotlin.test.assertEquals

@SpringBootTest
class ProductFinderServiceTest(
    @Autowired
    val productFinderService: ProductFinderService,
    @Autowired
    val productRepository: ProductRepository,
    @Autowired
    val categoryRepository: CategoryRepository,
    @Autowired
    val brandRepository: BrandRepository,
    @Autowired
    val databaseCleaner: DatabaseCleaner,

) {
    @BeforeEach
    fun init (){
        databaseCleaner.clean()
    }

    fun createCategories(): Map<String, Category> {
        return mapOf(
            "상의" to Category(name="상의"),
            "아우터" to Category(name="아우터"),
            "바지" to Category(name="바지"),
            "스니커즈" to Category(name="스니커즈"),
            "가방" to Category(name="가방"),
            "모자" to Category(name="모자"),
            "양말" to Category(name="양말"),
            "액세서리" to Category(name="액세서리")
        )
    }

    fun createBrands(): Map<String, Brand> {
        return mapOf(
            "A" to Brand(name = "A"),
            "B" to Brand(name = "B"),
            "C" to Brand(name = "C")
        )
    }

    fun createProductAList(categoryMap: Map<String, Category>, brandMap: Map<String, Brand>): List<Product> {
        val productAList = listOf(
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["상의"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["아우터"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(3000L), category = categoryMap["아우터"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(3000L), category = categoryMap["바지"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(150L), category = categoryMap["바지"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(250L), category = categoryMap["바지"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(3000L), category = categoryMap["스니커즈"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(3000L), category = categoryMap["가방"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["모자"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(150L), category = categoryMap["모자"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["양말"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(110L), category = categoryMap["양말"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(120L), category = categoryMap["양말"], brand = brandMap["A"]),
            Product(price = BigDecimal.valueOf(3000L), category = categoryMap["액세서리"], brand = brandMap["A"])
        )
        return productAList
    }

    fun createProductBList(categoryMap: Map<String, Category>, brandMap: Map<String, Brand>): List<Product> {
        val productBList = listOf(
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["상의"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["아우터"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["바지"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["바지"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["스니커즈"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(150L), category = categoryMap["스니커즈"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["가방"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["모자"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["모자"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["양말"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["양말"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(2000L), category = categoryMap["액세서리"], brand = brandMap["B"]),
            Product(price = BigDecimal.valueOf(150L), category = categoryMap["액세서리"], brand = brandMap["B"]),
        )

        return productBList
    }


    fun createProductCList(categoryMap: Map<String, Category>, brandMap: Map<String, Brand>): List<Product> {
        val productCList = listOf(
            Product(price = BigDecimal.valueOf(1000L), category = categoryMap["상의"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(1000L), category = categoryMap["아우터"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["바지"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["스니커즈"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(1000L), category = categoryMap["가방"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["가방"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(1000L), category = categoryMap["모자"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(150L), category = categoryMap["모자"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(1000L), category = categoryMap["양말"], brand = brandMap["C"]),
            Product(price = BigDecimal.valueOf(100L), category = categoryMap["액세서리"], brand = brandMap["C"])
        )
        return productCList
    }

    @Test
    fun getMinPriceProductsByCategory() {
        //given
        val categories = createCategories()
        val brands= createBrands()
        val productsA = createProductAList(categories, brands)
        val productsB = createProductBList(categories, brands)
        val productsC = createProductCList(categories, brands)
        categoryRepository.saveAll(categories.values)
        brandRepository.saveAll(brands.values)

        productRepository.saveAll(productsA)
        productRepository.saveAll(productsB)
        productRepository.saveAll(productsC)

        //when
        val response = productFinderService.getMinPriceProductsByCategory()
        val targetTotalPrice = BigDecimal.valueOf(800L) // 에상하는 최저가 지점의 합산

        //then
        val resultProductList = response.productList
        val resultTotalPrice = response.totalPrice
        for(resultProduct in resultProductList){
            if(resultProduct.categoryName == "상의"){
                assertEquals(resultProduct.brandName, "A")
            }
            if(resultProduct.categoryName == "아우터"){
                assertEquals(resultProduct.brandName, "B")
            }
            if(resultProduct.categoryName == "바지"){
                assertEquals(resultProduct.brandName, "C")
            }
            if(resultProduct.categoryName == "스니커즈"){
                assertEquals(resultProduct.brandName, "C",)
            }
            if(resultProduct.categoryName == "가방"){
                assertEquals(resultProduct.brandName, "C")
            }
            if(resultProduct.categoryName == "모자"){
                assertEquals(resultProduct.brandName, "B")
            }
            if(resultProduct.categoryName == "양말"){
                assertEquals(resultProduct.brandName, "B")
            }
            if(resultProduct.categoryName == "액세서리"){
                assertEquals(resultProduct.brandName, "C")
            }
        }

        assertEquals(resultTotalPrice, targetTotalPrice)
    }

    @Test
    fun getMinPriceProductByBrand() {
        //given
        val categories = createCategories()
        val brands= createBrands()
        val productsA = createProductAList(categories, brands)
        val productsB = createProductBList(categories, brands)
        val productsC = createProductCList(categories, brands)
        categoryRepository.saveAll(categories.values)
        brandRepository.saveAll(brands.values)

        productRepository.saveAll(productsA)
        productRepository.saveAll(productsB)
        productRepository.saveAll(productsC)

        //when
        val response = productFinderService.getMinPriceProductByBrand()
        val resultBrandName = response.lowestPrice.brandName
        val resultCategoryList = response.lowestPrice.categoryList
        val resultTotalPrice = response.lowestPrice.totalPrice

        //then

        val totalPriceSet = hashSetOf<Pair<String, Map<String, BigDecimal>>>()
        totalPriceSet.add("A" to getTotalPrice(productsA))
        totalPriceSet.add("B" to getTotalPrice(productsB))
        totalPriceSet.add("C" to getTotalPrice(productsC))

        var minTotalPrice = BigDecimal.valueOf(Long.MAX_VALUE)
        var minBrandName = ""
        var minMemory: Map<String, BigDecimal> = mapOf()

        for (item in totalPriceSet) {
            val brandName = item.first
            val memory = item.second
            val totalPrice = memory.values.sumOf { it }

            if (totalPrice <= minTotalPrice) {
                minBrandName = brandName
                minTotalPrice = totalPrice
                minMemory = item.second
            }
        }

        //then
        assertEquals(resultBrandName, minBrandName)
        assertEquals(resultTotalPrice, minTotalPrice)

        for(category in resultCategoryList){
            val categoryName = category.categoryName
            val resultPrice = minMemory[categoryName]
            val targetPrice = category.price
            assertEquals(resultPrice, targetPrice)
        }

    }


    private fun getTotalPrice(productList: List<Product>): MutableMap<String, BigDecimal> {
        val memory = mutableMapOf<String, BigDecimal>()
        for(product in productList){
            product.category?.let{ category ->
                if(memory.containsKey(category.name)){
                    memory[category.name] = (if(product.price < memory[category.name]) product.price else memory[category.name]) as BigDecimal
                }else{
                    memory[category.name] = product.price
                }
            }
        }

        return memory
    }

    @Test
    fun getMinMaxProductByCategory() {
        //given
        val categories = createCategories()
        val brands= createBrands()
        val productsA = createProductAList(categories, brands)
        val productsB = createProductBList(categories, brands)
        val productsC = createProductCList(categories, brands)
        categoryRepository.saveAll(categories.values)
        brandRepository.saveAll(brands.values)

        productRepository.saveAll(productsA)
        productRepository.saveAll(productsB)
        productRepository.saveAll(productsC)

        //when
        val testCategoryName = "바지"
        val response = runBlocking { productFinderService.getMinMaxProductByCategory(testCategoryName) }

        val minProductList = response.minProductList
        val maxProductList = response.maxProductList


        //then
        var minBrandName: String? = ""
        var minPrice = BigDecimal.valueOf(Long.MAX_VALUE)

        for(product in productsA + productsB + productsC){
            if (testCategoryName == product.category?.name && product.price <= minPrice) {
                minPrice = product.price
                minBrandName = product.brand?.name
            }
        }

        for(minProduct in minProductList){
            if(minProduct.brandName == minBrandName){
                assertEquals(minProduct.price, minPrice)
            }
        }

        var maxBrandName: String? = ""
        var maxPrice = BigDecimal.ZERO

        for(product in productsA + productsB + productsC){
            if (testCategoryName == product.category?.name && product.price > maxPrice) {
                maxPrice = product.price
                maxBrandName = product.brand?.name
            }
        }

        for(maxProduct in maxProductList){
            if(maxProduct.brandName == maxBrandName){
                assertEquals(maxProduct.price, maxPrice)
            }
        }
    }
}