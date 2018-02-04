package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.matyas.krista.pylt.Database.AppDatabase;
import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.R;

import java.util.Arrays;

/**
 * Created by Matyas on 2018.02.03..
 */

public class NewItemAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_item);

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveNewItem();
                    Intent myIntent = new Intent(NewItemAcivity.this, MainActivity.class);
                    NewItemAcivity.this.startActivity(myIntent);
                } catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                    Snackbar.make(view, "You need to fill all fields to save", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void saveNewItem() {
        final EIType eiType;
        final String name;
        final String tag;
        final long amount;

        EditText inputName = (EditText) findViewById(R.id.item_name);
        name = inputName.getText().toString();
        if (name.length() < 1) { throw new RuntimeException("No text was added");}
        EditText inputTag = (EditText) findViewById(R.id.item_tag);
        tag = inputTag.getText().toString();

        if (tag.length() < 1) { throw new RuntimeException("No text was added");}
        EditText inputAmount = (EditText) findViewById(R.id.amount);
        amount = Long.valueOf(inputAmount.getText().toString());
        if (inputAmount.getText().toString().length() < 1) { throw new RuntimeException("No text was added");}
        Switch inputSwitch = (Switch) findViewById(R.id.income_switch);
        if (inputSwitch.isChecked()) {
            eiType = EIType.INCOME;
        } else {
            eiType = EIType.EXPENSE;
        }
        EITag.addTag(tag, amount, eiType);
        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                adb.eiObject().insertEIObject(new EIObject(name,eiType,amount,EITag.getTag(tag)));
                return 1;
            }
        }.execute();


    }

}
