package com.snow.gotit.product

import com.snow.gotit.base.exception.GotItException
import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.category.repository.CategoryRepository
import com.snow.gotit.product.dto.ProductDto
import com.snow.gotit.product.entity.Product
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import com.snow.gotit.product.repository.ProductRepository
import lombok.RequiredArgsConstructor
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@RequiredArgsConstructor
class ProductManagerService(
    val productRepository: ProductRepository,
    val categoryRepository: CategoryRepository,
    val brandRepository: BrandRepository,
) {

   @Transactional
   fun createProduct(param: CreateProductParam): ResultResponse<ProductDto>{
       val category = categoryRepository.findById(param.categoryId).getOrNull()
           ?: throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "카테고리 정보가 없습니다.",
               data = hashMapOf("categoryId" to param.categoryId)
           )

       val brand =  brandRepository.findById(param.brandId).getOrNull()
           ?: throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "브랜드 정보가 없습니다.",
               data = hashMapOf("brandId" to param.brandId)
           )

        val product = Product(
            price = param.price,
            category = category,
            brand = brand,
        )
       productRepository.save(product)

       return ResultResponse.Success(
           ProductDto(
               productId = product.id,
               _price = product.price,
               categoryName = product.category?.name.orEmpty(),
               brandName = product.brand?.name.orEmpty(),
           )
       )
    }

   @Transactional
   fun modifyProduct(productId: Long, param: ModifyProductParam): ResultResponse<ProductDto>{
       val product = productRepository.findByIdWithCategoryAndBrand(productId) ?: throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "수정할 상품이 없습니다.",
               data = hashMapOf("productId" to productId)
           )


       param.brandId?.let {
           val brand = brandRepository.findById(param.brandId).getOrNull() ?: throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "브랜드 정보가 없습니다.",
               data = hashMapOf("brandId" to param.brandId)
           )

           product.updateBrand(brand)
       }

       param.categoryId?.let {
           val category = categoryRepository.findById(param.categoryId).getOrNull()?: throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "카테고리 정보가 없습니다.",
               data = hashMapOf("categoryId" to param.categoryId)
           )
           product.updateCategory(category)
       }

      param.price?.let{
          product.updatePrice(param.price)
      }

      productRepository.save(product)

       return ResultResponse.Success(
           ProductDto(
               productId = product.id,
               _price = product.price,
               categoryName = product.category?.name.orEmpty(),
               brandName = product.brand?.name.orEmpty(),
           )
       )
   }

   fun deleteProduct(productId: Long) =
       try {
           productRepository.deleteById(productId)
       } catch (e: EmptyResultDataAccessException){
           throw GotItException(
               status = HttpStatus.NOT_FOUND,
               message = "삭제할 상품이 없습니다. e: ${e.message}",
               data = hashMapOf("productId" to productId)
           )
       }

}