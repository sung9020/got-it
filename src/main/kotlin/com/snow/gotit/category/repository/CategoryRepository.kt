package com.snow.gotit.category.repository

import com.snow.gotit.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long>{
    fun findTopByName(name: String): Category?
}