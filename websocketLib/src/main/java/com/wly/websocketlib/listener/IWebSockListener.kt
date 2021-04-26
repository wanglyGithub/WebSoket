package com.wly.websocketlib.listener

import com.wly.websocketlib.constant.Constants
import okhttp3.WebSocket

/**
 * author: wangliyun
 * date: 2021-04-25
 * description:
 */
interface IWebSockListener {

    fun getWebSocket(): WebSocket?

    fun startConnect()
    fun closeConnect()


    fun setWsConnectStatus(status: Int = Constants.StatusCode.NONE_STATUS)

    fun getWsConnectStatus():Int


}