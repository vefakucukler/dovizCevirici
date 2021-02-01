package com.vkyazilim.dovizceviri;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tryTxt;
    TextView cadTxt;
    TextView chfTxt;
    TextView usdTxt;
    TextView jpyTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tryTxt = (TextView)findViewById(R.id.tryTxt);
         cadTxt = (TextView)findViewById(R.id.cadTxt);
         chfTxt = (TextView)findViewById(R.id.chftxt);
         usdTxt = (TextView)findViewById(R.id.usdTxt);
         jpyTxt = (TextView)findViewById(R.id.jpyTxt);
    }

    public void getir(View view){
        DownloadData downloadData=new DownloadData();
        try{
            String url = "http://data.fixer.io/api/latest?access_key=47d15c8cc92ad5a8284a5eee6eb00e02&format=1";
            downloadData.execute(url);
        }catch (Exception e){
        }
    }
    private class DownloadData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("Alınan data: "+s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("alınan base "+base);
                String rates = jsonObject.getString("rates");
                System.out.println("alınan rates"+rates);
                JSONObject jsonObject1 = new JSONObject(rates);
                String turkLira= jsonObject1.getString("TRY");
                tryTxt.setText("TRY: "+turkLira);
                String usd= jsonObject1.getString("USD");
                usdTxt.setText("USD: "+usd);
                String jpy= jsonObject1.getString("JPY");
                jpyTxt.setText("JPY: "+jpy);
                String chf= jsonObject1.getString("CHF");
                chfTxt.setText("CHF: "+chf);
                String cad= jsonObject1.getString("CAD");
                cadTxt.setText("CAD: "+cad);
            }catch (Exception e){

            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String sonuc="";
            URL url;
            HttpURLConnection httpURLConnection;
            try{
                url = new URL(strings[0]);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                InputStream ınputStream=httpURLConnection.getInputStream();
                InputStreamReader ınputStreamReader=new InputStreamReader(ınputStream);

                int data = ınputStream.read();
                while (data>0){
                    char karakter =(char)data;
                    sonuc+=karakter;
                    data = ınputStream.read();
                }
                return sonuc;

            }catch (Exception e){
                return null;
            }

        }
    }
}
