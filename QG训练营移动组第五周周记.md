# QG训练营移动组第五周周记：2019年4月21日

## 生活随记

生活就感觉比以前时间抓的更紧张了，节奏也比以前快了

感觉变化挺大的，包括回宿舍的主要做的事情

## 一周总结

这周主要写了上次小组培训布置的任务--做一个翻译用的APP，就刚开始没什么地方下手，那我就从简单的地方开始---UI界面设计

### UI

UI分了好几种布局模式，有线性布局，相对布局，百分百布局和帧布局

常用控件的用法(直接贴代码吧)

```

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <EditText
        android:id="@+id/Edit_query"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="2"
        android:hint="请输入你要查询的单词"
        android:maxLines="2"/>
    <Button
        android:id="@+id/Search_badge"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="查询" />
</LinearLayout>
```

上面是设置输入框加按键的布局

然后可以使用布局叠加让下面出现一个Listview

```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
<include layout="@layout/activity_main"/>
    <ListView
        android:id="@+id/history_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </ListView>

</LinearLayout>
```

使用<include layout .../>导入上一个布局

在布置好UI后，在设置SQLite

#### SQLite

数据库主要是通过继承SQLiteOpenHelper方法，再重写里面的一些方法来实现的

```

```

```java
public class Database extends SQLiteOpenHelper {

    public Context mContext;
    public static final String CRETE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
            +"English text)";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRETE_BOOK);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
```

然后在外部通过构造对象再调用方法来实现数据库的增删查改

```

```

```java
SQLiteDatabase db = dpHelper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put("English",inputText);
db.insert("Book",null,values);
Cursor cursor = db.query("Book",null,null,null,null,null,null);
```

学完数据库后，就到了网络编程和数据解析了

#### 网络编程与数据解析

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

首先在一个大XML文件下添加上面的语句来提供网络支持

然后

```xml
compile 'com.squareup.okhttp3:okhttp:3.4.1'
compile 'com.google.code.gson:gson:2.7'
```

这两句是添加依赖，上面的是对okhttp的依赖，下面是对gson解析的依赖

```java
private void pareJSONWithISONObject(String jsonData){
    try{
            JSONObject jsonObiect = new JSONObject(jsonData);
            String type = jsonObiect.getString("type");
            String errorCode = jsonObiect.getString("errorCode");
            String translateResult = jsonObiect.getString("translateResult");

            JSONArray word = new JSONArray(translateResult);
            JSONArray word2 = word.getJSONArray(0);
             for (int i = 0; i <word.length() ; i++) {
                JSONObject mains = word2.getJSONObject(i);
                String Chin = mains.getString("tgt");
                String Enin = mains.getString("src");
                 System.out.println("tgt is "+Chin);
                 System.out.println("src is "+Enin);
             }
            System.out.println("type is "+type);
            System.out.println("errorCode is "+errorCode);
            System.out.println("translateResult is "+translateResult);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

上面是对json解析的函数

```json
{"type":"EN2ZH_CN","errorCode":0,"elapsedTime":0,"translateResult":[[{"src":"word","tgt":"词"}]]}
```

这是那一段json数据

```java
private void sendRequestWithOkHttp(){
    new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=word").build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                pareJSONWithISONObject(responseData);
                showResponse(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
}
```

这是从链接http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=word中获取数据，并将其转换为字符串形式，再调用函数解析与显示

### 存在问题

解决的一部分想要实现的功能，但目前还欠缺的是实现搜索历史的直接触发活动和

删除搜索历史功能

### 下周规划

希望可以尽快实现上面的功能，然后再学习一些UI的进阶

主要是对数据库，网络，UI的使用