package fr.univavignon.projfinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_recherche extends Fragment {

    View view;

    Context context;

    ObjetDbHelper objetDbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recherche, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objetDbHelper = new ObjetDbHelper(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final ListView listView = (ListView) view.findViewById(R.id.listSearch);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);

        searchView.setFocusable(true);
        searchView.setFocusableInTouchMode(true);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor cursor = objetDbHelper.fetchObjInCatalog(query);
                SimpleCursorAdapter adapter = new ObjAdapter(
                        getContext(),
                        R.layout.content_category_listview,
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setAdapter(null);
                return false;
            }
        });
    }
}
