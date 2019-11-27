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
import com.ordolabs.clipit.ui.clip.ClipActivity;
import com.ordolabs.clipit.util.PrettyDate;

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
        setTextViews(holder, clips.get(i));
//        setNewDriverMark(holder.newDriverMark, clips.get(i));
    }

    Clip deleteItem(final int position) {
        Clip clip = clips.get(position);

        this.clips.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        return clip;
    }

    void restoreItem(final int position, Clip clip) {
        this.clips.add(position, clip);
        notifyItemInserted(position);

        rv.post(() -> rv.smoothScrollToPosition(position));
    }

    private void setTextViews(ClipItemViewHolder holder, Clip clip) {
//        if (clip.title == null || clip.title.length() == 0) {
//            holder.titleBody.setText(clip.body.replace("\n\n", "\n"));
//
//            holder.titleBody.setVisibility(View.VISIBLE);
//            holder.title.setVisibility(View.GONE);
//            holder.body.setVisibility(View.GONE);
//        }
//        else {
//            holder.title.setText(clip.title);
//            holder.body.setText(clip.body.replace("\n\n", "\n"));
//
//            holder.title.setVisibility(View.VISIBLE);
//            holder.body.setVisibility(View.VISIBLE);
//            holder.titleBody.setVisibility(View.GONE);
//        }
//        holder.dateText.setText(PrettyDate.from(clip.date));
        holder.body.setText(clip.body);
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

    public int getClipsCount() { return clips.size(); }
}
