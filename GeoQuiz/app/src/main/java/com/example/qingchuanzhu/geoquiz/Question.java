package com.example.qingchuanzhu.geoquiz;

class Question {
    private final int mTextResId;
    private final boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    Question(int textResId, boolean answerTrue) {
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }
}
