package edu.uoc.android.currentweek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String STATE_CURRENT_WEEK = "currentWeek";

    private Button mButtonSend;
    private EditText mEditTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current week number
        savedInstanceState.putString(STATE_CURRENT_WEEK, mEditTextDate.getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mEditTextDate.setText(savedInstanceState.getString(STATE_CURRENT_WEEK));
    }

    private void setViews() {
        mButtonSend = (Button) findViewById(R.id.main_btn_check);
        mButtonSend.setOnClickListener(this);
        mEditTextDate = (EditText) findViewById(R.id.main_ed_week_number);
    }

    private void goToResultActivity() {
        startActivity(ResultActivity.makeIntent(this, getWeekNumber()));
    }

    private int getWeekNumber() {
        return Integer.valueOf(mEditTextDate.getText().toString());
    }

    private boolean hasWeekNumber() {
        return !mEditTextDate.getText().toString().isEmpty();
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSend) {
            if (!hasWeekNumber()) {
                mEditTextDate.setError(getResources().getString(R.string.error_empty_date));
            } else {
                goToResultActivity();
            }
        }
    }
}
