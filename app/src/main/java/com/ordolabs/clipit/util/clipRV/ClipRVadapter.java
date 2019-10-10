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
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.ui.clip.ClipActivity;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipRVadapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<ClipRaw> clips;
    private AppCompatActivity caller;
    private RecyclerView rv;

    public ClipRVadapter(ArrayList<ClipRaw> clips, AppCompatActivity caller, RecyclerView rv) {
        this.clips = clips;
        this.caller = caller;
        this.rv = rv;
    }

    public void setClips(ArrayList<ClipRaw> clips) {
        this.clips.clear();
        this.clips.addAll(clips);
        this.notifyDataSetChanged();
    }

    public int getClipsCount() { return clips.size(); }

    @Override @NonNull
    public ClipItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.clip_list_item, parent, false);
        view.setOnClickListener(newOnClickListener(view));

        return new ClipItemViewHolder(view);
    }

    private View.OnClickListener newOnClickListener(final View view) {
        return v -> caller.startActivity(new Intent(
                caller,
                ClipActivity.class
            ).putExtra(
                C.EXTRA_CLIP_ID,
                clips.get(rv.getChildLayoutPosition(view)).id
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ClipItemViewHolder holder, int i) {
        ClipRaw clip = clips.get(i);

        // in cause of only RV-adapter has an ability to interact with RV items,
        // all VFX with them should be performed here

        holder.titleTextView.setText(clip.title != null ? clip.title : "");
        holder.bodyTextView.setText(clip.body);

        toggleClipTitle(holder.titleTextView);

        if (clip.isViewed == false) {
            holder.newDriverMark.setVisibility(View.VISIBLE);

            // doesn't work from xml file (android:layoutAnimation="...")
            Animation anim = AnimationUtils.loadAnimation(
                    caller,
                    R.anim.scale_left_hide
            );

            // hide driver mark at the end of animation
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}

                @Override public void onAnimationEnd(Animation animation) {
                    holder.newDriverMark.setVisibility(View.INVISIBLE);
                }
            });
            holder.newDriverMark.startAnimation(anim);

            RealmDealer.markClipViewed(clip.id);
            clip.isViewed = true;
        }
    }

    ClipRaw deleteItem(int position) {
        ClipRaw clip = clips.get(position);

        clips.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        return clip;
    }

    void restoreItem(final int position, ClipRaw clip) {
        this.clips.add(position, clip);
        notifyItemInserted(position);

        rv.post(() -> rv.smoothScrollToPosition(position));
    }

    private void toggleClipTitle(TextView title) {
        if (title.getText() == null || title.getText().equals("")) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (clips != null)
            return clips.size();
        else
            return 0;
    }
}
