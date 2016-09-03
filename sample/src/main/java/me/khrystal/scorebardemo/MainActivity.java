package me.khrystal.scorebardemo;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.khrystal.widget.KScoreBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animator.AnimatorListener {

    private KScoreBar mScoreBar;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreBar = (KScoreBar) findViewById(R.id.custom_scorebar);
        mScoreBar.setScoreAnimatorListener(this);

        mButton = (Button) findViewById(R.id.btn_show);
        mButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        mScoreBar.setProgressWithAnim(16, 30, 50, true, 1000);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(MainActivity.this, "anim end", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
