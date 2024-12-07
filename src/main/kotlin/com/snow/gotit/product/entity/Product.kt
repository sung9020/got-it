package com.snow.gotit.product.entity

import com.snow.gotit.base.entity.BaseEntity
import com.snow.gotit.base.entity.kotlinEquals
import com.snow.gotit.base.entity.kotlinHashCode
import com.snow.gotit.brand.entity.Brand
import com.snow.gotit.category.entity.Category

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.math.BigDecimal

@Entity
@Comment("상품 테이블")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Comment("상품 아이디")
    var id: Long? = null,

    @Column
    @Comment("가격")
    var price: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Comment("상품 카테고리")
    var category: Category? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @Comment("브랜드")
    var brand: Brand? = null,

    ): BaseEntity() {
    companion object {
        private val equalsAndHashCodeProperties = listOf(Product::id)
    }

    fun updateBrand(brand: Brand){
        this.brand = brand
    }

    fun updateCategory(category: Category){
        this.category = category
    }

    fun updatePrice(price: BigDecimal){
        this.price = price
    }

    override fun equals(other: Any?): Boolean = kotlinEquals(other, equalsAndHashCodeProperties)
    override fun hashCode(): Int = kotlinHashCode(equalsAndHashCodeProperties)
}