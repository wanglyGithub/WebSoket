package com.wly.websocket.example

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * author: wanglyGithub
 * date: 2021-04-17
 * description:
 */

object AssetsUtils {

    @JvmStatic
    fun readAssetsInfo(context: Context, fileName: String): String {

        var result = ""

        try {
            val inputStreamReader = InputStreamReader(context.resources.assets.open(fileName))

            val bufferReader = BufferedReader(inputStreamReader)

            val line = ""


            while (bufferReader.readLine() != null) {
                result += line

            }

            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}