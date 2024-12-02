package org.example

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

const val BASE_URL =
    "https://mybook.ru/author/duglas-adams/avtostopom-po-galaktike-restoran-u-konca-vselennoj/citations/?page=1"

//Попробовал реализовать парсинг всех цитат по книге (сделал из интереса, прошу сильно не ругаться))))
// В общем работает правда очень медленно. Либо я коряво написал, либо связанно с самим ресурсом.
//Если потребуется переделаю под ТЗ. Интересно что я сделал не так.
fun main() {
    var quotes: MutableList<String> = mutableListOf()
    var curentURL: String = BASE_URL
    var isLastPage = false

    while (!isLastPage) {

        var doc: Document = Jsoup.connect(curentURL).get()
        var pageLinks: Elements = doc.select("a.sc-1agxp9d-0 ") //парсинг элементов навигации

        if (pageLinks[1].absUrl("href") == doc.baseUri()) { // Проверка на последнюю страницу
            isLastPage = true                                          //Если текущая ссылка равна следующей значит больше страниц нет
        }
        var elements: Elements = doc.select("article div div div") // парсинг цитат в массив
        for (element in elements) {
            quotes.add(element.text())
        }
        curentURL = pageLinks[1].absUrl("href")  // переключение на следующую страницу
    }


}

