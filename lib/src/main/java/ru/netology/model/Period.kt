package ru.netology.model

import java.time.temporal.ChronoUnit

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