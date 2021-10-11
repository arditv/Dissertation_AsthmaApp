package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//Inspiration from source https://www.youtube.com/watch?v=Vcn4OuV4Ixg
public class StoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder> adapter;

    EditText edt_title, edt_content;
    Button  btn_post;

    TextView asthmatrigger_heading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        asthmatrigger_heading = (TextView) findViewById(R.id.asthmatrigger_heading);

        //User to store their comments
        edt_content = (EditText)findViewById(R.id.edt_content);
        edt_title = (EditText)findViewById(R.id.edt_title);
        btn_post = (Button)findViewById(R.id.btn_post);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ASTHMA_TRIGGERS").child(user_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayComment(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();

            }
        });

        displayComment(user);


    }

    @Override
    protected void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    private void postComment() {

        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = user.getUid();

        //Pass the title, content and the user id.
        Post post = new Post(title,content,user_id);

        databaseReference.push().setValue(post); //Use this to create a unique id for comment

        adapter.notifyDataSetChanged();



    }

    private void displayComment(FirebaseUser user) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("ASTHMA_TRIGGERS").child(user.getUid());

        final Query usernameQuery = databaseReference.orderByChild("userID");


        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(usernameQuery, Post.class).build();

        adapter= new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Post model) {
                holder.txt_title.setText(model.getTitle());
                holder.txt_comment.setText(model.getContent());

            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item, viewGroup, false);
                return new MyRecyclerViewHolder(itemView );
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
