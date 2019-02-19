package ru.av3969.stickers.jetpack.data

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

class LaststickerHelper {

    val patternUrlSplit by lazy { Pattern.compile("[/]") }

    private fun getCategoryUrl() = "$baseUrl$categoryPath"

    private fun getCollectionsUrl(categoryName: String) =
        "$baseUrl$categoryPath$collectionsPath$categoryName/"


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

    fun getCollectionList(categoryName: String): List<Album> {
        val collectionList = ArrayList<Album>()

        val doc: Document
        val patNumberId = Pattern.compile("\\d+")
        val patStickersType = Pattern.compile("Наклеек")
        var matcher: Matcher

        try {
            doc = Jsoup.connect(getCollectionsUrl(categoryName)).get()
        } catch (e: IOException) {
            return collectionList
        }

        val content = doc.selectFirst("div#content table")

        for (tr in content.select("tr")) {
            for (albumItem in tr.select("td div.album_item")) {

                //Ищем id коллекции
                val albumIcoUrl = albumItem.selectFirst("img.ico_album").attr("src")
                matcher = patNumberId.matcher(albumIcoUrl)
                if (!matcher.find()) continue
                val collId = matcher.group().toInt()

                //Ищем name и title
                val albumTitle = albumItem.selectFirst("h3 > a")
                val strAr = patternUrlSplit.split(albumTitle.attr("href"))
                val collName = if (strAr.size > 0) strAr[strAr.size - 1] else ""
                val collTitle = albumTitle.text()

                //Ищем год
                val yearElement = albumItem.selectFirst("span")
                matcher = patNumberId.matcher(yearElement.text())
                if (!matcher.find()) continue
                val collYear = matcher.group().toInt()

                //Ищем тип наклеек и их количество
                val sizeElement = yearElement.nextElementSibling().nextElementSibling()
                matcher = patStickersType.matcher(sizeElement.text())
                val collStype = if (matcher.find()) Album.STICKER_TYPE else Album.CARD_TYPE
                matcher = patNumberId.matcher(sizeElement.text())
                if (!matcher.find()) continue
                val collSize = matcher.group().toInt()

                //Ищем desc
                val collDesc = albumItem.selectFirst("p").text()

                collectionList.add(
                    Album(
                        collId, collName, collTitle, categoryName,
                        collYear, collStype, collSize, collDesc
                    )
                )
            }
        }

        return collectionList
    }

    companion object {
        @Volatile private var instance: LaststickerHelper? = null

        const val baseUrl = "https://www.laststicker.ru/"
        const val categoryPath = "cards/"
        const val collectionsPath = "s/"

        fun getInstance() : LaststickerHelper {
            return instance ?: synchronized(this) {
                return instance ?: LaststickerHelper().also { instance = it }
            }
        }
    }
}