package com.example.shinji.honeycomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Locale;

/**
 * Created by shinji on 2017/04/06.
 */

public class ScoreSurfaceView extends SurfaceView implements  Runnable,SurfaceHolder.Callback{

	// 投げて取って下の範囲が囲まれているか？
	// 爆発

	// スクリーンの大きさ(px)
	int screen_width, screen_height;

	// 背景RGB
	final static int BACK_R = 200 ;
	final static int BACK_G = 200 ;
	final static int BACK_B = 200 ;

	//
	boolean scoreCountFlg = true;
	boolean scoreFinishFlg = false;
	int win_user_id;

	int col_i = 0,row_i = 0;

	SurfaceHolder surfaceHolder;
	Thread thread;

	public ScoreSurfaceView(Context context){
		super(context);

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

//		hex_color_num = new int[SQUARE_NUM][SQUARE_NUM];

	}

	@Override public void run() {

		// キャンバスを設定
		Canvas canvas;

		// ペイントを設定
		Paint paint = new Paint();
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.argb(255, BACK_R, BACK_G, BACK_B));

		while(thread != null){
			try{

				TimeMng.fpsStart();

				canvas = surfaceHolder.lockCanvas();
				canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);

				if(!scoreFinishFlg){
					// 基本六角形
					FieldMng.CountHex(paint, canvas, col_i, row_i);
				}
				else{
					paint.reset();
					paint.setTextSize(150);
					paint.setColor(Color.RED);
					String printText = String.format("WINNER PLAYER%d",win_user_id);
					float center_x = canvas.getWidth() / 2;
					float center_y = canvas.getHeight() / 2;
					canvas.drawText(printText, center_x - paint.measureText(printText) / 2, center_y - ((paint.descent() + paint.ascent()) / 2), paint);
				}

				// スコア表示
				PrintScore(paint, canvas);



				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

				// 評価HEX変更
				col_i++;
				if(col_i == FieldMng.HEX_NUM_COL){
					row_i++;
					col_i=0;
				}
				if(row_i == FieldMng.HEX_NUM_ROW){
					col_i = -1;
					row_i = -1;
					scoreFinishFlg = true;

					win_user_id = PlayerMng.checkWinner();
				}

				// fps
				TimeMng.fpsEnd();

			} catch(Exception e){}
		}
	}

	public static void PrintScore(Paint paint,Canvas canvas){
		paint.reset();
		paint.setTextSize(100);
		paint.setColor(Color.GREEN);
		for( int user_i = 0; user_i < PlayerMng.playerNum; user_i++ ){
			canvas.drawText(String.format(Locale.JAPAN, "PLAYER%d score %d",user_i+1,PlayerMng.players.get(user_i).score ), 0, canvas.getHeight() - ( (PlayerMng.playerNum - user_i - 1) * 100 ) - 5, paint);
			if((canvas.getHeight() - ( (PlayerMng.playerNum - user_i - 1) * 100 ) - 5) != 2135 && (canvas.getHeight() - ( (PlayerMng.playerNum - user_i - 1) * 100 ) - 5) != 2235 ) Log.w( "AAAAA", "itiiiii " + String.format("%d",canvas.getHeight() - ( (PlayerMng.playerNum - user_i - 1) * 100 ) - 5));
		}
	}


	// 変更時に呼び出される
	@Override public void surfaceChanged( SurfaceHolder holder, int format, int width, int height) {
		screen_width = width;
		screen_height = height;
	}
	// 作成時に読みだされる
	// この時点で描画準備はできていて、SurfaceHoderのインスタンスを返却する
	@Override public void surfaceCreated(SurfaceHolder holder) {
		thread = new Thread(this);
		thread.start();
	}
	// 破棄時に呼び出される
	@Override public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

}

