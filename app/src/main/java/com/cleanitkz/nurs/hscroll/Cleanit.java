package com.cleanitkz.nurs.hscroll;

import androidx.databinding.ObservableField;

public class Cleanit {
    public ObservableField<String> getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }
    public ObservableField<String> score = new ObservableField<>();
    public Cleanit(String score)
    {
        this.score.set(score);
    }
}
