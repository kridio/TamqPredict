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

    static final String COUNTY_PINYIN = "county_pinyin";

    static final String SITE_NAME = "siteName";

    static final String SITE_NAME_PINYIN = "siteName_pinyin";

    static final String COUNTY_ID = "countyId";

    static final String CITY_Name = "cityName";

    static final String CITY_Name_PINYIN = "cityName_pinyin";

    private static final String CITY_INFO = "cityInfo";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " (" +
            COUNTY + TEXT_TYPE + COMMA_SEP +
            COUNTY_PINYIN + TEXT_TYPE + COMMA_SEP +
            SITE_NAME + TEXT_TYPE + COMMA_SEP +
            SITE_NAME_PINYIN + TEXT_TYPE + COMMA_SEP +
            CITY_Name + TEXT_TYPE + COMMA_SEP +
            CITY_Name_PINYIN + TEXT_TYPE + COMMA_SEP +
            COUNTY_ID + TEXT_TYPE + COMMA_SEP +
            CITY_INFO + TEXT_TYPE +
            ")";


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}


