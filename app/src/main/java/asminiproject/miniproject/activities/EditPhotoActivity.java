package asminiproject.miniproject.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

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
    private int currentStickerIndex = 0;
    private final int[] stickerResources = {R.drawable.emoji_sun_glasses, R.drawable.emoji_grimacing};

    ImageView imageView;
    Button cancelButton, retakeButton, validateButton;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    MaterialSwitch sensor_switch;

    SensorManager sensorManager;
    Sensor lightSensor;


    private ActivityResultLauncher<Intent> takepicturelauncher;
    private final ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    private float ratingBarValue;
    private CharSequence ratingTextValue;
    private Canvas canvas;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        activity = this;

        initUIElements();
        initIntentElements();
        initThumbnailList();
        initSensors();
        bindButtons();

        setImageView(toEditImage);
        canvas = new Canvas(toEditImage);

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

        imageView.setOnTouchListener(new View.OnTouchListener() {
            float x,y;
            final float[] touchPoint = {x, y};

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchPoint[0] = event.getX();
                        touchPoint[1] = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Matrix matrix = imageView.getImageMatrix();
                        matrix.invert(matrix);
                        matrix.mapPoints(touchPoint);
                        drawSticker(touchPoint[0], touchPoint[1], currentStickerIndex, canvas);
                        matrix.invert(matrix);
                        imageView.invalidate();
                        break;
                }
                return true;
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(radioButton1.isChecked()){
                currentStickerIndex = 0;
            }
            else if(radioButton2.isChecked()){
                currentStickerIndex = 1;
            }
        });

        SensorEventListener lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float lux = event.values[0];
                Log.d("DEBUG", "onSensorChanged: "+lux);

                Bitmap mutable = initImage.copy(Bitmap.Config.ARGB_8888, true);
                Filter filter = new Filter();
                filter.addSubFilter(new ContrastSubFilter(lux));
                Bitmap output = filter.processFilter(mutable);

                toEditImage = output;
                setImageView(toEditImage);
                canvas.setBitmap(toEditImage);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Je sais pas quoi faire ici ¯\_(ツ)_/¯
                Log.d("DEBUG", "onAccuracyChanged");
            }
        };

        sensor_switch.setOnClickListener(v -> {
            if (sensor_switch.isChecked()){
                sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            else {
                sensorManager.unregisterListener(lightSensorListener);
                toEditImage = initImage.copy(Bitmap.Config.ARGB_8888, true);
                setImageView(toEditImage);
                canvas.setBitmap(toEditImage);
            }
        });

    }

    protected void initUIElements(){
        imageView = findViewById(R.id.editableImage);
        retakeButton = findViewById(R.id.button_retake);
        cancelButton = findViewById(R.id.button_cancel);
        validateButton = findViewById(R.id.button_validate);
        linearLayout = findViewById(R.id.linearLayout);
        recyclerView = findViewById(R.id.recyclerView);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radio_button_1);
        radioButton2 = findViewById(R.id.radio_button_2);
        sensor_switch = findViewById(R.id.switch_sensor);
    }

    protected void initIntentElements(){
        ratingBarValue = getIntent().getFloatExtra("ratingBar", 0.0f);
        ratingTextValue = getIntent().getStringExtra("ratingText");
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        capturedImages = getIntent().getParcelableArrayListExtra("capturedImages");

        initImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        toEditImage = initImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    protected void initThumbnailList(){
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bindDatatoAdapter();
    }

    protected void initSensors(){

        sensor_switch.setChecked(false);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            sensor_switch.setEnabled(false);
        }
    }

    protected void bindButtons(){
        retakeButton.setOnClickListener(v -> retakeAction());
        cancelButton.setOnClickListener(v -> cancelAction());
        validateButton.setOnClickListener(v -> validateAction());
    }


    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void setImageViewFilter(Bitmap bitmap, Filter filter) {
        Bitmap mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap output = filter.processFilter(mutable);

        toEditImage = output;
        setImageView(toEditImage);
        canvas.setBitmap(toEditImage);
    }



    protected void cancelAction() {
        finish();
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
        setImageViewFilter(toEditImage, filter);
    }


    private void drawSticker(float x, float y, int stickerIndex, Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(this, stickerResources[stickerIndex]);
        if (drawable != null) {

            drawable.setBounds((int) x - 15, (int) y - 15,((int) x + 15),((int) y + 15));

            drawable.draw(canvas);

            imageView.invalidate(); // Rafraîchir l'image pour afficher le sticker

        } else {
            Toast.makeText(this, "Failed to load sticker", Toast.LENGTH_SHORT).show();
        }
    }
}
