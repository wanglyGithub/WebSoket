package com.wly.websocketlib.constant

/**
 * author: wanglyGithub
 * date: 2021-04-18
 * description: 常量管理类
 */

class Constants {

    // 基础配置
    interface BaseConfig {
        companion object {
            const val WS_PREFIX = "ws://"

            // 最大重试次数
            const val RETRY_MAX_NUMBER = 3
        }
    }


    // 状态码
    interface StatusCode {
        companion object {
            const val NONE_STATUS = -1 // 非正常状态
            const val DISCONNECTED_STATUS = 0 // 断开状态

            const val CONNECTED_STATUS = 1 //链接状态
        }
    }

}