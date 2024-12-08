package com.snow.gotit.brand

import com.snow.gotit.base.exception.GotItException
import com.snow.gotit.base.response.ResultResponse
import com.snow.gotit.brand.dto.BrandDto
import com.snow.gotit.brand.entity.Brand
import com.snow.gotit.brand.param.CreateBrandParam
import com.snow.gotit.brand.repository.BrandRepository
import com.snow.gotit.product.entity.Product
import com.snow.gotit.product.param.CreateProductParam
import com.snow.gotit.product.param.ModifyProductParam
import jakarta.persistence.IdClass
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BrandManagerService(
    val brandRepository: BrandRepository,
) {
    fun createBrand(brandName: String): ResultResponse<BrandDto> {
        val brand = Brand(
            name = brandName
        )
        brandRepository.save(brand)

        return ResultResponse.Success(
            BrandDto(
                brandId = brand.id,
                brandName = brand.name
        ))
    }

    fun modifyBrand(brandId: Long, brandName: String): ResultResponse<BrandDto>{
        val brand = brandRepository.findById(brandId).getOrNull()
            ?: throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "수정할 브랜드 정보가 없습니다.",
                data = hashMapOf("brandId" to brandId)
            )

        if(brandName.isNotBlank()){
            brand.updateName(brandName)
        }

        brandRepository.save(brand)

        return ResultResponse.Success(
            BrandDto(
                brandId = brand.id,
                brandName = brand.name
            ))
    }

    fun deleteBrand(brandId: Long){
        try{
            brandRepository.deleteById(brandId)
        }catch (e: EmptyResultDataAccessException){
            throw GotItException(
                status = HttpStatus.NOT_FOUND,
                message = "삭제할 브랜드가 없습니다.",
                data = hashMapOf("brandId" to brandId)
            )
        }catch (ex: DataIntegrityViolationException){
            throw GotItException(
                status = HttpStatus.CONFLICT,
                message = "연결된 상품이 존재하여 삭제가 불가합니다.",
                data = hashMapOf("brandId" to brandId)
            )
        }
    }
}