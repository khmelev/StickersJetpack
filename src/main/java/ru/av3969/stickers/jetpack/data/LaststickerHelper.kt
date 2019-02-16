package ru.av3969.stickers.jetpack.data

import org.jsoup.Jsoup
import java.util.regex.Pattern

class LaststickerHelper {

    val patternUrlSplit by lazy { Pattern.compile("[/]") }

    fun getCategoryUrl() = baseUrl + categoryPath

    fun getCategoryList(): List<Category> {

        val categoryList = arrayListOf<Category>()

        val doc = Jsoup.connect(getCategoryUrl()).get()

        val content = doc.selectFirst("div#content table")

        var catCounter = 0
        for (rootCat in content.select("td > p")) {
            val rootCatId = ++catCounter

            var strings = patternUrlSplit.split(rootCat.selectFirst("a").attr("href"))
            var catName = if (strings.size > 0 ) strings[strings.size - 1] else ""

            categoryList.add(Category(rootCatId, catName, rootCat.text(), 0))

            for (cat in rootCat.nextElementSibling().select("a")) {
                strings = patternUrlSplit.split(cat.attr("href"))
                catName = if (strings.size > 0) strings[strings.size - 1] else ""
                categoryList.add(Category(++catCounter, catName, cat.text(), rootCatId))
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