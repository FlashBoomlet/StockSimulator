package com.flashboomlet.db.controllers

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoConstants
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by ttlynch on 4/4/17.
  */
class IndustryController
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  /** Instance of pre-configured database driver. */
  val databaseDriver = MongoDatabaseDriver()

  // Insert all of the individual industry related queries

  val basicIndustriesMainCollection: BSONCollection = databaseDriver
    .db(BasicIndustriesMainCollection)

  val capitalGoodsMainCollection: BSONCollection = databaseDriver
    .db(CapitalGoodsMainCollection)

  val consumerDurablesMainCollection: BSONCollection = databaseDriver
    .db(ConsumerDurablesMainCollection)

  val consumerNonDurablesMainCollection: BSONCollection = databaseDriver
    .db(ConsumerNonDurablesMainCollection)

  val consumerServicesMainCollection: BSONCollection = databaseDriver
    .db(ConsumerServicesMainCollection)

  val energyMainCollection: BSONCollection = databaseDriver
    .db(EnergyMainCollection)

  val financeMainCollection: BSONCollection = databaseDriver
    .db(FinanceMainCollection)

  val healthCareMainCollection: BSONCollection = databaseDriver
    .db(HealthCareMainCollection)

  val miscellaneousMainCollection: BSONCollection = databaseDriver
    .db(MiscellaneousMainCollection)

  val otherMainCollection: BSONCollection = databaseDriver
    .db(OtherMainCollection)

  val publicUtilitiesMainCollection: BSONCollection = databaseDriver
    .db(PublicUtilitiesMainCollection)

  val technologyMainCollection: BSONCollection = databaseDriver
    .db(TechnologyMainCollection)

  val transportationMainCollection: BSONCollection = databaseDriver
    .db(TransportationMainCollection)

  /**
    * Inserts stock data to the appropriate collection
    *
    * @param sd stockData to insert
    */
  def insertBasicIndustriesMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, basicIndustriesMainCollection)
  }

  /**
    * Get BasicIndustries Main retrieves data for the main BasicIndustries collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getBasicIndustriesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      basicIndustriesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the capitalGoodsMainCollection
    *
    * @param sd stockData to insert
    */
  def insertCapitalGoodsMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, capitalGoodsMainCollection)
  }

  /**
    * Get CapitalGoods Main retrieves data for the main CapitalGoods collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getCapitalGoodsMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      capitalGoodsMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerDurablesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerDurablesMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, consumerDurablesMainCollection)
  }

  /**
    * Get ConsumerDurables Main retrieves data for the main ConsumerDurables collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerDurablesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerDurablesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerNonDurablesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerNonDurablesMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, consumerNonDurablesMainCollection)
  }

  /**
    * Get ConsumerNonDurables Main retrieves data for the main ConsumerNonDurables collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerNonDurablesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerNonDurablesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerServicesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerServicesMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, consumerServicesMainCollection)
  }

  /**
    * Get ConsumerServices Main retrieves data for the main ConsumerServices collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerServicesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerServicesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the energyMainCollection
    *
    * @param sd stockData to insert
    */
  def insertEnergyMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, energyMainCollection)
  }

  /**
    * Get Energy Main retrieves data for the main Energy collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getEnergyMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      energyMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the financeMainCollection
    *
    * @param sd stockData to insert
    */
  def insertFinanceMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, financeMainCollection)
  }

  /**
    * Get Finance Main retrieves data for the main Finance collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getFinanceMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      financeMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the healthCareMainCollection
    *
    * @param sd stockData to insert
    */
  def insertHealthCareMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, healthCareMainCollection)
  }

  /**
    * Get HealthCare Main retrieves data for the main HealthCare collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getHealthCareMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      healthCareMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the miscellaneousMainCollection
    *
    * @param sd stockData to insert
    */
  def insertMiscellaneousMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, miscellaneousMainCollection)
  }

  /**
    * Get Miscellaneous Main retrieves data for the main Miscellaneous collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getMiscellaneousMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      miscellaneousMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the otherMainCollection
    *
    * @param sd stockData to insert
    */
  def insertOtherMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, otherMainCollection)
  }

  /**
    * Get Other Main retrieves data for the main Other collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getOtherMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      otherMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the publicUtilitiesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertPublicUtilitiesMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, publicUtilitiesMainCollection)
  }

  /**
    * Get PublicUtilities Main retrieves data for the main PublicUtilities collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getPublicUtilitiesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      publicUtilitiesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the technologyMainCollection
    *
    * @param sd stockData to insert
    */
  def insertTechnologyMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, technologyMainCollection)
  }

  /**
    * Get Technology Main retrieves data for the main Technology collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getTechnologyMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      technologyMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the transportationMainCollection
    *
    * @param sd stockData to insert
    */
  def insertTransportationMain(sd: StockData): Unit = {
    databaseDriver.insert(sd, transportationMainCollection)
  }

  /**
    * Get Transportation Main retrieves data for the main transportation collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getTransportationMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = databaseDriver.stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      transportationMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  def clearAllIndustries(): Unit = {

    val one = basicIndustriesMainCollection.remove(BSONDocument())
    Await.result(one, Duration.Inf)

    val two = capitalGoodsMainCollection.remove(BSONDocument())
    Await.result(two, Duration.Inf)

    val three = consumerDurablesMainCollection.remove(BSONDocument())
    Await.result(three, Duration.Inf)

    val four = consumerNonDurablesMainCollection.remove(BSONDocument())
    Await.result(four, Duration.Inf)

    val five = consumerServicesMainCollection.remove(BSONDocument())
    Await.result(five, Duration.Inf)

    val six = energyMainCollection.remove(BSONDocument())
    Await.result(six, Duration.Inf)

    val seven = financeMainCollection.remove(BSONDocument())
    Await.result(seven, Duration.Inf)

    val eight = healthCareMainCollection.remove(BSONDocument())
    Await.result(eight, Duration.Inf)

    val nine = miscellaneousMainCollection.remove(BSONDocument())
    Await.result(nine, Duration.Inf)

    val ten = otherMainCollection.remove(BSONDocument())
    Await.result(ten, Duration.Inf)

    val eleven = publicUtilitiesMainCollection.remove(BSONDocument())
    Await.result(eleven, Duration.Inf)

    val twelve = technologyMainCollection.remove(BSONDocument())
    Await.result(twelve, Duration.Inf)

    val thirteen = transportationMainCollection.remove(BSONDocument())
    Await.result(thirteen, Duration.Inf)
  }

}


/**
  * Companion object for the IndustryController class
  */
object IndustryController {

  /**
    * Factory constructor method for the IndustryController driver
    *
    * @return a new instance of IndustryController
    */
  def apply(): IndustryController = new IndustryController()
}


