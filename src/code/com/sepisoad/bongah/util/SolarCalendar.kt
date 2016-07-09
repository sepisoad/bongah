package com.sepisoad.bongah.util

import java.util.*

class SolarCalendar {
  var strWeekDay = ""
  var strMonth = ""
  var date: Int = 0
  var month: Int = 0
  var year: Int = 0

  constructor() {
    val MiladiDate = Date()
    calcSolarCalendar(MiladiDate);
  }

  constructor(miladiDate: Date) {
    calcSolarCalendar(miladiDate)
  }

  fun calcSolarCalendar(miladiDate: Date): Unit {
    var ld: Int
    var miladiYear: Int = Calendar.YEAR + 1900
    var miladiMonth: Int = Calendar.MONTH + 1
    var miladiDate: Int = Calendar.DATE
    var WeekDay: Int = Calendar.DAY_OF_MONTH

    var buf1 = IntArray(12)
    var buf2 = IntArray(12)

    buf1[0] = 0
    buf1[1] = 31
    buf1[2] = 59
    buf1[3] = 90
    buf1[4] = 120
    buf1[5] = 151
    buf1[6] = 181
    buf1[7] = 212
    buf1[8] = 243
    buf1[9] = 273
    buf1[10] = 304
    buf1[11] = 334

    buf2[0] = 0
    buf2[1] = 31
    buf2[2] = 60
    buf2[3] = 91
    buf2[4] = 121
    buf2[5] = 152
    buf2[6] = 182
    buf2[7] = 213
    buf2[8] = 244
    buf2[9] = 274
    buf2[10] = 305
    buf2[11] = 335

    if ((miladiYear % 4) != 0) {
      date = buf1[miladiMonth - 1] + miladiDate

      if (date > 79) {
        date -= 79
        if (date <= 186) {
          when (date % 31) {
            0 -> {
              month = date / 31
              date = 31
            }
            else -> {
              month = (date / 31) + 1
              date = (date % 31)
            }
          }
          year = miladiYear - 621
        } else {
          date -= 186

          when (date % 30) {
            0 -> {
              month = (date / 30) + 6
              date = 30
            }
            else ->{
              month = (date / 30) + 7
              date = (date % 30)
            }
          }
          year = miladiYear - 621
        }
      } else {
        if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
          ld = 11
        } else {
          ld = 10
        }
        date += ld

        when (date % 30) {
          0 -> {
            month = (date / 30) + 9
            date = 30
          }
          else -> {
            month = (date / 30) + 10
            date = (date % 30)
          }
        }
        year = miladiYear - 622
      }
    } else {
      date = buf2[miladiMonth - 1] + miladiDate

      if (miladiYear >= 1996) {
        ld = 79
      } else {
        ld = 80
      }
      if (date > ld) {
        date -= ld

        if (date <= 186) {
          when (date % 31) {
            0 -> {
              month = (date / 31)
              date = 31
            }
            else -> {
              month = (date / 31) + 1
              date = (date % 31)
            }
          }
          year = miladiYear - 621
        } else {
          date = date - 186;

          when (date % 30) {
            0 -> {
              month = (date / 30) + 6
              date = 30;
            }
            else -> {
              month = (date / 30) + 7
              date = (date % 30)
            }
          }
          year = miladiYear - 621
        }
      }

      else {
        date += 10
        when (date % 30) {
          0 -> {
            month = (date / 30) + 9
            date = 30
          }
          else -> {
            month = (date / 30) + 10
            date = (date % 30)
          }
        }
        year = miladiYear - 622
      }
    }

    when (month) {
      1 -> strMonth = "فروردين"
      2 -> strMonth = "ارديبهشت"
      3 -> strMonth = "خرداد"
      4 -> strMonth = "تير"
      5 -> strMonth = "مرداد"
      6 -> strMonth = "شهريور"
      7 -> strMonth = "مهر"
      8 -> strMonth = "آبان"
      9 -> strMonth = "آذر"
      10 -> strMonth = "دي"
      11 -> strMonth = "بهمن"
      12 -> strMonth = "اسفند"
    }

    when(WeekDay) {
      0 -> strWeekDay = "يکشنبه"
      1 -> strWeekDay = "دوشنبه"
      2 -> strWeekDay = "سه شنبه"
      3 -> strWeekDay = "چهارشنبه"
      4 -> strWeekDay = "پنج شنبه"
      5 -> strWeekDay = "جمعه"
      6 -> strWeekDay = "شنبه"
    }
  }

  companion object CalculateCurrentSolarDare{
    fun get(): String{
      val loc = Locale("en_US")
      val sc = SolarCalendar()
      return sc.year.toString() + "/" + String.format(loc, "%02d", sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }
  }
}


