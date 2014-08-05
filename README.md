##Android ProgressText
----
用来实现歌词字级同步或
###使用方法
```
dependencies {
    compile project (':library')
}
```
在xml中声明：
```java
<com.withparadox2.progresstext.ProgressText
    android:id="@+id/progress_text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="50sp"
    custom:after_progress_color="@android:color/holo_purple"
    custom:before_progress_color="#FFFF00"
    custom:stroke_color="@android:color/black"
    custom:stroke_width="1px"/>
```
设置Progress：
```java
    textView = (ProgressText) findViewById(R.id.progress_text);
    textView.setText("绿岛小夜曲");
    textView.setProgressBypercentage(0.68f);
```
![image](https://github.com/withparadox2/ProgressText/raw/master/screenshots/progress_text_mini.png)

