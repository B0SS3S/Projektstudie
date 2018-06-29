package fhwedel.projektstudie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RadiusActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius);

        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.radius = (TextView) findViewById(R.id.text_radius);

        this.radius.setText("Radius: "+ seekBar.getProgress() + " / " +seekBar.getMax());

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                radius.setText("Radius: "+ progressValue + " / " +seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Updating Radius", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                radius.setText("Radius: "+ progress + " / " +seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Updated Radius", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getRadius(){
        return Integer.parseInt(radius.toString());
    }
}
