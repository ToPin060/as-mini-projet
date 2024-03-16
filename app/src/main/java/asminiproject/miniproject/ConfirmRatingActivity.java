package asminiproject.miniproject;

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

public class ConfirmRatingActivity extends AppCompatActivity {

    private Button sendButton;
    private Button returnButton;
    private RatingBar confirmRatingBar;
    private TextView ratingTextValue;
    private ImageView card_image;

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
        card_image = findViewById(R.id.card_image);

        ratingTextValue.setText(ratingComment);
        confirmRatingBar.setRating(ratingBarValue);
        confirmRatingBar.setIsIndicator(true);

        if(capturedImages != null){
            card_image.setImageBitmap(capturedImages.get(0));
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

    protected void onSend(){
        // TODO : Mettre ici l'envoie des informations de la review sur la BD

        Log.d("OnConfirm", "Bouton Envoyer");

        Intent intent = new Intent(ConfirmRatingActivity.this, MainActivity.class);

        startActivity(intent);
    }

    protected void onReturn(){

        float ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        CharSequence ratingComment = getIntent().getStringExtra("ratingText");
        ArrayList<Bitmap> capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");


        Intent intent = new Intent(ConfirmRatingActivity.this, RatingRestaurantActivity.class);
        intent.putExtra("ratingBarValue", ratingBarValue);
        intent.putExtra("ratingComment", ratingComment);
        intent.putParcelableArrayListExtra("capturedImages", capturedImages);
        startActivity(intent);

        finish();
    }

    //TODO
    // Improve view of the All captured images
}
