package com.example.juzhang.bicycle.Fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.etsy.android.grid.HeaderViewListAdapter;
import com.etsy.android.grid.StaggeredGridView;
import com.example.juzhang.bicycle.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * 功能：买车Fragment
 * 作者：JuZhang
 * 时间：2017/5/9
 */

public class BuyFragment extends Fragment {
    private View oldView = null;
    private StaggeredGridView sgv_display;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(oldView == null){
            oldView = view = View.inflate(getContext(), R.layout.fragment_buy, null);
            initUI();
        }else{
            view = oldView;
        }
        return view;
    }

    private void initUI() {
//        sgv_display = (StaggeredGridView) oldView.findViewById(R.id.sgv_buy_display);
//        sgv_display.setAdapter(new MyGridViewAdapter(getContext()));
    }
}
class MyGridViewAdapter implements ListAdapter {
    private Context context;

    MyGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_stagerdgridview_content, null);
        if (position==0) {
            ((SmartImageView) view.findViewById(R.id.siv_buy_pic)).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_user));
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}