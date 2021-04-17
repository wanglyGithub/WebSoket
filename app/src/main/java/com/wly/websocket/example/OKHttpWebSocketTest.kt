package com.wly.websocket.example

import android.os.Looper
import android.util.Log
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

/**
 * author: wangliyun
 * date: 2021-04-17
 * description:
 */
class OKHttpWebSocketTest {

    private val mockWebServer = MockWebServer()

    private var mWebSocket: WebSocket? = null

    private val mHanlder = android.os.Handler(Looper.getMainLooper())

    companion object {
        const val WS_PREFIX = "ws://"
    }


    fun init() {
        initMockServer()
        initClient()
    }


    private fun getWebSocketUrl(): String {

        return "${WS_PREFIX}${mockWebServer.hostName}:${mockWebServer.port}/"

    }


    private fun initClient() {
        val url = getWebSocketUrl()
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        // 建立链接
        mWebSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                mWebSocket = webSocket

                Log.i("wangly", "onOpen client onOpen.....")

                Log.i("wangly", "onOpen client  response: $response")

                sendMessage()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.i("wangly", "onMessage  text = $text")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.w("wangly", "onClosed  code = $code  reason = $reason")

                onRelease()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)

                Log.e("wangly", "onFailure  exception = ${t.message}")

            }
        })

        okHttpClient.dispatcher.executorService.shutdown()
    }


    private fun initMockServer() {
        mockWebServer.enqueue(MockResponse().withWebSocketUpgrade(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.i("wangly", "onOpen server onOpen.....")


            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                Log.i("wangly", "onOpen server onMessage..... text = $text")

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.w("wangly", "onClosed server  code = $code  reason = $reason")

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)

                Log.e("wangly", "onFailure  server exception = ${t.message}")

            }
        }))
    }


    private fun sendMessage() {
        mHanlder.postDelayed(DelayedRunnable(mWebSocket), 2000)
    }


    private fun onRelease() {
        mHanlder.removeCallbacksAndMessages(null)
    }


}