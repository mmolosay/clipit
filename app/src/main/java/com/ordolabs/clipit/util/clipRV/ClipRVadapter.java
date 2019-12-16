package com.ordolabs.clipit.util.clipRV;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.ui.edit.EditActivity;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipRVadapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<Clip> clips;
    private AppCompatActivity caller;
    private RecyclerView rv;

    public ClipRVadapter(ArrayList<Clip> clips, AppCompatActivity caller, RecyclerView rv) {
        this.clips = clips;
        this.caller = caller;
        this.rv = rv;
    }

    @Override @NonNull
    public ClipItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.clip_card_item, parent, false);
        view.setOnClickListener(makeClipOnClickListener(view));

        return new ClipItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClipItemViewHolder holder, int i) {
        holder.bindData(clips.get(i));
        //setNewDriverMark(holder.newDriverMark, clips.get(i));
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

        rv.post(() -> rv.smoothScrollToPosition(position));
    }

    @Override
    public int getItemCount() {
        if (clips != null) return clips.size();
        return 0;
    }

    public void setClips(ArrayList<Clip> clips) {
        this.clips.clear();
        this.clips.addAll(clips);
        notifyDataSetChanged();
    }

    public int getClipsCount() {
        return clips.size();
    }

    private View.OnClickListener makeClipOnClickListener(final View view) {
        return v -> caller.startActivity(new Intent(
                        caller,
                        EditActivity.class
                ).putExtra(
                C.EXTRA_CLIP_ID,
                clips.get(rv.getChildLayoutPosition(view)).id
                )
        );
    }

    private void setNewDriverMark(View mark, Clip clip) {
        if (clip.isViewed == false) {
            mark.setVisibility(View.VISIBLE);

            Animation anim = AnimationUtils.loadAnimation(caller, R.anim.scale_left_hide);

            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}

                @Override public void onAnimationEnd(Animation animation) {
                    mark.setVisibility(View.INVISIBLE);
                }
            });

            mark.startAnimation(anim);

            RealmDealer.markClipViewed(clip.id);
            clip.isViewed = true;
        }
    }
}
