package com.flea.market.architecture.rules

import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.jupiter.api.Test

class ViewModelShouldNotHaveSavedStateHandleAsParamTest {
    private val testConfig = TestConfig()

    private val rule = ViewModelShouldNotHaveSavedStateHandleAsParam(testConfig)

    @Test
    fun `passes when viewmodel does not have parameter of type SavedStateHandle`() {
        val code = """
            class NewsViewModel:ViewModelContract<ProductDetailsIntent, ProductDetailsUiState>, ViewModel()
""".trimIndent()

        assert(rule.lint(code).isEmpty())
    }

    @Test
    fun `error when viewmodel have parameter of type SavedStateHandle`() {
        val code = """
            class NewsViewModel(private val savedStateHandle: SavedStateHandle):ViewModelContract<ProductDetailsIntent, ProductDetailsUiState>, ViewModel()
""".trimIndent()

        assert(rule.lint(code).isNotEmpty())
    }
}