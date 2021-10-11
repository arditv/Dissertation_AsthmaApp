package se16.qmul.ac.uk.finalapplication.Model;
//Inspired from https://www.youtube.com/watch?v=wKrYU97Wwg4
//Used http://pojo.sodhanalibrary.com/ to create the POJO class
public class Opening_hours {
    private String open_now;

    public String getOpen_now ()
    {
        return open_now;
    }

    public void setOpen_now (String open_now)
    {
        this.open_now = open_now;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [open_now = "+open_now+"]";
    }
}
