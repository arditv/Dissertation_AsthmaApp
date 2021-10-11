package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    // Inspiration from source https://www.youtube.com/watch?v=Vcn4OuV4Ixg

    TextView txt_title, txt_comment ;
    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

       txt_comment = (TextView)itemView.findViewById(R.id.txt_content);
       txt_title = (TextView)itemView.findViewById(R.id.txt_title);
    }
}
