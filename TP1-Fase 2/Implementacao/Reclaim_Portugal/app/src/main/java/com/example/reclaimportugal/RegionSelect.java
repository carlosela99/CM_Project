package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RegionSelect extends AppCompatActivity {
    private ArrayList<Integer> regionImages;
    private int counter = 0;
    private ImageView currentImage;
    private ImageButton nextRegion;
    private ImageButton previousRegion;
    private ImageButton back;
    private TextView currentTitle;
    private TextView currentPercentage;
    private Button playButton;
    private Button discover;
    private Button quickmatch;
    private String json;
    private JSONObject obj;
    private JSONArray jArray;
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        try {
            jArray = new JSONArray(getJson());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        currentTitle = findViewById(R.id.regionName);
        currentPercentage = findViewById(R.id.regionPercentage);
        layout = findViewById(R.id.constraintLayoutMain);

        regionImages = new ArrayList<>();
        regionImages.add(R.drawable.dou_1);
        regionImages.add(R.drawable.alen_1);
        regionImages.add(R.drawable.alg_1);
        regionImages.add(R.drawable.bi_serra_da_estrela);
        regionImages.add(R.drawable.lis_1);
        regionImages.add(R.drawable.er_obidos);
        regionImages.add(R.drawable.a_corvo);
        regionImages.add(R.drawable.ma_1);
        regionImages.add(R.drawable.bl_mosteiro_de_lorvao);
        regionImages.add(R.drawable.tras_castelo_braganca);
        currentImage = findViewById(R.id.region_image);

        try {
            nextRegion();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        playButton = findViewById(R.id.play_from_select);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegionSelect.this, GameOngoing.class);
                intent.putExtra("regionIDGame", counter);
                startActivity(intent);
            }
        });

        quickmatch = findViewById(R.id.quickmatch);
        quickmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegionSelect.this, GameOngoing.class);
                intent.putExtra("regionIDGame", 99);
                startActivity(intent);
            }
        });

        discover = findViewById(R.id.discoverButton);
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegionSelect.this, RegionDetails.class);
                intent.putExtra("regionID", counter);
                startActivity(intent);
            }
        });

        nextRegion = findViewById(R.id.nextRegion);
        nextRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter == 10)
                    counter = 0;
                try {
                    nextRegion();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        previousRegion = findViewById(R.id.previousRegion);
        previousRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                if(counter < 0)
                    counter = 9;
                try {
                    nextRegion();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegionSelect.this, MainMenu.class);
                finish();
                startActivity(intent);
            }
        });
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

    private void nextRegion() throws JSONException {
        obj = jArray.getJSONObject(counter);
        int a = 0;
        try {
            currentTitle.setText(obj.getString("Name"));
            a = Local.getRegionScore(""+ counter, getApplicationContext());
            currentPercentage.setText("" + a + "%");
            currentImage.setImageResource((regionImages.get(counter)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(a >= 85)
            layout.setBackgroundResource(R.drawable.regions_shape_boxgreen);
        else
            layout.setBackgroundResource(R.drawable.regions_shape_box);

    }
}