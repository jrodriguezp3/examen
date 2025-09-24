package com.example.examen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<UserInfo> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchUsers();
    }

    private void fetchUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetUsers api = retrofit.create(GetUsers.class);
        Call<GetUsers.RandomUserResponse> call = api.getUsers(20);

        call.enqueue(new Callback<GetUsers.RandomUserResponse>() {
            @Override
            public void onResponse(Call<GetUsers.RandomUserResponse> call, Response<GetUsers.RandomUserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    userList = Arrays.asList(response.body().results);
                    adapter = new UserAdapter(userList, user -> {
                        // Al seleccionar, abrir DetailActivity
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetUsers.RandomUserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}