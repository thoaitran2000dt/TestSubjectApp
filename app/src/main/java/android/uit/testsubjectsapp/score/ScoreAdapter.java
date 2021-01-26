package android.uit.testsubjectsapp.score;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.uit.testsubjectsapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreAdapter extends CursorAdapter {
    public ScoreAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_score, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvShowScore = (TextView) view.findViewById(R.id.tvShowScore);
        TextView tvNamePlayer = (TextView) view.findViewById(R.id.tvNamePlayer);
        TextView tvRoom = (TextView) view.findViewById(R.id.tvRoom);
        LinearLayout lnlrank = (LinearLayout) view.findViewById(R.id.lnlrank);

        if (cursor.getPosition()%2 ==  0) {
            lnlrank.setBackgroundColor(Color.parseColor("#FFEAB6EA"));
        } else lnlrank.setBackgroundColor(Color.parseColor("#FFCCEFEA"));

        tvNamePlayer.setText(cursor.getString(1));
        tvShowScore.setText(cursor.getInt(2)+"");
        tvRoom.setText(cursor.getString(4));
    }
}
