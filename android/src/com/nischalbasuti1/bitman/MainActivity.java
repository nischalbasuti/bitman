package com.nischalbasuti1.bitman;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting views
        TextView title = (TextView) findViewById(R.id.title);
        TextView easy = (TextView) findViewById(R.id.easy);
        TextView medium = (TextView) findViewById(R.id.medium);
        TextView hard = (TextView) findViewById(R.id.hard);
        
        TextView exit = (TextView) findViewById(R.id.exit);
        TextView about = (TextView) findViewById(R.id.about);
        TextView help = (TextView) findViewById(R.id.help);

        //changing font
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/8-bit_wonder.ttf");
        title.setTypeface(typeface);

        easy.setTypeface(typeface);
        medium.setTypeface(typeface);
        hard.setTypeface(typeface);
        
        exit.setTypeface(typeface);
        about.setTypeface(typeface);
        help.setTypeface(typeface);

        //setting onclick listeners
        easy.setOnClickListener(this);
        medium.setOnClickListener(this);
        hard.setOnClickListener(this);
        
        exit.setOnClickListener(this);
        about.setOnClickListener(this);
        help.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.easy:
                Intent intentEasy = new Intent(this,AndroidLauncher.class);
                intentEasy.putExtra("bombs",2);
                startActivity(intentEasy);
                break;
            case R.id.medium:
                Intent intentMedium = new Intent(this,AndroidLauncher.class);
                intentMedium.putExtra("bombs",4);
                startActivity(intentMedium);
                break;
            case R.id.hard:
                Intent intentHard = new Intent(this,AndroidLauncher.class);
                intentHard.putExtra("bombs",6);
                startActivity(intentHard);
                break;
            case R.id.exit:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intentAbout = new Intent(this,AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.help:
                Intent intentHelp = new Intent(this, HelpActivity.class);
                startActivity(intentHelp);
                break;
        }

    }
}
