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
            case "Local1":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.diana,null);
            case "Local2":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.galom,null);
            case "Local3":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.belem_towerm,null);
            case "Local4":
                return ResourcesCompat.getDrawable(getResources(),R.drawable.portugalm,null);
            default:
                return ResourcesCompat.getDrawable(getResources(),R.drawable.icon_01,null);

        }
    }


}