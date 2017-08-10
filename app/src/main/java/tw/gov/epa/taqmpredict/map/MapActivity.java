package tw.gov.epa.taqmpredict.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import tw.gov.epa.taqmpredict.MainActivity;
import tw.gov.epa.taqmpredict.R;

public class MapActivity extends Activity {

    private static final String TAG = MapActivity.class.getSimpleName();
    ImageView iv;
    ImageButton btnReturn;
    ImageButton btnNowValue;
    ImageButton btnOneHrValue;
    ImageButton btnSixHrValue;
    ImageButton btnTwelveHrValue;
//    ImageButton btnRecover;
//    ImageButton btnList;
    RelativeLayout rl;
    private static int width = 1440;
    private static int height = 2464;
    private static int x = 0;
    private static int y = 0;

    float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;

    HashMap<String,LatLng> latlngSiteMap = new HashMap<>();
    private Bitmap mBasemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try {
            readItems(R.raw.site_latlng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setView();
        readFromFile(R.raw.hr);
    }

    private void setView(){
        iv = (ImageView)findViewById(R.id.imageView);
        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        btnReturn = (ImageButton) findViewById(R.id.btn_return);
        btnNowValue = (ImageButton) findViewById(R.id.btn_nowValue);
        btnOneHrValue = (ImageButton) findViewById(R.id.btn_oneHrValue);
        btnSixHrValue = (ImageButton) findViewById(R.id.btn_sixHrValue);
        btnTwelveHrValue = (ImageButton) findViewById(R.id.btn_twelveHrValue);
//        btnRecover = (ImageButton) findViewById(R.id.btn_recover);
//        btnList = (ImageButton) findViewById(R.id.btn_list);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnNowValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile(R.raw.hr);
            }
        });
        btnOneHrValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile(R.raw.hr1);
            }
        });
        btnSixHrValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile(R.raw.hr6);
            }
        });
        btnTwelveHrValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile(R.raw.hr12);
            }
        });
        mBasemap = BitmapFactory.decodeResource(getResources(), R.drawable.basemap)
                .copy(Bitmap.Config.ARGB_8888, true);
//        btnRecover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        btnList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private HashMap<String,LatLng> readItems(int resource) throws JSONException {
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            int x = object.getInt("x");
            int y = object.getInt("y");
            LatLng latLng = new LatLng(lat,lng,x,y);
            String site = object.getString("site");
            latlngSiteMap.put(site,latLng);
        }
        return latlngSiteMap;
    }

    private void drawMap(){

        Bitmap basemap = Bitmap.createScaledBitmap(mBasemap, width, height, true);

        Log.d(TAG, basemap.getWidth()+","+ basemap.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(mColors, 0, width, x, y, width, height);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(basemap, new Matrix(), null);
        Matrix mt = new Matrix();
        mt.postTranslate(516,886);
        canvas.drawBitmap(getBitmapFromVectorDrawable(this,R.drawable.ic_place_black_24dp),mt,new Paint());
//        Log.d(TAG,bitmap.getWidth()+","+bitmap.getHeight());
//        loadImage(bitmap);
//        sv.draw(canvas);
        iv.setImageBitmap(bitmap);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(basemap.getWidth(), basemap.getHeight());
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = 0;
        layoutParams.rightMargin = 0;
        iv.setLayoutParams(layoutParams);
        rl.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout.LayoutParams parms;
            int startwidth;
            int startheight;
            float dx = 0, dy = 0, x = 0, y = 0;
            float angle = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ImageView view = (ImageView) findViewById(R.id.imageView);
                final RelativeLayout rl_view = (RelativeLayout)findViewById(R.id.relativeLayout);
                Log.d(TAG,"pic coordinate:"+event.getX()+","+event.getY()+","+event.getRawX()+","+event.getRawY()+","+scalediff);
                ((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        parms = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        startwidth = parms.width;
                        startheight = parms.height;

                        dx = event.getRawX() - parms.leftMargin;
                        dy = event.getRawY() - parms.topMargin;
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            mode = ZOOM;
                        }

//                        d = rotation(event);

                        break;
                    case MotionEvent.ACTION_UP:

                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {

                            x = event.getRawX();
                            y = event.getRawY();

                            parms.leftMargin = (int) (x - dx);
                            parms.topMargin = (int) (y - dy);

                            parms.rightMargin = 0;
                            parms.bottomMargin = 0;
                            parms.rightMargin = parms.leftMargin + (5 * parms.width);
                            parms.bottomMargin = parms.topMargin + (10 * parms.height);

                            Log.d(TAG,"Margin:"+parms.rightMargin+","+parms.leftMargin+","+parms.topMargin+","+parms.bottomMargin);
                            view.setLayoutParams(parms);

                        } else if (mode == ZOOM) {

                            if (event.getPointerCount() == 2) {

//                                newRot = rotation(event);
//                                float r = newRot - d;
//                                angle = r;

                                x = event.getRawX();
                                y = event.getRawY();

                                float newDist = spacing(event);
                                if (newDist > 10f) {
                                    float scale = newDist / oldDist * rl_view.getScaleX();
                                    if (scale > 1.0 && scale < 10) {
                                        scalediff = scale;
//                                        view.setScaleX(scale);
//                                        view.setScaleY(scale);
                                        rl_view.setScaleX(scale);
                                        rl_view.setScaleY(scale);
                                    }
                                }

//                                view.animate().rotationBy(angle).setDuration(0).setInterpolator(new LinearInterpolator()).start();

                                x = event.getRawX();
                                y = event.getRawY();

                                parms.leftMargin = (int) ((x - dx) + scalediff);
                                parms.topMargin = (int) ((y - dy) + scalediff);

                                parms.rightMargin = 0;
                                parms.bottomMargin = 0;
                                parms.rightMargin = parms.leftMargin + (5 * parms.width);
                                parms.bottomMargin = parms.topMargin + (10 * parms.height);
                                view.setLayoutParams(parms);

                            }
                        }
                        break;
                }

                return true;

            }
        });
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

//    private static int resize_x = 0;
//    private static int resize_y = 0;
//    private static int resize_width = 0;
//    private static int resize_height = 0;
//
//    private void loadImage(Bitmap bitmap){
//
//        int scale = 2;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scale, scale);
//
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, resize_x, resize_y,
//                bitmap.getWidth()-resize_x, bitmap.getHeight()-resize_y, matrix, true);
//        iv.setImageBitmap(resizedBitmap);
//
//    }

    int mColors[] = new int[1440 * 2464];
    private void readFromFile(int data) {
        try {
            InputStream inputStream = getResources().openRawResource(data);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                int index=0;
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    mColors[index] = Integer.parseInt(receiveString);
                    mColors[index] = ColorGradient.get(mColors[index]);
                    index++;
//                    stringBuilder.append(receiveString+",");
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }
        drawMap();
    }
//    private float rotation(MotionEvent event) {
//        double delta_x = (event.getX(0) - event.getX(1));
//        double delta_y = (event.getY(0) - event.getY(1));
//        double radians = Math.atan2(delta_y, delta_x);
//        return (float) Math.toDegrees(radians);
//    }
}
