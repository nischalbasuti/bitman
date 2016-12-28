package com.nischalbasuti1.bitman;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/8-bit_wonder.ttf");
        TextView helpText = (TextView) findViewById(R.id.helpText);
        helpText.setTypeface(typeface);
    }
}
