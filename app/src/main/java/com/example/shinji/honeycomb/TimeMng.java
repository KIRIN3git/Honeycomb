package com.example.shinji.honeycomb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by shinji on 2017/06/08.
 */

public class TimeMng{
	// カウントダウンテキストサイズ
	static int COUNTDONW_TEXT_SIZE;
	// リミットテキストサイズ
	static int LIMIT_TEXT_SIZE;

	// カウントダウン秒
	static int countDownS = 3;
	static long countDownMS = countDownS * 1000;
	//戦闘時間秒
	static int fightS = 60;
	static long fightMS = fightS * 1000;
	// カウントダウン開始時間保存
	static long startCountDownMS;
	// 戦闘開始時間保存
	static long startFightMS = 0;
	// 現在時間
	static long CurrentTimeMillis;
	// 前回時間
	static long BeforeTimeMillis = startCountDownMS;

	static boolean countDownFlg = false;
	static boolean fightFlg = false;
	static boolean gameOverFlg = false;

	// FPS
	static long run_start_time = 0, run_end_time = 0;
	static final long FPS = 180;
	static final long FPS_MSEC = 1000/FPS;

	public static void timeInit(){
		// 端末に合わせた各サイズの調整
		if( MainActivity.real.x >= 1080 ) {
			// カウントダウンテキストサイズ
			COUNTDONW_TEXT_SIZE = 200;
			//リミット時間テキストサイズ
			LIMIT_TEXT_SIZE = 200;
		}
		else if( MainActivity.real.x >= 720 ){

			// カウントダウンテキストサイズ
			COUNTDONW_TEXT_SIZE = 50;
			//リミット時間テキストサイズ
			LIMIT_TEXT_SIZE = 50;
		}
		else{
			// カウントダウンテキストサイズ
			COUNTDONW_TEXT_SIZE = 50;
			//リミット時間テキストサイズ
			LIMIT_TEXT_SIZE = 50;
		}
		countDownFlg = true;
		startCountDownMS = System.currentTimeMillis();

	}

	public static void drawCountDown(Paint paint, Canvas canvas){

		String CountText = "";

		//カウントダウン開始からのミリ秒
		long StartMillis = System.currentTimeMillis() - startCountDownMS;

		paint.reset();
		paint.setTextSize(COUNTDONW_TEXT_SIZE);
		paint.setColor(Color.RED);

		Log.w( "AAAAAlllleew", "aaa1 " + String.valueOf(countDownMS - StartMillis));
		Log.w( "AAAAAlllleew", "aaa2 " + String.valueOf(fightFlg));

		if( countDownMS - StartMillis > 0 ){
			CountText = String.valueOf( ( (countDownMS - StartMillis) / 1000 ) + 1 );
		}
		else if( countDownMS - StartMillis > -500 ){
			CountText = "START";
		}
		else{
			countDownFlg = false;
			fightFlg = true;
			startFightMS = System.currentTimeMillis();
		}
		if( countDownFlg ){
			// Canvas 中心点
			float center_x = canvas.getWidth() / 2;
			float center_y = canvas.getHeight() / 2;
			canvas.drawText(CountText, center_x - paint.measureText(CountText) / 2, center_y - ((paint.descent() + paint.ascent()) / 2), paint);
		}
	}

	public static void drawLimitTime(Paint paint, Canvas canvas){
		long ss = ( fightMS - (System.currentTimeMillis() - startFightMS) ) / 1000;
		long ms = ( fightMS - (System.currentTimeMillis() - startFightMS) ) - ss * 1000;

		paint.reset();
		paint.setTextSize(LIMIT_TEXT_SIZE);
		paint.setColor(Color.RED);
		if( ss >= 0 ) canvas.drawText(String.format("%02d", ss), 0, canvas.getHeight(), paint);
		else{
			canvas.drawText("STOP", 0, canvas.getHeight(), paint);
			// 試合終了
			fightFlg = false;
			gameOverFlg = true;
		}
	}

	public static void fpsStart(){
		// FPSのためにwhileの起動時間保存
		run_start_time = System.currentTimeMillis();
	}

	// 処理が速い場合は若干のスリープ
	public static void fpsEnd(){
		run_end_time = System.currentTimeMillis();
//                Log.w( "FPS", String.valueOf( 1000 / (run_end_time - run_start_time) ) );
		if(run_end_time - run_start_time < FPS_MSEC){ // 1000 / 60 = 16.6666
			try {
				Thread.sleep(FPS_MSEC - (run_end_time - run_start_time));
			} catch (InterruptedException e) {
			}
		}
	}
}
