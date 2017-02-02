package tw.gov.epa.taqmpredict.data;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.Date;

/**
 * Created by user on 2017/1/23.
 */

public class DataCache<K, V> {
    /**
     * cache json data
     */
    private LruCache<K, V> mJsonCache;

    /**
     * cache picture info
     */
    private LruCache<K, Bitmap> mBitmapCache;

    private DataCalendar dataCalendar;

    public DataCache() {
        mJsonCache = new LruCache<K, V>(1 * 1024 * 1024);
        mBitmapCache = new LruCache<K, Bitmap>(2 * 1024 * 1024);
        dataCalendar = new DataCalendar();
    }

    /**
     * Generate data key
     * @param date
     * @return date key
     */
    public String getDateKey(Date date){
        return dataCalendar.getDateFormat(date);
    }

    /**
     * Generate real time data key
     * @return date key
     */
    public String getRealTimeInfoKey(){
        return getDateKey(new Date());
    }

    /**
     * add into cache list
     *
     * @param key
     * @param value
     */
    public void addJsonLruCache(K key, V value) {
        mJsonCache.put(key, value);
    }

    public void addBitmapLruCache(K key, Bitmap value) {
        mBitmapCache.put(key, value);
    }

    /**
     * get list from cache
     *
     * @param key
     * @return
     */
    public V getJsonLruCache(K key) {
        return mJsonCache.get(key);
    }

    public Bitmap getBitmapLruCache(K key) {
        return mBitmapCache.get(key);
    }

}
