package notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.hussnain.authinticationfirebase.R;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Notifications extends AppCompatActivity {
private FirebaseDatabase mDatabase;
@BindView(R.id.rvnotification)
RecyclerView rvnotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
    }
}
