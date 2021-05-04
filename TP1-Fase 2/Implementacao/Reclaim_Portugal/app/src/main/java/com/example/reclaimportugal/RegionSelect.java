package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegionSelect extends AppCompatActivity {
    private ArrayList<String> title;
    private ArrayList<Integer> regionImages;
    private int counter = 0;
    private ImageView currentImage;
    private ImageButton nextRegion;
    private TextView currentTitle;
    private Button playButton;
    private Button discover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        title = new ArrayList<>();
        title.add("Estremadura e Ribatejo");
        title.add("Alentejo");
        title.add("Algarve");
        title.add("Beira Litoral");
        title.add("Beira Interior");
        title.add("Madeira");
        title.add("Açores");
        title.add("Entre Douro e Minho");
        title.add("Trás os Montes e Alto Douro");
        title.add("Lisboa");
        currentTitle = findViewById(R.id.regionName);
        currentTitle.setText(title.get(counter));

        regionImages = new ArrayList<>();
        regionImages.add(R.drawable.friends);
        regionImages.add(R.drawable.er_obidos);
        regionImages.add(R.drawable.friends);
        regionImages.add(R.drawable.er_obidos);
        regionImages.add(R.drawable.friends);
        regionImages.add(R.drawable.er_obidos);
        regionImages.add(R.drawable.friends);
        regionImages.add(R.drawable.er_obidos);
        regionImages.add(R.drawable.friends);
        regionImages.add(R.drawable.er_obidos);
        currentImage = findViewById(R.id.region_image);
        currentImage.setImageResource((regionImages.get(counter)));

        playButton = findViewById(R.id.play_from_select);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegionSelect.this, GameOngoing.class));
            }
        });

        discover = findViewById(R.id.discoverButton);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegionSelect.this, RegionDetails.class));
            }
        });

        nextRegion = findViewById(R.id.nextRegion);
        nextRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter == 10)
                    counter = 0;
                currentTitle.setText(title.get(counter));
                currentImage.setImageResource((regionImages.get(counter)));

            }
        });
}
}
