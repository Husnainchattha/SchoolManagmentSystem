package adminpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hussnain.authinticationfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;
import model.CurrentUser;

public class AdminActivity extends AppCompatActivity {
    public TextView adminRecord;
    public TextView studentRecord;
    public TextView leaveRequest;
    public TextView attendanceSheet;
    FirebaseAuth mAuth;
    FirebaseStorage mfirebasestorage;
 private FirebaseFirestore mFireStore;
    private TextView userName,userEmail,userPassword,userType;
    private CircleImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bindLayout();

        mAuth=FirebaseAuth.getInstance();
        mfirebasestorage=FirebaseStorage.getInstance();

        Intent intent=getIntent();
        CurrentUser user_current =(CurrentUser) intent.getSerializableExtra("key");
        String u_name = user_current.name;
        userName.setText(u_name);
        String u_email=user_current.email;
        userEmail.setText(u_email);
        String u_type=user_current.type;
        userType.setText(u_type);
        String u_password=user_current.password;
        userPassword.setText(u_password);
        String u_image=user_current.image;
        Glide.with(getBaseContext()).load(u_image).into(userImage);

        adminRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, AllAdminRecord.class);
                startActivity(intent);
            }
        });
    }
    public void bindLayout(){
        userName=findViewById(R.id.name_aprofile);
        userEmail=findViewById(R.id.email_aprofile);
        userImage=(CircleImageView)findViewById(R.id.aprofile);
        userPassword=findViewById(R.id.email_apassword);
        userType=findViewById(R.id.email_atype);
        adminRecord=findViewById(R.id.adminrecord);
        studentRecord=findViewById(R.id.studentsRecord);
        leaveRequest=findViewById(R.id.leaverequest);
        attendanceSheet=findViewById(R.id.attendanceSheet);
    }
    public void getAllNotification(){
        FirebaseFirestore notification=mFireStore;

    }
}
