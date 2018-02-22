package com.matyas.krista.pylt.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.matyas.krista.pylt.Commons.AppColors;
import com.matyas.krista.pylt.R;
import com.matyas.krista.pylt.ToDo.Card;
import com.matyas.krista.pylt.ToDo.ShoppingList;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayout shoppingList = (LinearLayout) findViewById(R.id.shopping);
        shoppingList.addView(setItems(ShoppingList.getShopItems()));
        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, ToDoListActivity.class);
                intent.putExtra("Card", ShoppingList.getShopCard());
                startActivity(intent);
                finish();
            }
        });
        fillCards();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(ToDoActivity .this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fillCards() {
        LinearLayout toDoHolder = (LinearLayout) findViewById(R.id.todo_layout);
        ArrayList<Card> cards = Card.getCards();
        int color = 1;
        for (int i = 0; i < cards.size(); i++) {
            toDoHolder.addView(createCardView(cards.get(i), color));
            if (color >= 9) {
                color = 0;
            } else {
                color++;
            }
        }
    }

    private View createCardView(final Card card, int color) {
        LinearLayout cardBase = new LinearLayout(getApplicationContext());
        cardBase.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cardBase.setBackgroundColor(AppColors.getColors()[color]);
        cardBase.setOrientation(LinearLayout.VERTICAL);
        cardBase.addView(setTitle(card.getTitle()));
        cardBase.addView(createSeparator());
        cardBase.addView(setItems(card.getItems()));
        cardBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, ToDoListActivity.class);
                intent.putExtra("Card", card);
                startActivity(intent);
                finish();
            }
        });
        return cardBase;
    }

    private View setTitle(String title) {
        TextView textView = new TextView(getApplicationContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(title);
        textView.setPadding(20,20,20,20);
        textView.setAlpha(0.5f);
        textView.setTextSize(30);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    private View createSeparator() {
        View separator = new View(getApplicationContext());
        separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(R.attr.colorPrimary);
        return separator;
    }

    private View setItems(ArrayList<String> items) {
        ListView listView = new ListView(getApplicationContext());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                items );
        listView.setAdapter(arrayAdapter);
        return listView;
    }
}
