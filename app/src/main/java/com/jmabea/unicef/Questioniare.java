package com.jmabea.unicef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Questioniare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questioniare);

        CardView availCard = findViewById(R.id.availCard);
        availCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Questioniare.this, Availability.class));
            }
        });

        CardView usageCard = findViewById(R.id.usageCard);
        usageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Questioniare.this, Usage.class ));
            }
        });
    }
}
