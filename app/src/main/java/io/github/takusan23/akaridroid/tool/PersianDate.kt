package io.github.takusan23.akaridroid.tool

import java.util.Calendar
import java.util.Locale

object PersianDate {

    private val persianMonths = arrayOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )

    private val persianWeekDays = arrayOf(
        "شنبه", "یک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه"
    )

    fun gregorianToJalali(gy: Int, gm: Int, gd: Int): Triple<Int, Int, Int> {
        val gDM = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (gm > 2) gy + 1 else gy
        var days: Long = 355666L + (365L * gy) + ((gy2 + 3) / 4) -
                ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + gd + gDM[gm - 1]

        var jy: Int = (-1595 + 33 * (days / 12053)).toInt()
        days %= 12053
        jy += 4 * (days / 1461).toInt()
        days %= 1461
        if (days > 365) {
            jy += ((days - 1) / 365).toInt()
            days = (days - 1) % 365
        }
        val jm: Int
        val jd: Int
        if (days < 186) {
            jm = 1 + (days / 31).toInt()
            jd = 1 + (days % 31).toInt()
        } else {
            jm = 7 + ((days - 186) / 30).toInt()
            jd = 1 + ((days - 186) % 30).toInt()
        }
        return Triple(jy, jm, jd)
    }

    fun today(): String {
        val cal = Calendar.getInstance()
        val (jy, jm, jd) = gregorianToJalali(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        )
        val dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
        return "${persianWeekDays[dayOfWeek]} $jd ${persianMonths[jm - 1]} $jy"
    }

    fun now(): String {
        val cal = Calendar.getInstance()
        return String.format(
            Locale.US,
            "%02d:%02d",
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE)
        )
    }
}