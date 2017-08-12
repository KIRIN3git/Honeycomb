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

public class ResultSurfaceView extends SurfaceView implements  Runnable,SurfaceHolder.Callback{

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

	public ResultSurfaceView(Context context){
		super(context);

		// フィールド情報の初期化
		ScoreMng.scoreInit(context);

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

		// ノックアウト判定
		int countCrearNum = 0;
		for( int i = 0; i < PlayerMng.playerNum; i++ ) {

			if( PlayerMng.players.get(i).status != 2 ){
				countCrearNum++;
				win_user_id = PlayerMng.playerColorNo[i];
			}
		}
		if( countCrearNum == 1 ){
			scoreFinishFlg = true;
		}


		while(thread != null){
			try{

				TimeMng.fpsStart();

				canvas = surfaceHolder.lockCanvas();
				canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);



				// 基本六角形
				FieldMng.CountHex(paint, canvas, col_i, row_i);

				if(scoreFinishFlg){
					ScoreMng.PrintWinner(paint, canvas, win_user_id);
				}

				// スコア表示
				ScoreMng.PrintScore(paint, canvas);



				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

				if( !scoreFinishFlg ) {
					// 評価HEX変更
					col_i++;
					if (col_i == FieldMng.HEX_NUM_COL) {
						row_i++;
						col_i = 0;
					}
					if (row_i == FieldMng.HEX_NUM_ROW) {
						col_i = -1;
						row_i = -1;
						scoreFinishFlg = true;

						win_user_id = PlayerMng.checkWinner();
					}
				}

				// fps
				TimeMng.fpsEnd();

			} catch(Exception e){}
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

