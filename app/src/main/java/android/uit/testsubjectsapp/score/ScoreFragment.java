package android.uit.testsubjectsapp.score;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.uit.testsubjectsapp.MainActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.uit.testsubjectsapp.R;
import android.widget.ListView;


public class ScoreFragment extends Fragment {

    ListView lvScore;
    ScoreController scoreController;
    ScoreAdapter scoreAdapter;



    public ScoreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bảng điểm");
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scoreController = new ScoreController(getActivity());
        lvScore = (ListView) getActivity().findViewById(R.id.lvScore);
        Cursor cursor = scoreController.getScore();
        scoreAdapter = new ScoreAdapter(getActivity(), cursor, true);
        lvScore.setAdapter(scoreAdapter);
    }
}