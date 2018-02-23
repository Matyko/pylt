package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.matyas.krista.pylt.Database.AppDatabase;
import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.R;
import com.matyas.krista.pylt.ToDo.Card;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Card card = (Card) getIntent().getSerializableExtra("Card");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String fabMode = "add";
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.new_todo_item);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                if (fabMode.equals("add")) {
                    editText.setVisibility(View.VISIBLE);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                    fabMode = "save";
                } else if (fabMode.equals("save")) {
                    if (editText.getText().toString().length() > 1) {
                        for (Card oriCard : Card.getCards()) {
                            if (oriCard.getTitle().equals(card.getTitle()) && !card.getTitle().equals("Shopping List")) {
                                oriCard.getItems().add(editText.getText().toString());
                                card.addItem(editText.getText().toString());
                            } else if (card.getTitle().equals("Shopping List")) {
                                buildDialog();
                            }
                        }
                    }
                    editText.setVisibility(View.GONE);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    fabMode="add";
                    editText.setText("");
                }

            }
        });

        TextView textView = (TextView) findViewById(R.id.todo_title);
        textView.setText(card.getTitle());
        fillList(card);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(ToDoListActivity.this, ToDoActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fillList(Card card) {
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.list_view_todo);

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
                card.getItems());

        listView.setAdapter(arrayAdapter);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onMenuItemClick(final int position, final SwipeMenu menu, final int index) {
                switch (index) {
                    case 0:
                        //edit item
                        break;
                    case 1:
//                        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
//                        new AsyncTask<Void, Void, Integer>() {
//                            @Override
//                            protected Integer doInBackground(Void... params) {
//                                adb.eiObject().deleteObject(EIObject.getAllObjects().remove(position));
//                                menu.removeMenuItem(menu.getMenuItem(index));
//                                return 1;
//                            }
//                        }.execute();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    private void buildDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ToDoListActivity.this);
        alertDialog.setTitle("AMOUNT");

        final EditText input = new EditText(ToDoListActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Enter the price of the item");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_add_black_24dp);

        alertDialog.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String amount = input.getText().toString();
                        startActivity(getIntent());
                        finish();
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}

