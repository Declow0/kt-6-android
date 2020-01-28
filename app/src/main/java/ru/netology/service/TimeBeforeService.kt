package ru.netology.service

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun beforeNowMessage(timeFrom: LocalDateTime): String {
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

enum class Period(val chronoUnit: ChronoUnit) {
    SECONDS(ChronoUnit.SECONDS) {
        override fun wordForm(wordForm: WordForm) = "минуты"
    },
    MINUTES(ChronoUnit.MINUTES) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "минуту"
            WordForm.SECOND -> "минуты"
            WordForm.THIRD -> "минут"
        }
    },
    HOURS(ChronoUnit.HOURS) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "час"
            WordForm.SECOND -> "часа"
            WordForm.THIRD -> "часов"
        }
    },
    DAYS(ChronoUnit.DAYS) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "день"
            WordForm.SECOND -> "дня"
            WordForm.THIRD -> "дней"
        }
    },
    WEEKS(ChronoUnit.WEEKS) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "неделю"
            WordForm.SECOND -> "недели"
            WordForm.THIRD -> "недель"
        }
    },
    MONTHS(ChronoUnit.MONTHS) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "месяц"
            WordForm.SECOND -> "месяца"
            WordForm.THIRD -> "месяцев"
        }
    },
    YEARS(ChronoUnit.YEARS) {
        override fun wordForm(wordForm: WordForm) = when (wordForm) {
            WordForm.FIRST -> "год"
            WordForm.SECOND -> "года"
            WordForm.THIRD -> "лет"
        }
    };

    abstract fun wordForm(wordForm: WordForm): String
}

enum class WordForm {
    FIRST,
    SECOND,
    THIRD
}