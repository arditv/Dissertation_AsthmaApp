package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


//Inspiration from source https://www.youtube.com/watch?v=Vcn4OuV4Ixg


//Passing a string to the doInBackground method therefore String is the first parameter
//Do not use the onProgressUpdate so the second parameter is void
//OnPostExecute we are sending back a result, we are just alternating the TextEdit widgets, declared public static to allow us to do this.
public class fetchWeatherData extends AsyncTask<String,Void,Void> {

    String data;
    Double humidity;
    String description="";
    Double max_weather;
    Double min_weather;
    String city;



    //Computation of the child thread- where the URL call and the parsing of the JSON response is occurring
    // Parameter in the method header allows for many parameters to be passed on.
    @Override
    protected Void doInBackground(String... params) {
        try {

            //Only one parameter passed on so just needed to access index 0(item 1)
            city = params[0];
            //The passed on String which was stored in the city is being added to the URL
            URL url = new URL("https://openweathermap.org/data/2.5/weather?q=" + city + "&appid=");


            //Establish a URL connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null)
            {
                line = bufferedReader.readLine();
                data = data + line;

            }



            //Gets rid of the "null" at the start of the API JSON response
            data = data.substring(4);

            //The way the JSON file output is formatted, it starts as an object
            JSONObject jsonObj = new JSONObject(data);

            //The description is stored in the weather Array
            description = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description");

            //For testing purposes, make sure that the description is being fetched
            //Log.d("descriptionn", description);

            //The main object contains these details.
            max_weather = jsonObj.getJSONObject("main").getDouble("temp_max");
            min_weather = jsonObj.getJSONObject("main").getDouble("temp_min");
            humidity = jsonObj.getJSONObject("main").getDouble("humidity");




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    //Once the task completes its task, it send the result back to the main UI thread
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        WeatherActivity.weather_description.setText("Description: "+this.description);
        WeatherActivity.weather_max.setText("Maximum weather right now: "+this.max_weather.toString());
        WeatherActivity.weather_min.setText("Minimum weather right now: "+this.min_weather.toString());
        WeatherActivity.humidity.setText("Humidity: "+this.humidity.toString());

        if(humidity<40 && min_weather<10)
        {
            WeatherActivity.message_humidity.setText("Low humidity- dry air. This can have an effect on your asthma ");

            WeatherActivity.message_cold.setText("Cold temperature. This can have an effect on your asthma");

        }

        if(min_weather<10)
        {
            WeatherActivity.message_cold.setText("Cold temperature. This can have an effect on your asthma");


        }

        if(humidity>80)
        {
            WeatherActivity.message_humidity.setText("High humidity- moist air. This can have an effect on your asthma ");

        }

        if(max_weather>20)
        {
            WeatherActivity.message_warm.setText("Warm temperature. This can have an effect on your asthma");


        }

        if(humidity>80 && max_weather>20)
        {
            WeatherActivity.message_humidity.setText("High humidity- moist air. This can have an effect on your asthma ");

            WeatherActivity.message_warm.setText("Warm temperature. This can have an effect on your asthma");

        }



    }
}
