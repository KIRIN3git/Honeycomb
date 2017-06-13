package com.example.shinji.honeycomb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by shinji on 2017/06/13.
 */

public class ScoreActivity extends AppCompatActivity{
	ScoreSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		surfaceView = new ScoreSurfaceView(this);
		setContentView(surfaceView);
	}
}