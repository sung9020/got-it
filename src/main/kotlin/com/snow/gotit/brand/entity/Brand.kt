package com.snow.gotit.brand.entity

import com.snow.gotit.base.entity.BaseEntity
import com.snow.gotit.base.entity.kotlinEquals
import com.snow.gotit.base.entity.kotlinHashCode
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Comment("브랜드 테이블")
class Brand (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    @Comment("카테고리 아이디")
    var id: Long? = null,

    @Column
    @Comment("브랜드 이름")
    var name: String,
): BaseEntity(){
    companion object {
        private val equalsAndHashCodeProperties = listOf(Brand::id)
    }

    fun updateName( name: String){
        this.name  = name
    }

    override fun equals(other: Any?): Boolean = kotlinEquals(other, equalsAndHashCodeProperties)
    override fun hashCode(): Int = kotlinHashCode(equalsAndHashCodeProperties)
}