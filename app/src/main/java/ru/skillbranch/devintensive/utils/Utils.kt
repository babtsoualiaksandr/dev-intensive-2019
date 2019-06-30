package ru.skillbranch.devintensive.utils


object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        //  Utils.parseFullName(null) //null null
        //  Utils.parseFullName("") //null null
        //  Utils.parseFullName(" ") //null null
        //  Utils.parseFullName("John") //John null
        //  Utils.parseFullName(" ") //null null

        var firstName: String?
        var lastName : String?
        when (fullName) {
            null, "", " " -> {
                firstName = null
                lastName = null
            }
            else -> {

                val parts: List<String>? = fullName?.split(" ")
                firstName = parts?.getOrNull(0)
                lastName = parts?.getOrNull(1)
            }
        }

        //return  Pair(firstName,lastName)
        return  firstName to lastName

    }


    fun toInitials(firstName: String?, lastName: String?): String? {

        if ((firstName==null) && (lastName==null)) {
            return null
        }

        var i1: String?
        var i2: String?

        when (firstName) {
            null, "", " " -> i1= null
            else -> i1 = firstName[0].toString().toUpperCase()
        }

        when (lastName) {
            null, "", " "  -> i2= null
            else -> i2 = lastName[0].toString().toUpperCase()
        }

        var result: String?
        if ((i1 == null) && (i2 != null))
        {
            result = i2
        } else {
            if ((i1 != null) && (i2 == null)) {
                result = i1
            } else {
                result = "$i1$i2"
            }
        }

        return result
    }

    fun transliteration(payload: String, divider: String? = " "): String {

        val lexicon = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya",

            "А" to "A",
            "Б" to "B",
            "В" to "V",
            "Г" to "G",
            "Д" to "D",
            "Е" to "E",
            "Ё" to "E",
            "Ж" to "ZH",
            "З" to "Z",
            "И" to "I",
            "Й" to "I",
            "К" to "K",
            "Л" to "L",
            "М" to "M",
            "Н" to "N",
            "О" to "O",
            "П" to "P",
            "Р" to "R",
            "С" to "S",
            "Т" to "T",
            "У" to "U",
            "Ф" to "F",
            "Х" to "H",
            "Ц" to "C",
            "Ч" to "CH",
            "Ш" to "SH",
            "Щ" to "SH'",
            "Ъ" to "",
            "Ы" to "I",
            "Ь" to "",
            "Э" to "E",
            "Ю" to "YU",
            "Я" to "YA"
        )

        var parts: List<String> =  payload?.split(divider.orEmpty())
        var resultList: MutableList<String> = mutableListOf()

        for (partsitem in parts) {
            val builder = StringBuilder()
            for (i in 0..partsitem.length-1) {
                if (partsitem[i].toString() in lexicon) {
                    val item = if (i==0) {lexicon[partsitem[i].toString().toUpperCase()]} else { lexicon[partsitem[i].toString()]}
                    builder.append(item)
                } else if (i==0) { builder.append(partsitem[i].toString().toUpperCase())} else { builder.append(partsitem[i].toString()) }
            }
            resultList.add(builder.toString())
        }

        var result: String=""
        for (i in 0..resultList.count()-1) {
            if (i!= resultList.count()-1) {result = result + resultList[i] + divider} else {result = result + resultList[i]}
        }
        return result
    }
}