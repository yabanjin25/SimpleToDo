package com.example.ayamanaka.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ayamanaka on 4/18/15.
 */
public class TodoItemArrayAdapter extends ArrayAdapter<TodoItem> {
    private final Context context;
    private final List<TodoItem> todoItems;
    private MainActivity activity;
    private OnDataChangeListener onDataChangeListener;

    public TodoItemArrayAdapter(Context context, MainActivity activity, List<TodoItem> todoItems) {
        super(context, R.layout.todo_item_list_item, todoItems);
        this.context = context;
        this.activity = activity;
        this.todoItems = todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_item_list_item, parent, false);
        LinearLayout linear = (LinearLayout) rowView;
        final int itemPosition = position;
        final ImageButton doneCheck = (ImageButton) linear.findViewById(R.id.itemDone);
        doneCheck.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDoneButtonClick(itemPosition);
                        boolean newGetDone = todoItems.get(itemPosition).getDone();
                        if (newGetDone) {
                            doneCheck.setImageResource(R.drawable.marked_checkbox);
                        } else {
                            doneCheck.setImageResource(R.drawable.unmarked_checkbox);
                        }
                    }
                }
        );
        doneCheck.setClickable(true);

        int doneImageResource = R.drawable.unmarked_checkbox;
        if (todoItems.get(position).getDone()) {
            doneImageResource = R.drawable.marked_checkbox;
        }
        doneCheck.setImageResource(doneImageResource);

        TextView textView = (TextView) linear.findViewById(R.id.itemText);
        textView.setText(todoItems.get(position).getBody());

        return rowView;
    }

    private void itemDoneButtonClick(int itemPosition)
    {
        if (onDataChangeListener != null){
            onDataChangeListener.onDataChanged(itemPosition);
        }
    }

    public void setOnDataChangeListener(OnDataChangeListener listener)
    {
        onDataChangeListener = listener;
    }

    public interface OnDataChangeListener
    {
        public void onDataChanged(int position);
    }
}