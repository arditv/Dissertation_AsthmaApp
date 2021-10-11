package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Source code inspired from https://www.youtube.com/watch?v=EO-_vwfVi7c&t=1194s throughout this
// this class (MainActivity)

public class MainActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 6006;

    List<AuthUI.IdpConfig> providers;

    Button btn_sign_out, btn_store, btn_activity2, btn_activity3, btn_activity4, btn_activity5, btn_activity6;

    EditText user_peak_flow, user_age;

    TextView username_text, age_text, peakflow_text;


    //This will obtain the user id for the given user and store it in the user_id String.
    DatabaseReference current_user_db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button to sign out
        btn_sign_out = (Button)findViewById(R.id.btn_sign_out );
        //User information to be stored onto the database

        user_age = (EditText)findViewById(R.id.usr_age);

        user_peak_flow = (EditText)findViewById(R.id.usr_peak_flow);
        //user_weight = (EditText)findViewById(R.id.usr_weight);
        btn_store = (Button)findViewById(R.id.btn_store);
        username_text = (TextView)findViewById(R.id.username_text);
        age_text = (TextView)findViewById(R.id.age_text);
        peakflow_text = (TextView)findViewById(R.id.peakflow_text);

        //Inspiration from https://www.youtube.com/watch?v=bgIUdb-7Rqo
        btn_activity2 = (Button)findViewById(R.id.btn_activity2);
        btn_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        //Inspiration from https://www.youtube.com/watch?v=bgIUdb-7Rqo
        btn_activity3 = (Button)findViewById(R.id.btn_activity3);
        btn_activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        //Inspiration from https://www.youtube.com/watch?v=bgIUdb-7Rqo
        btn_activity4 = (Button)findViewById(R.id.btn_activity4);
        btn_activity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        //Inspiration from https://www.youtube.com/watch?v=bgIUdb-7Rqo
        btn_activity5 = (Button)findViewById(R.id.btn_activity5);
        btn_activity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });

        //Inspiration from https://www.youtube.com/watch?v=bgIUdb-7Rqo
        btn_activity6 = (Button)findViewById(R.id.btn_activity6);
        btn_activity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity6();
            }
        });

        //USER INFORMATION FOR DATABASE

        btn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //USER INFORMATION FOR DATABASE
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = user.getUid();

              //DatabaseReference current_user_db;
                current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                //Storing on data inspired from https://www.youtube.com/watch?v=vkf5z1raSyE&t=320s

                String usr_peak_flow = user_peak_flow.getText().toString();
                String usr_age= user_age.getText().toString();
                Map newPost = new HashMap();

                newPost.put("PeakFlow", usr_peak_flow);
                newPost.put("Age", usr_age );
                newPost.put("username", user.getUid());
                newPost.put("Name", user.getDisplayName());


                current_user_db.setValue(newPost);
            }
        });


        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                btn_sign_out.setEnabled(false);

                                //User information to add set to false

                                user_peak_flow.setEnabled(false);
                                user_age.setEnabled(false);
                                btn_store.setEnabled(false);

                                btn_activity2.setEnabled(false);
                                btn_activity3.setEnabled(false);
                                btn_activity4.setEnabled(false);
                                btn_activity5.setEnabled(false);
                                btn_activity6.setEnabled(false);


                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),    //Email builder
                new AuthUI.IdpConfig.GoogleBuilder().build()    //Google builder

        );

        showSignInOptions();

    }

    //onStart() method inspiration from https://firebase.google.com/docs/auth/android/start
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {


            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                //GET USER
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String user_id = user.getUid();
                updateUI(user);

                //show email on toast
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();

                //Set stuff visible
                btn_sign_out.setEnabled(true);


                user_peak_flow.setEnabled(true);
                user_age.setEnabled(true);
                btn_store.setEnabled(true);


                btn_activity2.setEnabled(true);
                btn_activity3.setEnabled(true);
                btn_activity4.setEnabled(true);
                btn_activity5.setEnabled(true);
                btn_activity6.setEnabled(true);

            }

            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                updateUI(null);
            }

        }
    }


    //Inspiration of this method from https://firebase.google.com/docs/auth/android/start
    private void updateUI(FirebaseUser user) {
        if (user!= null)
        {

            // Fetching and query inspiration from https://www.youtube.com/watch?v=Ocg5akI7WF4
            current_user_db= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            final Query usernameQuery = current_user_db.orderByChild("username");
            usernameQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount()>0)
                    {
                        btn_store.setText("Update");
                        user_peak_flow.setText(dataSnapshot.child("PeakFlow").getValue().toString());

                        user_age.setText(dataSnapshot.child("Age").getValue().toString());

                        username_text.setText(dataSnapshot.child("Name").getValue().toString());
                        age_text.setVisibility(View.VISIBLE);
                        peakflow_text.setVisibility(View.VISIBLE);

                    }
                    else {
                        btn_store.setText("Store");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE

        );
    }

// Inspiration for all the openActivity methods from https://www.youtube.com/watch?v=bgIUdb-7Rqo

    //Will open the MapsActivity
    public void openActivity2() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //Will open Education Activity
    public void openActivity3() {
        Intent intent = new Intent(this, EducationActivity.class);
        startActivity(intent);
    }

    //Will open WeatherActivity
    public void openActivity4() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
    //Will open StoryActivity
    public void openActivity5() {
        Intent intent = new Intent(this, StoryActivity.class);
        startActivity(intent);
    }
    //Will open PeakFlowActivity
    public void openActivity6() {
        Intent intent = new Intent(this, PeakFlowActivity.class);
        startActivity(intent);
    }

}
