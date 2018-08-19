package com.ibbhub.album;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/9 19:28
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumPreviewFragment extends Fragment {

    private RecyclerView rcList;
    private AlbumBottomMenu abMenu;

    private List<AlbumBean> mData;
    private PreviewAdappter mAdappter;
    private TaPagerHelper taPagerHelper = new TaPagerHelper();

    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_preview, container, false);

        mData = getArguments().getParcelableArrayList("data");
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

        taPagerHelper.setListener(new TaPagerHelper.PageHelperListener() {
            @Override
            public void onPageChanged(int position) {
                //设置标题
                AlbumPreviewFragment.this.position = position;
            }
        });
        taPagerHelper.attachToRecyclerView(rcList);
    }
}
