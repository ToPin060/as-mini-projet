package asminiproject.miniproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// com.zomato.photofilters.imageprocessors.Filter;
//import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import asminiproject.miniproject.R;
import asminiproject.miniproject.thumbnails.ThumbnailCallback;
import asminiproject.miniproject.thumbnails.ThumbnailItem;
import asminiproject.miniproject.thumbnails.ThumbnailsAdapter;
import asminiproject.miniproject.thumbnails.ThumbnailsManager;


public class EditPhotoActivity extends AppCompatActivity implements ThumbnailCallback {
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    Bitmap initImage;
    Bitmap toEditImage;
    Activity activity;
    ArrayList<Bitmap> capturedImages;

    ImageView imageView;
    Button cancelButton;
    Button retakeButton;
    Button validateButton;
    LinearLayout linearLayout;
    RecyclerView recyclerView;

    private ActivityResultLauncher<Intent> takepicturelauncher;
    private final ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    private float ratingBarValue;
    private CharSequence ratingTextValue;

    protected void initUIElements(){
        imageView = findViewById(R.id.editableImage);
        retakeButton = findViewById(R.id.button_retake);
        cancelButton = findViewById(R.id.button_cancel);
        validateButton = findViewById(R.id.button_validate);
        linearLayout = findViewById(R.id.linearLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    protected void initIntentElements(){
        ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        ratingTextValue = getIntent().getStringExtra("ratingText");
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        initImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        toEditImage = initImage;
    }

    protected void initThumbnailList(){
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bindDatatoAdapter();
    }

    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void setImageViewFilter(Bitmap bitmap, Filter filter) {
        Bitmap mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap output = filter.processFilter(mutable);

        imageView.setImageBitmap(output);
        toEditImage = output;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        activity = this;

        initUIElements();
        initIntentElements();
        initThumbnailList();


        setImageView(toEditImage);

        retakeButton.setOnClickListener(v -> retakeAction());
        cancelButton.setOnClickListener(v -> cancelAction());
        validateButton.setOnClickListener(v -> validateAction());

        takepicturelauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) {
                            initImage = imageBitmap;
                            imageView.setImageBitmap(initImage);
                            initThumbnailList();
                        } else {
                            Toast.makeText(this, "Erreur lors de la capture de la photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
    protected void cancelAction() {
        Intent intent = new Intent(EditPhotoActivity.this, RestaurantReviewActivity.class);

        intent.putExtra("ratingBarValue", ratingBarValue);
        intent.putExtra("ratingComment", ratingTextValue);
        startActivity(intent);
    }

    protected void retakeAction() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takepicturelauncher.launch(takePictureIntent);
    }

    protected void validateAction() {
        Intent intent = new Intent(EditPhotoActivity.this, RestaurantReviewActivity.class);
        toEditImage.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        intent.putExtra("image", byteArray);
        intent.putExtra("ratingBarValue", ratingBarValue);
        intent.putExtra("ratingComment", ratingTextValue);
        intent.putParcelableArrayListExtra("capturedImages", capturedImages);

        startActivity(intent);

    }

    private void bindDatatoAdapter(){
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = () -> {
            ThumbnailItem t1 = new ThumbnailItem();
            ThumbnailItem t3 = new ThumbnailItem();
            ThumbnailItem t4 = new ThumbnailItem();
            ThumbnailItem t5 = new ThumbnailItem();
            ThumbnailItem t6 = new ThumbnailItem();

            t1.image = initImage;
            t3.image = initImage;
            t4.image = initImage;
            t5.image = initImage;
            t6.image = initImage;

            ThumbnailsManager.clearThumbs();
            ThumbnailsManager.addThumb(t1);

            t3.filter = SampleFilters.getStarLitFilter();
            ThumbnailsManager.addThumb(t3);

            t4.filter = SampleFilters.getNightWhisperFilter();
            ThumbnailsManager.addThumb(t4);

            t5.filter = SampleFilters.getLimeStutterFilter();
            ThumbnailsManager.addThumb(t5);

            t6.filter = SampleFilters.getAweStruckVibeFilter();
            ThumbnailsManager.addThumb(t6);

            List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

            ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, (ThumbnailCallback) activity);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        setImageViewFilter(initImage, filter);
    }
}
