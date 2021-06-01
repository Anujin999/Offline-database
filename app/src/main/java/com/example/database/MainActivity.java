package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Databasehelper db;
    EditText etid, etname, etage;
    Button btnadd, btnedit, btndelete, btnview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Databasehelper(this);

        etid = findViewById(R.id.ev1);
        etname = findViewById(R.id.ev2);
        etage = findViewById(R.id.ev3);
        btnadd = findViewById(R.id.b1);
        btnedit = findViewById(R.id.b2);
        btndelete = findViewById(R.id.b3);
        btnview = findViewById(R.id.b4);

        addData();
        viewAll();
        updateData();
        deleteData();

    }

    private void deleteData(){
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = db.deleteData(etid.getText().toString());
                if (deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateData(){
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updateData(
                        etid.getText().toString(),
                        etname.getText().toString(),
                        etage.getText().toString()
                );
                if (isUpdate== true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addData() {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(etname.getText().toString(), etage.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void viewAll() {
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getData();
                if (res.getCount() == 0 ){
                    showMessage("Error", "Nothing found");
                    return;

                }

                StringBuffer sb = new StringBuffer();
                while (res.moveToNext()){
                    sb.append("ID: " + res.getString(0)+",");
                    sb.append("Name: " + res.getString(1)+",");
                    sb.append("Age: " + res.getString(2)+"\n");

                }
                showMessage("Data", sb.toString());
            }
        });
    }
    public void showMessage ( String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}