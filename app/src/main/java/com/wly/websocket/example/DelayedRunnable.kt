package com.wly.websocket.example

import okhttp3.WebSocket

/**
 * author: wanglyGithub
 * date: 2021-04-17
 * description:
 */
class DelayedRunnable(val webSocket: WebSocket?):Runnable {


    override fun run() {
        webSocket?.let {
            val message = "msg:----》${System.currentTimeMillis()}"
            it.send(message)
        }

    }


}