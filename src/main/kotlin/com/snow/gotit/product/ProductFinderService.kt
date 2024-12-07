package com.snow.gotit.product

import com.snow.gotit.base.exception.GotItException
import com.snow.gotit.base.utils.toKRW
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.repository.ProductRepository
import com.snow.gotit.product.response.MinMaxProductByCategoryResponse
import com.snow.gotit.product.response.MinPriceProductByBrandResponse
import com.snow.gotit.product.response.MinPriceProductByBrandResponse.MinPriceResponse
import com.snow.gotit.product.response.MinPriceProductByCategoryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ProductFinderService(
    val productRepository: ProductRepository,
    val categoryRepository: CategoryRepository
) {
    fun getMinPriceProductsByCategory(): MinPriceProductByCategoryResponse {
        val productList = productRepository.findMinPriceProductsByCategory()
        if (productList.isEmpty()) {
            throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "발견된 상품이 없습니다."
            )
        }

        val totalPrice = productList.sumOf { product -> product.price }

        return MinPriceProductByCategoryResponse(
            productList = productList,
            _totalPrice = totalPrice,
        )
    }

    fun getMinPriceProductByBrand(): MinPriceProductByBrandResponse {
        // 최저 합산액을 가지고 있는 브랜드를 pick
        val brandIdList = productRepository.findBrandIdListOrderByTotalPrice()
        if (brandIdList.isEmpty()) {
            throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "발견된 브랜드가 없습니다."
            )
        }

        val lowestBrandId = brandIdList.first()
        // 최저가를 합산액을 가지고 있는 브랜드 기준으로 카테고리별 최저가 리스트 조회
        val productList = productRepository.findMinPriceProductByBrand(lowestBrandId)
        if (productList.isEmpty()) {
            throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "발견된 상품이 없습니다."
            )
        }

        val brandName = productList.first().brandName
        val totalPrice = productList.sumOf { product -> product.price }

        val categoryResponseList = productList.map { product ->
            MinPriceProductByBrandResponse.CategoryResponse(
                categoryName = product.categoryName,
                _price = product.price,
            )
        }

        return MinPriceProductByBrandResponse(
            MinPriceResponse(
                brandName = brandName,
                _totalPrice = totalPrice,
                categoryList = categoryResponseList
            )
        )
    }

    suspend fun getMinMaxProductByCategory(categoryName: String): MinMaxProductByCategoryResponse {
        val categoryDeferred = CoroutineScope(Dispatchers.IO).async {
            categoryRepository.findTopByName(categoryName)
        }

        val category = categoryDeferred.await()
            ?: throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "카테고리 정보가 없습니다.",
                data = hashMapOf("categoryName" to categoryName)
            )

        val maxPriceProductListDeferred = CoroutineScope(Dispatchers.IO).async {
            // 최고가와 같은 브랜드가 배열로 등장
            productRepository.findMaxProductByCategory(category.name)
                .map { product ->
                    MinMaxProductByCategoryResponse.MinMaxProductResponse(
                        product.categoryName,
                        product.price
                    )
                }
        }

        val minPriceProductListDeferred = CoroutineScope(Dispatchers.IO).async {
            // 최저가와 같은 브랜드가 배열로 등장
            productRepository.findMinProductByCategory(category.name)
                .map { product ->
                    MinMaxProductByCategoryResponse.MinMaxProductResponse(
                        product.categoryName,
                        product.price
                    )
                }
        }
        val maxProductList = maxPriceProductListDeferred.await()
        val minProductList = minPriceProductListDeferred.await()

        if (maxProductList.isEmpty() && minProductList.isEmpty()) {
            throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "발견된 최저가/최고가 상품이 없습니다.",
                data = hashMapOf("categoryName" to category.name)
            )
        }

        return MinMaxProductByCategoryResponse(
            categoryName = category.name,
            maxProductList = maxPriceProductListDeferred.await(),
            minProductList = minPriceProductListDeferred.await(),
        )
    }
}