package com.example.qingchuanzhu.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = -1;

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
        setContentView(R.layout.activity_quiz);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(v -> checkAnswer(true));

        mFalseButton.setOnClickListener(v -> checkAnswer(false));

        mNextButton.setOnClickListener(v -> updateQuestion(true));

        mPrevButton.setOnClickListener(v -> updateQuestion(false));

        mQuestionTextView.setOnClickListener(v -> updateQuestion(true));

        updateQuestion(true);
    }

    private void updateQuestion(boolean next) {
        int incr = next ? 1 : -1;
        mCurrentIndex = (mCurrentIndex + incr);
        if (mCurrentIndex < 0) {
            mCurrentIndex = mQuestionBank.length - 1;
        } else {
            mCurrentIndex %= mQuestionBank.length;
        }

        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean correct = mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue;
        int messageResId = correct ? R.string.correct_toast : R.string.incorrect_toast;
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }
}
