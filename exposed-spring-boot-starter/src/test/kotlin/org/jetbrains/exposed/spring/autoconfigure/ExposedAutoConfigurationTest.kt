package org.jetbrains.exposed.spring.autoconfigure

import org.jetbrains.exposed.spring.DatabaseInitializer
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.spring.tables.TestTable
import org.jetbrains.exposed.sql.selectAll
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [org.jetbrains.exposed.spring.Application::class],
        properties = ["spring.datasource.url=jdbc:h2:mem:test", "spring.datasource.driver-class-name=org.h2.Driver"])
open class ExposedAutoConfigurationTest {

    @Autowired(required = false)
    private var springTransactionManager: SpringTransactionManager? = null

    @Autowired(required = false)
    private var databaseInitializer: DatabaseInitializer? = null

    @Test
    fun `should initialize the database connection`() {
        Assert.assertNotNull(springTransactionManager)
    }

    @Test
    fun `should not create schema`() {
        Assert.assertNull(databaseInitializer)
    }
}

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [org.jetbrains.exposed.spring.Application::class],
        properties = ["spring.datasource.url=jdbc:h2:mem:test", "spring.datasource.driver-class-name=org.h2.Driver","spring.exposed.generate-ddl=true"])
open class ExposedAutoConfigurationTestAutoGenerateDDL {

    @Autowired(required = false)
    private var springTransactionManager: SpringTransactionManager? = null

    @Test
    fun `should initialize the database connection`() {
        Assert.assertNotNull(springTransactionManager)
    }

    @Test @Transactional
    open fun `should create schema`() {
        Assert.assertEquals(0, TestTable.selectAll().count())
    }
}