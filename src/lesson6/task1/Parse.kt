@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalStateException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf(
    "января", "февраля", "марта", "апреля",
    "мая", "июня", "июля", "августа",
    "сентября", "октября", "ноября", "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val ind = months.indexOf(parts[1])
    val month = if (ind != -1) ind + 1 else return ""
    val day = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if ((day == null) || (year == null) || (day < 1) || (year < 0) || (day > daysInMonth(month, year))) return ""
    return "%02d.%02d.%d".format(day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val day = parts[0].toIntOrNull()
    val month = parts[1].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if ((day == null) || (year == null) || (month == null) ||
        (month !in 1..12) || (day < 1) || (year < 0) || (day > daysInMonth(month, year))
    ) return ""
    return "$day ${months[month - 1]} $year"
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */

fun flattenPhoneNumber(phone: String): String =
    if (!Regex("""^(\+\d+)?(\(\d+\))?\d+$""").matches(Regex("""[\s-]""").replace(phone, "")))
        "" else Regex("""[\s-()]""").replace(phone, "")

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ")
    var maxJump = -1
    for (i in parts) {
        if ((i != "%") && (i != "-"))
            try {
                val n = i.toIntOrNull() ?: return -1
                maxJump = kotlin.math.max(maxJump, n)
            } catch (e: NumberFormatException) {
                return -1
            }
    }
    return maxJump
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val correctChars = setOf('+', '%', '-')
    val parts = jumps.split(' ')
    if (parts.size % 2 != 0) return -1
    var bestResult = -1
    for (i in parts.indices step 2) {
        val result = parts[i].toIntOrNull() ?: return -1
        if (!parts[i + 1].all { it in correctChars }) return -1
        if ('+' in parts[i + 1]) bestResult = kotlin.math.max(bestResult, result)
    }
    return bestResult
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val list = expression.split(' ')
    require(list[0] != "")
    var res = 0
    var mark = 1
    for ((ind, element) in list.withIndex()) {
        if (ind % 2 == 0) {
            require(element.all { it in '0'..'9' })
            res += element.toInt() * mark
        } else mark = when (element) {
            "+" -> 1
            "-" -> -1
            else -> throw IllegalArgumentException()
        }
    }
    return res
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val list = str.split(' ')
    var index = 0
    for ((first, second) in list.zipWithNext()) {
        if (first.toLowerCase() == second.toLowerCase()) return index
        index += first.length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var mostExp = -1.0
    var mostExpName = ""
    val list = description.split("; ")
    for (i in list) {
        val parts = i.split(' ')
        if (parts.size != 2) return ""
        val num = parts[1].toDoubleOrNull()
        if ((num == null) || (num < 0.0)) return ""
        if (mostExp < num) {
            mostExp = num
            mostExpName = parts[0]
        }
    }
    return mostExpName
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var res = 0
    val d = mapOf(
        "M" to 1000, "CM" to 900, "DCCC" to 800, "DCC" to 700, "DC" to 600, "D" to 500,
        "CD" to 400, "CCC" to 300, "CC" to 200, "C" to 100, "XC" to 90, "LXXX" to 80,
        "LXX" to 70, "LX" to 60, "L" to 50, "XL" to 40, "XXX" to 30, "XX" to 20, "X" to 10,
        "IX" to 9, "VIII" to 8, "VII" to 7, "VI" to 6, "V" to 5, "IV" to 4, "III" to 3, "II" to 2, "I" to 1
    )
    if (roman == "") return -1
    var startIndex = 0
    var endIndex = 0
    var previewNum = 1001
    while (startIndex < roman.length) {
        var wasIn = false
        while (roman.substring(startIndex, endIndex + 1) in d) {
            wasIn = true
            endIndex++
            if (endIndex == roman.length) break
        }
        if (!wasIn) return -1
        val newNum = d.getOrDefault(roman.substring(startIndex, endIndex), 0)
        if (newNum > previewNum) return -1
        res += newNum
        previewNum = newNum
        startIndex = endIndex
    }
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    //Проверка на наличие лишних символов
    val setCorrectCommands = setOf('>', '<', '+', '-', '[', ']', ' ')
    require((commands.toSet() - setCorrectCommands).isEmpty())
    //Проверка на наличие пары у квадратных скобок
    var k = 0
    for (i in commands.indices) {
        when (commands[i]) {
            '[' -> k++
            ']' -> k--
        }
        require(k >= 0)
    }
    require(k == 0)
    //Начальная инициализация
    var position = cells / 2
    var countCommands = 0
    var indexCommands = 0
    val queueBrackets = mutableListOf<Int>()
    val transporter = MutableList(cells) {0}
    //Основная логика функции
    while ((countCommands < limit) && (indexCommands < commands.length)) {
        when (commands[indexCommands]) {
            '>' -> {
                check(position != cells - 1)
                position++
            }
            '<' -> {
                check(position != 0)
                position--
            }
            '+' -> transporter[position]++
            '-' -> transporter[position]--
            '[' -> {
                //Поиск индекса ']' для данной скобки
                var secondIndex = indexCommands + 1
                var h = 0
                while (secondIndex < commands.length) {
                    if (commands[secondIndex] == '[') h++
                    if (commands[secondIndex] == ']')
                        if (h != 0) h--
                        else break
                    secondIndex++
                }
                //Условие перехода
                if (transporter[position] == 0) indexCommands = secondIndex
                else queueBrackets.add(indexCommands)
            }
            ']' -> if (transporter[position] != 0) indexCommands = queueBrackets.last()
            else queueBrackets.removeAt(queueBrackets.size - 1)
        }
        countCommands++
        indexCommands++
    }
    return transporter
}
