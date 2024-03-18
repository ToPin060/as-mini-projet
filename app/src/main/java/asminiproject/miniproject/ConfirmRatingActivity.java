package asminiproject.miniproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
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

    public void initUIElements(){
        confirmRatingBar = findViewById(R.id.confirmRatingBar);
        ratingTextValue = findViewById(R.id.ratingTextValue);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.returnButton);
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

        sendButton.setOnClickListener(v -> onSend());
        returnButton.setOnClickListener(v -> onReturn());

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
}
