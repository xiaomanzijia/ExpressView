package com.licheng.android.expressview.expressview;

import java.util.List;

/**
 * Created by licheng on 19/3/17.
 */

public abstract class ExpressViewAdapter<T> {

    private OnDataChangedListener onDataChangedListener;

    private List<T> dataList;

    public ExpressViewAdapter(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getCount(){
       return dataList == null ? 0 : dataList.size();
    }

    public T getItem(int position){
        return dataList == null ? null : dataList.get(position);
    }

    public abstract ExpressViewData bindData(ExpressView expressView, int position, T t);

    public void notifyDataChanged(){
        onDataChangedListener.onDataChanged();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }
}
