package fr.univavignon.projfinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Fragment_categorie extends Fragment {

    View view;

    Context context;

    ObjetDbHelper objetDbHelper;

    ListView listViewCategory, list_item;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categorie, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        objetDbHelper = new ObjetDbHelper(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        listViewCategory = (ListView) view.findViewById(R.id.listViewCategory);
        list_item = (ListView) view.findViewById(R.id.list_item2);

        final CategoryAdapter categoryAdapter = new CategoryAdapter(context, android.R.layout.simple_expandable_list_item_1, MainActivity.categories);
        listViewCategory.setAdapter(categoryAdapter);
        listViewCategory.post(new Runnable() {
            @Override
            public void run() {
                changeList(0);
            }
        });


        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setPosition(position);
                changeList(position);
            }
        });
    }

    public void changeList(int position){
        Cursor cursor = objetDbHelper.fetchObjInCategory(MainActivity.categories.get(position));
        SimpleCursorAdapter adapter = new ObjAdapter(
                getContext(),
                R.layout.content_category_listview,
                cursor,
                new String[] {ObjetDbHelper.COLUMN_OBJ_NAME,ObjetDbHelper.COLUMN_OBJ_BRAND,ObjetDbHelper.COLUMN_OBJ_CATEGORY},
                new int[] {R.id.textViewName,R.id.textViewBrand,R.id.textViewCategory},
                0
        );
        list_item.setAdapter(adapter);

        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ObjActivity.class);
                final Cursor c = (Cursor) parent.getItemAtPosition(position);

                ObjetMusee objSend = objetDbHelper.cursorToObj(c);
                intent.putExtra("ObjSend", objSend.getIdObjet());
                startActivity(intent);
            }
        });
    }


}
