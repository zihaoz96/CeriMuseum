package fr.univavignon.projfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView objet, categorie;
    ImageView recherche;

    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;

    ObjetDbHelper objetDbHelper = new ObjetDbHelper(this);

    Bitmap bitmap = null;

    List<ObjetMusee> allObjs;

    public static List<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncAllRefresh().execute();

        setContentView(R.layout.activity_main);

        objet = (TextView) findViewById(R.id.objet);
        categorie = (TextView) findViewById(R.id.categorie);
        recherche = (ImageView) findViewById(R.id.recherche);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerViewAdapter);

        objet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                onChangeTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Request permission
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }
    public void onChangeTab(int position){
        if (position==0){
            objet.setBackgroundResource(R.drawable.tab_textview_isselect);
            recherche.setBackgroundResource(R.drawable.tab_textview_noselect);
            categorie.setBackgroundResource(R.drawable.tab_textview_noselect);
        } else if (position==1){
            objet.setBackgroundResource(R.drawable.tab_textview_noselect);
            recherche.setBackgroundResource(R.drawable.tab_textview_isselect);
            categorie.setBackgroundResource(R.drawable.tab_textview_noselect);
        } else if (position==2){
            objet.setBackgroundResource(R.drawable.tab_textview_noselect);
            recherche.setBackgroundResource(R.drawable.tab_textview_noselect);
            categorie.setBackgroundResource(R.drawable.tab_textview_isselect);
        }
    }



    private final class AsyncAllRefresh extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = WebServiceUrl.buildSearchId();
                Log.i("url", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(7 * 1000);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                JSONResponseHandlerObjet jsonResponseHandlerObjet = new JSONResponseHandlerObjet(objetDbHelper);
                jsonResponseHandlerObjet.readJsonStream(inputStream,"ids");
                conn.disconnect();

                url = WebServiceUrl.buildSearchCategory();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(7 * 1000);
                conn.connect();
                inputStream = conn.getInputStream();
                jsonResponseHandlerObjet = new JSONResponseHandlerObjet(objetDbHelper);
                jsonResponseHandlerObjet.readJsonStream(inputStream,"categorys");
                conn.disconnect();

                url = WebServiceUrl.buildSearchCatalog();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(7 * 1000);
                conn.connect();
                inputStream = conn.getInputStream();
                jsonResponseHandlerObjet = new JSONResponseHandlerObjet(objetDbHelper);
                jsonResponseHandlerObjet.readJsonStream(inputStream,"catalog");
                conn.disconnect();

                allObjs = objetDbHelper.getAllObjs();
                for (int i=0;i<allObjs.size();i++){
                    GetImageInputStream(allObjs.get(i));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private void GetImageInputStream(ObjetMusee obj){

        HttpURLConnection connection=null;
        try {
            URL url = WebServiceUrl.buildSearchItemImagePNG(obj.getIdObjet());
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/"+obj.getIdObjet()+".png");
        SavaImage(file);
    }

    public void SavaImage(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
