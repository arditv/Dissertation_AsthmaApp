package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import se16.qmul.ac.uk.finalapplication.Remote.IGoogleAPIService;
import se16.qmul.ac.uk.finalapplication.Remote.RetrofitClient;

//Inspired from soruce https://www.youtube.com/watch?v=wKrYU97Wwg4

public class Common {

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";


    public static IGoogleAPIService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }

}
