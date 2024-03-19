package asminiproject.miniproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private RestaurantsService _restaurantsService;

    private TextView _nameView, _overallRatingView, _ratingNumberView, _addressView, _phoneView;
    private RatingBar _ratingBarView;
    private Button _backButtonView, _bookButton, _reviewButton;

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
        _restaurantsService = RestaurantsService.getInstance();

        _restaurant = _restaurantsService
                .getRestaurantById(getIntent().getIntExtra("restaurantId", 0));

        setContentView(R.layout.activity_restaurant_profile);

        _nameView = findViewById(R.id.name_view);
        _overallRatingView = findViewById(R.id.overall_rating_view);
        _ratingNumberView = findViewById(R.id.rating_number_view);
        _ratingBarView = findViewById(R.id.rating_bar_view);
        _addressView = findViewById(R.id.address_view);
        _phoneView = findViewById(R.id.phone_view);
        _backButtonView = findViewById(R.id.back_button_view);
        _bookButton = findViewById(R.id.book_button_view);
        _reviewButton = findViewById(R.id.rating_button_view);
        _scheduleInclude = findViewById(R.id.schedule);
    }
    private void setupViewsContent() {
        _nameView.setText(_restaurant.name);
        _overallRatingView.setText(String.format("%s", _restaurant.overallRating));
        _ratingNumberView.setText(String.format("%s avis", _restaurant.ratingsNumber));
        _ratingBarView.setRating(_restaurant.overallRating);
        _ratingBarView.setIsIndicator(true);
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
        _backButtonView.setOnClickListener(view -> onBackButtonClick());
        _bookButton.setOnClickListener(view -> onBookButtonClick());
        _reviewButton.setOnClickListener(view -> onReviewButtonClick());
    }

    public void onBackButtonClick() {
        finish();
    }
    public void onReviewButtonClick() {
        Intent restaurantReviewActivity = new Intent(_context, RestaurantReviewActivity.class);
        restaurantReviewActivity.putExtra("restaurantId", _restaurant.id);
        restaurantReviewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(restaurantReviewActivity);
    }
    public void onBookButtonClick() {
        Intent reservationActivity = new Intent(_context, ReservationActivity.class);
        reservationActivity.putExtra("restaurantId", _restaurant.id);
        reservationActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(reservationActivity);
    }

    public String computeStringFromInt(int value) {
        if (value == 0)  return "00";
        else return String.format("%s", value);
    }
}
