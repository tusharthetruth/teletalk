package com.chatapp.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.chatapp.C
import com.chatapp.ChatMainActivity
import com.chatapp.ExtendedWebview
import com.chatapp.TransferHistoryAcitivty
import com.chatapp.activity.CourierActivity
import com.chatapp.activity.LawActivity
import com.chatapp.activity.QrActivity
import com.chatapp.activity.TicketingActivity
import com.chatapp.adapters.HomeAdapter
import com.chatapp.share.UserStatusActivity
import im.vector.R
import im.vector.activity.VectorSettingsActivity
import kotlinx.android.synthetic.chatapp.fragment_home2.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragmentThird : Fragment(),HomeAdapter.iHomClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_third, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.layoutManager = GridLayoutManager(activity, 3)
        val adapter: HomeAdapter = HomeAdapter(context, HomeModel.getThirdHomeList(), this)
        rv.adapter = adapter


    }



    override fun onHomeClick(title: String?) {
        when (title) {
            C.Status -> {
                startActivity(Intent(requireActivity(), UserStatusActivity::class.java))

            }
            C.InviteFriends -> {
                (activity as ChatMainActivity).invite()

            }
            C.Settings -> {
                startActivity(Intent(activity, VectorSettingsActivity::class.java))

            }
            C.MyBalance -> {
                (activity as ChatMainActivity).GetBalance()

            }
            C.BuyCredit -> {
                val myIntent = Intent(context, ExtendedWebview::class.java)
                myIntent.putExtra("Bundle", "Credit")
                startActivity(myIntent)
            }
            C.Meeting -> {
                val myIntent = Intent(context, ExtendedWebview::class.java)
                myIntent.putExtra("Bundle", "meeting")
                startActivity(myIntent)
            }
            C.VoucherRecharge -> {
                (activity as ChatMainActivity).voucherTransfer()

            }
            C.MobileTopup -> {
                val myIntent = Intent(context, ExtendedWebview::class.java)
                myIntent.putExtra("Bundle", "TopupA")
                startActivity(myIntent)
            }
            C.MobileTransfer -> {
                (activity as ChatMainActivity).voucherRegcharge()
            }
            C.TrnasferHistory -> {
                startActivity(Intent(requireActivity(), TransferHistoryAcitivty::class.java))

            }
            C.ContactBackup -> {
                C.showErr()

            }
            C.Tracking -> {
                C.showErr()
//                startActivity(Intent(activity, TrackingActivity::class.java))
            }
            C.Did -> {
                C.showErr()

            }
            C.UpdateProfile -> {
                startActivity(Intent(activity, VectorSettingsActivity::class.java))

            }
            C.Qr -> {
                startActivity(Intent(activity, QrActivity::class.java))

            }
            C.Ticketing -> {
                startActivity(Intent(activity, TicketingActivity::class.java))

            }
            C.Courier -> {
                startActivity(Intent(activity, CourierActivity::class.java))

            }
            C.WillEducation -> {
                val myIntent = Intent(context, ExtendedWebview::class.java)
                myIntent.putExtra("Bundle", "Education")
                startActivity(myIntent)
            }
            C.Medical -> {
                C.showErr()

            }
            C.Law -> {
                startActivity(Intent(activity, LawActivity::class.java))

            }
            C.smartAgro -> {
                C.showErr()
            }
            C.SmartCityGuide -> {
                C.showErr()
            }
            C.WhyWill -> {
                try {
                    val myIntent = Intent(activity, ExtendedWebview::class.java)
                    myIntent.putExtra("Bundle", "Why")
                    startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(activity, "No application can handle this request. Please install a webbrowser", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }

            C.Logout -> {
                (activity as ChatMainActivity).logout()
            }
            C.MyNumber -> {
                try {
                    val myIntent = Intent(activity, ExtendedWebview::class.java)
                    myIntent.putExtra("Bundle", "MyNumber")
                    startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(activity, "No application can handle this request. Please install a webbrowser", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        }
    }

}