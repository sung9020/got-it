package com.snow.gotit.product.repository

import com.snow.gotit.product.dto.ProductFinderDto
import com.snow.gotit.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface ProductRepository: JpaRepository<Product, Long>{

    @Query("""
        SELECT new com.snow.gotit.product.dto.ProductFinderDto(
            p1.id,
            p1.price, 
            c.name, 
            b.name
        )
        FROM Product p1
        LEFT JOIN Product p2 
            ON p1.category.id = p2.category.id
            AND 
            ( 
                (p1.price = p2.price AND p1.id < p2.id) OR 
                (p1.price > p2.price)
            )
        JOIN p1.brand b
        JOIN p1.category c
        WHERE p2.id IS NULL
        ORDER BY p1.category.id
    """)
    fun findMinPriceProductsByCategory(): List<ProductFinderDto>

    @Query("""
        SELECT p.brandId
        FROM (
           SELECT p2.category.id as categoryId, 
                  p2.brand.id as brandId, 
                  MIN(price) as price
           FROM Product p2
           GROUP BY p2.category.id, p2.brand.id
        ) p
        GROUP BY p.brandId
        ORDER BY SUM(p.price)
    """)
    fun findBrandIdListOrderByTotalPrice(): List<Long>

    @Query("""
        SELECT new com.snow.gotit.product.dto.ProductFinderDto(
            p1.id, 
            p1.price, 
            p1.category.name, 
            p1.brand.name
        )
        FROM Product p1
        LEFT JOIN Product p2 
            ON p1.category.id = p2.category.id
            AND p1.brand.id = p2.brand.id 
            AND 
            ( 
                (p1.price = p2.price AND p1.id < p2.id) OR 
                (p1.price > p2.price)
            )
        JOIN p1.brand b
        JOIN p1.category c
        WHERE p1.brand.id = :brandId
        AND p2.id IS NULL
    """)
    fun findMinPriceProductByBrand(@Param("brandId") brandId: Long): List<ProductFinderDto>

    @Query("""
        SELECT new com.snow.gotit.product.dto.ProductFinderDto(
            Max(p.id),
            p.price, 
            p.category.name, 
            p.brand.name
        )
        FROM Product p
        JOIN p.brand b
        JOIN p.category c
        WHERE p.category.id = :categoryId
        AND p.price = (
            SELECT MAX(p2.price)
            FROM Product p2
            WHERE p2.category.id = :categoryId
        )
        GROUP BY p.price, p.category.name, p.brand.name
    """)
    fun findMaxProductByCategory(@Param("categoryId") categoryId: Long?): List<ProductFinderDto>

    @Query("""
        SELECT new com.snow.gotit.product.dto.ProductFinderDto(
            Max(p.id), 
            p.price, 
            p.category.name, 
            p.brand.name
        )
        FROM Product p
        JOIN p.brand b
        JOIN p.category c
        WHERE p.category.id = :categoryId
        AND p.price = (
            SELECT MIN(p2.price)
            FROM Product p2
            WHERE p2.category.id = :categoryId
        )
        GROUP BY p.price, p.category.name, p.brand.name
    """)
    fun findMinProductByCategory(@Param("categoryId") categoryId: Long?): List<ProductFinderDto>

    @Query("select p From Product p join fetch p.category join fetch p.brand where p.id= :id")
    fun findByIdWithCategoryAndBrand(@Param("id") id: Long): Product?

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.brand LEFT JOIN FETCH p.category WHERE p.brand.name = :brandName AND p.category.name = :categoryName")
    fun findByBrand_NameAndCategory_Name(@Param("brandName") brandName: String, @Param("categoryName") categoryName: String): List<Product>
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.brand LEFT JOIN FETCH p.category WHERE p.brand.name = :brandName")
    fun findByBrand_Name(@Param("brandName") brandName: String): List<Product>
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.brand LEFT JOIN FETCH p.category WHERE p.category.name = :categoryName")
    fun findByCategory_Name(@Param("categoryName") categoryName: String): List<Product>

}