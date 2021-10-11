package se16.qmul.ac.uk.finalapplication.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import se16.qmul.ac.uk.finalapplication.Model.MyPlaces;


//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearbyPlaces (@Url String url);

}
