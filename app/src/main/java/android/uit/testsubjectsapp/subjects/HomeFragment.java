package android.uit.testsubjectsapp.subjects;

import android.os.Bundle;
import android.uit.testsubjectsapp.MainActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.uit.testsubjectsapp.R;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
}