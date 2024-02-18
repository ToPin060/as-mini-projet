package asminiproject.miniproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ConfirmRating extends AppCompatActivity {

    private Button sendButton;
    private Button returnButton;
    private float ratingBarValue;
    private RatingBar confirmRatingBar;
    private TextView ratingTextValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_rating);

        ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        CharSequence ratingComment = getIntent().getStringExtra("ratingText");

        confirmRatingBar = findViewById(R.id.confirmRatingBar);
        ratingTextValue = findViewById(R.id.ratingTextValue);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.returnButton);

        ratingTextValue.setText(ratingComment);
        confirmRatingBar.setRating(ratingBarValue);
        confirmRatingBar.setIsIndicator(true);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturn();
            }
        });

    }

    protected void onSend(){
        // TODO : Mettre ici l'envoie des informations de la review sur la BD

        Log.d("OnConfirm", "Bouton Envoyer");

        Intent intent = new Intent(ConfirmRating.this, MainActivity.class);
    }

    protected void onReturn(){
        Intent intent = new Intent(ConfirmRating.this, RatingRestaurantActivity.class);
        startActivity(intent);
        finish();
    }
}
