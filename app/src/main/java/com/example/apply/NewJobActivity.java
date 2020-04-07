package com.example.apply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

import static com.example.apply.MainActivity.EXTRA_DATA_DESCRIPTION;
import static com.example.apply.MainActivity.EXTRA_DATA_EMAIL;
import static com.example.apply.MainActivity.EXTRA_DATA_EMPLOYER;
import static com.example.apply.MainActivity.EXTRA_DATA_HOURS;
import static com.example.apply.MainActivity.EXTRA_DATA_ID;
import static com.example.apply.MainActivity.EXTRA_DATA_LOCATION;
import static com.example.apply.MainActivity.EXTRA_DATA_SUMMARY;
import static com.example.apply.MainActivity.EXTRA_DATA_TITLE;

public class NewJobActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY_TITLE = "com.example.android.roomjobssample.REPLY_TITLE";
    public static final String EXTRA_REPLY_EMPLOYER = "com.example.android.roomjobssample.REPLY_EMPLOYER";
    public static final String EXTRA_REPLY_EMAIL = "com.example.android.roomjobssample.REPLY_EMAIL";
    public static final String EXTRA_REPLY_SUMMARY = "com.example.android.roomjobssample.REPLY_SUMMARY";
    public static final String EXTRA_REPLY_DESCRIPTION = "com.example.android.roomjobssample.REPLY_DESCRIPTION";
    public static final String EXTRA_REPLY_LOCATION = "com.example.android.roomjobssample.REPLY_LOCATION";
    public static final String EXTRA_REPLY_HOURS = "com.example.android.roomjobssample.REPLY_HOURS";
    public static final String EXTRA_REPLY_ID = "com.example.android.roomjobssample.REPLY_ID";

    private EditText mEditTitle;
    private EditText mEditEmployer;
    private EditText mEditEmail;
    private EditText mEditSummary;
    private EditText mEditDescription;
    private Spinner mSpinnerLocation;
    private String location;
    private EditText mEditHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mEditTitle = findViewById(R.id.edit_title);
        mEditEmployer = findViewById(R.id.edit_employer);
        mEditEmail = findViewById(R.id.edit_email);
        mEditSummary = findViewById(R.id.edit_summary);
        mEditDescription = findViewById(R.id.edit_description);
        mSpinnerLocation = findViewById(R.id.spinner_location);
        mEditHours = findViewById(R.id.edit_hours);

        int id = -1;

        final Bundle extras = getIntent().getExtras();

        // If content was given to the intent, fill it in to the corresponding views
        // Otherwise fields are empty for blank new job
        if(extras != null){
            mEditTitle.setText(extras.getString(EXTRA_DATA_TITLE, ""));
            mEditEmployer.setText(extras.getString(EXTRA_DATA_EMPLOYER, ""));
            mEditEmail.setText(extras.getString(EXTRA_DATA_EMAIL));
            String[] tempArray = getResources().getStringArray(R.array.locations);
            int locationPos = Arrays.asList(tempArray).indexOf(extras.getString(EXTRA_DATA_LOCATION));
            mSpinnerLocation.setSelection(locationPos);
            mEditHours.setText(String.valueOf(extras.getInt(EXTRA_DATA_HOURS)));
            mEditSummary.setText(extras.getString(EXTRA_DATA_SUMMARY));
            mEditDescription.setText(extras.getString(EXTRA_DATA_DESCRIPTION));
        }

        // Set up spinner for selecting a location
        mSpinnerLocation.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLocation.setAdapter(adapter);

        // Find and create save button functionality
        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mEditTitle.getText()) ||
                        TextUtils.isEmpty(mEditEmployer.getText()) ||
                        TextUtils.isEmpty(mEditEmail.getText()) ||
                        location == null ||
                        TextUtils.isEmpty(mEditHours.getText()) ||
                        TextUtils.isEmpty(mEditSummary.getText()) ||
                        TextUtils.isEmpty(mEditDescription.getText())){
                    // Reply with save failure info
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Send info back to main activity to be created into a new job
                    String title = mEditTitle.getText().toString();
                    String employer = mEditEmployer.getText().toString();
                    String email = mEditEmail.getText().toString();
                    int hours = Integer.parseInt(mEditHours.getText().toString());
                    String summary = mEditSummary.getText().toString();
                    String description = mEditDescription.getText().toString();

                    // Put the new job info in the extras for the reply intent
                    Bundle extrasReply = new Bundle();
                    extrasReply.putString(EXTRA_REPLY_TITLE, title);
                    extrasReply.putString(EXTRA_REPLY_EMPLOYER, employer);
                    extrasReply.putString(EXTRA_REPLY_EMAIL, email);
                    extrasReply.putString(EXTRA_REPLY_SUMMARY, summary);
                    extrasReply.putString(EXTRA_REPLY_DESCRIPTION, description);
                    extrasReply.putString(EXTRA_REPLY_LOCATION, location);
                    extrasReply.putInt(EXTRA_REPLY_HOURS, hours);

                    // If updating a job, define which job to update
                    if(extras != null && extras.containsKey(EXTRA_DATA_ID)){
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if(id != -1){
                            extrasReply.putInt(EXTRA_REPLY_ID, id);
                        }
                    }

                    // Add data to reply intent and indicate success
                    replyIntent.putExtras(extrasReply);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        location = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // code
    }

    // Side step 'up' navigation to tell app that this activity has finished
    // Produces more appropriate animation
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
