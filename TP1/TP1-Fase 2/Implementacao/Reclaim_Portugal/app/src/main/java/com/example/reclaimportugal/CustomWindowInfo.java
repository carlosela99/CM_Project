package com.example.reclaimportugal;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class CustomWindowInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String nome = getIntent().getStringExtra("Title");
        String descricao = getIntent().getStringExtra("Snippet");

        setContentView(R.layout.custom_window_info);

        TextView title = findViewById(R.id.title);
        TextView snipp = findViewById(R.id.snipp);
        ImageView img = findViewById(R.id.picImageView);

        title.setText(nome);
        snipp.setText(descricao);
        img.setImageDrawable(getPictures(nome));


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5),height);

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.RIGHT;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);


    }

    public Drawable getPictures(String locais)
    {
        switch (locais)
        {
            case "Ponte de Lima":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.dou_1,null);
            case "Livraria Lello":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.dou_2,null);
            case "Ponte D.Luis":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.dou_3,null);
            case "Templo de Diana":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alen_1,null);
            case "Capela dos Ossos":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alen_2,null);
            case "Barragem do Alqueva":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alen_3,null);
            case "Praia da Rocha":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alg_1,null);
            case "Igreja de São Lourenço":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alg_igreja_sao_lourenco_almancil,null);
            case "Praça do Rossio":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.lis_1,null);
            case "Terreiro do Paço":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.lis_2,null);
            case "Castelo de S. Jorge":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.lis_3,null);
            case "Floresta Laurissilva":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.ma_1,null);
            case "Museu CR7":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.ma_2,null);
            case "Santana":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.ma_3,null);
            case "Parque Arq. do Vale do Côa":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bi_vale_do_coa,null);
            case "Capela do Bonfim":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bi_capelabonfim,null);
            case "Museu do Pão":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bi_museu_do_pao,null);
            case "Lagoa das Sete Cidades":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.a_lagoa,null);
            case "Montanha do Pico":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.a_pico,null);
            case "Igreja de Santo Cristo":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.a_igreja_de_santo_cristo,null);
            case "Pateira dos Fermentelos":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bl_lagoa_fermentelos,null);
            case "Mosteiro de Lorvão":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bl_mosteiro_de_lorvao,null);
            case "Torre de Almedina":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.bl_torrel_de_almedina,null);
            case "Sé de Faro":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.alg_2,null);
            case "Torre de Belém":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.er_torre_de_belem,null);
            case "Palácio da Pena":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.er_palacio_pena,null);
            case "Castelo de Bragança":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.tras_castelo_braganca,null);
            case "Igreja da Senhora dos Remédios":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.tras_igreja_senhora_remedios,null);
            case "Estação do Pinhão":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.tras_estacao_do_pinhao,null);
            default:
                return ResourcesCompat.getDrawable(getResources(),R.drawable.icon_01,null);

        }
    }


}