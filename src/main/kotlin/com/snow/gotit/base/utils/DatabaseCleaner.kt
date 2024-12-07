package com.snow.gotit.base.utils

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleaner: InitializingBean {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
    private lateinit var tableNames: List<String>
    override fun afterPropertiesSet() {
        tableNames = entityManager.createNativeQuery(
            "SHOW TABLES"
        )
            .resultList
            .map { (it as Array<*>)[0].toString() }
    }

    @Transactional
    fun clean() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tableNames.forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}