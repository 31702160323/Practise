package cn.mypackage.practise;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.myproject.rainproject.EmotionRainView;
import cn.myproject.rainproject.TickView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TickView tickView = findViewById(R.id.tickView);
        ObjectAnimator.ofInt(tickView,"progress", 0, 360).setDuration(3000).start();

//        EmotionRainView emotionRainView = findViewById(R.id.id_test);
//        ObjectAnimator.ofInt(emotionRainView, "progress", 0, 100).setDuration(3000).start();
    }
}
