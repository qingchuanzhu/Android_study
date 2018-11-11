package com.example.qingchuanzhu.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.qingchuanzhu.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.qingchuanzhu.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mShowAnswerButton.setOnClickListener(view -> {
                mAnswerTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
                setAnswerShowResult(true);

                // API level 21 code
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,cx , cy, radius,0 );
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        );
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN,false );
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown );
        setResult(RESULT_OK,intent );
    }
}
