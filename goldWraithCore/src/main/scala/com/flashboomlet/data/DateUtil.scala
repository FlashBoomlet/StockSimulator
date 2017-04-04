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

  val calendar = new GregorianCalendar()

  /**
    * date to Year takes a milliseconds formatted dateTime and gets the year
    *
    * @param datetime a milliseconds formatted dateTime
    * @return the year in the time
    */
  def dateToYear(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.YEAR)
  }

  /**
    * date to Year takes a milliseconds formatted dateTime and gets the Month
    *
    * @param datetime a milliseconds formatted dateTime
    * @return the Month in the time
    */
  def dateToMonth(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.MONTH) + 1
  }

  /**
    * date to Year takes a milliseconds formatted dateTime and gets the Day
    *
    * @param datetime a milliseconds formatted dateTime
    * @return the day in the time
    */
  def dateToDay(datetime: Long): Int = {
    calendar.setTime(new Date(datetime))
    calendar.get(Calendar.DAY_OF_MONTH)
  }

  /**
    * Date and Time to Time combines a seperate data and time and then converts it into
    * one milliseconds formatted time.
    *
    * @param date a date string in the format of "12-12-1234"
    * @param time a time string in the format of "12:34.56 PM"
    * @return a milliseconds formatted time
    */
  def dateandTimetoTime(date: String, time: String): Long = {
    val rawDate = date.split('-')
    val subTime = time.split(':')
    val rawTime = rawDate.flatMap(s => s.split('.')).flatMap(s => s.split(' '))

    val pm = if(rawTime(3) == "PM" ) { 2 } else { 1 }

    formattedTimeToMillis(
      rawDate(2).toInt,
      rawDate(1).toInt,
      rawDate.head.toInt,
      rawTime.head.toInt,
      rawTime(1).toInt,
      rawTime(2).toInt,
      pm
    )
  }

  /**
    * Formatted Time to Mills converts seperate data and time components and returns
    *
    * @param year the year that you wish to use
    * @param month the month that you wish to use
    * @param day the day that you wish to use
    * @param hour the hour that you wish to use
    * @param minutes the amount of minutes that you with to use
    * @param seconds the amount of seconds that you wish to use
    * @param pm a flag for weather to multiply the hours variable. (1=AM, 2=PM)
    * @return a milliseconds formatted time
    */
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

  /**
    * get FormattedTime in Millis takes a preformatted Time and converts it to the time in
    * milliseconds
    *
    * @param formattedTime a formatted time (String)
    * @return a milliseconds formatted time
    */
  def getFormattedTimeInMillis(formattedTime: String): Long = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH)
    format.parse(formattedTime).getTime - 21600000// subtract from GMT to local
  }

  /**
    * Get Now in Millis gets the current time in Milliseconds
    *
    * @return the current milliseconds formatted time
    */
  def getNowInMillis: Long = new Date().getTime
}
