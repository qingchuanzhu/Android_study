package com.example.qingchuanzhu.geoquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = -1;
    private boolean mIsCheater = false;

    private final Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_australia, true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called", new Exception());
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0) - 1;
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(v -> checkAnswer(true));

        mFalseButton.setOnClickListener(v -> checkAnswer(false));

        mNextButton.setOnClickListener(v -> {
            updateQuestion(true);
            mIsCheater = false;
        });

        mPrevButton.setOnClickListener(v -> updateQuestion(false));

        mQuestionTextView.setOnClickListener(v -> updateQuestion(true));

        mCheatButton.setOnClickListener(v -> {
            Intent intent = CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });

        updateQuestion(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion(boolean next) {
        int incr = next ? 1 : -1;
        mCurrentIndex = (mCurrentIndex + incr);
        if (mCurrentIndex < 0) {
            mCurrentIndex = mQuestionBank.length - 1;
        } else {
            mCurrentIndex %= mQuestionBank.length;
        }

        try {
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e(TAG, "Index out of bounds", ex);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean correct = mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue;
        int messageResId = correct ? R.string.correct_toast : R.string.incorrect_toast;
        if (mIsCheater)
            messageResId = R.string.judgment_toast;
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }
}
