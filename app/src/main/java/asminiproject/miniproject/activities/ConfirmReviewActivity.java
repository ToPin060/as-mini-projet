package asminiproject.miniproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import asminiproject.miniproject.R;

public class ConfirmReviewActivity extends AppCompatActivity {

    private Button _sendButton;
    private Button _returnButton;
    private RatingBar confirmRatingBar;
    private TextView ratingTextValue;
    private ImageView card_image;

    public void initUIElements(){
        confirmRatingBar = findViewById(R.id.confirmRatingBar);
        ratingTextValue = findViewById(R.id.ratingTextValue);
        _sendButton = findViewById(R.id.sendButton);
        _returnButton = findViewById(R.id.returnButton);
        card_image = findViewById(R.id.card_image);
    }

    public void initIntentElements(){
        float ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        CharSequence ratingComment = getIntent().getStringExtra("ratingText");
        ArrayList<Bitmap> capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        ratingTextValue.setText(ratingComment);
        confirmRatingBar.setRating(ratingBarValue);
        confirmRatingBar.setIsIndicator(true);

        if(capturedImages != null){
            card_image.setImageBitmap(capturedImages.get(0));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rating);

        initUIElements();
        initIntentElements();
        setupButtonsEvent();

    }

    public void setupButtonsEvent() {
        _returnButton.setOnClickListener(v -> onBackClick());
        _sendButton.setOnClickListener(v -> onConfirmClick());
    }

    protected void onBackClick(){
        finish();
    }
    protected void onConfirmClick(){
        // TODO : Mettre ici l'envoie des informations de la review sur la BD

        Intent intent = new Intent(ConfirmReviewActivity.this, MapActivity.class);
        startActivity(intent);

        // Remove the previous history stack
        finishAffinity();
    }
}
