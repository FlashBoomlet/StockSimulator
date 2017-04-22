package com.flashboomlet.controls

import com.flashboomlet.data.BankAccount

/**
  * Created by ttlynch on 4/20/17.
  */
class BankTeller {

  val bai = new BankAccountInfo

  def bankTeller(uid: String, deposit: Double, withdraw: Double, account: String): String = {
    if(bai.uidExist(uid)){
      if(deposit > 0) {
        // call to deposit
        bai.deposit(uid, deposit, account)
        (s"You successfully deposited $deposit in $account\n")
      } else if(withdraw > 0) {
        // call to withdraw
        if(bai.withdraw(uid, withdraw, account)){
          (s"You successfully withdrew $withdraw from $account\n")
        } else {
          (s"Sorry, you do not have sufficient funds in that account.\n")
        }
      }
      // call to get info on the account
      printAccount(bai.accountInfo(uid))

    } else {
      (s"Sorry, but there is no account for $uid\n")
    }
  }

  def createAccount(uid: String, first: String, last: String): String = {
    // Create a user account
    if(!bai.uidExist(uid)){
      val account = bai.createAccount(uid, first, last)
      (s"Hello, you successfully created an account. " +
      s"Your account number is:\n\t${account.accountNumber}\n")
    } else {
      (s"Sorry, but there is already an account for $uid\n")
    }
  }


  def printAccount(account: BankAccount): String = {
    // Print the Bank Account information in a nice clean format.
    ((s"Account information for ${account.uid}:\n")
    + (s"\tname: ${account.first} ${account.last}\n")
    + (s"\taccount number: ${account.first}\n")
    + (s"\tchecking: ${account.checking}\n")
    + (s"\tsaving: ${account.saving}\n"))
  }

  def transferFunds(uid: String, fromAccount: String, amount: Double): String = {
    // For future implementation
    if(bai.uidExist(uid)){
      val trans = bai.transferFunds(uid, fromAccount, amount)
      if(trans){
        ""
      } else {
        (s"Sorry, you do not have sufficient funds in that account.\n")
      }
    } else {
      ("Sorry, we could not find an account on file for you.\\n")
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
