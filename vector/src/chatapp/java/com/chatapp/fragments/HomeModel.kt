package com.chatapp.fragments

import android.app.Activity
import com.chatapp.C
import com.chatapp.C.Companion.BuyCredit
import com.chatapp.C.Companion.CallDetailsReport
import com.chatapp.C.Companion.CallRates
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
                        R.drawable.caller_id,
                        R.drawable.profile,
                        R.drawable.mybalance,
                        R.drawable.did,
                        R.drawable.send_sms,
                        R.drawable.buy_credit,
                        R.drawable.voucher
                )
                val titles = arrayOf(
                        Status,
                        InviteFriends,
                        callerId,
                        UpdateProfile,
                        MyBalance,
                        Did,
                        C.SendSMS,
                        BuyCredit,
                        VoucherRecharge
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
                        R.drawable.teletalk_tv,
                        R.drawable.teletalk_store,
                        R.drawable.setting
                )
                val titles = arrayOf(
                        ELearning,
                        C.Ticketing,
                        Courier,
                        ContactBackup,
                        C.Tracking,
                        C.TeletakTV,
                        C.TeletalkStore,
                        C.Settings
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
                    R.drawable.transfer_credit,
                    R.drawable.transfer_history,
                    R.drawable.mobile_topup,
                    R.drawable.data_bundle,
                    R.drawable.electricity,
                    R.drawable.tv_recharge,
                    R.drawable.call_details_report,
                    R.drawable.call_rates
                    )
            val titles = arrayOf(
                    TransferCash,
                    MobileTransfer,
                    TrnasferHistory,
                    MobileTopup,
                    DataBundle,
                    Electric,
                    TV,
                    CallDetailsReport,
                    CallRates,
                    ELearning
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