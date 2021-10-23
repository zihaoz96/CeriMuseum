package fr.univavignon.projfinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Fragment_obj extends Fragment {

    ListView listView;

    SimpleCursorAdapter adapter;

    ObjetDbHelper objetDbHelper;

    View view;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_obj, null);
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
    public void onStart() {
        super.onStart();
        Log.i("onStart","start");
        listView = (ListView) view.findViewById(R.id.listView);


        Cursor cursor = objetDbHelper.fetchAllObj();

        adapter = new ObjAdapter(
                getContext(),
                R.layout.content_obj_listview,
                cursor,
                new String[] {ObjetDbHelper.COLUMN_OBJ_NAME,ObjetDbHelper.COLUMN_OBJ_BRAND,ObjetDbHelper.COLUMN_OBJ_CATEGORY},
                new int[] {R.id.textViewName,R.id.textViewBrand,R.id.textViewCategory},
                0
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
