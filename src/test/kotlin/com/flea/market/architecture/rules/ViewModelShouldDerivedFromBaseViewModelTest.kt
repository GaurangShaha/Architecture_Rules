package com.flea.market.architecture.rules

import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.jupiter.api.Test

class ViewModelShouldDerivedFromBaseViewModelTest {
    private val testConfig = TestConfig()

    private val rule = ViewModelShouldDerivedFromBaseViewModel(testConfig)

    @Test
    fun `passes when viewmodel extends from BaseViewModel`() {
        val code = """
            class NewsViewModel:ViewModelContract<ProductDetailsIntent, ProductDetailsUiState>, ViewModel()
""".trimIndent()

        assert(rule.lint(code).isEmpty())
    }

    @Test
    fun `error when viewmodel does not extends from BaseViewModel`() {
        val code = """
            class NewsViewModel:ViewModel()
""".trimIndent()

        assert(rule.lint(code).isNotEmpty())
    }
}