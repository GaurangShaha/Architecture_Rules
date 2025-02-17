package com.flea.market.architecture.rules

import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.jupiter.api.Test

class ViewModelShouldNotHavePublicPropertyTest {
    private val testConfig = TestConfig()

    private val rule = ViewModelShouldNotHavePublicProperty(testConfig)

    @Test
    fun `passes when viewmodel does not have public property`() {
        val code = """
            class NewsViewModel:ViewModelContract<ProductDetailsIntent, ProductDetailsUiState>(Loading), ViewModel(){
                override fun processIntent(){}
            }
""".trimIndent()

        assert(rule.lint(code).isEmpty())
    }

    @Test
    fun `error when viewmodel has public property`() {
        val code = """
            class NewsViewModel(val id: Int):ViewModelContract<ProductDetailsIntent, ProductDetailsUiState>(Loading), ViewModel(){
                val name = "Harry"
                override fun processIntent(){}
            }
""".trimIndent()

        assert(rule.lint(code).isNotEmpty())
    }
}