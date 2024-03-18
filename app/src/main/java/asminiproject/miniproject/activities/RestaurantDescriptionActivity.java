package asminiproject.miniproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;

import asminiproject.miniproject.R;
import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.services.RestaurantsService;

public class RestaurantDescriptionActivity extends AppCompatActivity {
    private Restaurant _restaurant;

    private Context _context;
    private RestaurantsService restaurantsService;

    /*
        RestaurantDescriptionActivity views
     */
    private TextView _nameView, _overallRatingView, _ratingNumberView, _addressView, _phoneView;
    private RatingBar _ratingBarView;
    private Button _backButtonView, _bookButton;
    private FloatingActionButton _ratingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
        setupViewsContent();
        setupButtonsEvent();
    }

    private void initializeActivity() {
        _context = getApplicationContext();
        restaurantsService = RestaurantsService.getInstance();
        _restaurant = restaurantsService
                .getRestaurantById(getIntent().getIntExtra("RESTAURANT_ID", 11));

        Configuration.getInstance().load(_context, PreferenceManager.getDefaultSharedPreferences(_context));
        setContentView(R.layout.activity_restaurant_description);

        _nameView = (TextView) findViewById(R.id.name_view);
        _overallRatingView = (TextView) findViewById(R.id.overall_rating_view);
        _ratingNumberView = (TextView) findViewById(R.id.rating_number_view);
        _ratingBarView = (RatingBar) findViewById(R.id.rating_bar_view);
        _addressView = (TextView) findViewById(R.id.address_view);
        _phoneView = (TextView) findViewById(R.id.phone_view);
        _backButtonView = (Button) findViewById(R.id.back_button_view);
        _bookButton = (Button) findViewById(R.id.book_button_view);
        _ratingButton = (FloatingActionButton) findViewById(R.id.rating_button_view);
    }

    private void setupViewsContent() {
        _nameView.setText(_restaurant.name);
        _overallRatingView.setText(String.format("%s", _restaurant.overallRating));
        _ratingNumberView.setText(String.format("%s avis", _restaurant.ratingsNumber));
        _ratingBarView.setRating(_restaurant.overallRating);
        _addressView.setText(_restaurant.address);
        _phoneView.setText(_restaurant.phone);
    }
    private void setupButtonsEvent() {
        _backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reservationActivity = new Intent(_context, ReservationActivity.class);
                reservationActivity.putExtra("RESTAURANT_ID", _restaurant.id);
                reservationActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(reservationActivity);
            }
        });
        _ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurantRatingActivity = new Intent(_context, RatingRestaurantActivity.class);
                restaurantRatingActivity.putExtra("RESTAURANT_ID", _restaurant.id);
                restaurantRatingActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(restaurantRatingActivity);
            }
        });
    }
}
