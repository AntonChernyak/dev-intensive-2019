package ru.skillbranch.devintensive.utils

import kotlin.collections.HashMap

object Utils {
    // Получаем из полного имени отдельно имя и фамилию
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        val firstName = if (parts?.getOrNull(0).isNullOrEmpty()) null else parts?.get(0)
        val lastName = if (parts?.getOrNull(1).isNullOrEmpty()) null else parts?.get(1)
        return firstName to lastName
    }

    // Переводим кириллицу в транслит
    fun transliteration(payload: String, divider: String = " "): String {
        val map = createSymbolsMap()
        var result = ""
        for (char in payload.toCharArray()) {
            if (char.toString() == " ") {
                result += divider
                continue
            }
            val newSymbol: String? = map[char.toLowerCase().toString()]
            result += if (char.isLowerCase()) newSymbol else newSymbol?.replaceFirst(newSymbol.first(), newSymbol.first().toUpperCase())
        }
        return result
    }

    // Получем первые буквы имени и фамилии или null
    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstNameCheck = firstName?.trim().isNullOrEmpty()
        val lastNameCheck = lastName?.trim().isNullOrEmpty()
        return when {
            !firstNameCheck && !lastNameCheck -> "${firstName?.first()?.toUpperCase()}${lastName?.first()?.toUpperCase()}"
            firstNameCheck && !lastNameCheck -> lastName?.first()?.toUpperCase().toString()
            !firstNameCheck && lastNameCheck -> firstName?.first()?.toUpperCase().toString()
            else -> null
        }
    }

    // Таблица преобразования кариллицы в транслит
    private fun createSymbolsMap() : HashMap<String, String> {
        val symbols = HashMap<String, String>()
        if (symbols.isEmpty()) {
            symbols["а"] = "a"
            symbols["б"] = "b"
            symbols["в"] = "v"
            symbols["г"] = "g"
            symbols["д"] = "d"
            symbols["е"] = "e"
            symbols["ё"] = "e"
            symbols["ж"] = "zh"
            symbols["з"] = "z"
            symbols["и"] = "i"
            symbols["й"] = "i"
            symbols["к"] = "k"
            symbols["л"] = "l"
            symbols["м"] = "m"
            symbols["н"] = "n"
            symbols["о"] = "o"
            symbols["п"] = "p"
            symbols["р"] = "r"
            symbols["с"] = "s"
            symbols["т"] = "t"
            symbols["у"] = "u"
            symbols["ф"] = "f"
            symbols["х"] = "h"
            symbols["ц"] = "c"
            symbols["ч"] = "ch"
            symbols["ш"] = "sh"
            symbols["щ"] = "sh'"
            symbols["ъ"] = ""
            symbols["ы"] = "i"
            symbols["ь"] = ""
            symbols["э"] = "e"
            symbols["ю"] = "yu"
            symbols["я"] = "ya"
        }
        return symbols
    }
}