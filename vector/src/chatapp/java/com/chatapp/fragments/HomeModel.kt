package com.chatapp.fragments

import android.app.Activity
import com.chatapp.C
import com.chatapp.C.Companion.BuyCredit
import com.chatapp.C.Companion.ContactBackup
import com.chatapp.C.Companion.Courier
import com.chatapp.C.Companion.DataBundle
import com.chatapp.C.Companion.Did
import com.chatapp.C.Companion.ELearning
import com.chatapp.C.Companion.Electric
import com.chatapp.C.Companion.InviteFriends
import com.chatapp.C.Companion.Law
import com.chatapp.C.Companion.Logout
import com.chatapp.C.Companion.Medical
import com.chatapp.C.Companion.Meeting
import com.chatapp.C.Companion.MobileTopup
import com.chatapp.C.Companion.MobileTransfer
import com.chatapp.C.Companion.MyBalance
import com.chatapp.C.Companion.MyNumber
import com.chatapp.C.Companion.Qr
import com.chatapp.C.Companion.Settings
import com.chatapp.C.Companion.SmartCityGuide
import com.chatapp.C.Companion.Status
import com.chatapp.C.Companion.TV
import com.chatapp.C.Companion.Ticketing
import com.chatapp.C.Companion.TransferCash
import com.chatapp.C.Companion.TrnasferHistory
import com.chatapp.C.Companion.UpdateProfile
import com.chatapp.C.Companion.VoucherRecharge
import com.chatapp.C.Companion.WhyWill
import com.chatapp.C.Companion.WillEducation
import com.chatapp.C.Companion.bookId
import com.chatapp.C.Companion.callerId
import com.chatapp.C.Companion.services
import com.chatapp.C.Companion.smartAgro
import im.vector.R
import im.vector.VectorApp
import java.util.*

class HomeModel {
    var name:String = ""
    var colorCode = ""
    var icon = 0



    companion object {

        val firstHomeList: ArrayList<HomeModel>
            get() {
                val list = ArrayList<HomeModel>()
                val iconList = arrayOf(
                        R.drawable.status,
                        R.drawable.invite_friends,
                        R.drawable.setting,
                        R.drawable.mybalance,
                        R.drawable.did,
                        R.drawable.buy_credit,
                        R.drawable.voucher,
                        R.drawable.mobile_topup,
                        R.drawable.transfer_credit)
                val titles = arrayOf(
                        Status,
                        InviteFriends,
                        Settings,
                        MyBalance,
                        Did,
                        BuyCredit,
                        VoucherRecharge,
                        MobileTopup,
                        MobileTransfer
                )
                for (i in iconList.indices) {
                    val model = HomeModel()
                    model.name = titles[i]
                    model.icon = iconList[i]
                    list.add(model)
                }
                return list
            }

        val thirdHomeList: ArrayList<HomeModel>
            get() {
                val list = ArrayList<HomeModel>()
                val iconList = arrayOf(
                        R.drawable.e_learning,
                        R.drawable.book_ticket,
                        R.drawable.courier,
                        R.drawable.contact_backup,
                        R.drawable.tracking,
                        R.drawable.call_details_report,
                        R.drawable.call_rates,
                        R.drawable.teletalk_tv,
                        R.drawable.teletalk_store
                        )
                val titles = arrayOf(
                        ELearning,
                        bookId,
                        Courier,
                        ContactBackup,
                        C.Tracking,
                        C.CallDetailsReport,
                        C.CallRates,
                        C.TeletakTV,
                        C.TeletalkStore
                )
                for (i in iconList.indices) {
                    val model = HomeModel()
                    model.name = titles[i]
                    model.icon = iconList[i]
                    list.add(model)
                }
                return list
            }



        fun getSecHomeList(): ArrayList<HomeModel> {
            val list = ArrayList<HomeModel>()
            val iconList = arrayOf(
                    R.drawable.mobile_waltet,
                    R.drawable.transfer_history,
                    R.drawable.data_bundle,
                    R.drawable.electricity,
                    R.drawable.tv_recharge,
                    R.drawable.profile,
                    R.drawable.book_ticket,
                    R.drawable.caller_id,
                    R.drawable.setting)
            val titles = arrayOf(
                    TransferCash,
                    TrnasferHistory,
                    DataBundle,
                    Electric,
                    TV,
                    UpdateProfile,
                    bookId,
                    callerId,
                    Settings
            )
            for (i in iconList.indices) {
                val model = HomeModel()
                model.name = titles[i]
                model.icon = iconList[i]
                list.add(model)
            }
            return list
        }
    }
}