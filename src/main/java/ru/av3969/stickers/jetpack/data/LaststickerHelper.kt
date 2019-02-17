package ru.av3969.stickers.jetpack.data

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.regex.Pattern

class LaststickerHelper {

    val patternUrlSplit by lazy { Pattern.compile("[/]") }

    fun getCategoryUrl() = baseUrl + categoryPath

    fun getCategoryList(): List<Category> {

        val categoryList = arrayListOf<Category>()

        val doc: Document?
        try {
            doc = Jsoup.connect(getCategoryUrl()).get()
        } catch (e: Exception) {
            //Log.d(LaststickerHelper::class.simpleName, "No Internet connection")
            return categoryList
        }

        val content = doc.selectFirst("div#content table")

        var rootCatCounter = 0
        var catCounter = 100
        for (rootCat in content.select("td > p")) {

            var strings = patternUrlSplit.split(rootCat.selectFirst("a").attr("href"))
            var catName = if (strings.isNotEmpty()) strings[strings.size - 1] else ""

            categoryList.add(
                Category(++rootCatCounter, catName, rootCat.text())
            )

            for (cat in rootCat.nextElementSibling().select("a")) {
                strings = patternUrlSplit.split(cat.attr("href"))
                catName = if (strings.isNotEmpty()) strings[strings.size - 1] else ""
                categoryList.add(
                    Category(++catCounter, catName, cat.text(), rootCatCounter)
                )
            }
        }

        return categoryList
    }

    companion object {
        @Volatile private var instance: LaststickerHelper? = null

        const val baseUrl = "https://www.laststicker.ru/"
        const val categoryPath = "cards/"

        fun getInstance() : LaststickerHelper {
            return instance ?: synchronized(this) {
                return instance ?: LaststickerHelper().also { instance = it }
            }
        }
    }
}