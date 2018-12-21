package com.ibbhub.album;

import android.content.DialogInterface;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/9 19:28
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumPreviewFragment extends Fragment implements TaPagerHelper.PageHelperListener {

    private RecyclerView rcList;
    private AlbumBottomMenu abMenu;

    protected List<AlbumBean> mData = new ArrayList<>();
    private PreviewAdappter mAdappter;
    private TaPagerHelper taPagerHelper = new TaPagerHelper();

    protected int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_preview, container, false);
        List<AlbumBean> list = getArguments().getParcelableArrayList("data");
        mData.addAll(list);
        position = getArguments().getInt("pos");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcList = view.findViewById(R.id.rcList);
        abMenu = view.findViewById(R.id.abMenu);

        abMenu.setMenuListener(new AlbumBottomMenu.AlubmBottomMenuListener() {
            @Override
            public void onDeleteClick() {
                showConfirmDelete();
            }

            @Override
            public void onShareClick() {
                TaShareManager.getInstance().openShare(getContext(), mData.get(position).path);
            }
        });

        mAdappter = new PreviewAdappter(mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcList.setLayoutManager(linearLayoutManager);
        rcList.setAdapter(mAdappter);
        rcList.scrollToPosition(position);

        taPagerHelper.setListener(this);
        taPagerHelper.attachToRecyclerView(rcList);
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
                DeleteEvent event = new DeleteEvent();
                event.albumBean = mData.get(position);
                EventBus.getDefault().post(event);
                if (mData.size()>1){
                    mData.remove(position);
                    mAdappter.notifyItemRemoved(position);
                }else {
                    getActivity().finish();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void onPageChanged(int position) {
        //设置标题
        AlbumPreviewFragment.this.position = position;
    }
}
