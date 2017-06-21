package edu.uoc.android.currentweek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.uoc.android.currentweek.utils.DateUtils;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_WEEK = "weekNumber";

    private TextView mTextViewResult;
    private Button mButtonTryAgain;

    private MediaPlayer mediaPlayer;

    /**
     * Method to send data to ResultActivity throwing any view
     *
     * @param context: previous activity
     * @param weekNumber: week number of the year
     *
     * @return intent to be used by startActivity
     */
    public static Intent makeIntent(Context context, int weekNumber) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_WEEK, weekNumber);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get the week number send by previous activity
        int weekNumber = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Default value set as 0
            weekNumber = extras.getInt(EXTRA_WEEK, 0);
        } else {
            // End activity if no data has been send.
            finish();
        }
        setViews();
        checkWeekNumber(weekNumber);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stopping the sound if user leaves the screen and it is playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.release();
        }
    }

    private void setViews() {
        mTextViewResult = (TextView) findViewById(R.id.result_tv_message);
        mButtonTryAgain = (Button) findViewById(R.id.result_btn_try_again);
        mButtonTryAgain.setOnClickListener(this);
    }

    private void checkWeekNumber(int weekNumber) {
        DateUtils dateUtils = new DateUtils();
        boolean isTheResultCorrect = dateUtils.isTheCurrentWeekNumber(weekNumber);
        setTextDependingTheResult(isTheResultCorrect);
        setButtonTextDependingTheResult(isTheResultCorrect);
        setAudioDependingTheResult(isTheResultCorrect);
    }

    private void setTextDependingTheResult(boolean isTheResultCorrect) {
        if (isTheResultCorrect) {
            mTextViewResult.setText(getResources().getString(R.string.right_result));
            mTextViewResult.setTextColor(Color.GREEN);
        } else {
            mTextViewResult.setText(getResources().getString(R.string.fail_result));
            mTextViewResult.setTextColor(Color.RED);
        }
    }

    private void setButtonTextDependingTheResult(boolean isTheResultCorrect) {
        if (isTheResultCorrect) {
            mButtonTryAgain.setText(getResources().getString(R.string.button_start_again));
        } else {
            mButtonTryAgain.setText(getResources().getString(R.string.button_try_again));
        }
    }

    private void setAudioDependingTheResult(boolean isTheResultCorrect) {
        mediaPlayer = MediaPlayer.create(this, isTheResultCorrect ? R.raw.audio_yes_message : R.raw.audio_no_message);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonTryAgain) {
            // Finish this activity to start again the game.
            finish();
        }
    }
}
