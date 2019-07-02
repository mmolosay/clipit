package com.ordolabs.clipit.data.utils.rv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.ui.clip.ClipActivity;

import java.util.ArrayList;

import static com.ordolabs.clipit.ClipItApplication.getAppContext;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RecyclerViewAdapter extends RecyclerView.Adapter<ClipItemViewHolder> {

    private ArrayList<ClipRaw> clipsList;

    public RecyclerViewAdapter(ArrayList<ClipRaw> clipsList) {
        this.clipsList = clipsList;
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.clips_list_item,
                        parent,
                        false
                );
        v.setOnClickListener(makeOnClickListener(i));
        return new ClipItemViewHolder(v);
    }

    private View.OnClickListener makeOnClickListener(final int number) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ClipActivity.getStartingIntent(null);
                i.putExtra("EXTRA_CLICKED_CLIP_ID", number);
                ClipItApplication.getAppContext().startActivity(i);
            }
        };
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
