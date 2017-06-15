package com.example.shinji.honeycomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Locale;

/**
 * Created by shinji on 2017/06/08.
 */

public class TimeMng{
	// カウントダウンテキストサイズ
	static float COUNTDONW_TEXT_SIZE_DP = 60.0f;
	static float COUNTDONW_TEXT_SIZE_PX;
	// リミットテキストサイズ
	static float LIMIT_TEXT_SIZE_DP = 40.0f;
	static float LIMIT_TEXT_SIZE_PX;

	// カウントダウン秒
	static int countDownS = 3;
//	static int countDownS = 1;
	static long countDownMS = countDownS * 1000;
	//バトル時間秒
	static int battleS = 60;
	//static int battleS = 10;
	static long battleMS = battleS * 1000;
	// カウントダウン開始時間保存
	static long startCountDownMS;
	// 戦闘開始時間保存
	static long startBattleMS = 0;
	// 現在時間
	static long CurrentTimeMillis;
	// 前回時間
	static long BeforeTimeMillis = startCountDownMS;

	static boolean countDownFlg = false;
	static boolean battleFlg = false;
	static boolean gameOverFlg = false;

	// fps
	static long run_start_time = 0, run_end_time = 0;
	static long FPS_DEFO = 180;
	static long fps = FPS_DEFO;
	static long fpsMsec = 1000/ fps;
	static String printText = "";

	public static void timeInit(Context context){
		// dp→px変換
		float density = context.getResources().getDisplayMetrics().density;
		COUNTDONW_TEXT_SIZE_PX = CommonMng.PxToDp(COUNTDONW_TEXT_SIZE_DP,density);
		LIMIT_TEXT_SIZE_PX = CommonMng.PxToDp(LIMIT_TEXT_SIZE_DP,density);


		countDownFlg = true;
		startCountDownMS = System.currentTimeMillis();

	}

	public static void drawCountDown(Paint paint, Canvas canvas){

		//カウントダウン開始からのミリ秒
		long StartMillis = System.currentTimeMillis() - startCountDownMS;

		paint.reset();
		paint.setTextSize(COUNTDONW_TEXT_SIZE_PX);
		paint.setColor(Color.RED);

		Log.w( "AAAAAlllleew", "aaa1 " + String.valueOf(countDownMS - StartMillis));
		Log.w( "AAAAAlllleew", "aaa2 " + String.valueOf(battleFlg));

		if( countDownMS - StartMillis > 0 ){
			printText = String.valueOf( ( (countDownMS - StartMillis) / 1000 ) + 1 );
		}
		else if( countDownMS - StartMillis > -500 ){
			printText = "START";
		}
		else{
			countDownFlg = false;
			battleFlg = true;
			startBattleMS = System.currentTimeMillis();
		}
		if( countDownFlg ){
			// Canvas 中心点
			float center_x = canvas.getWidth() / 2;
			float center_y = canvas.getHeight() / 2;
			canvas.drawText(printText, center_x - paint.measureText(printText) / 2, center_y - ((paint.descent() + paint.ascent()) / 2), paint);
		}
	}

	public static void drawLimitTime(Paint paint, Canvas canvas){
		boolean timeOverFlg = false;

		long ss = ( ( battleMS - (System.currentTimeMillis() - startBattleMS) ) / 1000 ) + 1;
		long ms = ( battleMS - (System.currentTimeMillis() - startBattleMS) ) - ( ss * 1000 ) + 1000;

		if( ( battleMS - (System.currentTimeMillis() - startBattleMS) ) < 0 ) timeOverFlg = true;

		paint.reset();
		paint.setTextSize(LIMIT_TEXT_SIZE_PX);
		paint.setColor(Color.RED);
		if( !timeOverFlg ) canvas.drawText(String.format(Locale.JAPAN, "%02d", ss), 0, canvas.getHeight(), paint);
		else{
//			canvas.drawText("STOP", 0, canvas.getHeight(), paint);
			printText = "試合終了";
			// Canvas 中心点
			float center_x = canvas.getWidth() / 2;
			float center_y = canvas.getHeight() / 2;
			canvas.drawText(printText, center_x - paint.measureText(printText) / 2, center_y - ((paint.descent() + paint.ascent()) / 2), paint);

			// 試合終了
			battleFlg = false;
			gameOverFlg = true;
		}
	}

	public static void setFps(long fps){
		fpsMsec = 1000/ fps;
	}
	public static void fpsStart(){
		// FPSのためにwhileの起動時間保存
		run_start_time = System.currentTimeMillis();
	}

	// 処理が速い場合は若干のスリープ
	public static void fpsEnd(){
		run_end_time = System.currentTimeMillis();
//                Log.w( "fps", String.valueOf( 1000 / (run_end_time - run_start_time) ) );
		if(run_end_time - run_start_time < fpsMsec ){ // 1000 / 60 = 16.6666
			try {
				Thread.sleep(fpsMsec - (run_end_time - run_start_time));
			} catch (InterruptedException e) {
			}
		}
		setFps( FPS_DEFO );
	}
}
