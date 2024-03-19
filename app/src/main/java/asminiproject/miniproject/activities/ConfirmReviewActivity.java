package asminiproject.miniproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import asminiproject.miniproject.R;
import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.services.RestaurantsService;

public class ConfirmReviewActivity extends AppCompatActivity {
    private Restaurant _restaurant;
    private float _rating;
    private String _comment;
    private List<Bitmap> _pictures;

    private ViewGroup _reviewCardGroup;
    private ImageView _pictureView;
    private RatingBar _ratingBarView;
    private TextView _restaurantNameView, _commentView;
    private Button _returnButton, _sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rating);

        initUIElements();
        initIntentElements();
        setupButtonsEvent();
    }

    public void initUIElements(){
        _reviewCardGroup = findViewById(R.id.review_card);
        _ratingBarView = _reviewCardGroup.findViewById(R.id.rating_bar);
        _restaurantNameView = _reviewCardGroup.findViewById(R.id.restaurant_name);
        _commentView = _reviewCardGroup.findViewById(R.id.review_comment);
        _pictureView = _reviewCardGroup.findViewById(R.id.picture);

        _sendButton = findViewById(R.id.send_button);
        _returnButton = findViewById(R.id.back_button);
    }

    public void initIntentElements(){
        final int restaurantId = getIntent().getIntExtra("restaurantId", 11);
        _restaurant = RestaurantsService.getInstance().getRestaurantById(restaurantId);
        _rating = getIntent().getFloatExtra("ratingBar", 0.0f);
        _comment = getIntent().getStringExtra("ratingText");
        _pictures = getIntent().getParcelableArrayListExtra("capturedImages");

        _ratingBarView.setRating(_rating);
        _ratingBarView.setIsIndicator(true);
        _restaurantNameView.setText(_restaurant.name);
        _commentView.setText(_comment);


        if(_pictures != null){
            _pictureView.setImageBitmap(_pictures.get(0));
        }
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
