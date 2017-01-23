package tw.gov.epa.taqmpredict.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by user on 2017/1/23.
 */

public class DataCache {
    /**
     * cache json data
     */
    private LruCache<Integer, String> mJsonCache;

    /**
     * cache picture info
     */
    private LruCache<Integer, Bitmap> mBitmapCache;

    public DataCache() {
        mJsonCache = new LruCache<Integer, String>(1 * 1024 * 1024);
        mBitmapCache = new LruCache<Integer, Bitmap>(2 * 1024 * 1024);
    }

    /**
     * add into cache list
     *
     * @param key
     * @param value
     */
    public void addJsonLruCache(Integer key, String value) {
        mJsonCache.put(key, value);
    }

    public void addBitmapLruCache(Integer key, Bitmap value) {
        mBitmapCache.put(key, value);
    }

    /**
     * get list from cache
     *
     * @param key
     * @return
     */
    public String getJsonLruCache(Integer key) {
        return mJsonCache.get(key);
    }

    public Bitmap getBitmapLruCache(Integer key) {
        return mBitmapCache.get(key);
    }

}
