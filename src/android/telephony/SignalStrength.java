/**
 * author :  lipan
 * filename :  S.java
 * create_time : 2014-12-6 下午4:33:57
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author : lipan
 * @create_time : 2014-12-6 下午4:33:57
 * @desc : 重写信号强度信息类，为了调用隐藏方法
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class SignalStrength implements Parcelable
{

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }
    
    /**
     * Get the GSM Signal Strength, valid values are (0-31, 99) as defined in TS
     * 27.007 8.5
     */
    public int getGsmSignalStrength() {
        return -1;
    }
    
    /**
     * Get gsm as level 0..4
     *
     * @hide
     */
    public int getGsmLevel() {
        return -1;
    }
}
