package asminiproject.miniproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private TextInputLayout inputName;
    private TextInputLayout inputFirstname;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPhone;
    private TextInputLayout inputNumbers;
    private TextInputLayout inputAdditionnal;
    private TextInputLayout inputDate;
    private TextInputLayout inputTime;

    private AutoCompleteTextView autoCompleteTextView;

    private void initUIElements(){
        autoCompleteTextView = findViewById(R.id.autoComplete);
        inputName = findViewById(R.id.text_name);
        inputFirstname = findViewById(R.id.text_firstname);
        inputEmail = findViewById(R.id.text_email);
        inputPhone = findViewById(R.id.text_phone);
        inputAdditionnal = findViewById(R.id.text_additionalInfo);
        inputNumbers = findViewById(R.id.text_reservationNumbers);

        inputDate = findViewById(R.id.text_date);
        inputTime = findViewById(R.id.text_time);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        initUIElements();

        String[] numbers = getResources().getStringArray(R.array.numbers_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, numbers);
        autoCompleteTextView.setAdapter(adapter);

        inputDate.setEndIconOnClickListener(v -> {
            MaterialDatePicker<Long> datepicker = MaterialDatePicker.Builder.datePicker().setTitleText("Date de votre réservation").build();

            datepicker.addOnPositiveButtonClickListener(selection -> {
                String selectedDate = DateFormat.getDateInstance().format(new Date(selection));
                inputDate.getEditText().setText(selectedDate);
            });

            datepicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });



        inputTime.setEndIconOnClickListener(v -> {
            MaterialTimePicker timepicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Heure de votre réservation")
                    .build();

            timepicker.addOnPositiveButtonClickListener(dialog -> {
                int hour = timepicker.getHour();
                int minute = timepicker.getMinute();
                String selectedTime = String.format("%02d:%02d", hour, minute);
                inputTime.getEditText().setText(selectedTime);
            });

            timepicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });


        inputPhone.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phoneNumber = inputPhone.getEditText().getText().toString().trim();
                if (!isValidPhoneNumber(phoneNumber)) {
                    inputPhone.setError("Numéro de téléphone invalide");

                } else {
                    inputPhone.setError(null);
                }
            }
        });

    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10}$");
    }
}
