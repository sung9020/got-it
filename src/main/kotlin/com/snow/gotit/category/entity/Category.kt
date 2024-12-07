package com.snow.gotit.category.entity

import com.snow.gotit.base.entity.BaseEntity
import com.snow.gotit.base.entity.kotlinEquals
import com.snow.gotit.base.entity.kotlinHashCode
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Comment("카테고리 테이블")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @Comment("카테고리 아이디")
    var id: Long? = null,

    @Column
    @Comment("카테고리 이름")
    var name: String,

    ): BaseEntity() {
    companion object {
        private val equalsAndHashCodeProperties = listOf(Category::id)
    }

    override fun equals(other: Any?): Boolean = kotlinEquals(other, equalsAndHashCodeProperties)
    override fun hashCode(): Int = kotlinHashCode(equalsAndHashCodeProperties)
}