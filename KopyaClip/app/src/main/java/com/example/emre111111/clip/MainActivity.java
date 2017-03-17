package com.example.emre111111.clip;

//import android.support.v4.widget.SearchViewCompatIcs;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
        import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
        import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
        import android.widget.TextView;


        import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private final String TAG=this.getClass().getSimpleName();

    /*** hackathon değişkenle
     */
    String  Rescode=null;
    String  Resmsg=null;
    String ReselementDizi[][];

    TextView Restextlist;
    LinearLayout ll;
    int sayac=0;


    Bitmap bg;
    Canvas canvas;
    /** hack*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.emre111111.Bitaksi_Hackathon.R.layout.activity_main);
        Button Getir=(Button)findViewById(com.example.emre111111.Bitaksi_Hackathon.R.id.getir);

        /**hack
         */
        ll=(LinearLayout) findViewById(com.example.emre111111.Bitaksi_Hackathon.R.id.linear);
        /***/
        Getir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CEVIR();
            }
        });
    }//on create sonu

    public void RectCiz(String X,String Y,String width,String height,String color) {

        //Log.i(TAG,"rectciz fonksiyonu");
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#"+color));
        canvas.drawRect(Integer.parseInt(X) , Integer.parseInt(Y) ,
                Integer.parseInt(X)+Integer.parseInt(width),
                Integer.parseInt(Y)+Integer.parseInt(height),paint);
        ll.setBackground(new BitmapDrawable(bg));

    }


    public void CircCiz(String X,String Y,String R,String color)
    {
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#"+color));
        canvas.drawCircle(Integer.parseInt(X) , Integer.parseInt(Y),Float.parseFloat(R),paint);

    }



    public int CEVIR(){

        bg=Bitmap.createBitmap(800,800,Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bg);

        final String deneme=null;
        Log.i(TAG,"Ceviri Basladi");
       // String jsonurl="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170118T003338Z.f214da86872ae232.1dee3d16f8c722034eb5b5b54dfe7b5a9ca061ab&text="+SCvrAnaCumle+"&lang=en-tr&[format=html]&[options=1]&[callback=emreemre]";
        final String jsonurl="https://getir-bitaksi-hackathon.herokuapp.com/getElements?email=eer@gmail.com&name=asdf&gsm=531";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, jsonurl,(String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"ONRESPONSE");

                        try {
                            Rescode=response.getString("code");
                            Resmsg=response.getString("msg");

                            JSONArray jsonarray =  response.getJSONArray("elements");
                            ReselementDizi=new String[jsonarray.length()][6];
                            sayac=0;
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                ReselementDizi[i][0]= jsonobject.getString("type");
                                ReselementDizi[i][1]=jsonobject.getString("xPosition");
                                ReselementDizi[i][2]=jsonobject.getString("yPosition");

                                if(ReselementDizi[i][0].equals("circle")){
                                    ReselementDizi[i][3]= jsonobject.getString("r");
                                    Log.i(TAG,"CIRCLE");
                                }
                                else
                                {
                                    ReselementDizi[i][3]=jsonobject.getString("width");
                                    ReselementDizi[i][4]=jsonobject.getString("height");
                                    Log.i(TAG,"RECT");

                                }

                                ReselementDizi[i][5]=jsonobject.getString("color");
                                sayac++;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.i(TAG,"rect oncesi");

                           int i=0;
                        while(i<sayac)
                        {
                           // Log.i(TAG,"WHILE ICI");
                            if(ReselementDizi[i][0].equals("rectangle")){
                                    RectCiz(ReselementDizi[i][1],ReselementDizi[i][2],ReselementDizi[i][3],ReselementDizi[i][4],ReselementDizi[i][5]);
                                    Log.i(TAG,"if ici reck");

                                }else
                                    {
                                       CircCiz(ReselementDizi[i][1],ReselementDizi[i][2],ReselementDizi[i][3],ReselementDizi[i][5]);

                                    }


                            i++;
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"ceviri on error");
                error.printStackTrace();
            }
        });

        MySingleton.getInstence(MainActivity.this).addToRequestque(jsonObjectRequest);

        Log.i(TAG,"Ceviri bitti");



return 1;
    }


}
