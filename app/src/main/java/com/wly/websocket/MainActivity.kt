package com.wly.websocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wly.websocket.example.OKHttpWebSocketTest
import com.wly.websocketlib.WSLinkManager
import com.wly.websocketlib.listener.MainThreadWSListener
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    private var webSocketTest: OKHttpWebSocketTest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webSocketTest = OKHttpWebSocketTest(this)
    }

    fun onStart(view: View) {
        webSocketTest?.init()


    }

    fun test2() {

        val client = OkHttpClient.Builder().build()

        val wsLinkManager = WSLinkManager.Builder(this)
            .socketUrl("")
            .client(client)
            .build()

        wsLinkManager.setWebSocketListener(object : MainThreadWSListener {
            override fun onOpen(webSocket: WebSocket, response: Response) {

            }

            override fun onClose(webSocket: WebSocket, code: Int, reason: String) {
            }

            override fun onMessage(webSocket: WebSocket, body: String) {
            }

            override fun onFailure(webSocket: WebSocket, throwable: Throwable) {
            }

        })


        wsLinkManager.startConnect()
    }
}
