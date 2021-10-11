package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

//Inspiration from source https://www.youtube.com/watch?v=Vcn4OuV4Ixg

    Button btn_searchweather;
    public static TextView weather_description;
    public static TextView weather_max;
    public static TextView weather_min;
    public static TextView humidity;
    public static TextView weather_heading, message_cold, message_humidity, message_warm;
    EditText city_search;




    //THIS IS THE UI THREAD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weather_heading = (TextView) findViewById(R.id.weather_heading);
        message_cold = (TextView) findViewById(R.id.message_cold);
        message_warm = (TextView) findViewById(R.id.message_warm);
        message_humidity = (TextView) findViewById(R.id.message_humidity);
        city_search = (EditText) findViewById(R.id.city_search);
        btn_searchweather = (Button) findViewById(R.id.btn_searchweather);
        weather_description = (TextView) findViewById(R.id.weather_data);
        weather_max = (TextView) findViewById(R.id.weather_max);
        weather_min = (TextView) findViewById(R.id.weather_min);
        humidity = (TextView) findViewById(R.id.humidity);




        btn_searchweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = city_search.getText().toString();
                //Log.i("CityChange", city);

                //Calls the background thread (etchWeatherData)
                fetchWeatherData process = new fetchWeatherData();
                //passes the city the user typed to the background process
                process.execute(city);

                //The indicators are set to contain blank text as the city that is searched
                // may not have the relevant details displayed by a previous search
                message_humidity.setText("");
                message_cold.setText("");
                message_warm.setText("");


            }
        });




    }
}
