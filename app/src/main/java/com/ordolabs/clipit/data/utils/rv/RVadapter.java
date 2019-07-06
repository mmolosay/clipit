package com.ordolabs.clipit.data.utils.rv;

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
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.ui.clip.ClipActivity;

import java.util.ArrayList;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RVadapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<ClipRaw> clipsList;
    private AppCompatActivity callingActivity;
    private RecyclerView recyclerView;

    public RVadapter(ArrayList<ClipRaw> clipsList, AppCompatActivity callingActivity, RecyclerView rv) {
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
                        .putExtra(callingActivity.getResources().getString(R.string.EXTRA_CLICKED_CLIP_POSITION), itemPos);
                callingActivity.startActivity(i);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final ClipItemViewHolder holder, int i) {
        ClipRaw clip = clipsList.get(i);

        // in cause of only RVadapter has an ability to interact with RV items,
        // all VFX with them should be performed here :(

        holder.bodyTextView.setText(clip.body);
        holder.infoTextView.setText( getDateTimePretty(clip.datetime) );

        if (clip.isViewed == false) {
            holder.newDriverMark.setVisibility(View.VISIBLE);

            // doesn't work from xml file (android:layoutAnimation="...")
            Animation anim = AnimationUtils.loadAnimation(
                    callingActivity,
                    R.anim.rv_item_driver_mark_scale_hide_left_anim
            );

            // hide driver mark at the end of animation
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.newDriverMark.setVisibility(View.INVISIBLE);
                }
            });
            holder.newDriverMark.startAnimation(anim);

            RealmDealer.setClipAsViewed(i);
            clip.isViewed = true;
        }
    }

    private String getDateTimePretty(String datetime) {
        // [ day, month, time, year ] from pattern "d MMM HH:mm yyyy"
        String[] given = datetime.split(" ");
        String[] now = C.current_datetime.split(" ");

        // if given was yesterday (yeah, shitcode. if u know,
        // how to do it better, create an issue at repo, please)

        if (given[3].equals(now[3])) { // if this year
            if (given[1].equals(now[1])) { // if this month
                if (given[0].equals(now[0])) { // if this day
                    // then print just minutes difference
                    int deltaMinutes = Math.abs(
                            (Integer.parseInt(now[2].split(":")[0]) * 60 + Integer.parseInt(now[2].split(":")[1])) -
                            (Integer.parseInt(given[2].split(":")[0]) * 60 + Integer.parseInt(given[2].split(":")[1]))
                    );

                    if (deltaMinutes == 0)
                        return callingActivity.getResources().getString(R.string.prettyDayTime_LessThanMinuteAgo);
                    if (deltaMinutes == 1)
                        return callingActivity.getResources().getString(R.string.prettyDayTime_MinuteAgo);
                    if (deltaMinutes > 1 && deltaMinutes < 10)
                        return deltaMinutes + " " + callingActivity.getResources().getString(R.string.prettyDayTime_MinutesAgo);

                    return callingActivity.getResources().getString(R.string.prettyDayTime_Today) + ", " + given[2];
                }

                // if yesterday
                if (Integer.parseInt(now[0]) - Integer.parseInt(given[0]) == 1 ||
                        (now[0].equals("1") &&
                                (
                                    given[0].equals("28") ||
                                    given[0].equals("30") ||
                                    given[0].equals("31")
                                )
                        )
                ) {
                    return callingActivity.getResources().getString(R.string.prettyDayTime_Yesterday) + ", " + given[2];
                }
            }
            return given[0] + " " + given[1] + ", " + given[2];
        }
        return given[0] + " " + given[1] + ", " + given[2] + ", " + given[3];
    }

    public ClipRaw deleteItem(int position) {
        ClipRaw clip = clipsList.get(position);

        clipsList.remove(position);
        //TODO: add removement from DB
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        return clip;
    }

    public void restoreItem(int position, ClipRaw clip) {
        this.clipsList.add(position, clip);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        if (clipsList != null)
            return clipsList.size();
        else
            return 0;
    }
}
