package com.seoul.ddroad.intro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.seoul.ddroad.R;

import java.io.IOException;
import java.io.InputStream;

public class DustFragment extends Fragment {
    String url = "https://api.openweathermap.org/data/2.5/weather?q=chuncheon&APPID=0af6101dde7ca2a44d4cabe3d84ecc63&mode=html";
    Button btn;
    TextView area;
    TextView text;
    // String Cloud;
    DatePicker datePicker;  //  날짜 선택
    TextView viewDatePick;  //  선택 날짜 보여줌
    View view;

    //========================== 날씨 백그라운드 쓰레드 ===============================
    private class thr1 extends AsyncTask<Void, Void, Void> {      //날씨 텍스트 가져오기
        String content = "";
        String temp = "";
        String Area = "춘천";
        double tempInt = 28.0;
        @Override
        protected void onPreExecute()         {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).get();
                temp = doc.html();
                content = doc.text();
                int[] pos = new int[5];
                pos[0] = content.indexOf("Clouds:");//7
                pos[1] = content.indexOf("Humidity:");//9
                pos[2] = content.indexOf("Wind:");//5
                pos[3] = content.indexOf("Pressure:");//9
                pos[4] = content.indexOf("hpa");

                int index1 = temp.indexOf("Temperature");
                int index2 = temp.indexOf("°C");

                content = "온도 :" + content.substring(content.indexOf(" "), pos[0]-1) + "\n"
                        + "구름 : " + content.substring(pos[0] + 7, pos[1]-1) + "\n"
                        + "습도 : " + content.substring(pos[1] + 9, pos[2]-1) + "\n"
                        + "바람 : " + content.substring(pos[2]+5, pos[3]-1) + "\n"
                        + "기압 : " + content.substring(pos[3] + 9, pos[4] + 3);

                tempInt = Double.parseDouble(temp.substring(index1 + 13, index2));
                    /*String[] arr = content.split("More");
                    content = arr[0];*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ImageView cloth = (ImageView)view.findViewById(R.id.cloth);
            area.setText(Area);
            text.setText(content);
            if(tempInt >= 28){
                cloth.setImageResource(R.drawable.p1);
            }else if(tempInt >= 23){
                cloth.setImageResource(R.drawable.p2);
            }else if(tempInt >= 20){
                cloth.setImageResource(R.drawable.p3);
            }else if(tempInt >= 17){
                cloth.setImageResource(R.drawable.p4);
            }else if(tempInt >= 12){
                cloth.setImageResource(R.drawable.p5);
            }else if(tempInt >= 9){
                cloth.setImageResource(R.drawable.p6);
            }else if(tempInt >= 5){
                cloth.setImageResource(R.drawable.p7);
            }else{
                cloth.setImageResource(R.drawable.p8);
            }
        }
    }

    private class thr2 extends AsyncTask<Void, Void, Void> {      // 날씨 이미지 가져오기
        Bitmap bitmap;
        String text = "";
        String src = "";
        String tmp = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).get();
                text = doc.html();
                int pos = text.indexOf("://openweathermap.org/img/w/");
                int posend = text.indexOf("png&quot");
                src = text.substring(pos, posend + 3);
                tmp = "https" + src;

                InputStream input = new java.net.URL(tmp).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ImageView imgview = (ImageView) view.findViewById(R.id.img);
            imgview.setImageBitmap(bitmap);
        }
    }

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_dust_cool,null);

        //================   날씨 부분   ======================

        /*btn = (Button) view.findViewById(R.id.btn);*/
        area = (TextView) view.findViewById(R.id.area);
        text = (TextView) view.findViewById(R.id.text1);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                thr1 w = new thr1();
                thr2 e = new thr2();
                w.execute();
                e.execute();
                /*btn.setVisibility(View.GONE);
                btn.setEnabled(false);
            }
        });*/
        //================================날씨 끝부분 =====================================

        return view;

    }
}