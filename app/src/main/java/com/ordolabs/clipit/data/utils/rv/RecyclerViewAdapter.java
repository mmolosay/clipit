package com.ordolabs.clipit.data.utils.rv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.clip.ClipActivity;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RecyclerViewAdapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<ClipRaw> clipsList;
    private AppCompatActivity callingActivity;
    private RecyclerView recyclerView;

    public RecyclerViewAdapter(ArrayList<ClipRaw> clipsList, AppCompatActivity callingActivity, RecyclerView rv) {
        this.clipsList = clipsList;
        this.callingActivity = callingActivity;
        this.recyclerView = rv;
    }

    public void setClipsList(ArrayList<ClipRaw> clipsList) {
        this.clipsList.clear();
        this.clipsList.addAll(clipsList);
        this.notifyDataSetChanged();
    }

    public ArrayList<ClipRaw> getClipsList() {
        return clipsList;
    }

    @NonNull
    @Override
    public ClipItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.clips_list_item,
                        parent,
                        false
                );
        view.setOnClickListener(newOnClickListener(view));
        return new ClipItemViewHolder(view);
    }

    private View.OnClickListener newOnClickListener(final View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPos = recyclerView.getChildLayoutPosition(view);
                Intent i = ClipActivity
                        .getStartingIntent(callingActivity)
                        .putExtra( callingActivity.getResources().getString(R.string.EXTRA_CLICKED_CLIP_POSITION), itemPos );
                callingActivity.startActivity(i);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ClipItemViewHolder holder, int i) {
        ClipRaw clip = clipsList.get(i);

        holder.bodyTextView.setText(clip.body);
        holder.infoTextView.setText(clip.datetime);
    }

    @Override
    public int getItemCount() {
        if (clipsList != null)
            return clipsList.size();
        else
            return 0;
    }
}
