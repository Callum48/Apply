package com.example.apply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import static com.example.apply.MainActivity.EXTRA_DATA_DESCRIPTION;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        Toolbar toolbar = findViewById(R.id.toolbar_view_job);
        setSupportActionBar(toolbar);

        mTitleView = findViewById(R.id.view_title);
        mEmployerView = findViewById(R.id.view_employer);
        mLocationView = findViewById(R.id.view_location);
        mHoursView = findViewById(R.id.view_hours);
        mDescriptionView = findViewById(R.id.view_description);

        final Bundle extras = getIntent().getExtras();

        mTitleView.setText(extras.getString(EXTRA_DATA_TITLE));
        String employer = extras.getString(EXTRA_DATA_EMPLOYER);
        mEmployerView.setText(employer);
        mLocationView.setText(extras.getString(EXTRA_DATA_LOCATION));
        mHoursView.setText(String.valueOf(extras.getInt(EXTRA_DATA_HOURS)));
        mDescriptionView.setText(extras.getString(EXTRA_DATA_DESCRIPTION));
    }
}
