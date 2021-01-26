package android.uit.testsubjectsapp.subjects;

import android.content.Intent;
import android.os.Bundle;
import android.uit.testsubjectsapp.MainActivity;
import android.uit.testsubjectsapp.silde.ScreenSlideActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.uit.testsubjectsapp.R;

import java.util.ArrayList;

public class CodingFragment extends Fragment {

    ExamAdapter examAdapter;
    GridView gvExam;
    ArrayList<Exam> arr_exam = new ArrayList<>();

    public CodingFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tin học");
        View root = inflater.inflate(R.layout.fragment_civic_education, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gvExam = (GridView) getActivity().findViewById(R.id.gvExam);
        arr_exam.add(new Exam("Đề số 1"));
        arr_exam.add(new Exam("Đề số 2"));
        arr_exam.add(new Exam("Đề số 3"));
        arr_exam.add(new Exam("Đề số 4"));
        arr_exam.add(new Exam("Đề số 5"));
        arr_exam.add(new Exam("Đề số 6"));

        examAdapter = new ExamAdapter(getActivity(), arr_exam);
        gvExam.setAdapter(examAdapter);
        gvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ScreenSlideActivity.class);
                intent.putExtra("num_exam",i+1);
                intent.putExtra("subject","TH");
                intent.putExtra("test","yes");
                startActivity(intent);
            }
        });


    }
}