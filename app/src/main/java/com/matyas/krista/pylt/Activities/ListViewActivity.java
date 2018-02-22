package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.matyas.krista.pylt.Database.AppDatabase;
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
            case 0:
                eiType = EIType.OVERALL;
                break;
            case 1:
                eiType = EIType.INCOME;
                break;
            case 2:
                eiType = EIType.EXPENSE;
                break;
        }
        fillList(eiType);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(ListViewActivity.this, BalanceActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fillList(EIType eiType) {
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.list_view);

        final SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                EIObject.getAllObjectsAsStringsByType(eiType));

        listView.setAdapter(arrayAdapter);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onMenuItemClick(final int position, final SwipeMenu menu, final int index) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(ListViewActivity.this, NewItemAcivity.class);
                        Bundle b = new Bundle();
                        b.putInt("key", position);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
                        new AsyncTask<Void, Void, Integer>() {
                            @Override
                            protected Integer doInBackground(Void... params) {
                                adb.eiObject().deleteObject(EIObject.getAllObjects().remove(position));
                                menu.removeMenuItem(menu.getMenuItem(index));
                                return 1;
                            }
                        }.execute();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }


}
