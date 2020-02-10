package ru.netology.service

import ru.netology.model.Period
import ru.netology.model.WordForm
import java.time.LocalDateTime

fun intervalBetweenNowMessage(timeFrom: LocalDateTime): String {
    val now = LocalDateTime.now()

    val (period, amount) = betweenInterval(timeFrom, now)
    val wordForm = wordFormForNumber(amount)

    return if (period == Period.SECONDS) {
        "менее "
    } else {
        if (amount == 1L) {
            ""
        } else {
            "$amount "
        }
    } + "${period.wordForm(wordForm)} назад"
}

private fun wordFormForNumber(value: Long): WordForm {
    return when (value % 10L) {
        1L -> if (value % 100L == 11L) WordForm.THIRD else WordForm.FIRST
        2L, 3L, 4L -> if (value % 100L in 12L..14L) WordForm.THIRD else WordForm.SECOND
        else -> WordForm.THIRD
    }
}

private fun betweenInterval(
    from: LocalDateTime,
    to: LocalDateTime
): Pair<Period, Long> {
    Period.values()
        .sortedArrayDescending()
        .forEach {
            val between = it.chronoUnit.between(from, to)
            if (between > 0)
                return Pair(it, between)
        }
    return Pair(Period.SECONDS, 0L)
}
