package ru.skillbranch.devintensive.extensions

// Обрезаем строку. Если в конце получился пробел, то заменить его на ..., если мы реально обрезали, то в конце просто добавить ...
fun String.truncate(count: Int = 16): String {
    var newString = this.trim().take(count)
    if (newString.last().toString() == " ") {
        newString = newString.replaceRange(newString.length - 1, newString.length, "...")
        return newString
    }
    if (this.trim().length > count) return "${newString}..."
    return newString
}

// Метод для очистки строки от лишних пробелов, html тегов, escape последовательностей
fun String.stripHtml(): String {
    val clearString = this.replace("<[^>]*>".toRegex(), "")
    return clearString.replace("\\s+".toRegex(), " ")
}