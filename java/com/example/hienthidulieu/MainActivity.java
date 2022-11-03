package com.example.hienthidulieu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_computer;
    private SQLiteDatabase db;
    private Button them,sua,lammoi;
    private EditText txt_name;
    private adapter adap;
    private List<User> list_computer = new ArrayList<>();
    private int idupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        them = findViewById(R.id.btn_them);
        sua = findViewById(R.id.btn_sua);
        lammoi = findViewById(R.id.btn_lammoi);
        txt_name = findViewById(R.id.txt_name);
        initData();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                loadData();
            }
        });

        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_name.setText("");
            }
        });

        lv_computer = findViewById(R.id.lv_computer);
        adap = new adapter(this,R.layout.data_item,list_computer);
        lv_computer.setAdapter(adap);
        lv_computer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int item = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có xoá không ?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleData(item);
                                loadData();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        lv_computer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = list_computer.get(i);
                txt_name.setText(user.getName());
                idupdate = user.getId();
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn sữa không ?")
                        .setCancelable(false)
                        .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateData();
                                loadData();
                                Toast.makeText(MainActivity.this, "Bạn đã sữa thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        loadData();

    }
    private void initData(){
        db = openOrCreateDatabase("computer.db",MODE_PRIVATE,null);
        String sql = "create table if not exists computer (id integer primary key autoincrement, name text, giaban text)";
        db.execSQL(sql);
    }
    private void insertData(){
        String name = txt_name.getText().toString();
        String sql = "insert into computer (name) " +
                "values ('" + name + "')";
        db.execSQL(sql);
    }
    private void updateData(){
        String name = txt_name.getText().toString();
        String sql = " update computer " +
                "set name = '" + name + "' " +
                "where id = " + idupdate;

        db.execSQL(sql);
    }
    private void deleData(int i){
        int id = list_computer.get(i).getId();
        String sql = "delete from computer where id = " + id;
        db.execSQL(sql);
    }
    private void loadData(){
        list_computer.clear();
        String sql = "select * from computer";
        Cursor cursor =  db.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            User user = new User();
            user.setId(id);
            user.setName(name);
            list_computer.add(user);

            cursor.moveToNext();
        }
        adap.notifyDataSetChanged();
    }
}