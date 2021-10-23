package fr.univavignon.projfinal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;


public class ObjAdapter extends SimpleCursorAdapter {
    private Cursor m_cursor;
    private Context m_context;
    private LayoutInflater miInflater;

    View convertView = null;

    TextView name,category,brand;

    ImageView imagePNG;

    public ObjAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        m_context = context;
        m_cursor = c;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        if (view == null) {
            convertView = miInflater.inflate(R.layout.content_obj_listview, null);
        } else {
            convertView = view;
        }

        name = (TextView) convertView.findViewById(R.id.textViewName);
        category = (TextView) convertView.findViewById(R.id.textViewCategory);
        brand = (TextView) convertView.findViewById(R.id.textViewBrand);
        imagePNG = (ImageView) convertView.findViewById(R.id.imagePNG);

        name.setText(cursor.getString(cursor.getColumnIndex(ObjetDbHelper.COLUMN_OBJ_NAME)));
        category.setText(cursor.getString(cursor.getColumnIndex(ObjetDbHelper.COLUMN_OBJ_CATEGORY)));
        brand.setText(cursor.getString(cursor.getColumnIndex(ObjetDbHelper.COLUMN_OBJ_BRAND)));

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/" + cursor.getString(cursor.getColumnIndex(ObjetDbHelper.COLUMN_OBJ_ID)) + ".png");
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imagePNG.setImageBitmap(bitmap);
    }
}
