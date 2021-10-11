package se16.qmul.ac.uk.finalapplication.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


////Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl)
    {
        if(retrofit== null)
        {

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;

    }


}
