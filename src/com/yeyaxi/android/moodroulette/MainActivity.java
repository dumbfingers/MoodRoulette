package com.yeyaxi.android.moodroulette;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;

import com.digitalaria.gama.wheel.Wheel;
import com.digitalaria.gama.wheel.WheelAdapter;
import com.digitalaria.gama.wheel.WheelAdapter.OnItemClickListener;
import com.digitalaria.gama.wheel.WheelAdapter.OnItemSelectionUpdatedListener;
import com.digitalaria.gama.wheel.WheelAdapter.OnWheelRotationListener;

/**
 * <a href="http://www.vrallev.net">About the author</a>
 * 
 * @author Ralf Wondratschek
 * @version 1.0
 *
 */
public class MainActivity extends Activity {

	private static String TAG = MainActivity.class.getSimpleName();
	private Wheel wheel;
	private AbstractWheel scaleRatingWheel;
	private Resources res; 
	private int[] icons = { 
//			R.drawable.audio_wave2_96, //0
//			R.drawable.herald_trumpet_96, //1
//			R.drawable.music_96, //2
//			R.drawable.music_conductor_96, //3
//			R.drawable.piano_96 }; //4
//			R.drawable.images0, //0
			R.drawable.images1, //1
			R.drawable.images2, //2
			R.drawable.images3, //3
			R.drawable.images4, //4
			R.drawable.images5, //5
//			R.drawable.images6, //6
//			R.drawable.images7, //7
	};
	private int selected = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// init the horizontal wheel
		scaleRatingWheel = (AbstractWheel) findViewById(R.id.scalewheel);
		NumericWheelAdapter scaleRatingAdapter = new NumericWheelAdapter(this, 1, 5, "%1d");
		scaleRatingAdapter.setItemResource(R.layout.wheel_text_centred);
		scaleRatingAdapter.setItemTextResource(R.id.text);
		scaleRatingWheel.setViewAdapter(scaleRatingAdapter);
		
