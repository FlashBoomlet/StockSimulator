package com.flashboomlet.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

/**
  * Created by ttlynch on 2/12/17.
  */
object DateUtil {

  /*

    d - Numeric day of the month without a leading zero.
    dd - Numeric day of the month with a leading zero.
    ddd - Abbreviated name of the day of the week.
    dddd - Full name of the day of the week.

    M - Numeric month with no leading zero.
    MM - Numeric month with a leading zero.
    MMM - Abbreviated name of month.
    MMMM - Full month name.

    y - Year with out century and leading zero.
    yy - Year with out century, with leading zero.
    yyyy - Year with century.


    h - 12 Hour clock, no leading zero.
    hh - 12 Hour clock with leading zero.
    H - 24 Hour clock, no leading zero.
    HH - 24 Hour clock with leading zero.

    m - Minutes with no leading zero.
    mm - Minutes with leading zero.

    s - Seconds with no leading zero.
    ss - Seconds with leading zero.

    t - AM/PM but only the first letter.
    tt - AM/PM ( a.m. / p.m.)

    zz - Time zone off set with +/-
   */
  val calendar = new GregorianCalendar()
  def dateToYear(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.YEAR)
  }

  def dateToMonth(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.MONTH) + 1
  }

  def dateToDay(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.DAY_OF_MONTH)
  }

  def formattedTimeToMillis(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minutes: Int,
    seconds: Int,
    pm: Int): Long = {

    val gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"))
    gc.clear()
    gc.set(year, month, day)

    val left = gc.getTimeInMillis()
    left + (hour * 3600000) + (pm * 43200000) + (minutes * 60000) + (seconds * 1000)
  }

  def getFormattedTimeInMillis(formattedTime: String): Long = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH)
    format.parse(formattedTime).getTime - 21600000// subtract from GMT to local
  }

  def getNowInMillis: Long = new Date().getTime
}
