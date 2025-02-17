package com.flea.market.architecture.rules

import com.flea.market.architecture.util.isViewmodel
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.CorrectableCodeSmell
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity.CodeSmell
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class ViewModelShouldNotHavePublicMethod(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        CodeSmell,
        "ViewModel should not have extra public methods",
        Debt.FIVE_MINS
    )

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        if (klass.getSuperTypeList()?.entries?.isViewmodel == true) {
            klass.body?.functions?.filter { it.isPublic && it.name != "processIntent" }
                ?.forEach {
                    report(
                        CorrectableCodeSmell(
                            issue = issue,
                            entity = Entity.from(it),
                            message = """ViewModel should not have extra public methods. Make function ${it.name} as private.""",
                            references = emptyList(),
                            autoCorrectEnabled = false
                        )
                    )
                }
        }
    }
}
