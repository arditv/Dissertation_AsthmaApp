package se16.qmul.ac.uk.finalapplication.Model;

//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
//Used http://pojo.sodhanalibrary.com/ to create the POJO class
public class Geometry
{
    private Viewport viewport;

    private LocationResult location;

    public Viewport getViewport ()
    {
        return viewport;
    }

    public void setViewport (Viewport viewport)
    {
        this.viewport = viewport;
    }

    public LocationResult getLocation ()
    {
        return location;
    }

    public void setLocation (LocationResult location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [viewport = "+viewport+", location = "+location+"]";
    }
}