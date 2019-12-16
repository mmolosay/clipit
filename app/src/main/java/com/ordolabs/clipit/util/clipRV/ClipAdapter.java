package com.ordolabs.clipit.util.clipRV;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ordolabs.clipit.R;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipAdapter extends RecyclerView.Adapter<ClipHolder> {

    private ArrayList<Clip> clips;

    public ClipAdapter(ArrayList<Clip> clips) {
        this.clips = clips;
    }

    @Override
    @NonNull
    public ClipHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ClipHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.clip_card_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ClipHolder holder, int i) {
        holder.bindData(clips.get(i));
    }

    Clip removeClip(final int position) {
        Clip clip = clips.get(position);

        this.clips.remove(position);
        notifyItemRemoved(position);

        return clip;
    }

    void restoreClip(final int position, Clip clip) {
        this.clips.add(position, clip);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        if (clips != null)
            return clips.size();
        return 0;
    }

    public void setClips(ArrayList<Clip> clips) {
        this.clips.clear();
        this.clips.addAll(clips);
        notifyDataSetChanged();
    }
}
