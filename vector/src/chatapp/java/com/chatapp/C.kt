package com.chatapp

import android.preference.PreferenceManager
import android.widget.Toast
import im.vector.VectorApp

class C {
    companion object {
        var Status = "Status"
        var InviteFriends = "InviteFriends"
        var Settings = "Settings"
        var MyBalance = "MyBalance"
        var BuyCredit = "Credit"
        var VoucherRecharge = "VoucherRecharge"
        var MobileTopup = "MobileTopup"
        var MobileTransfer = "MobileTransfer"
        var TrnasferHistory = "TrnasferHistory"
        var ContactBackup = "ContactBackup"
        var Tracking = "Tracking"
        var Did = "Did"
        var UpdateProfile = "UpdateProfile"
        var Qr = "Qr"
        var Ticketing = "Ticketing"
        var Courier = "Courier"
        var WillEducation = "Will Education"
        var Medical = "Medical"
        var Law = "Law"
        var smartAgro = "smartAgro"
        var SmartCityGuide = "SmartCityGuide"
        var WhyWill = "WhyWill"
        var Logout = "Logout"
        var Meeting = "Meeting"
        var MyNumber = "My Number"
        var DataBundle = "Data Bundle"
        var Electric = "Electric Bill"
        var TV = "Television Bill"
        var MONEYTRANSFER = "Money Transfer"
        var SMS = "Send Sms"
        var TransferCash = "Transfer Cash"

        var AddMoneyToWallet = "Add Money To Wallet"
        var TransferCredit = "Transfer Credit"
        var MyWallet = "My Wallet"
        var WalletBalance = "Wallet Balance"
        var AddFundsToWallet = "Add Funds to wallet"
        var TransferMobileMoney = "Transfer Mobile Money"
        var LocalMobileTopup = "Local Mobile Topup"
        var InterMobileTopup = "International Mobile Topup"
        var Electricity = "Electricity"
        var LocalElectricity = "LocalElectricity Bill Pay"
        var InterElectricity = "International Electricity Bill Pay"
        var LocalDataBundle = "LocalElectricity Data Bundle"
        var InterDataBundle = "International Data Bundle"
        var TvRecharge = "TV Recharge"
        var LocalTv = "LocalElectricity TV Recharge"
        var InterTv = "International TV Recharge"
        var SendSMS = "Send SMS"
        var CallDetailsReport = "Call Details Report"
        var CallRates = "Call Rates"
        var ELearning = "E-Learning"


        public fun showErr() {
            Toast.makeText(VectorApp.getInstance(), "Coming Soon", Toast.LENGTH_LONG).show()
        }
        fun getUserName():String{
            var userName = ""
            var settings = PreferenceManager.getDefaultSharedPreferences(VectorApp.getInstance())
            userName = settings?.getString("Username", "").toString()
            return userName
        }
    }
}