## DeepLink 
Deep Link 是一种允许应用程序通过链接直接导航到特定页面或内容的机制
### URL Scheme

```text
weixin://dl/scan?level=1&light=1
在这个 URL 里面：
    Scheme：weixin -> 业务标识
    host：dl -> 域名，用来指定对应的页面
    path：/scan -> 页面路径，选参
    query：level=1&light=1，我们可以查询到对应的参数扫码的精细度 level 和是否打开闪光灯 light -> 携带的参数
```

