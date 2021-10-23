package fr.univavignon.projfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class ObjActivity extends AppCompatActivity {

    ObjetDbHelper objetDbHelper = new ObjetDbHelper(this);

    ObjetMusee obj;

    MyGridView gridView;

    List<Picture> data = new ArrayList<>();

    String[] pictures;

    GridViewAdapter gridViewAdapter;

    ImageView expanded_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj);

        Intent intent = getIntent();
        String idObj = intent.getStringExtra("ObjSend");
        obj = objetDbHelper.getObjet(idObj);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        Log.i("Size",linearLayout.getChildCount()+"");


        TextView name,categorys,description,timeFrame,year,brand,technical,working;
        ImageView imgPNG = (ImageView) findViewById(R.id.imageView);
        gridView = (MyGridView) findViewById(R.id.gridView);

        name = (TextView) findViewById(R.id.nameDetail);
        categorys = (TextView) findViewById(R.id.categoriesDetail);
        description = (TextView) findViewById(R.id.descriptionDetail);
        timeFrame = (TextView) findViewById(R.id.timeFrameDetail);
        year = (TextView) findViewById(R.id.yearDetail);
        brand = (TextView) findViewById(R.id.brandDetail);
        technical = (TextView) findViewById(R.id.technicalDetails);
        working = (TextView) findViewById(R.id.workingDetails);

        name.setText(obj.getName());
        categorys.setText(obj.getCategory());
        description.setText(obj.getDescription());
        timeFrame.setText(obj.getTimeFrame());
        year.setText(obj.getYear());
        brand.setText(obj.getBrand());
        technical.setText(obj.getTechnicalDetails());
        working.setText(obj.getWorking());

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/" + obj.getIdObjet() + ".png");
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imgPNG.setImageBitmap(bitmap);

        int alreadyRemove = 0;

        if (obj.getTechnicalDetails() == null){
            linearLayout.removeViewAt(5);
            alreadyRemove++;
        }

        if (obj.getYear() == null){
            linearLayout.removeViewAt(7-alreadyRemove);
            alreadyRemove++;
        }

        if (obj.getBrand() == null){
            linearLayout.removeViewAt(8-alreadyRemove);
            alreadyRemove++;
        }

        if (obj.getWorking() == null){
            linearLayout.removeViewAt(9-alreadyRemove);
            alreadyRemove++;
            Log.i("Working","null");
        }

        if (obj.getPictures() != null){
            Log.i("1","1");
            pictures = obj.getPictures().split("\\|");
            for (int i=0;i<pictures.length;i++){

                String[] picture = pictures[i].split(":");
                try {

                    File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/" + picture[0] + ".png");
                    if (f.exists()){

                        String fp = f.getPath();
                        Bitmap img = BitmapFactory.decodeFile(fp);
                        data.add(new Picture(img, picture[1]));

                    } else{
                        new AsyncAllRefresh(picture).execute();
                    }

                }catch (Exception e){}
            }
        }
        gridViewAdapter = new GridViewAdapter(ObjActivity.this, data);
        gridView.setAdapter(gridViewAdapter);
    }

    private final class AsyncAllRefresh extends AsyncTask<String, Void, String> {

        private String id;
        private String description;

        public AsyncAllRefresh(String[] picture) {
            this.id = picture[0];
            this.description = picture[1];
        }

        @Override
        protected String doInBackground(String... string) {
            try {
                if (this.id != null){
                    HttpURLConnection conn;
                    Bitmap bitmap;
                    InputStream inputStream;

                    URL url = WebServiceUrl.buildSearchItem(obj.getIdObjet(), this.id);
                    Log.i("url", url.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(6000);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    inputStream=conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/"+this.id+".png");
                    SavaImage(file, bitmap);

                    data.add(new Picture(bitmap, this.description));

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
            gridViewAdapter = new GridViewAdapter(ObjActivity.this, data);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public void SavaImage(File file, Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
