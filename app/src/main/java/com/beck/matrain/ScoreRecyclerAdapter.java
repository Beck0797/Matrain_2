package com.beck.matrain;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreRecyclerAdapter extends RecyclerView.Adapter<ScoreRecyclerAdapter.ViewHolder> {

    private static final String TAG = "bos";
    public List<Score> scoreList;

    public ScoreRecyclerAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }


    @NonNull
    @Override
    public ScoreRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_score_view, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreRecyclerAdapter.ViewHolder holder, int position) {

        String username = scoreList.get(position).getUsername();
        int score = scoreList.get(position).getScore();
        int level = scoreList.get(position).getLevel();
        Log.i(TAG, "onBindViewHolder: " + level);


//        Log.i(TAG, "onBindViewHolder: "+scoreList.get(position).toString());


        holder.setViewData(username, score, position + 1, level);

    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView usernameTextView;
        TextView levelTextView;
        TextView scoreTextView;
        TextView numberView;
        String levelString;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setViewData(String username, int score, int number, int level) {
            usernameTextView = mView.findViewById(R.id.usernameTextRanking);
            levelTextView = mView.findViewById(R.id.levelTextRaning);
            scoreTextView = mView.findViewById(R.id.scoreTextRanking);
            numberView = mView.findViewById(R.id.numberView);

            numberView.setText(number + "");
            usernameTextView.setText(username);
            switch (level) {

                case 1: {
                    levelString = mView.getContext().getString(R.string.easy_text);
                    break;
                }
                case 2: {
                    levelString = mView.getContext().getString(R.string.medium_text);
                    break;
                }

                case 3: {
                    levelString = mView.getContext().getString(R.string.hard_text);

                    break;
                }
                default: {
                    levelString = "Unknown";
                    break;
                }

            }

            levelTextView.setText(levelString);
            scoreTextView.setText(score + "");


        }
    }
}
