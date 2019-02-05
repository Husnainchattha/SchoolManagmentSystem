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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import attendance.AttendanceSheetActivity;
import loginactivities.MainActivity;
public class SendLeaveRequest extends AppCompatActivity  {
    private Button sendnotificationbtn;
    private EditText notificationMessage;
    String uemail,umessage;
    FirebaseDatabase mfireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_leave_request);
        notificationMessage=findViewById(R.id.send_message);
       uemail=getIntent().getStringExtra("key");
       mfireStore=FirebaseDatabase.getInstance();
       sendnotificationbtn=(Button) findViewById(R.id.button);

    sendnotificationbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            umessage=notificationMessage.getText().toString();

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
        notificationmessage.put("From",uemail);
        notificationmessage.put("Message",umessage);
        mfireStore.getReference().child("notification")
                .child(uemail.replace("@","").replace(".","")).child(String.valueOf(System.currentTimeMillis()))
                .setValue(notificationmessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SendLeaveRequest.this,"Send notification successfully",Toast.LENGTH_LONG).show();
            }
        });


}
}
