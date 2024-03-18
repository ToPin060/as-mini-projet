package asminiproject.miniproject.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import asminiproject.miniproject.R;

public class ReservationActivity extends AppCompatActivity {

    private String name;
    private String firstname;
    private String email;
    private String phone;
    private String numbers;
    private String additionnalInfos;
    private String date;
    private String time;

    private TextInputLayout inputName;
    private TextInputLayout inputFirstname;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPhone;
    private TextInputLayout inputNumbers;
    private TextInputLayout inputAdditionnal;
    private TextInputLayout inputDate;
    private TextInputLayout inputTime;
    private Button sendButton;
    private Button returnButton;

    private AutoCompleteTextView autoCompleteTextView;

    private void initUIElements(){
        sendButton = findViewById(R.id.submitReservationButton);
        returnButton = findViewById(R.id.backReservationbutton);

        autoCompleteTextView = findViewById(R.id.text_autoComplete);
        String[] numbers = getResources().getStringArray(R.array.numbers_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, numbers);
        autoCompleteTextView.setAdapter(adapter);

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
                    .setMinute(0)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
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

        inputName.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputName.setErrorEnabled(false);
            }
        });
        inputFirstname.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputFirstname.setErrorEnabled(false);
            }
        });
        inputPhone.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputPhone.setErrorEnabled(false);
            }
        });
        inputNumbers.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputNumbers.setErrorEnabled(false);
            }
        });
        inputDate.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputDate.setErrorEnabled(false);
            }
        });
        inputTime.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                inputTime.setErrorEnabled(false);
            }
        });

        inputPhone.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phoneNumber = inputPhone.getEditText().getText().toString().trim();
                if (!isValidPhoneNumber(phoneNumber)) {
                    inputPhone.setError("Numéro de téléphone invalide");
                } else {
                    inputPhone.setErrorEnabled(false);
                }
            }
        });

        inputEmail.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = inputEmail.getEditText().getText().toString().trim();
                if (!isValidEmail(email)) {
                    inputEmail.setError("Email invalide");
                } else {
                    inputEmail.setError(null);
                }
            }
        });

        sendButton.setOnClickListener(v -> onSendClick());

    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10}$");
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getFormInformations(){
        name = inputName.getEditText().getText().toString();
        firstname = inputFirstname.getEditText().getText().toString();
        email = inputEmail.getEditText().getText().toString();
        phone = inputPhone.getEditText().getText().toString();
        numbers = inputNumbers.getEditText().getText().toString();
        additionnalInfos = inputAdditionnal.getEditText().getText().toString();
        date = inputDate.getEditText().getText().toString();
        time = inputTime.getEditText().getText().toString();
    }

    private void onSendClick() {
        boolean isValid = true;

        if (TextUtils.isEmpty(inputName.getEditText().getText().toString().trim())) {
            inputName.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputName.setError(null);
        }

        if (TextUtils.isEmpty(inputFirstname.getEditText().getText().toString().trim())) {
            inputFirstname.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputFirstname.setError(null);
        }

        if (TextUtils.isEmpty(inputEmail.getEditText().getText().toString().trim())) {
            inputEmail.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputEmail.setError(null);
        }

        if (TextUtils.isEmpty(inputPhone.getEditText().getText().toString().trim())) {
            inputPhone.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputPhone.setError(null);
        }

        if (TextUtils.isEmpty(inputNumbers.getEditText().getText().toString().trim())) {
            inputNumbers.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputNumbers.setError(null);
        }

        if (TextUtils.isEmpty(inputDate.getEditText().getText().toString().trim())) {
            inputDate.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputDate.setError(null);
        }

        if (TextUtils.isEmpty(inputTime.getEditText().getText().toString().trim())) {
            inputTime.setError("Champ obligatoire");
            isValid = false;
        } else {
            inputTime.setError(null);
        }

        if (isValid) {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
        }
    }
}
