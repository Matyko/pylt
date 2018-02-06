package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

        Bundle b = getIntent().getExtras();
        int mode = -1;
        try {
            int i = b.getInt("key");
            TextView textView = (TextView) findViewById(R.id.new_update_item);
            textView.setText(R.string.edit_item);
            EditText inputName = (EditText) findViewById(R.id.item_name);
            inputName.setText(EIObject.getAllObjects().get(i).getName());
            EditText inputTag = (EditText) findViewById(R.id.item_tag);
            inputTag.setText(EIObject.getAllObjects().get(i).getTag().getName());
            EditText inputAmount = (EditText) findViewById(R.id.amount);
            inputAmount.setText(Long.valueOf(EIObject.getAllObjects().get(i).getAmount()).toString());
            Switch inputSwitch = (Switch) findViewById(R.id.income_switch);
            if (EIObject.getAllObjects().get(i).getEitype().equals(EIType.INCOME)) {inputSwitch.setChecked(true);}
            mode = i;
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        final int finalMode = mode;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveItem(finalMode);
                    Intent myIntent = new Intent(NewItemAcivity.this, BalanceActivity.class);
                    NewItemAcivity.this.startActivity(myIntent);
                } catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                    Snackbar.make(view, "You need to fill all fields to save", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                }
            }
        });

        String[] data = EITag.getTagNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, data);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.item_tag);
        textView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(NewItemAcivity.this, BalanceActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("StaticFieldLeak")
    private void saveItem(int i) {
        final EIType eiType;
        final String name;
        final String tag;
        final long amount;

        EditText inputName = (EditText) findViewById(R.id.item_name);
        name = inputName.getText().toString();
        if (name.length() < 1) { throw new RuntimeException("No text was added");}
        AutoCompleteTextView inputTag = (AutoCompleteTextView) findViewById(R.id.item_tag);
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
        if (i < 0) {
            final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    adb.eiObject().insertEIObject(new EIObject(name, eiType, amount, EITag.getTag(tag)));
                    return 1;
                }
            }.execute();
        } else if (i >= 0) {
            final EIObject obj = EIObject.getAllObjects().get(i);
            obj.setAmount(amount);
            obj.setEitype(eiType);
            obj.setName(name);
//            obj.setDate(Calendar.getInstance().getTime());
            obj.setTag(EITag.getTag(tag));
            final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    adb.eiObject().updateEIObject(obj);
                    return 1;
                }
            }.execute();
        }

    }

}
