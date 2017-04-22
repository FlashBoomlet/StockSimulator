package com.flashboomlet.db.controllers

import com.flashboomlet.data.BankAccount
import com.flashboomlet.data.BankAccountTransaction
import com.flashboomlet.db.MongoConstants
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

/**
  * Created by ttlynch on 4/4/17.
  */
class BankAccountController
extends MongoConstants
with LazyLogging
with MongoImplicits {

  /** Instance of pre-configured database driver. */
  val databaseDriver = MongoDatabaseDriver()

  // Insert all of the individual industry related queries

  val bankAccountCollection: BSONCollection = databaseDriver
  .db(BankAccountCollection)

  val bankAccountTransactionCollection: BSONCollection = databaseDriver
  .db(BankAccountTransactionCollection)



  /**
    * Inserts stock data to the appropriate collection
    *
    * @param ba stockData to insert
    */
  def insertBankAccount(ba: BankAccount): Unit = {
    if(!uidExist(ba.uid)){
      databaseDriver.insert(ba, bankAccountCollection)
      logger.info(s"Successfully inserted ${ba.uid} with account number ${ba.accountNumber}")
    } else {
      // The russians are winning. This is not okay.
      logger.error(s"Failed to insert ${ba.uid} with account number ${ba.accountNumber}")
    }
  }

  def updateBankAccount(ba: BankAccount): Unit = {
    val selector = BSONDocument(
      BankAccountConstants.Uid -> BSONString(ba.uid),
      BankAccountConstants.AccountNumber -> BSONLong(ba.accountNumber)
    )
    bankAccountCollection.findAndUpdate(selector, ba).onComplete {
      case Success(result) => // logger.info(s"successfully updated ${sl.symbol} id: ${sl.key}")
      case _ => logger.error(s"failed to update ${ba.uid}'s account: ${ba.accountNumber}")
    }
  }

  def getAccountNumber: Long = {
    val accounts = getAllBankAccounts.map(s => s.accountNumber)
    if(accounts.nonEmpty) accounts.max+1
    else 1
  }

  /**
    * Get BasicIndustries Main retrieves data for the main BasicIndustries collection
    *
    * @param uid the time from which to begin the results
    * @return a list of stock data (order does matter)
    */
  def getBankAccount(
    uid: String): BankAccount =  {

    val selector = BSONDocument(
      BankAccountConstants.Uid -> BSONString(uid)
    )

    Await.result(
      bankAccountCollection
      .find(selector)
      .cursor[BankAccount]()
      .collect[List]()
      .map(list => list.head),
      Duration.Inf)
  }

  /**
    * Get BasicIndustries Main retrieves data for the main BasicIndustries collection
    *
    * @return a list of stock data (order does matter)
    */
  private def getAllBankAccounts(): List[BankAccount] =  {

    val selector = BSONDocument()

    Await.result(
      bankAccountCollection
      .find(selector)
      .cursor[BankAccount]()
      .collect[List](),
      Duration.Inf)
  }

  def uidExist(uid: String): Boolean = {
    val future =  bankAccountCollection
    .find(BSONDocument(
      BankAccountConstants.Uid -> BSONString(uid)
    ))
    .cursor[BSONDocument]()
    .collect[List]()
    .map(list => list.nonEmpty)

    Await.result(future, Duration.Inf)
  }




  def getTransactions(accountNumber: Long): List[BankAccountTransaction] = {
    val selector = BSONDocument(
      BankAccountTransactionConstants.AccountNumber -> BSONLong(accountNumber)
    )

    Await.result(
      bankAccountTransactionCollection
      .find(selector)
      .cursor[BankAccountTransaction]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the appropriate collection
    *
    * @param bat stockData to insert
    */
  def insertBankAccountTransaction(bat: BankAccountTransaction): Unit = {
    databaseDriver.insert(bat, bankAccountTransactionCollection)
  }



  private def clearAllBankAccounts(): Unit = {
    val future = bankAccountCollection.remove(BSONDocument())
    Await.result(future, Duration.Inf)
  }

  private def clearAllBankAccountTransactions(): Unit = {
    val future = bankAccountTransactionCollection.remove(BSONDocument())
    Await.result(future, Duration.Inf)
  }


  def clearAll(): Unit = {
    clearAllBankAccounts
    clearAllBankAccountTransactions
  }
}


/**
  * Companion object for the IndustryController class
  */
object BankAccountController {

  /**
    * Factory constructor method for the IndustryController driver
    *
    * @return a new instance of IndustryController
    */
  def apply(): BankAccountController = new BankAccountController()
}


