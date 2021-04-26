package com.wly.websocketlib

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.wly.websocketlib.constant.Constants
import com.wly.websocketlib.listener.IWebSockListener
import com.wly.websocketlib.listener.MainThreadWSListener
import com.wly.websocketlib.utils.NetWorkUtils
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
    private var mContext = builder.mContext

    private val mHandler = Handler(Looper.getMainLooper())

    private var mOkHttpClient = builder.okHttpClient
    private var mRequest: Request? = null

    private var mWebSocket: WebSocket? = null

    private var connectionStatus = Constants.StatusCode.NONE_STATUS

    private var listener: MainThreadWSListener? = null

    private val mSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            mWebSocket = webSocket
            setWsConnectStatus(Constants.StatusCode.CONNECTED_STATUS)
            if (isMainThread()) {
                listener?.onOpen(webSocket, response)
            } else {
                mHandler.post { listener?.onOpen(webSocket, response) }
            }

        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            if (isMainThread()) {
                listener?.onMessage(webSocket, text)
            } else {
                mHandler.post { listener?.onMessage(webSocket, text) }
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if (isMainThread()) {
                listener?.onClose(webSocket, code, reason)
            } else {
                mHandler.post { listener?.onClose(webSocket, code, reason) }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            if (isMainThread()) {
                listener?.onFailure(webSocket, t)
            } else {
                mHandler.post { listener?.onFailure(webSocket, t) }
            }
        }
    }

    fun setWebSocketListener(listener: MainThreadWSListener) {
        this.listener = listener
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


    override fun getWebSocket(): WebSocket? {
        return mWebSocket
    }

    override fun startConnect() {

        if (!NetWorkUtils.isConnected(mContext)) {
            setWsConnectStatus(Constants.StatusCode.DISCONNECTED_STATUS)
            return
        }

        if (getWsConnectStatus() == Constants.StatusCode.CONNECTED_STATUS) {
            return
        }

        initSocket()

    }

    override fun closeConnect() {

        val statusCode = getWsConnectStatus()
        if (statusCode == Constants.StatusCode.NONE_STATUS || statusCode == Constants.StatusCode.DISCONNECTED_STATUS) {
            return
        }

        onRelease()
        mOkHttpClient?.dispatcher?.cancelAll()


        setWsConnectStatus(Constants.StatusCode.NONE_STATUS)

    }


    override fun setWsConnectStatus(status: Int) {
        this.connectionStatus = status
    }

    override fun getWsConnectStatus(): Int {
        return this.connectionStatus
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

        // 承接在项目之外的 OKHttpClient 自主配置
        fun client(client: OkHttpClient): Builder {
            this.okHttpClient = client
            return this
        }


        fun build(): WSLinkManager {

            return WSLinkManager(this)
        }

    }

    /***
     * 是否在主线程
     */
    private fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }

}