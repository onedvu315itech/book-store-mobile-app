package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entity.UserEntity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textViewName, textViewDescription, textViewAddress;
    private GoogleMap mMap;

    private TextView viewHomeButton, viewCartButton, chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textViewName = findViewById(R.id.textViewName);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewAddress = findViewById(R.id.textViewAddress);

        // Set example values (replace with actual data)
        textViewName.setText("Book Store");
        textViewDescription.setText("Khám phá nhiều tầng tri thức tại đây");
        textViewAddress.setText("Địa chỉ: Nhà Văn Hóa Sinh Viên TP.HCM, Lưu Hữu Phước, Đông Hoà, Dĩ An, Bình Dương, Vietnam");

        chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(AboutActivity.this, ChatActivity.class);
                    startActivity(intent);
                }
            }
        });

        viewHomeButton = findViewById(R.id.home_button);
        viewHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        viewCartButton = findViewById(R.id.cart_button);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in a default location and move the camera
        LatLng defaultLocation = new LatLng(10.875197542357116, 106.80072339454937); // Replace with your desired coordinates
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Default Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15)); // Zoom level 15 is street level
    }
}
