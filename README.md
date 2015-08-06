# BeautifulRefreshLayout
======================================================================================================================================

说明
----------------------------------------------------------------------------------------------------------------------------------
前些天，看到一个很漂亮的美食下拉刷新[（来源地址）](https://dribbble.com/shots/2096383-Pull-To-Refresh-V2?list=users&offset=1) ，可惜技术水平菜菜的，只能模仿一下下，啊哈哈。。。

效果图
--------------------------------------------------------------------------------------------------------------------------------
![](http://www.apkbus.com/data/attachment/forum/201508/06/102056yctnkn6tcck3cych.png)

![](http://www.apkbus.com/data/attachment/forum/201508/06/102058t7xl9q3dd3bsgoxs.png)

![](http://www.apkbus.com/data/attachment/forum/201508/06/102100xt44oltpwaju1lj4.png)

![](http://www.apkbus.com/data/attachment/forum/201508/06/102102lcmmsym2kmy4ehss.png)

![](http://www.apkbus.com/data/attachment/forum/201508/06/101826dztai4gnnfmgmuql.gif)

碎碎念
-----------------------------------------------------------------------------------------------------------------------------
说起下拉刷新，好像经历一段历史的洗礼。。。

(1)在我刚学android的时候，用的是XListView,在github上搜索有[MarkMjw/PullToRefresh](https://github.com/MarkMjw/PullToRefresh),根据Maxwin的XListView改造而来，完善下拉刷新上拉加载更多的功能并实现自动刷新以及自动加载等功能， 并增加对ScrollView的支持。 原XListView参考链接：https://github.com/Maxwin-z/XListView-Android(听说原作者停止维护了)
![](https://camo.githubusercontent.com/d5987bf40a04dc9894fb0ea814515088b8afd7d6/68747470733a2f2f7261772e6769746875622e636f6d2f4d61726b4d6a772f50756c6c546f526566726573682f6d61737465722f53637265656e73686f74732f312e706e67)

(2)然后又学了[chrisbanes/Android-PullToRefresh](https://github.com/chrisbanes/Android-PullToRefresh)的那个库，这个库牛逼到要死，支持ListView、ExpandableListView、GridView、WebView、ScrollView、HorizontalScrollView、ViewPager、ListFragment、、、

![](https://github.com/chrisbanes/Android-PullToRefresh/raw/master/header_graphic.png)

自己也侮辱了这个库，改的乱七八糟[https://github.com/android-cjj/ComicReader/tree/master/YinHunPulltoRefreshLibrary](https://github.com/android-cjj/ComicReader/tree/master/YinHunPulltoRefreshLibrary)，增加了支持瀑布流刷新的功能和下拉动画效果的。。。。

![](https://camo.githubusercontent.com/1b016544f28f6abe5775f9b8fdde4ece8c874263/687474703a2f2f7777772e61706b6275732e636f6d2f646174612f6174746163686d656e742f666f72756d2f3230313530342f31342f3039313630366570766f63636e6e38376f67387a38742e706e67)

（3）那时候看了知乎的客户端，下拉刷新很炫，查了下是用什么实现的，最终知道是用[chrisbanes/ActionBar-PullToRefresh](https://github.com/chrisbanes/ActionBar-PullToRefresh)的库可以实现那种效果，又去学了，啊哈哈，然而过些日子也没见人用了，啊哈哈哈

![](https://github.com/chrisbanes/ActionBar-PullToRefresh/raw/master/header.png)

(4)这时候google也有自己的下拉控件了，刚出来的效果，一条加载直线，个人觉得，一般到要死。

![](https://camo.githubusercontent.com/9c0181efd67b9b7f080a1526311eba64485539c2/687474703a2f2f73746f726d7a68616e672e6769746875622e696f2f696d6167652f5377697065526566726573684c61796f75742e676966)

android 5.0之后效果是个加载圆圈,还可以接受了，现在很多应用都用这个

![](https://camo.githubusercontent.com/cfdc4a235ea790c206e802949cea6be0ed2b1546/687474703a2f2f7777772e61706b6275732e636f6d2f646174612f6174746163686d656e742f666f72756d2f3230313530342f32322f31323031303576357a6a6930307278726377727a7a782e706e67)












