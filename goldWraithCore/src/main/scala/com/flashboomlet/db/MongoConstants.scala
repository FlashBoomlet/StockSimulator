package com.flashboomlet.db

/**
  * Yeah, that's right. We got dank mongo constants
  *
  * The val's name must be HumpBack case
  * The string constant must be ALL lower case
  *
  * Collections must correspond
  */
trait MongoConstants {

  // DATABASE CONFIGURATION VALUES //

  /* String constant for connecting to MongoDB */
  final val DatabaseIp = "localhost"

  /* String constant for the database name */
  final val TheGoldWraithDatabaseString = "goldWraith"

  /* String constant for the conversation state collection */
  final val MinuteTickCollection = "minuteTick"


  /**
    * String constants used in the conversation state collection model schema
    */
  object MinuteTickConstants {

    /* String constant for the `Company` field in a MongoDB MinuteTick document */
    final val Company = "company"

    /* String constant for the `Symbol` field in a MongoDB MinuteTick document */
    final val Symbol = "symbol"

    /* String constant for the `Time` field in a MongoDB MinuteTick document */
    final val Time = "time"

    /* String constant for the `Ask` field in a MongoDB MinuteTick document */
    final val Ask = "ask"

    /* String constant for the `Bid` field in a MongoDB MinuteTick document */
    final val Bid = "bid"

  }

  /**
    * String constants used across the entirity of the MongoDB database
    */
  object GlobalConstants {

    /* String constant for the `_id` field in a MongoDB document */
    final val IdString = "_id"

    final val SetString = "$set"

    final val ElemMatchString = "$elemMatch"
  }

}
