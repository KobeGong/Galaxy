package com.kobe.viewdev;



import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.Menu;
import android.view.animation.LinearInterpolator;

public class MainActivity extends Activity {

	private RatarView ratarView;
	private ObjectAnimator animator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ratarView = (RatarView) findViewById(R.id.ratarView);
		buildAnimator();
	}

	private void buildAnimator() {
		animator = ObjectAnimator.ofFloat(ratarView, "angle", 0, 360);
		animator.setDuration(300000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatMode(ValueAnimator.RESTART);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animator.start();
		}else{
			animator.cancel();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
