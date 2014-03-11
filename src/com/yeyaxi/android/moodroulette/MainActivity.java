package com.yeyaxi.android.moodroulette;

import android.R.integer;
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
 * 
 * @author Yaxi Ye
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
			R.drawable.images1, //1 sad
			R.drawable.images2, //2 happy
			R.drawable.images3, //3 excited
			R.drawable.images4, //4 angry
			R.drawable.images5, //5 relaxing
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
//					Log.d(TAG, "Selected: " + selected);

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
				// This one is updated while hovering the wheel
				if (index == 0) {
					Log.d(TAG, "Hovering selected: " + String.valueOf(icons.length - 1));
				} else {
					int i = index - 1;
					Log.d(TAG, "Hovering selected: " + i);
				}
				Log.d(TAG, "Currently Selected (on 12 o'clock): " + index);
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
}
