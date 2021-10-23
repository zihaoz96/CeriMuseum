package fr.univavignon.projfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter {

    Context context;

    public int position = 0;

    List<String> objects;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.content_arraylist, null);
        }

        if (this.position == position){
            convertView.setBackgroundResource(R.color.white);
        } else{
            convertView.setBackgroundResource(R.color.light_grey);
        }

        TextView textView = convertView.findViewById(R.id.textCategory);
        textView.setText(objects.get(position).trim().toUpperCase());
        return convertView;
    }

    public void setPosition(int position){
        this.position = position;
        notifyDataSetChanged();
    }
}
