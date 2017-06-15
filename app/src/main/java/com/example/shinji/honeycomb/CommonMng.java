package com.example.shinji.honeycomb;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by shinji on 2017/06/15.
 */

public class CommonMng{


	public static float PxToDp(float px,float density){
		float dp = px * density + 0.5f;

		return dp;
	}
}
