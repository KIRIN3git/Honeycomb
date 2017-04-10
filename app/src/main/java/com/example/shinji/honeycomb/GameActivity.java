package com.example.shinji.honeycomb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity{
	BaseSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		surfaceView = new BaseSurfaceView(this);
		setContentView(surfaceView);
	}
}
