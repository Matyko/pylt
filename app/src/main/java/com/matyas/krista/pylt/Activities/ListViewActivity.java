package com.matyas.krista.pylt.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.R;

/**
 * Created by Matyas on 2018.02.04..
 */

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        EIType eiType = EIType.OVERALL;
        Bundle b = getIntent().getExtras();
        int i = b.getInt("key");
        switch (i) {
            case 0 : eiType = EIType.OVERALL;
            break;
            case 1 : eiType = EIType.INCOME;
            break;
            case 2 : eiType = EIType.EXPENSE;
            break;
        }
        fillList(eiType);
    }

    private void fillList(EIType eiType) {
        ListView listView = (ListView) findViewById(R.id.list_view);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                EIObject.getAllObjectsAsStringsByType(eiType));

        listView.setAdapter(arrayAdapter);
    }
}
