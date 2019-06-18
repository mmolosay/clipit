package com.ordolabs.clipit.data.utils.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordolabs.clipit.R;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RecyclerViewAdapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<ClipRaw> clipsList;

    public RecyclerViewAdapter(ArrayList<ClipRaw> clipsList) {
        this.clipsList = clipsList;
    }

    @NonNull
    @Override
    public ClipItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clips_list_item, parent, false);
        return new ClipItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipItemViewHolder holder, int i) {
        ClipRaw clip = clipsList.get(i);

        holder.bodyTextView.setText(clip.body);
    }

    @Override
    public int getItemCount() {
        if (clipsList != null)
            return clipsList.size();
        else
            return 0;
    }
}
