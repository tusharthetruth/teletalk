package com.chatapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.C
import im.vector.R

class TicketingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(v: View?) {
        C.showErr()
    }

}