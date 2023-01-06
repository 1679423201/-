### OKhttp

#### 调度器dispatcher（单例模式）

主要属性：

- 最大请求数：65
- 最大请求主机：5
- 线程池：可以传入，不传则创建ThreadPoolExecutor
- 等待异步队列：若任意超过上面两个最大则加入
- 运行中异步队列：同上，不超过则加入
- 运行中同步队列：直接加入

异步请求：

1. 创建OKhttpClient对象 (可设置连接时间，读时间，写时间，拦截器)
2. 创建请求体 FormBody.Builder() 创建请求体
3. 创建request对象Request.Builder() 设置请求体，url
4. newCall().enqueue 将其加入调度器队列
5. 执行操作getResponseWithInterceptorChain
6. 检查拦截器（后面细说）
7. 调用异步接口方法

拦截器：

1. **RetryAndFollowUpInterceptor (重定向拦截器)**
2.  **BridgeInterceptor (桥接拦截器)**
3. **CacheInterceptor (缓存拦截器)**
4.  **ConnectInterceptor (连接拦截器)**
5. **CallServerInterceptor(读写拦截器)**