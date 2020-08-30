package com.chatapp.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.chatapp.C
import com.chatapp.ChatMainActivity
import com.chatapp.SettingsWebActivity
import com.chatapp.TransferHistoryAcitivty
import com.chatapp.activity.*
import com.chatapp.adapters.HomeAdapter
import im.vector.Matrix
import im.vector.R
import im.vector.activity.VectorSettingsActivity
import im.vector.util.VectorUtils
import kotlinx.android.synthetic.chatapp.fragment_home2.*
import org.matrix.androidsdk.MXSession


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeAdapter.iHomClick {

    private lateinit var mSession: MXSession

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv.setLayoutManager(GridLayoutManager(activity, 3))
        var adapter: HomeAdapter = HomeAdapter(context, HomeModel.getHomeList(), this)
        rv.adapter = adapter

    }

    override fun onHomeClick(title: String?) {
        when (title) {
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
                val myIntent = Intent(context, SettingsWebActivity::class.java)
                myIntent.putExtra("Bundle", "Credit")
                startActivity(myIntent)
            }
            C.VoucherRecharge -> {
                (activity as ChatMainActivity).voucherTransfer()

            }
            C.MobileTopup -> {
                val myIntent = Intent(context, SettingsWebActivity::class.java)
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
                showErr()

            }
            C.Tracking -> {
                C.showErr()
//                startActivity(Intent(activity, TrackingActivity::class.java))
            }
            C.Did -> {
                showErr()

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
                showErr()


            }
            C.Medical -> {
                showErr()

            }
            C.Law -> {
                startActivity(Intent(activity, LawActivity::class.java))

            }
            C.smartAgro -> {
                showErr()
            }
            C.SmartCityGuide -> {
                showErr()
            }
            C.WhyWill -> {
                try {
                    val myIntent = Intent(activity, SettingsWebActivity::class.java)
                    myIntent.putExtra("Bundle", "Why")
                    startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(activity, "No application can handle this request. Please install a webbrowser", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }

//            C.LOGOUT -> {
//                (activity as ChatMainActivity).logout()
//            }
        }
    }

    private fun showErr() {
        Toast.makeText(requireActivity(), "Coming Soon", Toast.LENGTH_LONG).show()

    }

    override fun onResume() {
        super.onResume()
        (activity as ChatMainActivity).hideItem();
        mSession = Matrix.getInstance(requireActivity()).defaultSession

        VectorUtils.loadUserAvatar(activity, mSession, settings_avatar, mSession.myUser)
    }

}