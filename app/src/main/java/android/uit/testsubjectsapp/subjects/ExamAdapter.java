package android.uit.testsubjectsapp.subjects;

import android.content.Context;
import android.uit.testsubjectsapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ExamAdapter extends ArrayAdapter<Exam> {
    public ExamAdapter(@NonNull Context context, ArrayList<Exam> exam) {
        super(context, 0, exam);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvNumExam);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);

        Exam exam = getItem(position);
        if (exam != null)
        {
            tvName.setText(""+ exam.getName());
            imgIcon.setImageResource(R.drawable.subject);
        }

        return convertView;
    }
}
