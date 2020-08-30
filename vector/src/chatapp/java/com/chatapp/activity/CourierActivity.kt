package com.chatapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.C
import im.vector.Matrix
import im.vector.R
import im.vector.util.VectorUtils
import kotlinx.android.synthetic.chatapp.activity_courier.*
import org.matrix.androidsdk.MXSession

class CourierActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier)
        dhl.setOnClickListener(this)
        fedex.setOnClickListener(this)
        smartDelivery.setOnClickListener(this)
        aramex.setOnClickListener(this)
        gig.setOnClickListener(this)
        ups.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        var mSession: MXSession? = Matrix.getInstance(this).defaultSession
        VectorUtils.loadUserAvatar(this, mSession, settings_avatar, mSession!!.myUser)

    }

    override fun onClick(v: View?) {
        C.showErr()
    }
}