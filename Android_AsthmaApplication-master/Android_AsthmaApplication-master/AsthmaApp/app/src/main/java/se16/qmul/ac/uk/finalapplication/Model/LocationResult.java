package se16.qmul.ac.uk.finalapplication.Model;

//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
//Used http://pojo.sodhanalibrary.com/ to create the POJO class
public class LocationResult
{
    private double lng;

    private double lat;

    public double getLng ()
    {
        return lng;
    }

    public void setLng (double lng)
    {
        this.lng = lng;
    }

    public double getLat ()
    {
        return lat;
    }

    public void setLat (double lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lng = "+lng+", lat = "+lat+"]";
    }
}

