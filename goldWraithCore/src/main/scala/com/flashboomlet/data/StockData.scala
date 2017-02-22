package com.flashboomlet.data


/**
  * Created by ttlynch on 2/12/17.
  *
  * @param company the company name being stored
  * @param symbol the symbol that corresponds to the company
  * @param exchange the market or exchange that tth company is in
  * @param time the time stamp that corresponds to the entry
  * @param lastTrade the last or latest trading price that corresponds to the time.
  * @param open the opening price
  * @param ask the ask price (what was asked for, helpful in candle stick charts and that is it)
  * @param bid the bid price (what was paid, helpful in candle stick charts and that is it)
  * @param volume the volume of the stock
  * @param dayLow the low of the day
  * @param dayHigh the high of the day
  * @param source the source that the data came from
  * @param fetchDate the date that the data was fetched on
  */
case class StockData(
  key: Int,
  company: String,
  symbol: String,
  exchange: String,
  time: Long,
  lastTrade: Long,
  open: Long,
  ask: Long,
  bid: Long,
  volume: Long,
  dayLow: Long,
  dayHigh: Long,
  source: String,
  fetchDate: Long
)
