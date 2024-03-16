package asminiproject.miniproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class RatingRestaurantActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private ImageView restaurantPreview;
    private RatingBar ratingBar;
    private Button submitButton;
    private TextInputEditText ratingInput;
    private ActivityResultLauncher<Intent> takepicturelauncher;
    private ArrayList<Bitmap> capturedImages;
    private final ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    private Bitmap editedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_restaurant_activity);

        String ratingComment = getIntent().getStringExtra("ratingComment");
        float ratingBarValue = getIntent().getFloatExtra("ratingBarValue", 0.0f);
        capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        restaurantPreview = findViewById(R.id.restaurant_preview);
        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.submitButton);
        ratingInput = findViewById(R.id.rating_text);
        FloatingActionButton buttonOpenCamera = findViewById(R.id.openCamera);
        takepicturelauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) {
                            editPreviewImage(imageBitmap);
                        } else {
                            Toast.makeText(this, "Erreur lors de la capture de la photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        if(ratingComment != null){
            ratingInput.setText(ratingComment);
        }
        if(ratingBarValue != 0.0f){
            ratingBar.setRating(ratingBarValue);
        }
        if(capturedImages == null){
            capturedImages = new ArrayList<>();
        }
        else {
            updateRestaurantPreview();
        }

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        if (byteArray != null){
            editedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            capturedImages.add(editedImage);
            updateRestaurantPreview();
        }

        buttonOpenCamera.setOnClickListener(view -> checkCameraPermission());
        submitButton.setOnClickListener(view -> onSubmitReview());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takepicturelauncher.launch(takePictureIntent);
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            // La permission est déjà accordée, vous pouvez ouvrir l'appareil photo
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, vous pouvez ouvrir l'appareil photo
                dispatchTakePictureIntent();
            } else {
                // La permission a été refusée, vous pouvez informer l'utilisateur ou prendre une autre action
                Log.d("Photo", "Permission refusé");
            }
        }
    }

    protected void onSubmitReview(){
        float ratingScore = ratingBar.getRating();
        String ratingText = Objects.requireNonNull(ratingInput.getText()).toString();

        if (ratingScore > 0) {

            ratingInput.setActivated(false);

            Intent intent = new Intent(RatingRestaurantActivity.this, ConfirmRatingActivity.class);
            intent.putExtra("ratingBar", ratingScore);
            intent.putExtra("ratingText", ratingText);
            intent.putParcelableArrayListExtra("capturedImages", capturedImages);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Erreur, la note ne peut pas être à 0",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRestaurantPreview() {
        if(!capturedImages.isEmpty()){
            Bitmap lastImage = capturedImages.get(capturedImages.size() - 1);
            restaurantPreview.setImageBitmap(lastImage);
        }
    }

    private void editPreviewImage(Bitmap image){

        float ratingScore = ratingBar.getRating();
        String ratingText = Objects.requireNonNull(ratingInput.getText()).toString();

        Intent intent = new Intent(RatingRestaurantActivity.this, EditPhotoActivity.class);
        image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        intent.putExtra("image", byteArray);
        intent.putExtra("ratingBar", ratingScore);
        intent.putExtra("ratingText", ratingText);

        startActivity(intent);
    }
}

//TODO
// 3 - Sur l'endroit du placement de l'image, bouton pour entrer dans l'appareil photo, prender photo + preview