package tw.gov.epa.taqmpredict.util;

import java.lang.reflect.Field;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by user on 2017/4/19.
 */

public class ResourceUtil {
    public static int getResourceId(String name) {
        try {
            //根據資源ID的變數名獲得Field的對象，使用反射機制來實現
            Field field = R.drawable.class.getField(name);
            //取得並返回資源的id的值，使用反射機制
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
