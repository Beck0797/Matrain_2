package com.beck.matrain;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;

public class Score implements Serializable, Comparable {
    @PropertyName("username")
    String username;
    @PropertyName("level")
    int level;
    @PropertyName("score")
    int score;

    public Score() {

    }

    public Score(String username, int level, int score) {
        this.username = username;
        this.level = level;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int compareTo(Object o) {
        int compareScore = ((Score) o).getScore();

        return compareScore - this.score;
    }
}
