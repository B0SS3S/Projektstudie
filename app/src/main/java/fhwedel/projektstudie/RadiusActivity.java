package fhwedel.projektstudie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RadiusActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    private Button button;

    int progress = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius);

        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.textView = (TextView) findViewById(R.id.text_radius);
        this.button = (Button) findViewById(R.id.buttonRadiusSave);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RadiusActivity.this);
        int tmp = preferences.getInt("Radius", 5);
        if (tmp != progress){
            seekBar.setProgress(tmp);
        }

        this.textView.setText("Radius: " + seekBar.getProgress() + " / " + seekBar.getMax());

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                textView.setText("Radius: " + progress + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Radius: " + progress + " / " + seekBar.getMax());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RadiusActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("Radius", progress);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Saved Radius: " + progress, Toast.LENGTH_SHORT).show();
            }

        });
    }


    public int getRadius() {

        return progress;
    }

}
