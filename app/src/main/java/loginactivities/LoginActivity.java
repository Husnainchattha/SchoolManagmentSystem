package loginactivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.hussnain.authinticationfirebase.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adminpackage.AdminActivity;
import model.CurrentUser;
import model.Student;
import model.User;
import studentpackage.StudentProfile;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ArrayList<User> userArrayList =new ArrayList<>();
    private ArrayList<Student> studentArrayList=new ArrayList<>();

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String type;
    private String muser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindLayout();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        //getAllStudent();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(LoginActivity.this, AdminActivity.class);
//                startActivity(intent);

                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getAllUserData();


                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.


                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            inputPassword.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        boolean check = false;
                                        User user = new User();
                                        for (User student : userArrayList
                                        ) {
                                            if (student.email.equals(inputEmail.getText().toString())) {
                                                user = student;
                                                check = true;
                                            }

                                        }
                                        if (check) {
                                            if (inputPassword.getText().toString().equals(user.password)) {
                                             String   u_type=user.getType();
                                                String u_name= user.getName();
                                                String u_id = user.getEmail();
                                                String u_password=user.getPassword();
                                                String img_url = user.getImage();
                                                CurrentUser currentUser=new CurrentUser(u_name,u_type,u_id,u_password,img_url);
                                                if (u_type.equals("Admin")){
                                                    inputEmail.setText("");
                                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                               intent.putExtra("key",currentUser);
                                                startActivity(intent);
                                                }
                                                else {
                                                    inputPassword.setText("");
                                                    inputEmail.setText("");
                                                    Intent intent=new Intent(LoginActivity.this, StudentProfile.class);
                                                    intent.putExtra("key",currentUser);
                                                    startActivity(intent);
                                                }

                                            } else {
                                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "User is not exist", Toast.LENGTH_SHORT).show();

                                        }

                                    }


                                }
                            });
                }

            } });
    }

   public void getAllUserData() {
       DatabaseReference reference = mDatabase;

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   User user = new User(postSnapshot.child("name").getValue().toString(), postSnapshot.child("type").getValue().toString()
                           , postSnapshot.child("email").getValue().toString(), postSnapshot.child("password").getValue().toString(), postSnapshot.child("image").getValue().toString());
                   userArrayList.add(user);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }

public void bindLayout(){
    inputEmail = (EditText) findViewById(R.id.loginemail);
    inputPassword = (EditText) findViewById(R.id.loginpassword);
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    btnSignup = (Button) findViewById(R.id.btn_signup);
    btnLogin = (Button) findViewById(R.id.btn_login);
    btnReset = (Button) findViewById(R.id.btn_reset_password);
}


















        /* getalldataAdmin();
        getalldataStudent();
        createAccount=findViewById(R.id.createaccountTextView);
        type=getIntent().getStringExtra("data");
        email=findViewById(R.id.emailLogin);
        password=findViewById(R.id.passwordLogin);
        login=findViewById(R.id.login);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

ek mint..                if (type.equals("student")){
                    boolean check = false;
                    Student currentAdmin=new Student();
                    for (Student student : studentArrayList
                            ) {
                        if (student.email.equals(email.getText().toString())) {
                            currentAdmin=student;
                            check = true;
                        }

                    }
                    if (check){
                        if (password.getText().toString().equals(currentAdmin.password)){

                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "User is not exist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    boolean check = false;
                    Admin currentAdmin=new Admin();
                    for (Admin student : userArrayListlist
                            ) {
                        if (student.email.equals(email.getText().toString())) {
                            currentAdmin=student;
                            check = true;
                        }
                    }
                    if (check){
                        if (password.getText().toString().equals(currentAdmin.password)){

                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "User is not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void getalldataAdmin(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Admin admin=new Admin(postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString());
                    userArrayListlist.add(admin);
                }
                ArrayList<Admin> newarray=userArrayListlist;
                int i=0;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Ok", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("AdminArray").addValueEventListener(postListener);
    }
    private void getalldataStudent(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student=new Student(postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString());
                    studentArrayList.add(student);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Ok", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("AdminArray").addValueEventListener(postListener);
    }






       /* type=getIntent().getStringExtra("data");
        getalldataAdmin();
        getalldataStudent();
       bindLayout();
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
               // intent.putExtra("data",type);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Student student : studentArrayList
                        )
                if (student.type.equals("Student")){
                    boolean check = false;
                    Student currentStudent=new Student();
                     {
                        if (student.email.equals(email.getText().toString())) {
                            currentStudent=student;
                            check = true;
                        }

                    }
                    if (check){
                        if (password.getText().toString().equals(currentStudent.password)){
                         Toast.makeText(LoginActivity.this,"you Are Succeeded",Toast.LENGTH_LONG).show();
                         Intent intent=new Intent(LoginActivity.this,StudentProfile.class);
                         startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "User is not exist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    boolean check = false;
                    Admin currentAdmin=new Admin();
                    for (Admin admin : userArrayListlist
                            ) {
                        if (admin.email.equals(email.getText().toString())) {
                            currentAdmin=admin;
                            check = true;
                        }
                    }
                    if (check){
                        if (password.getText().toString().equals(currentAdmin.password)){
                            Intent intent=new Intent(LoginActivity.this,AdminActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "User is not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void getalldataAdmin(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Admin admin=new Admin(postSnapshot.child("type").getValue().toString(),postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString());
                    //  Admin admin=new Admin(postSnapshot.child("type").getValue().toString(),postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString());
                    userArrayListlist.add(admin);
                }
                ArrayList<Admin> newarray=userArrayListlist;
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
    private void getalldataStudent(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student=new Student(postSnapshot.child("type").getValue().toString(),postSnapshot.child("email").getValue().toString(),postSnapshot.child("password").getValue().toString());
                    studentArrayList.add(student);
                }
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
    public void bindLayout(){

        email=findViewById(R.id.emailLogin);
        password=findViewById(R.id.passwordLogin);
        login=findViewById(R.id.login);
        createAccount=findViewById(R.id.createaccountTextView);

    }*/







}
