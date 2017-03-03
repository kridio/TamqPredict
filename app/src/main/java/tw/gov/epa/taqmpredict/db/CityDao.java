package tw.gov.epa.taqmpredict.db;

import android.provider.BaseColumns;

/**
 * Created by SilenceDut on 16/10/28.
 */

class CityDao implements BaseColumns {
    private CityDao() {
    }

    static final String TABLE_NAME = "city";

    static final String COUNTY = "county";

    static final String COUNTY_PINYIN = "countyPinyin";

    static final String SITE_NAME = "siteName";

    static final String SITE_PINYIN = "siteNamePinyin";

    static final String CITY_ID = "cityId";

    private static final String CITY_INFO = "cityInfo";

    //private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " (" +
            COUNTY + COMMA_SEP +
            COUNTY_PINYIN + COMMA_SEP +
            SITE_NAME + COMMA_SEP +
            SITE_PINYIN + COMMA_SEP +
            CITY_ID + COMMA_SEP +
            CITY_INFO +
            ")";


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}


