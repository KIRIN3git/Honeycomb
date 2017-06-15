package com.example.shinji.honeycomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Locale;

/**
 * Created by shinji on 2017/06/15.
 */

public class ScoreMng{

	// 勝者表示
	static float WINNER_MSG_DP = 40.0f;
	static float WINNER_MSG_PX;
	// 点数表示
	static float SCORE_MSG_DP = 20.0f;
	static float SCORE_MSG_PX;

	public static void scoreInit(Context context){

		// dp→px変換
		float density = context.getResources().getDisplayMetrics().density;
		WINNER_MSG_PX = CommonMng.PxToDp(WINNER_MSG_DP,density);
		SCORE_MSG_PX = CommonMng.PxToDp(SCORE_MSG_DP,density);

	}

	public static void PrintScore(Paint paint, Canvas canvas){
		paint.reset();
		paint.setTextSize(SCORE_MSG_PX);
		paint.setColor(Color.BLUE);
		for( int user_i = 0; user_i < PlayerMng.playerNum; user_i++ ){
			canvas.drawText(String.format(Locale.JAPAN, "PLAYER%d score %d",user_i+1,PlayerMng.players.get(user_i).score ), 0, canvas.getHeight() - ( (PlayerMng.playerNum - user_i - 1) * SCORE_MSG_PX ) - 5, paint);
		}
	}

	public static void PrintWinner(Paint paint, Canvas canvas, int win_user_id){
		paint.reset();
		paint.setTextSize(WINNER_MSG_PX);
		paint.setColor(Color.RED);
		String printText = String.format("WINNER PLAYER%d",win_user_id);
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;
		canvas.drawText(printText, center_x - paint.measureText(printText) / 2, center_y - ((paint.descent() + paint.ascent()) / 2), paint);
	}
}