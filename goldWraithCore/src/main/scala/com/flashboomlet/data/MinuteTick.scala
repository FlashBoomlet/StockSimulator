package com.flashboomlet.data

/**
  * Created by ttlynch on 10/29/16.
  */
case class MinuteTick(
  company: String,
  symbol: String,
  time: Long,
  ask: Long,
  bid: Long
)
