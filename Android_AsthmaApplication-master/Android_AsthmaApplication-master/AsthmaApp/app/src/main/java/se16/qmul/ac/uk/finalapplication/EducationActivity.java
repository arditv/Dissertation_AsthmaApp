package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EducationActivity extends AppCompatActivity {

    Button animation_pump, animation_pf;
    TextView education_heading, ed_diary_heading, ed_diary_text, ed_symptom_heading, ed_symptom_text, ed_weather_heading, ed_weather_text;
    TextView ed_holiday_text, ed_holiday_heading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        animation_pf = (Button) findViewById(R.id.animation_pf);
        animation_pump = (Button) findViewById(R.id.animation_pump);
        education_heading = (TextView) findViewById(R.id.education_heading);
        ed_holiday_heading = (TextView) findViewById(R.id.ed_holiday_heading);
        ed_holiday_text = (TextView) findViewById(R.id.ed_holiday_text);
        ed_diary_heading = (TextView) findViewById(R.id.ed_diary_heading);
        ed_diary_text = (TextView) findViewById(R.id.ed_diary_text);
        ed_weather_heading = (TextView) findViewById(R.id.ed_weather_heading);
        ed_weather_text = (TextView) findViewById(R.id.ed_weather_text);
        ed_symptom_heading = ed_weather_heading = (TextView) findViewById(R.id.ed_symptom_heading);
        ed_symptom_text = ed_weather_heading = (TextView) findViewById(R.id.ed_symptoms_text);

        //on the click of this button, will go to the startPumpAnimation() method
        animation_pump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPumpAnimation();

            }
        });

        //on the click of this button, will go to the startPfAnimation() method
        animation_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPfAnimation();

            }
        });

    }


    //Inspiration from source https://www.youtube.com/watch?v=bgIUdb-7Rqo
    //This will then open the AnimationPFActivity
    private void startPfAnimation() {
        Intent intent = new Intent(this, AnimationPFActivity.class);
        startActivity(intent);
    }

    //Inspiration from source https://www.youtube.com/watch?v=bgIUdb-7Rqo
    //This will then open the PumpAnimationActivity
    public void startPumpAnimation() {
        Intent intent = new Intent(this, PumpAnimationActivity.class);
        startActivity(intent);
    }



}
