package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegionDetails extends AppCompatActivity {
    private ImageButton back;
    private Button playButton;
    private TextView description;
    private TextView title;
    private int id;
    private String json;
    private JSONObject obj;
    private String language;
    private List<SlideModel> slideModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("regionID", -1);

        try {
            JSONArray jArray = new JSONArray(getJson());
            obj = jArray.getJSONObject(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        language = Locale.getDefault().getLanguage();

        setContentView(R.layout.activity_region_detail);

        ImageSlider imageSlider = findViewById(R.id.region_slider);
        getSlideImages();
        imageSlider.setImageList(slideModels, true);

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
        description.setMovementMethod(new ScrollingMovementMethod());
        try {
            if(language == "en"){
            description.setText(obj.getString("DescriptionEn"));
            }else{
            description.setText(obj.getString("DescriptionPt"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        title = findViewById(R.id.region_title);
        try {
            title.setText(obj.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openActivityPlay() {
        Intent intent = new Intent(RegionDetails.this, GameOngoing.class);
        intent.putExtra("regionIDGame", id);
        startActivity(intent);
    }

    private void openActivityBack() {
        Intent intent = new Intent(this, RegionSelect.class);
        startActivity(intent);
    }

    private String getJson(){
        try{
            InputStream is = getAssets().open("regions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;

        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void getSlideImages(){
        slideModels = new ArrayList<>();
        if(id == 5){
        slideModels.add(new SlideModel(R.drawable.er_torre_de_belem,"Torre de Belem"));
        slideModels.add(new SlideModel(R.drawable.er_palacio_pena,"Palácio da Pena"));
        slideModels.add(new SlideModel(R.drawable.er_castelo_sao_jorge,"Castelo São Jorge"));
        }else if(id == 1){
            slideModels.add(new SlideModel(R.drawable.er_castelo_de_tomar,"Castelo de Tomár"));
            slideModels.add(new SlideModel(R.drawable.er_praca_pedro,"Praça de D. Pedro"));
        }

    }
}
