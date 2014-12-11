package android.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author : lipan
 * @create_time : 2014年11月12日 下午2:06:37
 * @desc : 为了调用隐藏的方法 ,重写android的AutoCompleteTextView类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class AutoCompleteTextView extends EditText implements Filter.FilterListener
{

    public AutoCompleteTextView(Context context)
    {
        super(context);
    }

    public AutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AutoCompleteTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter)
    {
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l)
    {
    }

    public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible)
    {
    }

    @Override
    public void onFilterComplete(int count)
    {

    }

    public void setThreshold(int threshold)
    {
    }

    public boolean enoughToFilter()
    {
        return true;
    }

    public void showDropDownAfterLayout()
    {
    }

    public void showDropDown()
    {
    }

    public ListAdapter getAdapter()
    {
        return null;
    }

    public void clearListSelection()
    {
    }

    public void performCompletion()
    {
    }
    public void dismissDropDown() {
    }
    public void setDropDownHeight(int height)  {
    }
    public boolean isPopupShowing() {
        return false;
    }
}
