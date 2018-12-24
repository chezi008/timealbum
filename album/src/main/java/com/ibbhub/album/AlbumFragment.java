package com.ibbhub.album;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：chezi008 on 2018/8/1 22:34
 * @description ：时间相册显示的主页面
 * @email ：chezi008@163.com
 */
public abstract class AlbumFragment extends Fragment implements TimeAlbumListener {
    public static boolean isChooseMode = false;
    private String TAG = getClass().getSimpleName();

    private List<TimeBean> mData = new ArrayList<>();
    private TimeAdapter mAdapter;
    private RecyclerView rc_list;
    private ProgressBar pb_loading;
    private AlbumBottomMenu album_menu;

    private List<TimeBean> choosedCache = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initVariable();
        initData();
    }

    private void initVariable() {
        isChooseMode = false;
        TaHelper.getInstance().setAdapterListener(new AdapterListener<AlbumBean>() {
            @Override
            public void onItemClick(AlbumBean albumBean, View v) {
                TimeBean timeBean = new TimeBean();
                timeBean.setDate(albumBean.date);
                if (isChooseMode) {
                    int index = choosedCache.indexOf(timeBean);
                    List<AlbumBean> mbList;
                    if (index < 0) {
                        //被选中
                        mbList = new ArrayList<>();
                        mbList.add(albumBean);
                        timeBean.setItemList(mbList);
                        choosedCache.add(timeBean);
                    } else {
                        mbList = choosedCache.get(index).itemList;

                        //如果被选中，则添加到缓存中
                        if (albumBean.isChecked) {
                            mbList.add(albumBean);
                        } else {
                            mbList.remove(albumBean);
                            if (mbList.size() == 0) {
                                choosedCache.remove(index);
                            }
                        }
                    }
                } else {
                    int index = mData.indexOf(timeBean);
                    TimeBean ab = mData.get(index);
                    index = ab.itemList.indexOf(albumBean);
                    if (index >= 0) {
                        start2Preview((ArrayList<AlbumBean>) ab.itemList, index);
                    }
                }
            }

            @Override
            public void onItemLongClick(AlbumBean albumBean, View v) {
                //进入选择模式
                enterChoose();
            }
        });
        TaHelper.getInstance()
                .setSrcFiles(buildAlbumSrc())
                .setTbDecoration(buildDecoration())
                .setLoadImageListener(this);
    }

    private void initView(View view) {
        pb_loading = view.findViewById(R.id.pb_loading);
        album_menu = view.findViewById(R.id.album_menu);

        rc_list = view.findViewById(R.id.rc_list);
        mAdapter = new TimeAdapter(mData);

        rc_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rc_list.setAdapter(mAdapter);

        album_menu.setMenuListener(new AlbumBottomMenu.AlubmBottomMenuListener() {
            @Override
            public void onDeleteClick() {
                showConfirmDelete();
            }

            @Override
            public void onShareClick() {
                //分享
                processShare();
            }
        });
    }

    /**
     * 弹出确认删除提示
     */
    private void showConfirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除照片");
        builder.setMessage("确认是否删除照片？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                processDelete();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private Calendar cal1 = Calendar.getInstance();

    private void initData() {
        List<File> fileList = TaHelper.getInstance().getSrcFiles();
        Observable.fromIterable(fileList)
                .flatMapIterable(new Function<File, Iterable<File>>() {
                    @Override
                    public Iterable<File> apply(File file) throws Exception {
                        return Arrays.asList(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File it) throws Exception {
                        return it.getName().endsWith(".jpg") || it.getName().endsWith(".mp4");
                    }
                })
                .map(new Function<File, AlbumBean>() {
                    @Override
                    public AlbumBean apply(File file) throws Exception {
                        Date fileDate = FileUtils.parseDate(file);
                        cal1.setTime(fileDate);
                        // 将时分秒,毫秒域清零
                        cal1.set(Calendar.HOUR_OF_DAY, 0);
                        cal1.set(Calendar.MINUTE, 0);
                        cal1.set(Calendar.SECOND, 0);
                        cal1.set(Calendar.MILLISECOND, 0);
                        AlbumBean albumBean = new AlbumBean();
                        albumBean.date = cal1.getTime().getTime();
                        albumBean.path = file.getAbsolutePath();
                        return albumBean;
                    }
                })
                .collect(new Callable<List<TimeBean>>() {
                    @Override
                    public List<TimeBean> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<TimeBean>, AlbumBean>() {
                    @Override
                    public void accept(List<TimeBean> timeBeans, AlbumBean albumBean) throws Exception {
                        TimeBean timeBean = new TimeBean();
                        timeBean.setDate(albumBean.date);
                        int index = timeBeans.indexOf(timeBean);
                        if (index >= 0) {
                            timeBeans.get(index).itemList.add(albumBean);
                        } else {
                            timeBean.itemList.add(albumBean);
                            timeBeans.add(timeBean);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BiConsumer<List<TimeBean>, Throwable>() {
                    @Override
                    public void accept(List<TimeBean> timeBeans, Throwable throwable) throws Exception {
                        if (timeBeans != null) {
                            mData.addAll(timeBeans);
                            sortList();
                        }

                    }
                });


    }

    /**
     * 数据根据时间进行排序
     */
    private void sortList() {
        Collections.sort(mData, new Comparator<TimeBean>() {
            @Override
            public int compare(TimeBean o1, TimeBean o2) {
                if (o1.date > o2.date) {
                    return -1;
                } else if (o1.date == o2.date) {
                    return 0;
                }
                return 1;
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb_loading.setVisibility(View.GONE);
                rc_list.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 处理分享
     */
    private void processShare() {
        //判断是多张还是单张
        if (choosedCache.size() == 1 && choosedCache.get(0).itemList.size() == 1) {
            //单张
            AlbumBean albumBean = choosedCache.get(0).itemList.get(0);
            TaShareManager.getInstance().openShare(getContext(), albumBean.path);
        } else {
            //多张
            ArrayList<Uri> uriList = new ArrayList<>();
            for (int i = 0; i < choosedCache.size(); i++) {
                for (AlbumBean mb :
                        choosedCache.get(i).itemList) {
                    uriList.add(Uri.fromFile(new File(mb.path)));
                }
            }
            TaShareManager.getInstance().openShare(getContext(), uriList);
        }
        cancelChoose();
    }

    /**
     * 处理照片删除
     */
    private void processDelete() {
        //判断是多张还是单张
        if (choosedCache.size() == 1 && choosedCache.get(0).itemList.size() == 1) {
            //单张
            AlbumBean albumBean = choosedCache.get(0).itemList.get(0);
            notifyAlbumRemove(albumBean);
        } else {
            //多张
            for (int i = 0; i < choosedCache.size(); i++) {
                for (AlbumBean mb :
                        choosedCache.get(i).itemList) {
                    notifyAlbumRemove(mb);
                }
            }
        }
        choosedCache.clear();
    }

    private void notifyAlbumRemove(AlbumBean albumBean) {
        FileUtils.delete(albumBean.path);
        int index = mData.indexOf(new TimeBean(albumBean.date));
        TimeBean tb = mData.get(index);
        tb.itemList.remove(albumBean);
        if (tb.itemList.size() == 0) {
            mData.remove(index);
        }
        mAdapter.notifyItemChanged(index);
    }



    /**
     * 取消选择
     */
    public void cancelChoose() {
        for (int i = 0; i < choosedCache.size(); i++) {
            for (AlbumBean mb :
                    choosedCache.get(i).itemList) {
                mb.isChecked = false;
            }
        }
        choosedCache.clear();
        isChooseMode = false;
        TaHelper.getInstance().onChooseModeChange(isChooseMode);
        mAdapter.notifyDataSetChanged();
        album_menu.setVisibility(View.GONE);
    }

    /**
     * 进入选择
     */
    public void enterChoose() {
        isChooseMode = true;
        TaHelper.getInstance().onChooseModeChange(isChooseMode);
        mAdapter.notifyDataSetChanged();
        album_menu.setVisibility(View.VISIBLE);
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processDeleteEvent(DeleteEvent event){
        notifyAlbumRemove(event.albumBean);
    }
}
