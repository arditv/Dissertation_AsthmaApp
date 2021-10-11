package se16.qmul.ac.uk.finalapplication.Model;
//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
//Used http://pojo.sodhanalibrary.com/ to create the POJO class
public class Southwest {
    private String lng;

    private String lat;

    public String getLng ()
    {
        return lng;
    }

    public void setLng (String lng)
    {
        this.lng = lng;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lng = "+lng+", lat = "+lat+"]";
    }
}
