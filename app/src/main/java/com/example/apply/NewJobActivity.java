package com.example.apply;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewJobActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY_TITLE = "com.example.android.roomjobssample.REPLY_TITLE";
    public static final String EXTRA_REPLY_EMPLOYER = "com.example.android.roomjobssample.REPLY_EMPLOYER";
    public static final String EXTRA_REPLY_EMAIL = "com.example.android.roomjobssample.REPLY_EMAIL";
    public static final String EXTRA_REPLY_SUMMARY = "com.example.android.roomjobssample.REPLY_SUMMARY";
    public static final String EXTRA_REPLY_DESCRIPTION = "com.example.android.roomjobssample.REPLY_DESCRIPTION";
    public static final String EXTRA_REPLY_LOCATION = "com.example.android.roomjobssample.REPLY_LOCATION";
    public static final String EXTRA_REPLY_HOURS = "com.example.android.roomjobssample.REPLY_HOURS";

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

        mEditTitle = findViewById(R.id.edit_title);
        mEditEmployer = findViewById(R.id.edit_employer);
        mEditEmail = findViewById(R.id.edit_email);
        mEditSummary = findViewById(R.id.edit_summary);
        mEditDescription = findViewById(R.id.edit_description);
        mSpinnerLocation = findViewById(R.id.spinner_location);
        mEditHours = findViewById(R.id.edit_hours);

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

                    Bundle extras = new Bundle();
                    extras.putString(EXTRA_REPLY_TITLE, title);
                    extras.putString(EXTRA_REPLY_EMPLOYER, employer);
                    extras.putString(EXTRA_REPLY_EMAIL, email);
                    extras.putString(EXTRA_REPLY_SUMMARY, summary);
                    extras.putString(EXTRA_REPLY_DESCRIPTION, description);
                    extras.putString(EXTRA_REPLY_LOCATION, location);
                    extras.putInt(EXTRA_REPLY_HOURS, hours);
                    replyIntent.putExtras(extras);

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
}
