package com.wly.websocketlib.listener

import okhttp3.Response
import okhttp3.WebSocket

/**
 * author: wanglyGithub
 * date: 2021-04-18
 * description:
 */
interface MainThreadWSListener {

    fun onOpen(webSocket: WebSocket, response: Response)

    fun onClose(webSocket: WebSocket, code: Int, reason: String)


    fun onMessage(webSocket: WebSocket, body:String)


    fun onFailure(webSocket: WebSocket,throwable: Throwable)



}