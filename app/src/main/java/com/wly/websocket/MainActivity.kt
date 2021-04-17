package com.wly.websocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wly.websocket.example.OKHttpWebSocketTest

class MainActivity : AppCompatActivity() {
    private var webSocketTest: OKHttpWebSocketTest?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webSocketTest = OKHttpWebSocketTest()
    }

    fun onStart(view: View) {
        webSocketTest?.init()
    }
}
