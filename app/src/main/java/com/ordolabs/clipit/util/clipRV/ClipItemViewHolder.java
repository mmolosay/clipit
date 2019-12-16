package com.ordolabs.clipit.util.clipRV;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.ordolabs.clipit.App;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.util.PrettyDate;

/**
 * Created by ordogod on 18.06.19.
 **/

final class ClipItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    TextView dateView;

    ClipItemViewHolder(View view) {
        super(view);

        textView = view.findViewById(R.id.RVclipsItemText);
        dateView = view.findViewById(R.id.RVclipsItemDate);
    }

    void bindData(Clip clip) {
        // replaces all \n sequences with one
        String trimmed = clip.text.trim().replaceAll("[\n]{2,}", "\n");
        int titleLength = getTitleLength(trimmed);

        SpannableStringBuilder styledText = new SpannableStringBuilder(trimmed);
        styledText.setSpan(
                new StyleSpan(Typeface.BOLD),
                0, titleLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        styledText.setSpan(
                new ForegroundColorSpan(App.getContext().getResources().getColor(R.color.textPrimary)),
                0, titleLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        this.textView.setText(styledText);

        this.dateView.setText(PrettyDate.from(clip.date));
    }

    private int getTitleLength(String clipText) {
        for (int i = 0; i < clipText.length() ; i++)
            if (clipText.charAt(i) == '\n')
                return i;
        return clipText.length();
    }
}
