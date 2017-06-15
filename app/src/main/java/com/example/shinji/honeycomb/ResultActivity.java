package com.example.shinji.honeycomb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shinji on 2017/06/13.
 */

public class ResultActivity extends AppCompatActivity{
	ResultSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		surfaceView = new ResultSurfaceView(this);
		setContentView(surfaceView);
	}
}