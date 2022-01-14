package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

ArrayList<String> items;
ArrayAdapter<String> itemsAdapter;
ListView lvItems;

private void readItems(){
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try{
        items = new ArrayList<String>(FileUtils.readLines(todoFile));
    }catch (IOException e){
        items = new ArrayList<String>();
    }
}
private void writeItems(){
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try{
        FileUtils.writeLines(todoFile, items);
    }catch(IOException e){
        e.printStackTrace();
    }
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        // items.add("First Item");
        // items.add("Second Item");
        setupListViewListener();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void onAddItem(View v)   {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        }

}