**【DrawerLayout】**

------

1、在Android的support库中增加了一个专门用于创建侧滑菜单的组件DrawerLayout，接下来我们就讲解一下怎样使用这个原生的组件创建一个仿推酷的应用

2、先来看看使用DrawerLayout的步骤：

①在布局文件中添加<android.support.v4.widget.DrawerLayout> 根元素

②在这个根元素中首先添加一个 内容视图组件，比如：我们添加一个<FrameLayout>容器

③再在这个根元素中添加侧滑菜单视图组件，一般来说就是一个ListView组件

④为ListView设定Adapter，和点击事件监听器

⑤为DrawerLayout添加 开、关 状态监听器

3、只要遵循上面的几步就能够完成一个侧滑菜单的创建，非常的简单，下面我们就通过模仿推酷客户端，一步一步的说明怎样创建侧滑菜单

# 学习日记 5-7

#### recyclerView

使用时要设置其排列

出现No layout manager attached; skipping layout错误

是没有设置LayoutManager

```java
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于gridview
mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
```

设置完再设置Adapter就解决了

```java
getLayoutPosition//返回-1，求错误
```

# 登录时遇到数据解析错误

# 为recyclerView添加点击事件时获取实例（position）错误

原因：返回时new了一个新的holder

解决方法：这个方法返回的ViewHolder会加载到RecyclerView中，你的点击事件中持有的是上文中的final ViewHolder holder，这个holder没有加载入RecyclerView中，没有加入RecyclerView的holder无法知道自己的位置，所以拿到一直是-1，所以直接返回上方调用过的holder

# DrawerLayout使用时侧滑的内容无法点击且无法滑动回去

原因：DrawerLayout的侧滑布局没有放在主布局下放

解决办法：使侧滑的NavigView定义在主界面的下方

# ToolBar的使用

可以添加item，

item里面的属性常用有alway（总是会显示在ToolBar）

ifRoom（有空间的时候显示）

never（永远不显示，而是会显示Title在菜单栏）

自带的按键R.id.home，在左上方，原型为返回键，需重现按键监听事件，可以重新设置图标

# 学习日记 5-8

登录时，需使用（“POST”）发送数据，然后接受服务器传回来数据，再检测是否登录成功

本地实现自动登录，可以使用数据库（简单），或者使用cookie（有点难）

实现点击变灰色：可以用数据库存点击后的id

