package fr.univavignon.projfinal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    List<Picture> data;
    Context context;

    boolean zoom = false;

    public GridViewAdapter(Context context,List<Picture> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.grid_picture, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.pictureJPEG);
        TextView textView = (TextView) view.findViewById(R.id.descriptionPhoto);

        imageView.setImageBitmap(data.get(position).getBitmap());
        textView.setText(data.get(position).getDescription().toString());

        return view;
    }
}
