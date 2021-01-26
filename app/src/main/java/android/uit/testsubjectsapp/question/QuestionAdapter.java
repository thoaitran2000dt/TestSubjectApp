package android.uit.testsubjectsapp.question;

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

public class QuestionAdapter extends CursorAdapter {
    public QuestionAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_question, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvQues = (TextView) view.findViewById(R.id.tvSearchQuestion);
        LinearLayout lightQues = (LinearLayout) view.findViewById(R.id.lightQuestion);

        if (cursor.getPosition()%2 ==  0) {
            lightQues.setBackgroundColor(Color.parseColor("#FFB6DFB8"));
        } else lightQues.setBackgroundColor(Color.parseColor("#FFC5CAF3"));

        tvQues.setText(cursor.getString(1));
    }
}

