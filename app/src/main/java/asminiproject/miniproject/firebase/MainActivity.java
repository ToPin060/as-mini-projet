package asminiproject.miniproject.firebase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

import asminiproject.miniproject.R;
import asminiproject.miniproject.firebase.services.SimpleCallback;
import asminiproject.miniproject.firebase.model.Address;
import asminiproject.miniproject.firebase.services.AddressService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressToFirestore();
            }
        });
    }

    private void addAddressToFirestore() {
        //Quick examples :)
        CollectionReference dbAddress = db.collection("Address");

        AddressService.addAddress(db, new Address((float) 1.8456124E-4, (float) 1.9864198E-4));

        //Use getAllAddresses et al like this.
        AddressService.getAllAddresses(db, new SimpleCallback<List<Address>>() {
            @Override
            public void callback(List<Address> data) {
                data.forEach(address -> Log.d(TAG, address.toString()));
            }
        });

        //Log.d(TAG, allAddresses.toString());
    }
}
