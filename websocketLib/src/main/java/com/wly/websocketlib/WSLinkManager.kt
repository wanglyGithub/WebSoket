package com.wly.websocketlib

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.wly.websocketlib.constant.Constants
import com.wly.websocketlib.listener.IWebSockListener
import okhttp3.*

/**
 * author: wanglyGithub
 * date: 2021-04-18
 * description:
 */
class WSLinkManager(builder: Builder) : IWebSockListener {


    @JvmField
    val TAG = "WSLinkManager"
    private var socketUrl = builder.socketUrl

    private val mHandler = Handler(Looper.getMainLooper())

    private var mOkHttpClient = builder.okHttpClient
    private var mRequest: Request? = null

    private var mWebSocket: WebSocket? = null

    private var connectionStatus = Constants.StatusCode.NONE_STATUS

    private val mSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            mWebSocket = webSocket
            setWsConnectStatus(Constants.StatusCode.CONNECTED_STATUS)

            
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
        }
    }

    private fun initSocket() {
        // 释放资源
        onRelease()


        if (mOkHttpClient == null) {
            mOkHttpClient = OkHttpClient.Builder().build()
        }

        if (mRequest == null) {
            mRequest = Request.Builder()
                .url(socketUrl)
                .build()
        }
        mOkHttpClient?.dispatcher?.cancelAll()

        mRequest?.let { request ->
            mOkHttpClient?.newWebSocket(request, mSocketListener)

        }


    }


    override fun getWebSocket(): WebSocket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startConnect() {

        if (getWsConnectStatus() == Constants.StatusCode.CONNECTED_STATUS) {
            return
        } else {
            initSocket()
        }


    }

    override fun closeConnect() {

        val statusCode = getWsConnectStatus()
        if (statusCode == Constants.StatusCode.NONE_STATUS || statusCode == Constants.StatusCode.DISCONNECTED_STATUS) {
            return
        }

        onRelease()
        mOkHttpClient?.dispatcher?.cancelAll()


        setWsConnectStatus(Constants.StatusCode.DISCONNECTED_STATUS)

    }


    override fun setWsConnectStatus(status: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWsConnectStatus(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    // 释放资源
    private fun onRelease() {
        mHandler.removeCallbacksAndMessages(null)
    }


    class Builder(context: Context) {
        var mContext = context
        var okHttpClient: OkHttpClient? = null

        var socketUrl = ""


        fun socketUrl(url: String): Builder {
            this.socketUrl = url
            return this
        }


        fun client(client: OkHttpClient): Builder {
            this.okHttpClient = client
            return this
        }


        fun build(): WSLinkManager {

            return WSLinkManager(this)
        }

    }

}