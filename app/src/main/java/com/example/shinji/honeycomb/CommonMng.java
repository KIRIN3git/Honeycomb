package com.example.shinji.honeycomb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by shinji on 2017/06/15.
 */

public class CommonMng{

	static String printText = "";
	static float printX,printY;
	static float mirrorX,mirrorY;

	public static float PxToDp(float px,float density){
		float dp = px * density + 0.5f;

		return dp;
	}

	// テキストビューの反転表示
	// 後々canvasサイズはこちらで保持？
	public static void MirrorDrowText(Canvas canvas,Paint paint,float x,float y,String text){
		canvas.drawText(text, x - paint.measureText(text) / 2, y - ((paint.descent() + paint.ascent()) / 2), paint);

		// 反転表示
		mirrorX = canvas.getWidth() - x;
		mirrorY = canvas.getHeight() - y;

		canvas.rotate(180,mirrorX,mirrorY);
		canvas.drawText(text, mirrorX - paint.measureText(text) / 2, mirrorY - ((paint.descent() + paint.ascent()) / 2), paint);
		canvas.rotate(180,mirrorX,mirrorY);
	}
}
