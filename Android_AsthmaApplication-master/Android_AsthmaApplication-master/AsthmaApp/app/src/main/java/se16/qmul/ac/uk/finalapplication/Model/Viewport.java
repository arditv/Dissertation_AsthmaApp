package se16.qmul.ac.uk.finalapplication.Model;
//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
//Used http://pojo.sodhanalibrary.com/ to create the POJO class
public class Viewport {
    private Southwest southwest;

    private Northeast northeast;

    public Southwest getSouthwest ()
    {
        return southwest;
    }

    public void setSouthwest (Southwest southwest)
    {
        this.southwest = southwest;
    }

    public Northeast getNortheast ()
    {
        return northeast;
    }

    public void setNortheast (Northeast northeast)
    {
        this.northeast = northeast;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [southwest = "+southwest+", northeast = "+northeast+"]";
    }
}
