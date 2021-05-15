package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                goToMap(id,i);
            }
        });

        back = findViewById(R.id.back_region_selection);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegionDetails.this, RegionSelect.class);
                startActivity(intent);
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
            if(language.equals("en")){
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
    private void goToMap(int idRegion,int idPlace){
        Intent intent = new Intent(com.example.reclaimportugal.RegionDetails.this, MapsActivity.class);
        intent.putExtra("regionIDGame", String.valueOf(idRegion));
        intent.putExtra("placeIDGame", String.valueOf(idPlace));
        startActivity(intent);
    }


    private void openActivityPlay() {
        Intent intent = new Intent(RegionDetails.this, GameOngoing.class);
        intent.putExtra("regionIDGame", id);
        startActivity(intent);
    }

    private void openActivityBack() {
        finish();
    }

    private String getJson(){
        try{
            InputStream is = getAssets().open("regions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            return json;

        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void getSlideImages(){
        slideModels = new ArrayList<>();
        if(id == 0){
            slideModels.add(new SlideModel(R.drawable.dou_1,"Ponte de Lima"));
            slideModels.add(new SlideModel(R.drawable.dou_2,"Livraria Lello"));
            slideModels.add(new SlideModel(R.drawable.dou_3,"Ponte D.Luis"));
        }
        else if(id == 1){
            slideModels.add(new SlideModel(R.drawable.alen_1,"Templo de Diana"));
            slideModels.add(new SlideModel(R.drawable.alen_2,"Capela dos ossos"));
            slideModels.add(new SlideModel(R.drawable.alen_3,"Barragem do Alqueva"));
        }
        else if(id == 2){
            slideModels.add(new SlideModel(R.drawable.alg_1,"Praia da Rocha"));
            slideModels.add(new SlideModel(R.drawable.alg_2,"Sé de Faro"));
            slideModels.add(new SlideModel(R.drawable.alg_igreja_sao_lourenco_almancil,"Igreja de São Lourenço"));
        }
        else if(id == 3){
            slideModels.add(new SlideModel(R.drawable.bi_capelabonfim, "Capela Bonfim"));
            slideModels.add(new SlideModel(R.drawable.bi_museu_do_pao, "Museu do Pão"));
            slideModels.add(new SlideModel(R.drawable.bi_vale_do_coa, "Vale do Coa"));
        }
        else if(id == 4){
            slideModels.add(new SlideModel(R.drawable.lis_1, "Praça do Rossio"));
            slideModels.add(new SlideModel(R.drawable.lis_2, "Terreiro do Paço"));
            slideModels.add(new SlideModel(R.drawable.lis_3, "Castelo S. Jorge"));
        }
        else if(id == 5) {
            slideModels.add(new SlideModel(R.drawable.er_torre_de_belem, "Torre de Belem"));
            slideModels.add(new SlideModel(R.drawable.er_palacio_pena, "Palácio da Pena"));
            slideModels.add(new SlideModel(R.drawable.er_castelo_sao_jorge, "Castelo São Jorge"));
        }else if(id == 6){
            slideModels.add(new SlideModel(R.drawable.a_igreja_de_santo_cristo, "Igreja de Santo Cristo"));
            slideModels.add(new SlideModel(R.drawable.a_lagoa, "Lagoa"));
            slideModels.add(new SlideModel(R.drawable.a_pico, "Pico"));
        }
        else if(id == 7){
            slideModels.add(new SlideModel(R.drawable.ma_1, "Floresta Laurissilva"));
            slideModels.add(new SlideModel(R.drawable.ma_2, "Museu CR7"));
            slideModels.add(new SlideModel(R.drawable.ma_3, "Santana"));
        }else if(id == 8){
            slideModels.add(new SlideModel(R.drawable.bl_lagoa_fermentelos, "Lagoa de Fermentelos"));
            slideModels.add(new SlideModel(R.drawable.bl_torrel_de_almedina, "Torre de Almedina"));
            slideModels.add(new SlideModel(R.drawable.bl_mosteiro_de_lorvao, "Mosteiro do Lorvão"));
        }else if(id == 9){
            slideModels.add(new SlideModel(R.drawable.tras_castelo_braganca, "Castelo de Bragança"));
            slideModels.add(new SlideModel(R.drawable.tras_igreja_senhora_remedios, "Igreja da Senhora dos Remédios"));
            slideModels.add(new SlideModel(R.drawable.tras_estacao_do_pinhao, "Estação do Pinhão"));
        }

    }
}
