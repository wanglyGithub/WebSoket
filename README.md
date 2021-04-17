# WebSocket
## 前序
由于项目当中有一些定义发送任务，而这些后台任务通过长链接方式下发数据，通过解析数据进行相对应的操作。
例如：通过actionType 类型，判断来源类型

- actionType = 1 , 执行下载任务，
- actionType = 2 , 执行用户行为日志回捞。
,,,,,,,

为了满足项目需要。通过创建长链接方式进行实现。当然也不乏使用消息透传形式进行如个推/等等。

## 实现
本次实现方式主要是通过OKHttp实现WebSocket 长链接。本身OKHttp 很灵活，对外提供了WebSocket 创建入口，以及Socket 监听。大家可以详细了解OKHttp 的实现。

1. gradle 配置
'''
dependencies {
    // 省略部分配置

    implementation 'com.squareup.okhttp3:okhttp:4.9.1'
    implementation 'com.squareup.okhttp3:mockwebserver:3.8.1'
}

'''

2. 代码实现


3. 测试
借助Mockwebserver 同样是OKHttp 提供的强大API 实现。



## 拓展
逐步完善，拓展长链接实现，下面是要逐渐完成的功能

### 重试

### 保活