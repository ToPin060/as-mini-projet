package asminiproject.miniproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConfirmRating extends AppCompatActivity {

    private Button sendButton;
    private Button returnButton;
    private RatingBar confirmRatingBar;
    private TextView ratingTextValue;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        CharSequence ratingComment = getIntent().getStringExtra("ratingText");
        ArrayList<Bitmap> capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_rating);

        confirmRatingBar = findViewById(R.id.confirmRatingBar);
        ratingTextValue = findViewById(R.id.ratingTextValue);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.returnButton);
        container = findViewById(R.id.container);

        ratingTextValue.setText(ratingComment);
        confirmRatingBar.setRating(ratingBarValue);
        confirmRatingBar.setIsIndicator(true);


        if(capturedImages != null) {
            for (Bitmap image : capturedImages){
                addImagetoLayout(image);
            }
        }

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

    private void addImagetoLayout(Bitmap image) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        imageView.setImageBitmap(image);

        container.addView(imageView);
    }

    protected void onSend(){
        // TODO : Mettre ici l'envoie des informations de la review sur la BD

        Log.d("OnConfirm", "Bouton Envoyer");

        Intent intent = new Intent(ConfirmRating.this, MainActivity.class);

        startActivity(intent);
    }

    protected void onReturn(){

        float ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        CharSequence ratingComment = getIntent().getStringExtra("ratingText");
        ArrayList<Bitmap> capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");


        Intent intent = new Intent(ConfirmRating.this, RatingRestaurantActivity.class);
        intent.putExtra("ratingBarValue", ratingBarValue);
        intent.putExtra("ratingComment", ratingComment);
        intent.putParcelableArrayListExtra("capturedImages", capturedImages);
        startActivity(intent);

        finish();
    }
}
