package ru.netology.service

import ru.netology.model.Period
import java.time.LocalDateTime

object LocalDateTimeService {
    fun betweenInterval(
        from: LocalDateTime,
        to: LocalDateTime
    ): Pair<Period, Long> {
        Period.values()
            .sortedArrayDescending()
            .forEach {
                val between = it.chronoUnit.between(from, to)
                if (between > 0)
                    return it to between
            }
        return Period.SECONDS to 0L
    }
}
