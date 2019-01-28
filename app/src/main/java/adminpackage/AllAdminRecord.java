package adminpackage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hussnain.authinticationfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.User;

public class AllAdminRecord extends AppCompatActivity {
    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;


    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<User> uploads;
    private DatabaseReference mDatabase;
    private ArrayList<User> adminArrayListlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_record);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        getalldataAdmin();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }
    private void getalldataAdmin(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  progressDialog.dismiss();
                    User admin=new User(postSnapshot.child("name").getValue().toString(),postSnapshot.child("type").getValue().toString()
                            ,postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString(),postSnapshot.child("image").getValue().toString());
                    adminArrayListlist.add(admin);
                }
                ArrayList<User> newarray=adminArrayListlist;
                adapter=new OurAdaptr(AllAdminRecord.this,newarray);
                recyclerView.setAdapter(adapter);
                int i=0;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Ok", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("Users").addValueEventListener(postListener);
    }
}
