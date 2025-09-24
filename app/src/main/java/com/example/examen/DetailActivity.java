package com.example.examen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private UserInfo user;
    private TextView tvDetails;
    private ImageView ivFlag;
    private GoogleMap mMap;
    private ImageView ivUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = (UserInfo) getIntent().getSerializableExtra("user");

        tvDetails = findViewById(R.id.tvDetails);
        ivFlag = findViewById(R.id.ivFlag);
        ivUserPhoto = findViewById(R.id.ivUserPhoto);

        Picasso.get().load(user.picture.large).into(ivUserPhoto);

        String details = "Nombre: " + user.name.title + " " + user.name.first + " " + user.name.last + "\n" +
                "Email: " + user.email + "\n" +
                "Dirección: " + user.location.street.number + " " + user.location.street.name + "\n" +
                "Edad: " + user.dob.age + "\n" +
                "Teléfono: " + user.phone + "\n" +
                "Celular: " + user.cell + "\n" +
                "Nacionalidad: " + user.nat + "\n" +
                "Ciudad: " + user.location.city + "\n" +
                "ID: " + user.id.name + " " + user.id.value + "\n" +
                "Coordenadas: " + user.location.coordinates.latitude + ", " + user.location.coordinates.longitude;
                ;
        tvDetails.setText(details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Map fragment is null");
            Toast.makeText(this, "Error: Map fragment not found", Toast.LENGTH_SHORT).show();
        }

        fetchFlag(user.location.country);
    }

    private void fetchFlag(String country) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCountries api = retrofit.create(GetCountries.class);
        Call<GetCountries.CountryResponse[]> call = api.getCountry(country);

        call.enqueue(new Callback<GetCountries.CountryResponse[]>() {
            @Override
            public void onResponse(Call<GetCountries.CountryResponse[]> call, Response<GetCountries.CountryResponse[]> response) {
                if (response.isSuccessful() && response.body().length > 0) {
                    String flagUrl = response.body()[0].flags.png;
                    Picasso.get().load(flagUrl).into(ivFlag);
                }
            }

            @Override
            public void onFailure(Call<GetCountries.CountryResponse[]> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error al cargar bandera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            double lat = Double.parseDouble(user.location.coordinates.latitude);
            double lng = Double.parseDouble(user.location.coordinates.longitude);
            LatLng location = new LatLng(lat, lng);

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(user.location.city));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Coordenadas inválidas", Toast.LENGTH_SHORT).show();
        }
    }
}