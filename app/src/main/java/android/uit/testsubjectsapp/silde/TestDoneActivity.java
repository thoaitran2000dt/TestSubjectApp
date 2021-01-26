package android.uit.testsubjectsapp.silde;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.uit.testsubjectsapp.R;
import android.uit.testsubjectsapp.question.Question;
import android.uit.testsubjectsapp.score.ScoreController;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestDoneActivity extends AppCompatActivity {

    ArrayList<Question> arr_QuesBegin = new ArrayList<Question>();
    int numNoAns = 0;
    int numTrue = 0;
    int numFalse = 0;
    int TotalScore = 0;

    ScoreController scoreController;

    TextView tvTrue,tvFalse,tvNotAns,tvTotalScore;
    Button btnAgain,btnSave,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);

        scoreController = new ScoreController(TestDoneActivity.this);

        Intent intent = getIntent();
        arr_QuesBegin = (ArrayList<Question>) intent.getExtras().getSerializable("arr Ques");
        begin();
        checkResult();
        TotalScore = numTrue*10;
        tvNotAns.setText(""+numNoAns);
        tvTrue.setText(""+numTrue);
        tvFalse.setText(""+numFalse);
        tvTotalScore.setText(""+TotalScore);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Notification");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                LayoutInflater inflater = TestDoneActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.alert_dialog_save_score, null);
                builder.setView(view);

                EditText edtName = (EditText) view.findViewById(R.id.edtName);
                EditText edtRoom = (EditText) view.findViewById(R.id.edtRoom);
                TextView tvScoreLate = (TextView) view.findViewById(R.id.tvShowScoreLate);
                int numTotal = numTrue*10;
                tvScoreLate.setText(numTotal+" point");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = edtName.getText().toString();
                        String room = edtRoom.getText().toString();

                        scoreController.insertScore(name, numTotal, room);
                        Toast.makeText(TestDoneActivity.this, "Save score completed!", Toast.LENGTH_LONG).show();
                        finish();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog b = builder.create();
                b.show();
            }
        });

        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                finish();
                Intent intent2 = new Intent(TestDoneActivity.this, ScreenSlideActivity.class);
                intent2.putExtra("arr Ques", arr_QuesBegin);
                intent2.putExtra("test", "no");
                startActivity(intent2);
            }
        });

    }

    public void refresh() {
        for (int i=0; i<arr_QuesBegin.size();i++){
            arr_QuesBegin.get(i).setTraloi("")  ;
        }
    }

    public void begin() {
        tvFalse = (TextView) findViewById(R.id.tvFalse);
        tvFalse.setTextColor(Color.RED);
        tvTrue = (TextView) findViewById(R.id.tvTrue);
        tvTrue.setTextColor(Color.GREEN);
        tvNotAns = (TextView) findViewById(R.id.tvNotAns);
        tvNotAns.setTextColor(Color.BLACK);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalPoint);
        tvTotalScore.setTextColor(Color.BLUE);

        btnAgain = (Button) findViewById(R.id.btnAgain);
        btnSave = (Button) findViewById(R.id.btnSaveScore);
        btnExit = (Button) findViewById(R.id.btnExit);
    }

    public void checkResult() {
        for (int i=0;i<arr_QuesBegin.size();i++)
        {
            if (arr_QuesBegin.get(i).getTraloi().equals("") == true)
            {
                numNoAns++;
            } else if (arr_QuesBegin.get(i).getResult().equals(arr_QuesBegin.get(i).getTraloi()) == true)
            {
                numTrue++;
            } else numFalse++;
        }
    }
}