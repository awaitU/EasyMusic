# EasyMusic
just a simple Music player，but  I think you can learn more from it.<br> 
## 功能展示：<br> 

### 引导页面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/Splash.png)
### 首页界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/Mainview.png)
### 侧滑菜单栏界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/DrawerLayout.png)
### 每日推荐界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/UpdateMusic.png)
### 歌曲列表：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/Songlist.png)
### 播放主界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/MusicPlay.png)
### 通知栏界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/Notification.png)
### 锁屏界面：<br> 
![image](https://github.com/awaitU/EasyMusic/blob/master/UIshow/ScreenLock.png)

## 系统设计：<br>

###  app主界面界面：
#### 运用了viewpager轮播图片，需要注意的事就是一定要注意不使用图片的视乎需要释放图片资源，防止造成内存泄漏；
#### 为了实现每天动态更新客户端上的音乐列表，本案例采用了在android中嵌入服务器上的h5展示服务器上的音视频资源，用户只要安装一次软件，开发者只要改变服务器上的网页设计就可以实现动态更新软件UI显示与功能，而不需要重新安装。注意！这不同于基于React Native,Thinker,插件化实现软件的动态更新，按照我的理解，仅仅是一种实现软件动态更新的方式。当然，点击跳转到每日歌单页面的时候一定要判断网络状态，当网络状态ok的时候，实现跳转页面，当网络不ok的时候提示用户打开网络。还有就是在展示网络音乐列表的页面需要监听网络状态，不然如果断网的时候是不能成功播放服务器上的音乐的。

### 我的音乐界面：
#### 我在这里实现了本地音乐的检索及播放控制的功能。主要设计思想是先检索本地音乐，将mp3格式的音乐文件保存在一个动态数组中，注册音乐服务，实现音乐服务的逻辑，ListMusicFragment展示本地音乐列表，添加列表点击事件，当点击音乐列表的时候，发送广播实现音乐的播放，暂停，下一首等功能。核心思想是注册服务与广播。

### 注册界面：
#### 主要是依赖掌淘科技的短信消息SDK，实现了短信验证码验证，接下来就是验证码通过之后，实现注册功能。注册实现的核心思想是通过将用户输入的用户名与密码以键值对的方式保存在SharePreference中。

### 侧滑菜单栏：
#### 侧滑菜单栏主要是通过自定义DrawerLayout实现。

## 上面是简单的实现介绍，后面有时间再更新一下。

