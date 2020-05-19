package com.example.gnl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Myadapter extends ArrayAdapter<newslist> {

    public Myadapter(@NonNull Context context, List<newslist> newsl) {
        super(context, 0, newsl);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.selected_news, parent, false);

        }
        newslist current = getItem(position);

        TextView newsTitle = (TextView) listItem.findViewById(R.id.Newstitle);
        String title = current.gettitlefornews();
        newsTitle.setText(title);

        TextView newsCategoryTextView = listItem.findViewById(R.id.Newscategory);
        String category = current.getcategoryfornews();
        newsCategoryTextView.setText(category);

        TextView newsDateTextView = listItem.findViewById(R.id.date);
        String date = current.getdatefornews();
        newsDateTextView.setText(date);

        TextView newsTypeTextView = listItem.findViewById(R.id.NewsType);
        String type = current.gettypefornews();
        newsTypeTextView.setText(type);

        return listItem;
    }
}
