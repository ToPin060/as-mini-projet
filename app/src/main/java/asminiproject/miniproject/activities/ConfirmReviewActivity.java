package asminiproject.miniproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import asminiproject.miniproject.R;
import asminiproject.miniproject.controllers.DataBaseController;
import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.dc.Review;
import asminiproject.miniproject.services.RestaurantsService;
import asminiproject.miniproject.services.ReviewsService;

public class ConfirmReviewActivity extends AppCompatActivity {
    private Restaurant _restaurant;
    private float _rating;
    private String _review;
    private List<Bitmap> _pictures;

    private ReviewsService _reviewsService;

    private ViewGroup _reviewCardGroup;
    private ImageView _pictureView;
    private RatingBar _ratingBarView;
    private TextView _restaurantNameView, _commentView;
    private Button _returnButton, _sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
        setupViewsContent();
        setupButtonsEvent();
    }

    public void initializeActivity(){
        _reviewsService = ReviewsService.getInstance();

        final int restaurantId = getIntent().getIntExtra("restaurantId", 11);
        _restaurant = RestaurantsService.getInstance().getRestaurantById(restaurantId);
        _rating = getIntent().getFloatExtra("ratingBar", 0.0f);
        _review = getIntent().getStringExtra("ratingText");
        _pictures = getIntent().getParcelableArrayListExtra("capturedImages");

        setContentView(R.layout.activity_confirm_rating);

        _reviewCardGroup = findViewById(R.id.review_card);
        _ratingBarView = _reviewCardGroup.findViewById(R.id.rating_bar);
        _restaurantNameView = _reviewCardGroup.findViewById(R.id.restaurant_name);
        _commentView = _reviewCardGroup.findViewById(R.id.review_comment);
        _pictureView = _reviewCardGroup.findViewById(R.id.picture);
        _sendButton = findViewById(R.id.send_button);
        _returnButton = findViewById(R.id.back_button);
    }

    public void setupViewsContent(){
        _ratingBarView.setRating(_rating);
        _ratingBarView.setIsIndicator(true);
        _restaurantNameView.setText(_restaurant.name);
        _commentView.setText(_review);

        if (_pictures == null) _pictureView.setVisibility(View.INVISIBLE);
        else _pictureView.setImageBitmap(_pictures.get(0));
    }

    public void setupButtonsEvent() {
        _returnButton.setOnClickListener(v -> onBackClick());
        _sendButton.setOnClickListener(v -> onConfirmClick());
    }

    protected void onBackClick(){
        finish();
    }
    protected void onConfirmClick(){
        FirebaseStorage storage = FirebaseStorage.getInstance();



        _pictures.forEach(picture -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] pictureBytes = baos.toByteArray();
            UUID photoUUID = UUID.randomUUID();

            StorageReference reviewStorageReference = storage.getReference(
                    String.format("reviews/%s/%s.png", _restaurant.getName(), photoUUID));

            UploadTask picturesUploadTask = reviewStorageReference.putBytes(pictureBytes);

            Task<Uri> urlTask = picturesUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                        throw task.getException();

                    return reviewStorageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Log.d("EditPhotoActivity", "task.getResult().getPath()" + task.getResult().getPath());
                    } else {
                        Log.e("EditPhotoActivity", "To be pizda");
                    }
                }
            });
        });

        final boolean isAddedOnDb = _reviewsService.addReview(new Review(_restaurant, _rating, _review, _pictures));

        if (!isAddedOnDb) return;

        // Return to the main page, and remove previous history
        Intent intent = new Intent(ConfirmReviewActivity.this, MapActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
