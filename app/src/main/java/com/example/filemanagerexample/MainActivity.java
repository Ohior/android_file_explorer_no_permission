package com.example.filemanagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String path;
    ListView list_item_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_item_id = findViewById(R.id.list_item_id);

        //use the current dir as title
        path = "/";
        if (getIntent().hasExtra("path")){
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        //put data into the list_view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, getFliesInPath());
        list_item_id.setAdapter(adapter);

        checkForListClickListener();
    }

    private List<String> getFliesInPath(){
        // Read all file sorted into the value-array
        List<String> values = new ArrayList<>();
        File dir = new File(path);
        if (!dir.canRead()){
            setTitle(getTitle() + " not a dir");
        }
        String [] list = dir.list();
        if (list != null) {
            for(String file : list){
                Log.e("getFliesInPath: ", file);
//                if (!file.startsWith(".")){
//                if (!file.contains(".")){
//                    values.add(file);
//                }
                    values.add(file);
            }
        }
        //arrange the item in ascending other
        Collections.sort(values);
        return values;
    }

    private void checkForListClickListener() {
        list_item_id.setOnItemClickListener((parent, view, position, id) -> {
            String file_name = (String) list_item_id.getAdapter().getItem(position);
            if (path.endsWith(File.separator)){
                file_name = path + file_name;
            }else {
                file_name = path + File.separator + file_name;
            }
            if (new File(file_name).isDirectory()){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("path", file_name);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),
                        file_name + " is not a directory",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}