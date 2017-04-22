package com.flashboomlet.controls

import com.flashboomlet.data.BankAccount
import com.flashboomlet.data.BankAccountTransaction
import com.flashboomlet.data.DateUtil
import com.flashboomlet.db.controllers.BankAccountController

/**
  * Created by ttlynch on 4/20/17.
  */
class BankAccountInfo {

  val bac = new BankAccountController
  val du = new DateUtil

  def accountInfo(uid: String): BankAccount = {
    // Check to make sure that there is an account for the uid specified.
    // Get Account Info and return it.
    bac.getBankAccount(uid)
  }

  def createAccount(uid: String, first: String, last: String): BankAccount = {
    // Create a user account
    val accountNum = bac.getAccountNumber
    val account = BankAccount(
      accountNum,
      uid,
      first,
      last,
      0,
      0
    )
    bac.insertBankAccount(account)
    // Print Account Number
    account
  }

  def deposit(uid: String, amount: Double, account: String): Unit = {
    // Check to make sure that there is an account for the uid specified.
    val ba = bac.getBankAccount(uid)
    createTransaction(ba, "deposit", amount, account)
    if(account == "checking"){
      bac.updateBankAccount(
        BankAccount(
          ba.accountNumber,
          ba.uid,
          ba.first,
          ba.last,
          ba.checking + amount,
          ba.saving
        )
      )
    } else {
      bac.updateBankAccount(
        BankAccount(
          ba.accountNumber,
          ba.uid,
          ba.first,
          ba.last,
          ba.checking,
          ba.saving + amount
        )
      )
    }
  }

  def withdraw(uid: String, amount: Double, account: String): Boolean = {
    // Check to make sure that there is an account for the uid specified.
    val ba = bac.getBankAccount(uid)
    createTransaction(ba, "deposit", amount, account)
    if(account == "checking"){
      if(ba.checking - amount > 0) {
        bac.updateBankAccount(
          BankAccount(
            ba.accountNumber,
            ba.uid,
            ba.first,
            ba.last,
            ba.checking - amount,
            ba.saving
          )
        )
        true
      } else false
    } else {
      if(ba.saving - amount > 0) {
        bac.updateBankAccount(
          BankAccount(
            ba.accountNumber,
            ba.uid,
            ba.first,
            ba.last,
            ba.checking,
            ba.saving - amount
          )
        )
        true
      } else false
    }
  }

  def getTransactions(uid: String): List[BankAccountTransaction] = {
    bac.getTransactions(bac.getBankAccount(uid).accountNumber)
  }

  def createTransaction(
    bankAccount: BankAccount,
    action: String,
    amount: Double,
    account: String): Unit = {

    val description = action + " " + amount.toString + " into " + account
    val debit = if(action == "withdraw"){ amount } else { 0 }
    val credit = if(action == "withdraw"){ amount } else { 0 }
    val balance = if(action == "withdraw"){
      if(account == "checking"){
        bankAccount.checking - amount
      } else {
        bankAccount.saving - amount
      }
    } else {
      if(account == "checking"){
        bankAccount.checking + amount
      } else {
        bankAccount.saving + amount
      }
    }

    val transaction = BankAccountTransaction(
      bankAccount.accountNumber,
      du.getNowInMillis,
      action,
      description,
      debit,
      credit,
      balance
    )

    bac.insertBankAccountTransaction(transaction)
  }

  def uidExist(uid: String): Boolean = {
    bac.uidExist(uid)
  }

  def fundsPresent(uid: String, amount: Double): Boolean = {
    // By Default we will only check checking accounts. Saving's funds must be transferred.
    val ba = bac.getBankAccount(uid)
    if(ba.checking-amount >= 0.0) true
    else false
  }

  def transferFunds(uid: String, fromAccount: String, amount: Double): Boolean = {
    if(fromAccount == "checking") {
      if(fundsPresent(uid, amount)) {
        withdraw(uid, amount, "checking")
        deposit(uid, amount, "saving")
        true
      } else {
        false
      }
    } else {
      val ba = bac.getBankAccount(uid)
      if(ba.saving-amount >= 0.0) {
        withdraw(uid, amount, "saving")
        deposit(uid, amount, "checking")
        true
      } else {
        false
      }
    }
  }
}
