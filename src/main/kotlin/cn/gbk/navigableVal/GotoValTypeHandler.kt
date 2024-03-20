package cn.gbk.navigableVal

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandlerBase
import com.intellij.codeInsight.navigation.actions.GotoTypeDeclarationAction
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.kotlin.lexer.KtKeywordToken

class GotoValTypeHandler : GotoDeclarationHandlerBase() {
    private val keyWords = setOf("val", "var")
    override fun getGotoDeclarationTarget(sourceElement: PsiElement?, editor: Editor?): PsiElement? {
        val keyWord = sourceElement?.text ?: return null
        if (keyWord !in keyWords || sourceElement.elementType !is KtKeywordToken) {
            return null
        }
        runCatching {
            val name = sourceElement.nextSibling.nextSibling
            return editor?.let { GotoTypeDeclarationAction.findSymbolType(it, name.textOffset) }
        }
        return null
    }
}