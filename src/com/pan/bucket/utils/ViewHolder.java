/**
 * author :  lipan
 * filename :  ViewHolder.java
 * create_time : 2014-3-11 上午11:41:14
 */
package com.pan.bucket.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * @create_time : 2014-3-11 上午11:41:14
 * @desc : ViewHolder工具类，用于缓存view对象，节省每次都从布局文件中获取view的开销
 * 
 *       使用方法如下： public View getView(int position, View convertView, ViewGroup
 *       parent) { if(null == convertView) { convertView =
 *       View.inflate(mContext, R.layout.file_item, null); } ImageView image =
 *       ViewHolder.get(convertView, R.id.fileImg); TextView text =
 *       ViewHolder.get(convertView, R.id.fileName); //.... }
 * 
 * @update_time :
 * @update_desc :
 * 
 */
public class ViewHolder {

	// I added a generic return type to reduce the casting noise in client code

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {

		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
	
	
	public SparseArray<View> views = new SparseArray<View>();
    /**
     * 指定resId和类型即可获取到相应的view
     * @param convertView
     * @param resId
     * @param <T>
     * @return
     */
	public <T extends View> T obtainView(View convertView, int resId){
        View v = views.get(resId);
        if(null == v){
            v = convertView.findViewById(resId);
            views.put(resId, v);
        }
        return (T)v;
    }
}
