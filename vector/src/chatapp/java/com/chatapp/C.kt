package com.chatapp

import android.widget.Toast
import im.vector.VectorApp

class C {
    companion object {
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
        var WillEducation = "WillEducation"
        var Medical = "Medical"
        var Law = "Law"
        var smartAgro = "smartAgro"
        var SmartCityGuide = "SmartCityGuide"
        var WhyWill = "WhyWill"
        var Logout = "Logout"

        public fun showErr() {
            Toast.makeText(VectorApp.getInstance(), "Coming Soon", Toast.LENGTH_LONG).show()

        }
    }
}