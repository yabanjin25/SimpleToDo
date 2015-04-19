package com.example.ayamanaka.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ayamanaka on 4/18/15.
 */
public class TodoItemArrayAdapter extends ArrayAdapter<TodoItem> {
    private final Context context;
    private final List<TodoItem> todoItems;

    public TodoItemArrayAdapter(Context context, List<TodoItem> todoItems) {
        super(context, android.R.layout.simple_list_item_1, todoItems);
        this.context = context;
        this.todoItems = todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        textView.setText(todoItems.get(position).getBody());

        return rowView;
    }
}