package tw.gov.epa.taqmpredict.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.ArrayList;
import java.util.List;

import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.util.FileUtil;
import tw.gov.epa.taqmpredict.util.PreferencesUtil;
import tw.gov.epa.taqmpredict.util.TaskExecutor;

import tw.gov.epa.taqmpredict.AirPollutionApplication;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class DBManage {
    private static DBManage sDBManage;
    private static DBHelper sDBHelper;
    private static final String CITY_INITED = "CITY_INITED";

    private DBManage() {
    }

    public static DBManage getInstance() {
        if (sDBManage == null) {
            synchronized (DBHelper.class) {
                if (sDBManage == null) {
                    sDBManage = new DBManage();
                    sDBHelper = DBHelper.getInstance(AirPollutionApplication.getContext());
                }
            }
        }
        return sDBManage;
    }

    public void copyCitysToDB() {
        boolean cityInited = PreferencesUtil.get(CITY_INITED, false);
        if (cityInited) {
            return;
        }
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                String citys = FileUtil.assetFile2String("cityList.txt", AirPollutionApplication.getContext());
                CityEntry cityEntry = AirPollutionApplication.getGson().fromJson(citys, CityEntry.class);
                //Collections.sort(cityEntry.getCity_info(), new CityComparator());
                SQLiteDatabase db = sDBHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    ContentValues values;
                    for (CityEntry.CityInfoEntity cityInfoEntity : cityEntry.getCity_info()) {
                        // Create a new map of values, where column names are the keys
                        values = new ContentValues();
                        values.put(CityDao.COUNTY_ID, cityInfoEntity.getCountyId());
                        values.put(CityDao.AREA_GROUP,cityInfoEntity.getAreaGroup());
                        values.put(CityDao.CITY_NAME,cityInfoEntity.getCityName());
                        values.put(CityDao.CITY_NAME_PINYIN,
                                PinyinHelper.convertToPinyinString(
                                        cityInfoEntity.getCityName(),",", PinyinFormat.WITH_TONE_MARK));
                        values.put(CityDao.COUNTY,cityInfoEntity.getCounty());
                        values.put(CityDao.COUNTY_PINYIN,
                                PinyinHelper.convertToPinyinString(
                                        cityInfoEntity.getCounty(),",", PinyinFormat.WITH_TONE_MARK));
                        values.put(CityDao.SITE_NAME,cityInfoEntity.getSiteName());
                        values.put(CityDao.SITE_NAME_PINYIN,
                                PinyinHelper.convertToPinyinString(
                                        cityInfoEntity.getSiteName(),",", PinyinFormat.WITH_TONE_MARK));
                        long resu = db.insert(CityDao.TABLE_NAME, null, values);
                        Log.d("copyCitysToDB: ",resu+": "+cityInfoEntity.getSiteName()+"("+cityInfoEntity.getCountyId()+")");
                    }
                    db.setTransactionSuccessful();
                    PreferencesUtil.put(CITY_INITED, true);
                } catch (Exception e) {
                    e.getMessage();
                } finally {
                    db.endTransaction();

                }
            }
        },false);
    }

    /**
     * read all city
     *
     * @return
     */
    public List<CityInfoData> getAllCities() {
        String allCitySql = "select * from " + CityDao.TABLE_NAME;
        return getCitys(allCitySql, true);
    }

    //read group
    public List<CityInfoData> getGroupArea(String group) {
        String allCitySql = "select * from " + CityDao.TABLE_NAME+" where "+CityDao.AREA_GROUP + " like \"%" + group + "%\"";
        return getCitys(allCitySql, true);
    }

    //read site
    public List<CityInfoData> getLocality(String county) {
        String allCitySql = "select * from " + CityDao.TABLE_NAME+" where "+CityDao.COUNTY + " like \"%" + county + "%\"";
        return getCitys(allCitySql, true);
    }

    /**
     * search by name or pinyin
     *
     * @param keyword
     * @return
     */
    public List<CityInfoData> searchCity(final String keyword) {

        String searchSql = "select * from " + CityDao.TABLE_NAME +
                " where " + CityDao.SITE_NAME + " like \"%" + keyword + "%\" or " +
                            CityDao.SITE_NAME_PINYIN + " like \"%" + keyword + "%\" or " +
                            CityDao.COUNTY + " like \"%" + keyword + "%\" or " +
                            CityDao.COUNTY_PINYIN + " like \"%" + keyword + "%\" or " +
                            CityDao.CITY_NAME + " like \"%" + keyword + "%\" or " +
                            CityDao.CITY_NAME_PINYIN + " like \"%" + keyword + "%\" or " +
                            CityDao.COUNTY_ID + " like \"%" + keyword + "%\"";

        return getCitys(searchSql, false);
    }

    private List<CityInfoData> getCitys(String sql, boolean all) {
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<CityInfoData> result = new ArrayList<>();
        CityInfoData city;
        String lastInitial = "";
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(CityDao.COUNTY_ID));
            String cityName = cursor.getString(cursor.getColumnIndex(CityDao.CITY_NAME));
            String cityName_pinyin = cursor.getString(cursor.getColumnIndex(CityDao.CITY_NAME_PINYIN));
            String county = cursor.getString(cursor.getColumnIndex(CityDao.COUNTY));
            String county_pinyin = cursor.getString(cursor.getColumnIndex(CityDao.COUNTY_PINYIN));
            String siteName = cursor.getString(cursor.getColumnIndex(CityDao.SITE_NAME));
            String siteName_pinyin = cursor.getString(cursor.getColumnIndex(CityDao.SITE_NAME_PINYIN));
            city = new CityInfoData(id,cityName,cityName_pinyin,county,county_pinyin,siteName,siteName_pinyin);
            String currentInitial = county_pinyin.substring(0, 1);
            if (!lastInitial.equals(currentInitial) && all) {
                city.setInitial(currentInitial);
                lastInitial = currentInitial;
            }
            result.add(city);
        }
        cursor.close();
        db.close();
        return result;
    }


    /**
     * order by a-z
     */
//    private class CityComparator implements Comparator<CityEntry.CityInfoEntity> {
//        @Override
//        public int compare(CityEntry.CityInfoEntity lhs, CityEntry.CityInfoEntity rhs) {
//            int c = 0;
//            try {
//                char a = PinyinHelper.convertToPinyinString(lhs.getCounty(),",", PinyinFormat.WITH_TONE_MARK).charAt(0);
//                char b = PinyinHelper.convertToPinyinString(rhs.getCounty(),",", PinyinFormat.WITH_TONE_MARK).charAt(0);
//                c = a-b;
//            } catch (PinyinException e) {
//                e.printStackTrace();
//            }
//            return c;
//        }
//    }

}
