package asminiproject.miniproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asminiproject.miniproject.R;
import asminiproject.miniproject.services.RestaurantService;
import asminiproject.miniproject.thumbnails.ThumbnailCallback;
import asminiproject.miniproject.thumbnails.ThumbnailItem;
import asminiproject.miniproject.thumbnails.ThumbnailsAdapter;

public class RestaurantReviewActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;
    private final ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    private Bitmap editedImage;
    List<ThumbnailItem> thumbs;
    private String _restaurantId;

    private Activity _activity;
    private ActivityResultLauncher<Intent> takepicturelauncher;
    private RatingBar ratingBar;
    private Button _backButton, _submitButton, _cameraButton;
    private TextInputLayout ratingInput;
    private ArrayList<Bitmap> capturedImages;
    private RecyclerView recyclerView;
    private TextView _review_title_restaurant_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_review);

        initUIElements();
        initIntentElements();
        setupButtonsEvent();

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

        Bundle bundle = getIntent().getExtras();

        Log.e("", bundle.toString());
    }

    public void initUIElements(){
        ratingBar = findViewById(R.id.ratingBar);
        ratingInput = findViewById(R.id.rating_text);
        recyclerView = findViewById(R.id.recyclerView);
        _backButton = findViewById(R.id.back_button);
        _submitButton = findViewById(R.id.submit_button);
        _cameraButton = findViewById(R.id.camera_button);
        _review_title_restaurant_name = findViewById(R.id.restaurant_name);
    }

    public void initIntentElements(){
        _restaurantId = getIntent().getStringExtra("restaurantId");
        _review_title_restaurant_name.setText(RestaurantService.getInstance().getRestaurantById(_restaurantId).getName());
        String ratingComment = getIntent().getStringExtra("ratingComment");
        float ratingBarValue = getIntent().getFloatExtra("ratingBarValue", 0.0f);
        capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        if(ratingComment != null){
            ratingInput.getEditText().setText(ratingComment);
        }
        if(ratingBarValue != 0.0f){
            ratingBar.setRating(ratingBarValue);
        }
        if(capturedImages == null){
            capturedImages = new ArrayList<>();
        }

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        if (byteArray != null){

            editedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            capturedImages.add(editedImage);
            initThumbnailList();
        }
    }

    protected void initThumbnailList(){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bindDatatoAdapter();
    }

    private void bindDatatoAdapter(){
        Log.d("Debug", "Dans BINDDATA");
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

                thumbs = new ArrayList<>();
                for (Bitmap bitmap: capturedImages){
                    ThumbnailItem t = new ThumbnailItem();
                    t.image = bitmap;
                    thumbs.add(t);
                }

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, (ThumbnailCallback) _activity);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    private void dispatchTakePictureIntent() {
        if (capturedImages.size() < 2) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takepicturelauncher.launch(takePictureIntent);
        }
        else {
            Toast.makeText(this, "Vous ne pouvez prendre qu'au maximum 2 photos",
                    Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Accès à l'appareil photo refusé", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Log.d("Permissions","Refused");
            }
        }
    }
    public void setupButtonsEvent() {
        _backButton.setOnClickListener(view -> onBackButtonClick());
        _cameraButton.setOnClickListener(view -> onCameraButtonClick());
        _submitButton.setOnClickListener(view -> onSubmitButtonClick());
    }

    public void onBackButtonClick() {
        finish();
    }
    protected void onSubmitButtonClick(){
        final float ratingScore = ratingBar.getRating();
        final String ratingText = Objects.requireNonNull(ratingInput.getEditText().getText()).toString();


        if (ratingScore > 0 && !TextUtils.isEmpty(ratingInput.getEditText().getText().toString().trim())) {
            ratingInput.setActivated(false);

            Intent intent = new Intent(getApplicationContext(), ConfirmReviewActivity.class);
            intent.putExtra("ratingBar", ratingScore);
            intent.putExtra("ratingText", ratingText);
            intent.putExtra("restaurantId", _restaurantId);

            if(!capturedImages.isEmpty()) {
                intent.putParcelableArrayListExtra("capturedImages", capturedImages);
            }
            startActivity(intent);

        } else {
            Toast.makeText(this, "Vous devez mettre un commentaire ET une note",
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCameraButtonClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);

        } else {
            dispatchTakePictureIntent();
        }
    }

    private void editPreviewImage(Bitmap image){

        float ratingScore = ratingBar.getRating();
        String ratingText = Objects.requireNonNull(ratingInput.getEditText().getText()).toString();

        Intent intent = new Intent(getApplicationContext(), EditPhotoActivity.class);
        image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        intent.putExtra("image", byteArray);
        intent.putExtra("ratingBar", ratingScore);
        intent.putExtra("ratingText", ratingText);
        intent.putExtra("restaurantId", _restaurantId);
        intent.putParcelableArrayListExtra("capturedImages", capturedImages);

        startActivity(intent);
    }
}