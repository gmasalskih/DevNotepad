package com.example.devnotepad.ui.fragment_article_content

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.devnotepad.*
import kotlinx.android.synthetic.main.article_code_snippet_item.view.*
import kotlinx.android.synthetic.main.article_header_item.view.*
import kotlinx.android.synthetic.main.article_paragraph_item.view.*

// test
class ArticleContentAdapter(
    context: Context
) : RecyclerView.Adapter<ArticleContentAdapter.BaseViewHolder<*>>() {

    companion object {
        private const val headerType = 1
        private const val paragraphType = 2
        private const val codeSnippetType = 3
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var articlePieces = ArrayList<ArticlePiece>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            headerType -> ArticleHeaderViewHolder(
                inflater.inflate(
                    R.layout.article_header_item,
                    parent,
                    false
                )
            )
            paragraphType -> ArticleParagraphViewHolder(
                inflater.inflate(
                    R.layout.article_paragraph_item,
                    parent,
                    false
                )
            )
            codeSnippetType -> ArticleCodeSnippetViewHolder(
                inflater.inflate(
                    R.layout.article_code_snippet_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (articlePieces[position]) {
            is ArticleHeader -> headerType
            is ArticleParagraph -> paragraphType
            is ArticleCodeSnippet -> codeSnippetType
            else -> throw IllegalArgumentException("Invalid type of data $position.")
        }
    }

    override fun getItemCount() = articlePieces.size

    internal fun addArticlePieces(articlePieces: List<ArticlePiece>) {
        this.articlePieces = getAllSortedPieces(this.articlePieces, articlePieces)
        notifyDataSetChanged()
    }

    /**
     * Поскольку фрагмент устанавливает несколько источников информации, они добавляются поочередно.
     * Данный метод объединяет элементы статьи и сортирует их по позициям.
     * */
    private fun getAllSortedPieces(
        formerPieces: List<ArticlePiece>,
        newPieces: List<ArticlePiece>
    ): ArrayList<ArticlePiece> {
        val allSortedPieces = ArrayList<ArticlePiece>()
        allSortedPieces.addAll(formerPieces)
        allSortedPieces.addAll(newPieces)
        allSortedPieces.sortBy { A -> A.positionInArticle }
        return allSortedPieces
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class ArticleHeaderViewHolder(itemView: View) :
        BaseViewHolder<ArticleHeader>(itemView) {
        private val articleHeader: TextView = itemView.header

        override fun bind(item: ArticleHeader) {
            articleHeader.text = item.getContentOfPiece()
        }
    }

    class ArticleParagraphViewHolder(itemView: View) :
        BaseViewHolder<ArticleParagraph>(itemView) {
        private val articleParagraph: TextView = itemView.paragraph

        override fun bind(item: ArticleParagraph) {
            val text = "\t ${item.getContentOfPiece()}"
            articleParagraph.text = text
        }
    }

    class ArticleCodeSnippetViewHolder(itemView: View) :
        BaseViewHolder<ArticleCodeSnippet>(itemView) {
        private val webView: WebView = itemView.webView
        private val loadingPlaceholder: LinearLayout = itemView.loadingPlaceholder
        private val noInternetConnectionPlaceholder: LinearLayout = itemView.noInternetConnectionPlaceholder

        override fun bind(item: ArticleCodeSnippet) {
            val codeSnippetController = CodeSnippetController(
                item, webView, loadingPlaceholder, noInternetConnectionPlaceholder)

            codeSnippetController.loadCodeSnippet()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val piece = articlePieces[position]
        when (holder) {
            is ArticleHeaderViewHolder -> holder.bind(piece as ArticleHeader)
            is ArticleParagraphViewHolder -> holder.bind(piece as ArticleParagraph)
            is ArticleCodeSnippetViewHolder -> holder.bind(piece as ArticleCodeSnippet)
            else -> throw IllegalArgumentException()
        }
    }
}
