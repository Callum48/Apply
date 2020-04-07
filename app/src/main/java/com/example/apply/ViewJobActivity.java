package com.example.apply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.apply.MainActivity.EXTRA_DATA_DESCRIPTION;
import static com.example.apply.MainActivity.EXTRA_DATA_EMAIL;
import static com.example.apply.MainActivity.EXTRA_DATA_EMPLOYER;
import static com.example.apply.MainActivity.EXTRA_DATA_HOURS;
import static com.example.apply.MainActivity.EXTRA_DATA_LOCATION;
import static com.example.apply.MainActivity.EXTRA_DATA_TITLE;

public class ViewJobActivity extends AppCompatActivity {

    private TextView mTitleView;
    private TextView mEmployerView;
    private TextView mLocationView;
    private TextView mHoursView;
    private TextView mDescriptionView;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        Toolbar toolbar = findViewById(R.id.toolbar_view_job);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        extras = getIntent().getExtras();

        mTitleView = findViewById(R.id.view_title);
        mEmployerView = findViewById(R.id.view_employer);
        mLocationView = findViewById(R.id.view_location);
        mHoursView = findViewById(R.id.view_hours);
        mDescriptionView = findViewById(R.id.view_description);



        mTitleView.setText(extras.getString(EXTRA_DATA_TITLE));
        String employer = extras.getString(EXTRA_DATA_EMPLOYER);
        mEmployerView.setText(employer);
        mLocationView.setText(extras.getString(EXTRA_DATA_LOCATION));
        mHoursView.setText(String.valueOf(extras.getInt(EXTRA_DATA_HOURS)));
        mDescriptionView.setText(extras.getString(EXTRA_DATA_DESCRIPTION));
    }

    // Side step 'up' navigation to tell app that this activity has finished
    // Produces more appropriate animation
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void startEmail(View view) {
        String[] recipient = {extras.getString(EXTRA_DATA_EMAIL)};
        String subject = extras.getString(EXTRA_DATA_TITLE) + " application";
        String message = "Email employer to apply and don't forget to attach your CV!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        // Starts a new email in default email app
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

        //
        //startActivity(Intent.createChooser(intent, "Choose an email app"));
    }
}
