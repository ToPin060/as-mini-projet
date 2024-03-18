package asminiproject.miniproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;

import asminiproject.miniproject.R;
import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.services.RestaurantsService;

public class RestaurantProfileActivity extends AppCompatActivity {
    private final int[] dayIds = new int[]{
            R.id.monday_slots,
            R.id.tuesday_slots,
            R.id.wednesday_slots,
            R.id.thursday_slots,
            R.id.friday_slots,
            R.id.saturday_slots,
            R.id.sunday_slots
    };

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
    private ViewGroup _scheduleInclude;


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
        setContentView(R.layout.activity_restaurant_profile);

        _nameView = (TextView) findViewById(R.id.name_view);
        _overallRatingView = (TextView) findViewById(R.id.overall_rating_view);
        _ratingNumberView = (TextView) findViewById(R.id.rating_number_view);
        _ratingBarView = (RatingBar) findViewById(R.id.rating_bar_view);
        _addressView = (TextView) findViewById(R.id.address_view);
        _phoneView = (TextView) findViewById(R.id.phone_view);
        _backButtonView = (Button) findViewById(R.id.back_button_view);
        _bookButton = (Button) findViewById(R.id.book_button_view);
        _ratingButton = (FloatingActionButton) findViewById(R.id.rating_button_view);
        _scheduleInclude = (ViewGroup) findViewById(R.id.schedule);
    }

    private void setupViewsContent() {
        _nameView.setText(_restaurant.name);
        _overallRatingView.setText(String.format("%s", _restaurant.overallRating));
        _ratingNumberView.setText(String.format("%s avis", _restaurant.ratingsNumber));
        _ratingBarView.setRating(_restaurant.overallRating);
        _addressView.setText(_restaurant.address);
        _phoneView.setText(_restaurant.phone);

        setupScheduleView();
    }

    private void setupScheduleView() {
        for (int i = 0; i < 7; i++) {
            ViewGroup timeslot1 =  _scheduleInclude.findViewById(dayIds[i]).findViewById(R.id.timeslot1);
            ViewGroup timeslot2 =  _scheduleInclude.findViewById(dayIds[i]).findViewById(R.id.timeslot2);

            ((TextView) timeslot1.findViewById(R.id.startH_text))
                    .setText(computeStringFromInt(_restaurant.schedule.get(i).first.startH));
            ((TextView) timeslot1.findViewById(R.id.startM_text))
                    .setText(computeStringFromInt(_restaurant.schedule.get(i).first.startM));
            ((TextView) timeslot1.findViewById(R.id.endH_text))
                    .setText(computeStringFromInt(_restaurant.schedule.get(i).first.endH));
            ((TextView) timeslot1.findViewById(R.id.endM_text))
                    .setText(computeStringFromInt(_restaurant.schedule.get(i).first.endM));

            if (_restaurant.schedule.get(i).second != null) {
                ((TextView) timeslot2.findViewById(R.id.startH_text))
                        .setText(computeStringFromInt(_restaurant.schedule.get(i).second.startH));
                ((TextView) timeslot2.findViewById(R.id.startM_text))
                        .setText(computeStringFromInt(_restaurant.schedule.get(i).second.startM));
                ((TextView) timeslot2.findViewById(R.id.endH_text))
                        .setText(computeStringFromInt(_restaurant.schedule.get(i).second.endH));
                ((TextView) timeslot2.findViewById(R.id.endM_text))
                        .setText(computeStringFromInt(_restaurant.schedule.get(i).second.endM));
            } else {
                timeslot2.setVisibility(View.INVISIBLE);
            }
        }
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

    public String computeStringFromInt(int value) {
        if (value == 0)  return "00";
        else return String.format("%s", value);
    }
}
