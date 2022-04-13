package com.awen.codebase.activity.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.DragRecyclerViewAdapter;

public class DragRecyclerViewFragment extends Fragment{
	private ArrayList<String> originImages;//原始图片
	private DragRecyclerViewAdapter postArticleImgAdapter;
	private ItemTouchHelper itemTouchHelper;
	private RecyclerView rcvImg;
	private DragItemCallBack itemDragCallBack;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anigrid, container);
		rcvImg = view.findViewById(R.id.recyclerView);
		initData();
		initView();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initData() {
		originImages = new ArrayList<>();
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
		originImages.addAll(originImages);
		originImages.add("1");
		originImages.add("1");
		originImages.add("1");
	}

	private void initView() {

		initRcv();
	}

	private void initRcv() {

		postArticleImgAdapter = new DragRecyclerViewAdapter(getActivity(), originImages);
		rcvImg.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
		rcvImg.setAdapter(postArticleImgAdapter);
		itemDragCallBack = new DragItemCallBack(postArticleImgAdapter, originImages, originImages);
		itemTouchHelper = new ItemTouchHelper(itemDragCallBack);
		itemTouchHelper.attachToRecyclerView(rcvImg);//绑定RecyclerView

		//事件监听
		rcvImg.addOnItemTouchListener(new ItemTouchListener(rcvImg) {

			@Override
			public void onItemClick(RecyclerView.ViewHolder vh) {
			}

			@Override
			public void onItemLongClick(RecyclerView.ViewHolder vh) {
				//如果item不是最后一个，则执行拖拽
				if (vh.getLayoutPosition() != originImages.size() - 1) {
					itemTouchHelper.startDrag(vh);
				}
			}
		});
		itemDragCallBack.setDragListener(new DragItemCallBack.DragListener() {
			@Override
			public void deleteState(boolean delete) {
			}

			@Override
			public void dragState(boolean start) {
			}

			@Override
			public void clearView() {
			}
		});

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