		//init the round wheel
		init();

	}


	private void init() {
		res = getApplicationContext().getResources();
		wheel = (Wheel) findViewById(R.id.wheel);

		// set the image what items will be in wheel.
		wheel.setItems(getDrawableFromData(icons)); 

		// set the diameter of Wheel.
		wheel.setWheelDiameter(500); 

		// CCW, 90 is selected
		// CW, 270 is selected
		//wheel.setSelectionAngle(270); Not working properly

		// set the listener
		wheel.setOnWheelRotationListener(new OnWheelRotationListener() {

			@Override
			public void onWheelRotationStart() {

			}

			@Override
			public void onWheelRotationEnd() {

				if (wheel.isRotationFinished() == true) {
					selected = wheel.getSelectedItem(true);
					if (selected == 0) {
						selected = icons.length - 1;
					} else {
						selected = selected - 1;
					}
					Log.d(TAG, "Selected: " + selected);

					Toast.makeText(getApplication(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
				}
			}
		});

		// item on click listener
		wheel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(WheelAdapter<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == icons.length - 1) {
					wheel.setSelectedItem(0);
				} else {
					wheel.setSelectedItem(position + 1);
				}
				//				Log.d(TAG, "Item Clicked: " + position);
			}
		});

		wheel.setOnItemSelectionUpdatedListener(new OnItemSelectionUpdatedListener() {

			@Override
			public void onItemSelectionUpdated(View view, int index) {
				//				Log.d(TAG, "Selected (on 12 o'clock): " + index);
			}
		});
	}

	// This function is a helper for drawable data.
	private Drawable[] getDrawableFromData(int[] data) {
		Drawable[] ret = new Drawable[data.length];
		for (int i = 0; i < data.length; i++) {
			ret[i] = res.getDrawable(data[i]);
		}
		return ret;
	}

	@Override
	public void onResume() {
		super.onResume();



	}

	//	/**
	//	 * Rotate the dialer.
	//	 * 
	//	 * @param degrees The degrees, the dialer should get rotated.
	//	 */
	//	private void rotateDialer(float degrees) {
	//		matrix.postRotate(degrees, dialerWidth / 2, dialerHeight / 2);
	//
	//		dialer.setImageMatrix(matrix);
	//	}
	//
	//	/**
	//	 * @return The angle of the unit circle with the image view's center
	//	 */
	//	private double getAngle(double xTouch, double yTouch) {
	//		double x = xTouch - (dialerWidth / 2d);
	//		double y = dialerHeight - yTouch - (dialerHeight / 2d);
	//
	//		switch (getQuadrant(x, y)) {
	//		case 1:
	//			return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
	//
	//		case 2:
	//		case 3:
	//			return 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	//
	//		case 4:
	//			return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
	//
	//		default:
	//			// ignore, does not happen
	//			return 0;
	//		}
	//	}
	//
	//	/**
	//	 * @return The selected quadrant.
	//	 */
	//	private static int getQuadrant(double x, double y) {
	//		if (x >= 0) {
	//			return y >= 0 ? 1 : 4;
	//		} else {
	//			return y >= 0 ? 2 : 3;
	//		}
	//	}
	//
	//	/**
	//	 * Simple implementation of an {@link OnTouchListener} for registering the dialer's touch events. 
	//	 */
	//	private class MyOnTouchListener implements OnTouchListener {
	//
	//		private double startAngle;
	//
	//		@Override
	//		public boolean onTouch(View v, MotionEvent event) {
	//
	//			switch (event.getAction()) {
	//
	//			case MotionEvent.ACTION_DOWN:
	//
	//				// reset the touched quadrants
	//				for (int i = 0; i < quadrantTouched.length; i++) {
	//					quadrantTouched[i] = false;
	//				}
	//
	//				allowRotating = false;
	//
	//				startAngle = getAngle(event.getX(), event.getY());
	//				break;
	//
	//			case MotionEvent.ACTION_MOVE:
	//				double currentAngle = getAngle(event.getX(), event.getY());
	//				rotateDialer((float) (startAngle - currentAngle));
	//				startAngle = currentAngle;
	//				break;
	//
	//			case MotionEvent.ACTION_UP:
	//				allowRotating = true;
	//				break;
	//			}
	//
	//			// set the touched quadrant to true
	//			quadrantTouched[getQuadrant(event.getX() - (dialerWidth / 2), dialerHeight - event.getY() - (dialerHeight / 2))] = true;
	//
	//			detector.onTouchEvent(event);
	//
	//			return true;
	//		}
	//	}
	//
	//	/**
	//	 * Simple implementation of a {@link SimpleOnGestureListener} for detecting a fling event. 
	//	 */
	//	private class MyGestureDetector extends SimpleOnGestureListener {
	//		@Override
	//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	//
	//			// get the quadrant of the start and the end of the fling
	//			int q1 = getQuadrant(e1.getX() - (dialerWidth / 2), dialerHeight - e1.getY() - (dialerHeight / 2));
	//			int q2 = getQuadrant(e2.getX() - (dialerWidth / 2), dialerHeight - e2.getY() - (dialerHeight / 2));
	//
	//			// the inversed rotations
	//			if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) < Math.abs(velocityY))
	//					|| (q1 == 3 && q2 == 3)
	//					|| (q1 == 1 && q2 == 3)
	//					|| (q1 == 4 && q2 == 4 && Math.abs(velocityX) > Math.abs(velocityY))
	//					|| ((q1 == 2 && q2 == 3) || (q1 == 3 && q2 == 2))
	//					|| ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3))
	//					|| (q1 == 2 && q2 == 4 && quadrantTouched[3])
	//					|| (q1 == 4 && q2 == 2 && quadrantTouched[3])) {
	//
	//				dialer.post(new FlingRunnable(-1 * (velocityX + velocityY)));
	//			} else {
	//				// the normal rotation
	//				dialer.post(new FlingRunnable(velocityX + velocityY));
	//			}
	//
	//			return true;
	//		}
	//	}
	//
	//	/**
	//	 * A {@link Runnable} for animating the the dialer's fling.
	//	 */
	//	private class FlingRunnable implements Runnable {
	//
	//		private float velocity;
	//
	//		public FlingRunnable(float velocity) {
	//			this.velocity = velocity;
	//		}
	//
	//		@Override
	//		public void run() {
	//			if (Math.abs(velocity) > 5 && allowRotating) {
	//				rotateDialer(velocity / 75);
	//				velocity /= 1.0666F;
	//
	//				// post this instance again
	//				dialer.post(this);
	//			}
	//		}
	//	}
}
