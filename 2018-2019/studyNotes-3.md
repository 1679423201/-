# 周记 5-14

#### ViewHolder复用异常

```java
if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex("readId"));
                    if (id.equals(Article.getId())) {
                        holder.textViewTitle.setTextColor(MyApplication.getContext().getResources().getColor(R.color.isOnClick));
                        break;
                    } else
                        holder.textViewTitle.setTextColor(MyApplication.getContext().getResources().getColor(R.color.noOnClick));
                }
                while (cursor.moveToNext());
            }
```

点击过的文章用数据库粗存其唯一标识符——ID，后在适配器里对数据库进行检索

当id对应时，将文章的标题设置成灰色。但此时会导致ViewHolder复用布局，

即11的会复用1的布局，导致一次点击，多次变色。

###### 解决方法

使用一次判断，当数据库里没有储存此文章的ID时，让此文章变成原来的颜色

这样可以解决ViewHolder的复用

### 浏览器进度条的实现

```java
webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                //如果进度条隐藏则让它显示
                if (View.GONE == progressBarView.getVisibility()) {
                    progressBarView.setVisibility(View.VISIBLE);
                }

                if (newProgress >= 80) {
                    if (isContinue) {
                        return;
                    }
                    isContinue = true;
                    progressBarView.setCurProgress(1000, new WebProgressBarView.EventEndListener() {
                        @Override
                        public void onEndEvent() {
                            isContinue = false;
                            if (progressBarView.getVisibility() == View.VISIBLE) {
                                hideProgress();
                            }
                        }
                    });
                } else {
                    progressBarView.setNormalProgress(newProgress);
                }
            }
        });
```

用newProgress来获取浏览器的加载进度，但是浏览器的加载进去变化非常快，若直接利用其

数值的变化来绘图，则会导致进度条变化太快，降低用户体验。

###### 解决方法

通过自定义View来创建一个进度条，再通过对newProgress的获取来决定加载的快慢

### 收藏的实现

通过使用wanandroid的登录Api来获得cookie，再使用其收藏API，并在请求中添加cookie

则可以实现收藏操作

```java
public void shouCollect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.wanandroid.com/lg/collect/list/0/json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    ToolUntil.InputCookie(connection);
                    InputStream in = connection.getInputStream();
                    //                        对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();   //新建一个字符串变量
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    articleList = JsonForCollect.getCollectDatas(response.toString());
                    showList(articleList);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(connection != null)
                    connection.disconnect();
            }
        }).start();

    }
    public void showList(final List<Article> response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView.LayoutManager
                        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                RecyclerView recyclerView= (RecyclerView)findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                myAdapter adapter = new myAdapter(response);
                recyclerView.setAdapter(adapter);
            }
        });
    }
```

###### 注意事项：

###### 1.cookie的携带：ToolUntil.InputCookie(connection);

```java
 public static void InputCookie(HttpURLConnection connection){
        CookieDatabaseHelper cookieDatabaseHelper = new CookieDatabaseHelper(MyApplication.getContext(), "Cookie.db", null, 1);
        SQLiteDatabase dbOfCookie = cookieDatabaseHelper.getWritableDatabase();
        Cursor cursor = dbOfCookie.query("cookies",null,null,null,null,null,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            String cookie = cursor.getString(cursor.getColumnIndex("cookie"));
            connection.setRequestProperty("Cookie",cookie);
        }
    }
```

###### 2.请求的方式，需按照官方API的说明来请求

# 数据库的使用

1.使用一个用户库来存放用户的信息，包括账号密码等，实现自动登录

2.使用一个cookie库来保存cookie信息，用来实现收藏等操作

​	需要使用cookie的操作：收藏，加载页面（可以有，可以无），退出

3.用户信息库和cookie库都需要在用户退出登录时清楚所有数据

4.使用一个ID库来记录用户的点击操作，实现点击后变色

# 有关多活动的布局

### 1.在一个活动里面改变另一个活动布局的方式

##### 一.使用通知

在实现登录后自动把用户名打印在主界面时，尝试了很多方法，后面采用了通知，在登录的同时发起一条通知

给主活动，再在主活动里面设置一个通知接收器，以此来达到多活动同步的状态

```java
 //发送通知
Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                        intent.putExtra("username",Username);
                        sendBroadcast(intent);
```

```java
   
//接受通知并改变布局
public  class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public  void onReceive(Context context, Intent intent) {
            TextView textView = (TextView)findViewById(R.id.tv_mail);
        String username = intent.getStringExtra("username");
        textView.setText(username);
        Button button = (Button)findViewById(R.id.but_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"你已登录成功",Toast.LENGTH_SHORT);
            }
        });
    }
    }
```

主XML文件里要添加

```xim
                <action android:name="com.example.broadcasttest.MY_BROADCAST" />
              
```

##### 二.使用Onstart方法

onstart方法会在活动由不可见变为可见时调用

若使用onstart则会导致从任意活动返回主页时都会调用一次该方法

但也是可以实现同步更改

### 目前尚未完成的功能

###### 1.搜索热词

需要使用自定义View来实现流式布局

需要获取使用网络编程来获取热词数据

###### 2.搜索历史记录

使用数据库保存

有删除功能

用recyclerview展示