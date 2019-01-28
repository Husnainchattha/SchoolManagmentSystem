package studentpackage;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hussnain.authinticationfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import loginactivities.MainActivity;
public class SendLeaveRequest extends AppCompatActivity  {
    private Button sendnotificationbtn;
    private EditText notificationMessage;
    String u_email,u_message;
    FirebaseFirestore mfireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_leave_request);
        notificationMessage=findViewById(R.id.send_message);
       u_email=getIntent().getStringExtra("key");
       mfireStore=FirebaseFirestore.getInstance();
       sendnotificationbtn=(Button) findViewById(R.id.button);

    sendnotificationbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            u_message=notificationMessage.getText().toString();

            if (TextUtils.isEmpty(notificationMessage.getText().toString())){
                Toast.makeText(SendLeaveRequest.this,"Please enter any message",Toast.LENGTH_SHORT).show();

            }
            else {

                addNotification();

        }
        }
    });
    }
    private void addNotification() {
        final Map<String,Object> notificationmessage=new HashMap<>();
        notificationmessage.put("From",u_email);
        notificationmessage.put("Message",u_message);
        mfireStore.collection("Users"+u_email).add(notificationmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SendLeaveRequest.this,"Send Successfully...",Toast.LENGTH_SHORT).show();
                notificationMessage.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SendLeaveRequest.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

}
}
