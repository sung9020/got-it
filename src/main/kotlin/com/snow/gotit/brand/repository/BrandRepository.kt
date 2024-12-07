package com.snow.gotit.brand.repository

import com.snow.gotit.brand.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository: JpaRepository<Brand, Long> {
}