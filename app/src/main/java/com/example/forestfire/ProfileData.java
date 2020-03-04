package com.example.forestfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileData extends AppCompatActivity {

    AutoCompleteTextView city, state, country;
    EditText name, phone, occuptn, plotNo, lane, pin, locality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

        TextView username = findViewById(R.id.user_email);
        String email = getIntent().getStringExtra("user");
        username.setText(email);
        name = findViewById(R.id.username);
        phone = findViewById(R.id.contact);
        occuptn = findViewById(R.id.user_occupation);
        plotNo = findViewById(R.id.plot_no);
        lane = findViewById(R.id.lane);
        locality = findViewById(R.id.locality);
        pin = findViewById(R.id.user_pincode);

        String uName = name.getText().toString();
        String uPhone = phone.getText().toString();
        String uOccup = occuptn.getText().toString();
        String uPlot = plotNo.getText().toString();
        String uLane = lane.getText().toString();
        String uLocality = locality.getText().toString();
        String uPin = pin.getText().toString();
//*************************************************************************************************
//        https://www.youtube.com/watch?v=y6mvLlAQ_lk&list=PLG1VhO2NjDZmi2FZTlwqpMkq_NU69i2Gj
//**************************************************************************************************

//Autocomplete cities states and countires
        String[] STATES = getResources().getStringArray(R.array.states);

        city = findViewById(R.id.user_city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, STATES);
        city.setAdapter(adapter);
        String uCity = city.getText().toString();

        state = findViewById(R.id.user_state);
        state.setAdapter(adapter);
        String uState = city.getText().toString();

        country = findViewById(R.id.user_country);
        country.setAdapter(adapter);
        String uCountry = city.getText().toString();

//Autocomplete cities states and countires

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileData.this, MainActivity.class));
            }
        });
    }
}


//        Address work********************************************************************************
//        String apiKey = getString(R.string.api_key);
//        /**
//         * Initialize Places. For simplicity, the API key is hard-coded. In a production
//         * environment we recommend using a secure mechanism to manage API keys.
//         */
//        if (!Places.isInitialized()) {
//            Places.initialize(getApplicationContext(), apiKey);
//        }
//// Create a new Places client instance.
//        PlacesClient placesClient = Places.createClient(this);
//        // Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//        assert autocompleteFragment != null;
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("Here", "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("Errors are here", "An error occurred: " + status);
//            }
//        });
//
//        Address work********************************************************************************
