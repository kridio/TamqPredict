package tw.gov.epa.taqmpredict.map;

import android.graphics.Color;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ColorGradient {
    private static int opacity = 255;
    private static int color = 0;
    private static int fullColor = 255;
    private static int colorScale = 0;
    public static int get(int value){
//        colorScale = value;
//        while(colorScale >= 12){
//            colorScale = colorScale - 12;
//        }
//        if(value < 12){
//            color = Color.argb(opacity,169,fullColor,169);
//        }
//        else if(value < 24){
//            if(colorScale == 11){
//                color = Color.argb(opacity, 0, fullColor, 0);
//            }
//            else {
//                color = Color.argb(opacity, 154 - colorScale * 15, fullColor, 154 - colorScale * 15);
//            }
//        }
        if(value < 24){
            color = Color.argb(opacity, 36 + value*9, fullColor , 36 - value*1);
        }
//        if(value<36){
//            if(value % 3 == 0){
//                color = Color.argb(opacity, 7 * value + 1, fullColor, 0);
//            }
//            else {
//                color = Color.argb(opacity, 7 * value, fullColor, 0);
//            }
//        }
        else if(value < 36){
            colorScale = value - 24;
            color = Color.argb(opacity, fullColor, fullColor - 10 * colorScale , 5 * colorScale);
        }
//        else if(value < 53){
//
//        }
        else if(value < 53){
            colorScale = value - 17;
            color = Color.argb(opacity, fullColor, fullColor - 3 * colorScale, 51);
        }
        else if(value < 71){
            colorScale = value - 18;
            color = Color.argb(opacity, fullColor, 150 - 8 * colorScale, 51 - 3 * colorScale);
        }
        else{
            color = Color.argb(opacity, fullColor, 0, 0);
        }
//        else if(value < 48){
//            color = Color.argb(opacity, fullColor,104 + colorScale*10, 102 - colorScale*20);
//        }
//        else if(value < 54){
//            color = Color.argb(opacity, fullColor,151 - colorScale*6, colorScale*25);
//        }
//        else if(value<59){
//            color = Color.argb(opacity, fullColor,124 - colorScale*24, 128 - colorScale*25);
//        }
//        else if(value<65){
//            color = Color.argb(opacity, fullColor - colorScale * 20, 0 , colorScale*10);
//        }
//        else{
//            color = Color.argb(opacity, 153, 0, 51);
//        }
        return color;
    }
}
