package com.example.ayamanaka.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TodoItemDatabase db;
    ArrayList<TodoItem> items;
    TodoItemArrayAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new TodoItemDatabase(this);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new TodoItemArrayAdapter(this, this, items);
        lvItems.setAdapter(itemsAdapter);
        itemsAdapter.setOnDataChangeListener(new TodoItemArrayAdapter.OnDataChangeListener() {
            public void onDataChanged(int position) {
                updateTodoItem(position);
            }
        });

        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoItem itemToAdd = new TodoItem(itemText, 1, false);
        long id = db.addTodoItem(itemToAdd);
        itemsAdapter.add(db.getTodoItem((int)id));
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        TodoItem itemToRemove = items.get(pos);
                        db.deleteTodoItem(itemToRemove);
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        TodoItem todoItem = items.get(pos);
                        i.putExtra("item", todoItem);
                        i.putExtra("itemPosition", pos);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    private void readItems() {
        List<TodoItem> todoItems = db.getAllTodoItems();
        items = new ArrayList<TodoItem>(todoItems);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        //if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
        // Extract name value from result extras
        TodoItem editedItem = (TodoItem) data.getExtras().getSerializable("editedItem");
        int itemPosition = data.getExtras().getInt("itemPosition");
        //int code = data.getExtras().getInt("code", 0);
        db.updateTodoItem(editedItem);
        items.set(itemPosition, editedItem);
        itemsAdapter.notifyDataSetChanged();
        //}
    }

    protected void updateTodoItem(int position)
    {
        TodoItem itemToChange = items.get(position);
        boolean newGetDone = !itemToChange.getDone();
        itemToChange.setDone(newGetDone);

        db.updateTodoItem(itemToChange);
        items.set(position, itemToChange);
        itemsAdapter.notifyDataSetChanged();
    }
}
