通过第三方框架实现换肤
-----------
- 换肤效果图如下所示：
---
<img src="skin.gif" width="500px"/>

---
- 注：[参考链接](http://blog.csdn.net/u013478336/article/details/53083054)
### 1.首先导入第三方框架QSkinLoaderlib,并添加依赖或者在项目的build.gradle文件中直接添加依赖
```
compile 'com.excellence:skinloader:1.2.2'
```
- 如果添加成功，会在Project 的External Library目录下生成skinloader:1.2.2目录，这时就可以调用里面的东西进行换肤了。

### 2. 定义BaseActivity，重写Activity生命周期方法和onWindowFocusChange（），在onCreate（）中初始化IActivitySkinEventHandler对象，用这个对象在生命周期和onWindowFocusChange（）当中调用相对应的方法。需要具有换肤功能的Activity必须继承BaseActivity。
### 3.BaseActivity必须实现框架中的 ISkinActivity接口，重写其中三个方法isSupportSkinChange() ，isSwitchSkinImmediately()，handleSkinChange()方法。
### 4.定义SkingChangeHeper类，用于实现换肤的操作逻辑，以及皮肤切换成功时定义一个标识，用于保存当前皮肤。
### 5.定义MyApplication继承与Application，这个节点主要是获取皮肤切换成功时的标识，用这个标识来加载当前皮肤。并在清单文件的Application中使用。
### 6.在xml进行配置，对于一个布局引入xmlns:skin="http://schemas.android.com/android/skin"，然后对所有需要换肤的View增加属性：skin:enable="true
### 7.新建一个代码为空的皮肤包，在资源文件的drawable中存入要换肤的图片，注意图片的前缀名要和调用的View的background的图片名相同，后缀名可以不同，根据后缀名替换皮肤。


