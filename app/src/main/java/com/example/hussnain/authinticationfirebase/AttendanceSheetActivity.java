package com.example.hussnain.authinticationfirebase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import model.Admin;
import model.DataModel;
import model.DateModel;

public class AttendanceSheetActivity extends AppCompatActivity {
    public DatePicker datePicker;

    private TextView mDateDisplay;
    private Button mPickDate, upload;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String nameofuser;
  private RadioGroup radioGroup;
  private RadioButton radioButtonabsent,radioButtonpresent,radioButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    static final int DATE_DIALOG_ID = 0;
         private Context context;
    private Button submitbutton;
    private ArrayList<DateModel> datearray = new ArrayList<>();
    protected DatabaseReference mDatabase;
    private ArrayList<Admin> adminArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        bindLayout();
        this.context=this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getNameOfMember();


           mDateDisplay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   showDialog(DATE_DIALOG_ID);
               }
           });
          upload.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String date=mDateDisplay.getText().toString();
                   DataModel dataModel=new DataModel();

                   DateModel model=new DateModel(date);

                   Toast.makeText(AttendanceSheetActivity.this,date,Toast.LENGTH_LONG).show();
                   mDatabase.child(date).setValue(model);
               }
           });


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
    }
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    updateDisplay();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }
        return null;
    }



    private void bindLayout(){
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        upload=(Button)findViewById(R.id.upload);
        submitbutton = findViewById(R.id.upload);
        recyclerView=findViewById(R.id.recyclerviewAttendance);
        radioGroup=findViewById(R.id.radios);
        radioButtonabsent=findViewById(R.id.absent);
        radioButtonpresent=findViewById(R.id.present);
    }


 /*  public void getNameOfMember(){
        ValueEventListener postiner=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Admin admin=new Admin(snapshot.child("email").getValue().toString());
                    adminArrayList.add(admin);

                }
                ArrayList<Admin> newArray=adminArrayList;
                adapter=new AttendanceAdapter(AttendanceSheetActivity.this,newArray);
                recyclerView.setAdapter(adapter);
                int i=0;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Ok", "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabase.child("Users").addValueEventListener(postiner);
    }*/
    }




