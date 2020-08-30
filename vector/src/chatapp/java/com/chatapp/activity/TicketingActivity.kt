package com.chatapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.C
import im.vector.R
import kotlinx.android.synthetic.chatapp.activity_ticketing.*

class TicketingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing)
        train.setOnClickListener(this)
        taxi.setOnClickListener(this)
        smartcab.setOnClickListener(this)
        bus.setOnClickListener(this)
        fligt.setOnClickListener(this)
        hotels.setOnClickListener(this)
        bike.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(v: View?) {
        C.showErr()
    }

}