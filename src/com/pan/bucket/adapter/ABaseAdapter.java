package com.pan.bucket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pan.bucket.utils.ViewHolder;

/**
 * 实现对BaseAdapter中ViewHolder相关的简化
 */
public abstract class ABaseAdapter extends BaseAdapter{
    Context context;

    /**
     * 必须调用该构造方法，否则context为空...
     * @param context
     */
    protected ABaseAdapter(Context context) {
        this.context = context;
    }

    protected ABaseAdapter() {
    }
    
    /**
     * 改方法需要子类实现，需要返回item布局的resource id
     * @return
     */
    public abstract int itemLayoutRes();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(itemLayoutRes(), null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return getView(position, convertView, parent, holder);
    }

    /**
     * 使用该getView方法替换原来的getView方法，需要子类实现
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getView(int position, View convertView, ViewGroup parent, ViewHolder holder);


}
