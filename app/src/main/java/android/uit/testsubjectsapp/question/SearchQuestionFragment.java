package android.uit.testsubjectsapp.question;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.uit.testsubjectsapp.MainActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.uit.testsubjectsapp.R;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class SearchQuestionFragment extends Fragment {

    ListView lvQuestion;
    QuestionController questionController;
    QuestionAdapter adapter;
    EditText edtSearch;
    ImageButton imgSubject;
    String subject ="";

    public SearchQuestionFragment() {
        // Required empty public constructor
    }

    public void begin() {
        lvQuestion = (ListView) getActivity().findViewById(R.id.lvQuestion);
        edtSearch = (EditText) getActivity().findViewById(R.id.edtSearch);
        imgSubject = (ImageButton) getActivity().findViewById(R.id.imgSubject);
        questionController = new QuestionController(getActivity());
        listCursor(questionController.getSearchQuestion(subject, edtSearch.getText().toString()));
    }

    public void listCursor(Cursor cursor) {
        adapter = new QuestionAdapter(getActivity(), cursor, true);
        lvQuestion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Danh sách câu hỏi");
        return inflater.inflate(R.layout.fragment_search_question, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        begin();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listCursor(questionController.getSearchQuestion(subject, edtSearch.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }


    public void showMenu (View v){
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.quesAll:
                        subject="";
                        listCursor(questionController.getSearchQuestionBySubject(subject));
                        break;
                    case R.id.qGDCD:
                        subject="GDCD";
                        listCursor(questionController.getSearchQuestionBySubject(subject));
                        break;
                    case R.id.qTH:
                        subject="TH";
                        listCursor(questionController.getSearchQuestionBySubject(subject));
                        break;
                    case R.id.qLS:
                        subject="LS";
                        listCursor(questionController.getSearchQuestionBySubject(subject));
                        break;
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_question);
        setForceShowIcon(popupMenu);
        popupMenu.show();
    }

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}