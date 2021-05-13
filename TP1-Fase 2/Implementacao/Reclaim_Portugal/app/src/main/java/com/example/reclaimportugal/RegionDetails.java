package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class RegionDetails extends AppCompatActivity {
    private ImageButton back;
    private ImageButton goMap;
    private Button playButton;
    private TextView description;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);

        ImageSlider imageSlider = findViewById(R.id.region_slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.er_torre_de_belem,"Torre de Belem"));
        slideModels.add(new SlideModel(R.drawable.er_palacio_pena,"Palácio da Pena"));
        slideModels.add(new SlideModel(R.drawable.er_castelo_sao_jorge,"Castelo São Jorge"));
        slideModels.add(new SlideModel(R.drawable.er_aquapolis,"Aquapolis"));
        slideModels.add(new SlideModel(R.drawable.er_aqueduto,"Aqueduto"));
        slideModels.add(new SlideModel(R.drawable.er_castelo_de_tomar,"Castelo de Tomár"));
        slideModels.add(new SlideModel(R.drawable.er_praca_pedro,"Praça de D. Pedro"));
        slideModels.add(new SlideModel(R.drawable.er_obidos,"Castelo de Óbidos"));
        imageSlider.setImageList(slideModels, true);

        goMap = findViewById(R.id.go_to_map);
        goMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               goToMap();
           }
       });


        back = findViewById(R.id.back_region_selection);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBack();
            }
        });

        playButton = findViewById(R.id.button_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityPlay();
            }
        });

        description = findViewById(R.id.region_description);
        description.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

       title = findViewById(R.id.region_title);
        title.setText("Estremadura e Ribatejo");
    }

    private void openActivityPlay() {
        Intent intent = new Intent(this, GameOngoing.class);
        startActivity(intent);
    }

    private void openActivityBack() {
        Intent intent = new Intent(this, RegionSelect.class);
        startActivity(intent);
    }

    private void goToMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        //intent.putExtra("LOCATION", "Belem");
        startActivity(intent);
    }
}
