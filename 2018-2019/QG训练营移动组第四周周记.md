# QG训练营移动组第四周周记：2019年4月15日

### 生活随记

么的大事发生，这周的训练营大组任务有点头疼

### 一周总结

#### Android

**AndroidManifest文件 注册文件**

​       所有活动要在AndroidManifest文件中进行注册才能够生效。

```
<activity android:name=".FirstActivity"
    android:label="This is FirstActivity">
    <intent-filter>
   
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
        
    </intent-filter>
</activity>
```

*<activity>*标签中使用了*android:name* 指定注册的活动名称 *“.FirstActivity”*（左下角有个点）

android:label="This is FirstActivity"指定活动中标题栏的名称，还会成为启动器中应用程序的名称

 

<action android:name="android.intent.action.MAIN" />
 <category android:name="android.intent.category.LAUNCHER" />这两句
 这两句为程序配置主活动

 

若未声明任何一个活动为主活动，这个程序依然可以正常安装，只是无法再启动器打开，这种程序一般作为第三方服务供其他应用在内部进行调用，如支付宝快捷支付服务。

**在活动中使用Toast**

```java
public class ThirdActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third);

        Button button = (Button)findViewById(R.id.button_3);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Toast.makeText(ThirdActivity.this ,"You Click it!",

                        Toast.LENGTH_SHORT).show();

            }

        });

    }

}
```



```

  
```

*findViewById()*可获得布局中定义的元素，传入id可获得实例，由于*findViewById()*返回的是一个View对象，要先向下造型转成*Button*对象，然后调用*setOnClickListener()*为该对象注册一个监听器，点击按钮就会执行*onClick()*方法

**Toast用法：**

​       用*makeText()*方法创建出*Toast*对象并调用*show()*将*Toast*显示出来。

​       makeTest()方法需要传入3个参数：

​              第一个是*Context*，也就是Toast要求的上下文，活动本身就是一个*Contest*对象，所以这里直接传入*ThirdActivity.this*。

​              第二个是*Toast*显示的文本内容。

​              第三个是*Toast*显示的时长，有两个内置常量可以选择 *Toast.LENGTH_SHORT*和*Toast.LENGTH_LONG*。

 

**在活动中使用Menu**

*Main.xml*

```
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item 

        android:id="@+id/add_item"

        android:title="Add"/>

    <item

        android:id="@+id/remove_item"

        android:title="Remove"/>

    <item

        android:id="@+id/back_item"

        android:title="Back" />

</menu>
```

这里创建了两个菜单项，其中*<item>*标签就是用来创建具体的某个菜单项，然后用

*android:id*来设定id标识符，通过*android:title*给这个菜单设置名称。

 

```
@Override

public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.main,menu);

    return true;

}
```

在活动中重写*onCreateOptionsMenu()*方法：

​       通过*getMenuInflater()*方法得到*MenuInflater*对象，再调用他的*inflate()*方法就可以给当前活动创建菜单。

​       *Inflate()*方法接受两个参数：

​              第一个参数指定我们通过哪一个资源文件来创建菜单，我们传入*R.menu.main*

​              第二个参数指定我们的菜单项要添加到哪一个Menu对象中，这里直接使用*onCreateOptionsMenu()*方法中传入的*menu*参数。

​       最后返回，*true*表示允许创建的菜单显示，*false*表示创建的菜单无法显示。

 

**Intent.setData方法**

```
public void onClick(View v) {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:10086"));
    startActivity(intent);
}
```

```java
public void onClick(View v) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("http://www.baidu.com"));
    startActivity(intent);
}
```

除了http外，intent里的url还可以指定很多的协议如 geo显示地理位置 tel表示拨打电话。





**使用intent向下一个活动传递数据**

​	用intent传递数据的思路很简单，intent提供了一系列的putExtra()方法的重载可以把我们想要传递的数据暂存在intent中，然后启动另一个活动数据取出	

比如可以先在一个中写

```
public void onClick(View v) {
    String data = "Hello!!";
    Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
    intent.putExtra("extra_data111",data);
    startActivity(intent);
}
```

这里利用intent.putExtra()方法接收2个参数，其中一个是key（关键字，用于后面从intent中取值），第二个参数才是真正要传递的数据。

然后在另一个中写

```
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);
    Intent intent = getIntent();
    String data = intent.getStringExtra("extra_data111");
    Log.d("Main3Activity",data);
}
```

首先通过getIntent()方法得到用于启动该活动的intent，然后用getStringExtra()方法，传入相应的key，就可以得到数据了，这里传递的是String数据，所以用getStringExtra，如果是int数据，就用getIntExtra()方法，如果传递的是布尔数据，则使用getBooleanExtra()方法。

2019-03-14 00:50:49.978 31889-31889/com.tryaction.intentdemo D/Main3Activity: Hello!!

可以看到，在logcat中打印出了该信息



### 存在问题

​	Android 进度略慢，想要加快点进度

### 下周规划

##### 一.安卓编程继续学习

##### 二.开始做训练营任务