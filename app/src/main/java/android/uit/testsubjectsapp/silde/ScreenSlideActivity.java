package android.uit.testsubjectsapp.silde;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.uit.testsubjectsapp.R;
import android.uit.testsubjectsapp.question.Question;
import android.uit.testsubjectsapp.question.QuestionController;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ScreenSlideActivity extends FragmentActivity {

    private static final int NUM_PAGES = 20;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    TextView tvKiemtra, tvTimer, tvXemDiem;
    public int checkAns = 0;

    QuestionController questionController;
    ArrayList<Question> arr_Ques;
    CounterClass timer;
    String subject;
    int num_exam;
    int totalTimer = 10;
    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam", 0);
        test = intent.getStringExtra("test");


        timer = new CounterClass(totalTimer * 60 * 1000, 1000);

        questionController = new QuestionController(this);
        arr_Ques = new ArrayList<Question>();

        if (test.equals("yes") == true) {
            arr_Ques = questionController.getQuestion(num_exam, subject);
        } else {
            arr_Ques = (ArrayList<Question>) intent.getExtras().getSerializable("arr Ques");
        }

        tvKiemtra = (TextView) findViewById(R.id.tvKiemTra);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvXemDiem = (TextView) findViewById(R.id.tvScore);


        tvKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        tvXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent1 = new Intent(ScreenSlideActivity.this, TestDoneActivity.class);
                intent1.putExtra("arr Ques", arr_Ques);
                startActivity(intent1);
            }
        });

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        timer.start();
    }

    public ArrayList<Question> getData() {
        return arr_Ques;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void dialogExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlideActivity.this);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Notification");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ScreenSlidePageFragment.create(position, checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public void checkAnswer() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");

        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(arr_Ques, this);
        GridView gvLsQuestion = (GridView) dialog.findViewById(R.id.gvLsQuestion);
        gvLsQuestion.setAdapter(answerAdapter);

        gvLsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        Button btnCancel, btnFinish;
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnFinish = (Button) dialog.findViewById(R.id.btnFinish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                result();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void result() {
        checkAns = 1;
        if (mPager.getCurrentItem() >= 4) mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        else if (mPager.getCurrentItem() <= 4) mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        tvXemDiem.setVisibility(View.VISIBLE);
        tvKiemtra.setVisibility(View.GONE);
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime);
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");
        }
    }

}