package com.ibbhub.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ibbhub.album.adapter.AlbumAdapter;
import com.ibbhub.album.bean.AlbumBean;
import com.ibbhub.album.bean.MediaBean;
import com.ibbhub.album.util.FileUtils;
import com.ibbhub.album.view.AlbumBottomMenu;

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
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumFragment extends Fragment {
    public static boolean isChooseMode = false;
    private String TAG = getClass().getSimpleName();

    private List<AlbumBean> mData = new ArrayList<>();
    private AlbumAdapter mAdapter;
    private RecyclerView rc_list;
    private ProgressBar pb_loading;
    private AlbumBottomMenu album_menu;

    private List<AlbumBean> choosedCache = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initData();
    }

    private void initView(View view) {
        pb_loading = view.findViewById(R.id.pb_loading);
        album_menu = view.findViewById(R.id.album_menu);

        rc_list = view.findViewById(R.id.rc_list);
        mAdapter = new AlbumAdapter(mData);

        rc_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rc_list.setAdapter(mAdapter);

        album_menu.setMenuListener(new AlbumBottomMenu.AlubmBottomMenuListener() {
            @Override
            public void onDeleteClick() {

            }

            @Override
            public void onShareClick() {
                //分享
                processShare();
            }
        });
    }

    private Calendar cal1 = Calendar.getInstance();

    private void initData() {

        AlbumHelper.getInstance().setAdapterListener(new AdapterListener<MediaBean>() {
            @Override
            public void onItemClick(MediaBean mediaBean, View v) {
                AlbumBean albumBean = new AlbumBean();
                albumBean.setDate(mediaBean.date);
                if (isChooseMode) {
                    int index = choosedCache.indexOf(albumBean);
                    List<MediaBean> mbList;
                    if (index < 0) {
                        //被选中
                        mbList = new ArrayList<>();
                        mbList.add(mediaBean);
                        albumBean.setItemList(mbList);
                        choosedCache.add(albumBean);
                    } else {
                        mbList = choosedCache.get(index).itemList;

                        //如果被选中，则添加到缓存中
                        if (mediaBean.isChecked) {
                            mbList.add(mediaBean);
                        } else {
                            mbList.remove(mediaBean);
                            if (mbList.size() == 0) {
                                choosedCache.remove(index);
                            }
                        }
                    }
                } else {
                    int index = mData.indexOf(albumBean);
                    AlbumBean ab = mData.get(index);
                    index = ab.itemList.indexOf(mediaBean);
                    if (index >= 0) {
                        AlbumPreviewActivity.start(getContext(), (ArrayList<MediaBean>) ab.itemList, index);
                    }
                }
            }

            @Override
            public void onItemLongClick(MediaBean mediaBean, View v) {
                //进入选择模式
                isChooseMode = true;
                mAdapter.notifyDataSetChanged();
                album_menu.setVisibility(View.VISIBLE);
            }
        });

        List<File> fileList = AlbumHelper.getInstance().getSrcFiles();
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
                .map(new Function<File, MediaBean>() {
                    @Override
                    public MediaBean apply(File file) throws Exception {
                        Date fileDate = FileUtils.parseDate(file);
                        cal1.setTime(fileDate);
                        // 将时分秒,毫秒域清零
                        cal1.set(Calendar.HOUR_OF_DAY, 0);
                        cal1.set(Calendar.MINUTE, 0);
                        cal1.set(Calendar.SECOND, 0);
                        cal1.set(Calendar.MILLISECOND, 0);
                        MediaBean mediaBean = new MediaBean();
                        mediaBean.date = cal1.getTime().getTime();
                        mediaBean.path = file.getAbsolutePath();
                        return mediaBean;
                    }
                })
                .collect(new Callable<List<AlbumBean>>() {
                    @Override
                    public List<AlbumBean> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<AlbumBean>, MediaBean>() {
                    @Override
                    public void accept(List<AlbumBean> albumBeans, MediaBean mediaBean) throws Exception {
                        AlbumBean albumBean = new AlbumBean();
                        albumBean.setDate(mediaBean.date);
                        int index = albumBeans.indexOf(albumBean);
                        if (index >= 0) {
                            albumBeans.get(index).itemList.add(mediaBean);
                        } else {
                            albumBean.itemList.add(mediaBean);
                            albumBeans.add(albumBean);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BiConsumer<List<AlbumBean>, Throwable>() {
                    @Override
                    public void accept(List<AlbumBean> albumBeans, Throwable throwable) throws Exception {
                        mData.addAll(albumBeans);
                        sortList();
                    }
                });


    }

    /**
     * 数据根据时间进行排序
     */
    private void sortList() {
        Collections.sort(mData, new Comparator<AlbumBean>() {
            @Override
            public int compare(AlbumBean o1, AlbumBean o2) {
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

    private void processShare() {
        //判断是多张还是单张
        if (choosedCache.size() == 1 && choosedCache.get(0).itemList.size() == 1) {
            //单张
            MediaBean mediaBean = choosedCache.get(0).itemList.get(0);
            ALShareManager.getInstance().openShare(getContext(), mediaBean.path);
        } else {
            //多张
            ArrayList<Uri> uriList = new ArrayList<>();
            for (int i = 0; i < choosedCache.size(); i++) {
                for (MediaBean mb :
                        choosedCache.get(i).itemList) {
                    uriList.add(Uri.fromFile(new File(mb.path)));
                }
            }
            ALShareManager.getInstance().openShare(getContext(),uriList);
        }
        resetRecycler();
    }

    private void resetRecycler() {
        for (int i = 0; i < choosedCache.size(); i++) {
            for (MediaBean mb :
                    choosedCache.get(i).itemList) {
                mb.isChecked = false;
            }
        }
        choosedCache.clear();
        isChooseMode = false;
        mAdapter.notifyDataSetChanged();
    }
}
