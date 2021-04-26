package com.wly.websocketlib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * author: wanglyGithub
 * date: 2021-04-26
 * description: 网络工具类
 */
object NetWorkUtils {

    @JvmStatic
    fun isConnected(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
                ?: return false


        val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }

        return false
    }
}