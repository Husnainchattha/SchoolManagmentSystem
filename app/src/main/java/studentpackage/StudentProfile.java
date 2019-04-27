package studentpackage;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hussnain.authinticationfirebase.R;

import de.hdodenhof.circleimageview.CircleImageView;
import model.CurrentUser;

public class StudentProfile extends AppCompatActivity {
private TextView userName,userEmail,userPassword,userType,leaveRequest;
private CircleImageView userImage;
private String stringName,stringEmail,stringPassword,stringImage,stringType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        bindLayout();
        Intent intent=getIntent();
        CurrentUser user_current =(CurrentUser) intent.getSerializableExtra("key");
        stringName=user_current.name;
        stringEmail=user_current.email;
        stringPassword=user_current.password;
        stringType=user_current.type;
        stringImage=user_current.image;
        userName.setText(stringName);
        userEmail.setText(stringEmail);
        userPassword.setText(stringPassword);
        userType.setText(stringType);
        Glide.with(getBaseContext()).load(stringImage).into(userImage);
        leaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(StudentProfile.this,SendLeaveRequest.class);
                intent1.putExtra("key",stringEmail);
                startActivity(intent1);
            }
        });
    }
    private void bindLayout(){
        leaveRequest=findViewById(R.id.leaverequest);
        userName=findViewById(R.id.name_sprofile);
        userEmail=findViewById(R.id.email_sprofile);
        userPassword=findViewById(R.id._spassword);
        userType=findViewById(R.id._stype);
        userImage=findViewById(R.id.sprofile);
    }
}
