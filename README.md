# TimeAlbum 时间相册

该库是将本地的图片和视频资源进行按时间分类排序并进行展示的相册库。

[![](https://jitpack.io/v/chezi008/TimeAlbum.svg)](https://jitpack.io/#chezi008/TimeAlbum)

#### 功能说明
1. 图片和视频资源根据日期排序显示。
2. 图片视频预览功能，图片、视频预览带缓存功能。
3. 单个图片或视频可进行删除及分享操作。
4. 多张图片进行分享功能，多张图片和视频进行删除功能。
5. Decoration可自定义扩展。
6. 图片显示可自定义扩展。
7. 图片视频可自定义预览操作。

#### 更新说明
**1.0.1**

- 预览界面添加删除功能。
- 添加超级照片的标记。

**1.0.3**

- 继承AlbumFragment 需要实现fileProviderName()方法，因为Android 7.0及以上系统Uri.fromFile获取Uri会报错。fileProviderName()方法返回的值填下方的authorities对应的值。
```
<provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="com.ibbhub.albumdemo.provider"
            android:exported="false"
            android:grantUriPermissions="true">

            <!-- 元数据 -->
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
```
file_paths内容
```
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:android="http://schemas.android.com/apk/res/android">
    <paths>
        <external-path
            name="external_files"
            path="." />
    </paths>
</resources>
```

- 修复删除多个文件失败的问题

#### 依赖
You need to make sure you have the JCenter and Google repositories included in the build.gradle file in the root of your project:
```
repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
```
Next add a dependency in the build.gradle file of your app module. The following will add a dependency to the full library:
```
implementation 'com.github.chezi008:TimeAlbum:x.x.x'
```
maven
```
	<dependency>
  	<groupId>com.github.chezi008</groupId>
	<artifactId>TimeAlbum</artifactId>
	<version>1.0.0</version>
	</dependency>
```

#### 如何使用
**只需要继承AlbumFragment即可**。需要实现的方法下面有详细的说明：
```
/**
     * 设置相册的媒体源
     *
     * @return
     */
    public abstract List<File> buildAlbumSrc();

    /**
     * 设置recyclerView的装饰器
     *
     * @return
     */
    public abstract ITaDecoration buildDecoration();

    /**
     * 跳转至预览界面
     *
     * @param data 预览数据
     * @param pos  当前选择albumBean 的位置
     */
    public void start2Preview(ArrayList<AlbumBean> data, int pos) {
        AlbumPreviewActivity.start(getContext(), data, pos);
    }
	/**
     * 加载图片，覆盖原来图片的大小
     * 预览多个小图片的时候使用
     *
     * @param path
     * @param iv
     */
    void loadOverrideImage(String path, ImageView iv);

    /**
     * 加载图片
     * 单个图片预览的时候使用
     *
     * @param path
     * @param iv
     */
    void loadImage(String path, ImageView iv);

    /**
     * 选择模式改变
     *
     * @param isChoose
     */
    void onChooseModeChange(boolean isChoose);
```
**Sample**
```
public class MyAlbumFragment extends AlbumFragment {
    @Override
    public List<File> buildAlbumSrc() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/DCIM/Camera";
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        return fileList;
    }

    @Override
    public ITaDecoration buildDecoration() {
        return null;
    }

    @Override
    public void loadOverrideImage(String path, ImageView iv) {
        Glide.with(iv)
                .load(path)
                .thumbnail(0.1f)
                .apply(buildOptions())
                .into(iv);
    }

    @Override
    public void loadImage(String path, ImageView iv) {
        Glide.with(iv)
                .load(path)
                .thumbnail(0.1f)
                .into(iv);
    }

    @Override
    public void onChooseModeChange(boolean isChoose) {
        ((MainActivity)getActivity()).onChooseModeChange(isChoose);
    }

    public static RequestOptions buildOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(100, 100);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        return requestOptions;
    }
}
```

#### 扩展Decoration
相册默认的decoration为浅蓝色背景带日期和图片数量提示的。用户可以自定义decoration进行显示，只需要实现**ITaDecoration**接口，在继承AlbumFragment的buildDecoration方法中返回即可。
```
public interface ITaDecoration {
    /**
     * 显示日期
     *
     * @param date
     */
    void showDate(long date);

    /**
     * 显示相册下面的视频、图片的数量
     *
     * @param num
     */
    void showNum(int num);

    /**
     * 构建decoration 的视图
     * @return
     */
    View buildView();
}
```
#### 显示效果
| 相册主页 | 多选效果 | 预览照片 | 预览视频 |
| ------ | ------ | ------ |------ |
| ![](https://i.imgur.com/BR8Txtb.png) | ![](https://i.imgur.com/qYwCh00.png) | ![](https://i.imgur.com/HwGg87J.png) |![](https://i.imgur.com/jwWbcCT.png)|

#### 性能方面
图片预览使用的是glide包，用户也可以自己选择加载图片的框架，所以这里的性能方面无需担忧。

#### 问题
**1、recycleview 在grid模式下面如何添加ItemDecoration(item间隙)?**
之前一直遇到，item之间的间隔大小不均的问题，后面再stackOverFlow里面找到了答案：
```
class GridDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
```

#### GITHUB
https://github.com/chezi008/TimeAlbum
