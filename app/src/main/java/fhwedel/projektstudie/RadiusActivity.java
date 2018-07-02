package fhwedel.projektstudie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RadiusActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    private CheckBox checkBox;
    private Button button;

    boolean activated = false;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius);

        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.textView = (TextView) findViewById(R.id.text_radius);
        this.checkBox = (CheckBox) findViewById(R.id.checkBoxRadius);
        this.button = (Button) findViewById(R.id.buttonRadiusSave);

        this.textView.setText("Radius: " + seekBar.getProgress() + " / " + seekBar.getMax());

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                textView.setText("Radius: " + progressValue + " / " + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Updating Radius", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Radius: " + progress + " / " + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Updated Radius", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getRadius() {
        return progress;
    }

    public boolean isActivated() {
        boolean tmp = false;
        if (checkBox.isChecked()) {
            tmp = true;
        }
        return tmp;
    }

    private void buttonSaveClicked(View view){
        this.progress = progress;
        this.activated = isActivated();
    }
}
