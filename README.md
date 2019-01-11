# utilsDemo

已更新到1.2版本，添加依赖时请注意版本号：

## 常用的工具类
    整理一些常用的工具类方便以后使用，将持续更新，欢迎 star，欢迎issues.
可以将项目clone下来，将utilLibrary当做modlue引进项目中进行开发。
或者在budle.gradle中添加一下：
   
1：将其添加到存储库末尾的根build.gradle中
     
     allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
2：添加依赖关系
    
    dependencies {
            // 请查看releases,填写最新版本
	        implementation 'com.github.zhangshaopeng:utilsDemo:+'
	}
## 注意事项
### 1：权限问题
目前已经添加的权限有以下几种,如果有使用到相关方权限方法，可自行在项目里添加。

                      <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
                      <uses-permission android:name="android.permission.READ_PHONE_STATE" />
                      <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
                      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
                      <uses-permission android:name="android.permission.INTERNET"/>
### 2：第三方依赖问题
目前已经添加的第三方依赖有以下几种，如有需要，自己再另行添加

               compile 'com.belerweb:pinyin4j:2.5.0'
               compile 'com.google.code.gson:gson:2.7'
               compile 'com.alibaba:fastjson:1.2.8'
               compile 'com.github.bumptech.glide:glide:3.7.0'
### 3：初始化问题	      
*在使用的时候一定注意在你的application的onCreat（）方法中调用以下方法初始化该lib

            UtilManager.init(this);

### Other
欢迎您的到来，如果您有更好的方法：希望您能提出您的宝贵意见，一起进步！
