package loginactivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hussnain.authinticationfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Admin;
import model.Student;
import model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView creatAccount;
    private String type;
    private ImageView imageView;
    private Uri imageUri;
   private Spinner spinner;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private ArrayList<Admin> adminArrayListlist=new ArrayList<>();
    private ArrayList<Student> studentArrayList=new ArrayList<>();
   private FirebaseFirestore mfirestor;
    private EditText inputEmail, namefield,inputPassword;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private CircleImageView sign_up_image;
    private FirebaseAuth auth;
    public String s;
    public String u_name;

    private final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageUri = null;
        storageReference = FirebaseStorage.getInstance().getReference().child("Images");
        auth = FirebaseAuth.getInstance();
        mfirestor = FirebaseFirestore.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();

        binLayout();
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Student");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);
        // Upload image for sign up

        sign_up_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageUri==null){
                    Toast.makeText(getApplicationContext(), "Please Select Any Profile Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else
                                    uploadImage();

                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            }
                        });
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE){
            imageUri=data.getData();
            sign_up_image.setImageURI(imageUri);
        }
    }

    private void uploadImage(){
    if (imageUri!=null){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Image Uploading...");
        progressDialog.show();
        final StorageReference ref=storageReference.child("images/"+UUID.randomUUID().toString());
        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downUri=uri;
                        Toast.makeText(getBaseContext(),uri.toString(),  Toast.LENGTH_SHORT).show();
                         s=downUri.toString();
                        User usew=new User();
                        usew.setName(namefield.getText().toString());
                        u_name=namefield.getText().toString();
                        usew.setType(type);
                        usew.setEmail(inputEmail.getText().toString());
                        usew.setPassword(inputPassword.getText().toString());
                        usew.setImage(s);
                        DatabaseReference newRef=mDatabase.child(namefield.getText().toString());
                        newRef.setValue(usew);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this,"Failed"+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Upload "+(int)progress+"%");
            }
        });
    }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    public void binLayout(){
        sign_up_image=(CircleImageView) findViewById(R.id.sign_up_image);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email_signup);
        inputPassword = (EditText) findViewById(R.id.password_signup);
        spinner=findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        namefield=findViewById(R.id.name_signup);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type =spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
        /* storageReference=FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        bindLayout();
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Student");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);



       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           showFileChooser();
           }
       });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // upload();
                if (type.equals("Student")) {
                    boolean check = false;
                    for (Student student : studentArrayList
                            ) {
                        if (student.email.equals(emailEdittext.getText().toString())) {
                            Toast.makeText(SignupActivity.this, "User Already exist", Toast.LENGTH_SHORT).show();
                            check = true;
                        }
                    }
                    if (check == false) {
                        String email = emailEdittext.getText().toString();
                        Student student = new Student(type,emailEdittext.getText().toString(), passwordEdittext.getText().toString());
                        mDatabase.child("Users").child(email).setValue(student);

                        Toast.makeText(SignupActivity.this,"Sign Up Successfully...",Toast.LENGTH_LONG).show();

                    }
                } else {
                    boolean check = false;
                    for (Admin student : adminArrayListlist
                            ) {
                        if (student.email.equals(emailEdittext.getText().toString())) {
                            Toast.makeText(SignupActivity.this, "User Already exist", Toast.LENGTH_SHORT).show();
                            check = true;
                        }
                    }
                    if (check==false) {
                        Admin admin = new Admin(type,emailEdittext.getText().toString(), passwordEdittext.getText().toString());
                        mDatabase.child("Users").child(emailEdittext.getText().toString()).setValue(admin);
                        Toast.makeText(SignupActivity.this,"Sign Up Successfully...",Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }
    public void bindLayout(){
        signupButton=findViewById(R.id.signupButton);
        emailEdittext=findViewById(R.id.emailSignUp);
        passwordEdittext=findViewById(R.id.passwordSignUp);
        creatAccount=findViewById(R.id.createaccountTextView);
        imageView=findViewById(R.id.imageup);
        spinner=findViewById(R.id.spinner);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type =spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && requestCode==RESULT_OK &&data!=null){
            filePath=data.getData();
            try {

                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }
    private void upload(){
        if (filePath!=null){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Uploaded...");
            progressDialog.show();
            StorageReference str=storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis()+ "."+  filePath);
            str.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            Upload upload = new Upload( taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
        }
    }
*/
