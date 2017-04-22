package com.flashboomlet.controls

import com.flashboomlet.data.BankAccount

/**
  * Created by ttlynch on 4/20/17.
  */
class BankTeller {

  val bai = new BankAccountInfo

  def bankTeller(uid: String, deposit: Double, withdraw: Double, account: String): Unit = {
    if(bai.uidExist(uid)){
      if(deposit > 0) {
        // call to deposit
        bai.deposit(uid, deposit, account)
        println(s"You successfully deposited $deposit in $account")
      } else if(withdraw > 0) {
        // call to withdraw
        if(bai.withdraw(uid, withdraw, account)){
          println(s"You successfully withdrew $withdraw from $account")
        } else {
          println(s"Sorry, you do not have sufficient funds in that account.")
        }
      }
      // call to get info on the account
      printAccount(bai.accountInfo(uid))

    } else {
      println(s"Sorry, but there is no account for $uid")
    }
  }

  def createAccount(uid: String, first: String, last: String): Unit = {
    // Create a user account
    if(!bai.uidExist(uid)){
      val account = bai.createAccount(uid, first, last)
      println(s"Hello, you successfully created an account. " +
      s"Your account number is:\n\t${account.accountNumber}")
    } else {
      println(s"Sorry, but there is already an account for $uid")
    }
  }


  def printAccount(account: BankAccount): Unit = {
    // Print the Bank Account information in a nice clean format.
    println(s"Account information for ${account.uid}:")
    println(s"\tname: ${account.first} ${account.last}")
    println(s"\taccount number: ${account.first}")
    println(s"\tchecking: ${account.checking}")
    println(s"\tsaving: ${account.saving}")
  }

  def transferFunds(uid: String, fromAccount: String, amount: Double): Unit = {
    // For future implementation
    if(bai.uidExist(uid)){
      val trans = bai.transferFunds(uid, fromAccount, amount)
      if(trans){
      } else {
        println(s"Sorry, you do not have sufficient funds in that account.")
      }
    } else {
      println("Sorry, we could not find an account on file for you.")
    }
  }

  def fundsPresent(uid: String, amount: Double): Boolean = {
    // By Default we will only check checking accounts. Saving's funds must be transferred.
    if(bai.uidExist(uid)){
      // Could be true or false
      bai.fundsPresent(uid, amount)
    } else {
      false
    }
  }

}
