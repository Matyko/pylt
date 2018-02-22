package com.matyas.krista.pylt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.matyas.krista.pylt.R;
import com.matyas.krista.pylt.ToDo.Card;

public class NewCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        final String mode = (String) getIntent().getSerializableExtra("Mode");
        Button deleteButton = (Button) findViewById(R.id.delete_button_card);

        if (mode.equals("edit")) {
            TextView textView = (TextView) findViewById(R.id.new_edit_card);
            textView.setText(R.string.edit_card);
            EditText editText = (EditText) findViewById(R.id.card_title);
            Card card = (Card) getIntent().getSerializableExtra("Card");
            editText.setText(card.getTitle());
            deleteButton.setVisibility(View.VISIBLE);
        }

        Button saveButton = (Button) findViewById(R.id.save_button_card);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.card_title);
                if (mode.equals("edit")) {
                    Card card = (Card) getIntent().getSerializableExtra("Card");
                    for (Card oriCard : Card.getCards()) {
                        if (oriCard.getTitle().equals(card.getTitle())) {
                            oriCard.setTitle(editText.getText().toString());
                        }
                    }
                } else {
                    new Card(editText.getText().toString());
                }
                Intent intent = new Intent(NewCardActivity.this, ToDoActivity.class);
                startActivity(intent);
                finish();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card card = (Card) getIntent().getSerializableExtra("Card");
                for (Card oriCard : Card.getCards()) {
                    if (oriCard.getTitle().equals(card.getTitle())) {
                        card = oriCard;
                    }
                }
                Card.getCards().remove(card);
                Intent intent = new Intent(NewCardActivity.this, ToDoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(NewCardActivity.this, ToDoActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
