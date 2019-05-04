# 学习笔记  5-1

##### BufferedReader

BufferedReader 是缓冲字符输入流。它继承于Reader。
BufferedReader 的作用是为其他字符输入流添加一些缓冲功能

**说明**：
要想读懂BufferReader的源码，就要先理解它的思想。BufferReader的作用是为其它Reader提供缓冲功能。创建BufferReader时，我们会通过它的构造函数指定某个Reader为参数。BufferReader会将该Reader中的数据分批读取，每次读取一部分到缓冲中；操作完缓冲中的这部分数据之后，再从Reader中读取下一部分的数据。
为什么需要缓冲呢？原因很简单，效率问题！缓冲中的数据实际上是保存在内存中，而原始数据可能是保存在硬盘或NandFlash中；而我们知道，从内存中读取数据的速度比从硬盘读取数据的速度至少快10倍以上。
那干嘛不干脆一次性将全部数据都读取到缓冲中呢？第一，读取全部的数据所需要的时间可能会很长。第二，内存价格很贵，容量不想硬盘那么大。

##### (BufferedReader)为其他的reader提供缓存，加快运行速度



##### Java中的String，StringBuilder，StringBuffer三者的区别

String是创建字符串产量的，其余两个是创建字符串变量的

而且String的速度比较满，

StringBuilder和StringBuffer的区别是：**StringBuilder是线程不安全的，而StringBuffer是线程安全的**，但是StringBuilder速度比较快

　　3. 总结一下
　　**String：适用于少量的字符串操作的情况**

　　**StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况**
　　
　　**StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况**

### ScrollView

可以提供Textview的滚动，就是一页装不下时候，可以滚动

# 强烈失误

在使用网络编程的时候，忘记在主xml中添加支持

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## 今日完成

#### 1.完成的网络编程，可以从服务器获取JSON格式的数据

#### 2.完成了通知栏透明和刘海屏适配（因为我用刘海屏调试）

#### 3.完成的屏幕上面的大概UI

<img src='C:\Users\杨美端\Desktop\最终图标\刘海屏适配后.png' style="zoom:20%">



# 学习笔记 5-3

##### WebView有时候会跳转到系统浏览器的问题

```java

 webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")){
                        view.loadUrl(web_link);
                        return true;
                    }
                }

                return false;
            }

        });
        webView.loadUrl(web_link);

```

### 视图
##### (主界面，暂时美观UI)

<img src='C:\Users\杨美端\Desktop\最终图标\主界面.png' style="zoom:20%">



##### (点击后，进入里面的链接)

<img src='C:\Users\杨美端\Desktop\最终图标\进入链接后.jpg' style="zoom:20%">


# 学习笔记 5-4

#### 解决WebView进度条

##### 1.使用WebChromeClient的onProgressChanged的方法来监听

##### 2.

```java
onProgressChanged(WebView View, int newProgress){
    /**
    *newProgress 是检测当前网页加载的值
    *View是当前网页
    **/
    //
	//应该让其平滑的加载
    /*这个newProgress值不是说按我们想象中那样，从0慢慢到100，可能就是（0->16->30->100）就返回这四次数字。所以如果我们直接让我们的进度条按照它的newProgress值来变化，就有个问题，那就是会变化特别大，而且速度也特别快。体验一点也不好。所以这里我处理方式是，当newProgress 大于85 的时候，让他慢慢的在特定时间内加载完剩下的进度，这样给人的感觉也是很平稳的
}*/
```

##### 3.图形绘制(未完成)











