package com.example.shinji.honeycomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by shinji on 2017/04/06.
 */

public class BaseSurfaceView extends SurfaceView implements  Runnable,SurfaceHolder.Callback{

	// 投げて取って下の範囲が囲まれているか？
	// 爆発

	// 四角の縦、横の数
	final static int SQUARE_NUM = 11;

	final static int SQUARE_LENGTH = 100;

	// プライヤーオブジェクト
//	PlayerStatus player1 = new PlayerStatus( 0,-100,1 );
//	PlayerStatus player2 = new PlayerStatus( 0,100,2 );

	// スクリーンの大きさ(px)
	int screen_width, screen_height;

	// Canvas 中心点
	float center_x = 0.0f;
	float center_y = 0.0f;

	// 背景RGB
	final static int BACK_R = 200 ;
	final static int BACK_G = 200 ;
	final static int BACK_B = 200 ;




	SurfaceHolder surfaceHolder;
	Thread thread;

	public BaseSurfaceView(Context context){
		super(context);


		// フィールド情報の初期化
		FieldMng.fieldInit();
		// プレイヤー情報の初期化
		PlayerMng.playerInit();
		// 時間情報の初期化
		TimeMng.timeInit();

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

				// タップ移動比率xyと指示マーカーのxyを取得
				if(TimeMng.fightFlg) PlayerMng.GetMoveXY();

				// 基本六角形
				FieldMng.DrawHex(paint, canvas);

				// プレイヤーの表示
				PlayerMng.DrawPlayer(paint, canvas);

				// カウントダウン中
				if(TimeMng.countDownFlg){
					// 開始カウントダウンの表示
					TimeMng.drawCountDown(paint, canvas);
				}
				// 試合中
				else if(TimeMng.fightFlg){
					// 指示器の表示
					PlayerMng.DrawIndicator(paint, canvas);
					// リミット時間の表示
					TimeMng.drawLimitTime(paint, canvas);
				}

				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

				// FPS
				TimeMng.fpsEnd();

			} catch(Exception e){}
		}
	}

	// タッチイベントを処理するためOverrideする
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// タッチしている数を取得
		int count = event.getPointerCount();
		//タッチアクションの情報を取得
		int action = event.getAction();
		int data_id;
		int pointId;
		float x,y;

		for(int i=0; i<count; i++) {
			// ポインタID
			pointId = event.getPointerId(i);
			// データID
			data_id = event.findPointerIndex(pointId);
			x = event.getX(data_id);
			y = event.getY(data_id);

			if (data_id == -1) continue;
			Log.w( "AAAAAxx1zzz", "PlayerMng.players.get(0).now_touch_x " + PlayerMng.players.get(0).now_touch_x);
			Log.w( "AAAAAxx1zzz", "PlayerMng.players.get(0).start_touch_x " + PlayerMng.players.get(0).start_touch_x);
			Log.w( "AAAAAxx1", "PlayerMng.players.get(0).data_id " + PlayerMng.players.get(0).data_id);
			Log.w( "AAAAAxx1", "MainActivity.real.y " + MainActivity.real.y);
			Log.w( "AAAAAxx1", "y " + y);

			// Player1の情報
			if(data_id == PlayerMng.players.get(0).data_id){
				// タッチしている位置取得
				PlayerMng.players.get(0).now_touch_x = (int)x;
				PlayerMng.players.get(0).now_touch_y = (int)y;
			}
			// 画面上半分の位置をタップ
			else if(data_id == PlayerMng.players.get(1).data_id){
				// タッチしている位置取得
				PlayerMng.players.get(1).now_touch_x = (int)x;
				PlayerMng.players.get(1).now_touch_y = (int)y;
			}
			Log.i("tag2", "DataIndex[" + data_id + "] PointIndex[" + pointId + "] x[" + x + "]");
			Log.i("tag2", "DataIndex[" + data_id + "] PointIndex[" + pointId + "] y[" + y + "]");
		}

		data_id = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		x = event.getX(data_id);
		y = event.getY(data_id);

		switch(action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				Log.w( "aaaAAAAAxx21 DOWN", "data_id " + data_id );
				Log.w( "aaaAAAAAxx21 DOWN", "PlayerMng.players.get(0).data_id " + PlayerMng.players.get(0).data_id );
				Log.w( "aaaAAAAAxx21 DOWN", "PlayerMng.players.get(1).data_id " + PlayerMng.players.get(1).data_id );

				if(MainActivity.real.y / 2 < y && PlayerMng.players.get(0).data_id == -1){
					PlayerMng.players.get(0).start_touch_x = (int)x;
					PlayerMng.players.get(0).start_touch_y = (int)y;
					PlayerMng.players.get(0).now_touch_x = (int)x;
					PlayerMng.players.get(0).now_touch_y = (int)y;
					PlayerMng.players.get(0).touch_flg = true;
					PlayerMng.players.get(0).data_id = data_id;
				}
				else if(MainActivity.real.y / 2 > y && PlayerMng.players.get(1).data_id == -1){
					PlayerMng.players.get(1).start_touch_x = (int)x;
					PlayerMng.players.get(1).start_touch_y = (int)y;
					PlayerMng.players.get(1).now_touch_x = (int)x;
					PlayerMng.players.get(1).now_touch_y = (int)y;
					PlayerMng.players.get(1).touch_flg = true;
					PlayerMng.players.get(1).data_id = data_id;
				}

				Log.i("tag1", "Touch Down" + " count=" + count + ", DataIndex=" + data_id);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.w( "aaaAAAAAxx21 DOWN2", "data_id " + data_id );
				Log.w( "aaaAAAAAxx21 DOWN2", "PlayerMng.players.get(0).data_id " + PlayerMng.players.get(0).data_id );
				Log.w( "aaaAAAAAxx21 DOWN2", "PlayerMng.players.get(1).data_id " + PlayerMng.players.get(1).data_id );

				if(MainActivity.real.y / 2 < y && PlayerMng.players.get(0).data_id == -1){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					PlayerMng.players.get(0).start_touch_x = (int)x;
					PlayerMng.players.get(0).start_touch_y = (int)y;
					PlayerMng.players.get(0).now_touch_x = (int)x;
					PlayerMng.players.get(0).now_touch_y = (int)y;
					PlayerMng.players.get(0).touch_flg = true;
					PlayerMng.players.get(0).data_id = data_id;
				}
				else if(MainActivity.real.y / 2 > y && PlayerMng.players.get(1).data_id == -1){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					PlayerMng.players.get(1).start_touch_x = (int)x;
					PlayerMng.players.get(1).start_touch_y = (int)y;
					PlayerMng.players.get(1).now_touch_x = (int)x;
					PlayerMng.players.get(1).now_touch_y = (int)y;
					PlayerMng.players.get(1).touch_flg = true;
					PlayerMng.players.get(1).data_id = data_id;
				}

				Log.i("tag1", "Touch PTR Down" + " count=" + count + ", DataIndex=" + data_id);
				break;
			case MotionEvent.ACTION_UP:
				Log.w( "aaaAAAAAxx21 UP", "data_id " + data_id );
				Log.w( "aaaAAAAAxx21 UP", "PlayerMng.players.get(0).data_id " + PlayerMng.players.get(0).data_id );
				Log.w( "aaaAAAAAxx21 UP", "PlayerMng.players.get(1).data_id " + PlayerMng.players.get(1).data_id );

				if(PlayerMng.players.get(0).data_id == data_id){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					PlayerMng.players.get(0).touch_flg = false;
					PlayerMng.players.get(0).data_id = -1;
					PlayerMng.players.get(0).indicatorXY[0] = 0;
					PlayerMng.players.get(0).indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(PlayerMng.players.get(1).data_id == 1) PlayerMng.players.get(1).data_id = 0;
				}
				else if(PlayerMng.players.get(1).data_id == data_id){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					PlayerMng.players.get(1).touch_flg = false;
					PlayerMng.players.get(1).data_id = -1;
					PlayerMng.players.get(1).indicatorXY[0] = 0;
					PlayerMng.players.get(1).indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(PlayerMng.players.get(0).data_id == 1) PlayerMng.players.get(0).data_id = 0;
				}

				Log.i("tag1", "Touch Up" + " count=" + count + ", DataIndex=" + data_id);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				Log.w( "aaaAAAAAxx21 UP2", "data_id " + data_id );
				Log.w( "aaaAAAAAxx21 UP2", "PlayerMng.players.get(0).data_id " + PlayerMng.players.get(0).data_id );
				Log.w( "aaaAAAAAxx21 UP2", "PlayerMng.players.get(1).data_id " + PlayerMng.players.get(1).data_id );

				if(PlayerMng.players.get(0).data_id == data_id){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					PlayerMng.players.get(0).touch_flg = false;
					PlayerMng.players.get(0).data_id = -1;
					PlayerMng.players.get(0).indicatorXY[0] = 0;
					PlayerMng.players.get(0).indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(PlayerMng.players.get(1).data_id == 1) PlayerMng.players.get(1).data_id = 0;
				}
				else if(PlayerMng.players.get(1).data_id == data_id){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					PlayerMng.players.get(1).touch_flg = false;
					PlayerMng.players.get(1).data_id = -1;
					PlayerMng.players.get(1).indicatorXY[0] = 0;
					PlayerMng.players.get(1).indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(PlayerMng.players.get(0).data_id == 1) PlayerMng.players.get(0).data_id = 0;
				}

				Log.i("tag1", "Touch PTR Up" + " count=" + count + ", DataIndex=" + data_id);
				break;
		}

		//	Log.w( "DEBUG_DATA", "tauch x " + PlayerMng.players.get(0).now_touch_x );
		//	Log.w( "DEBUG_DATA", "tauch y " + PlayerMng.players.get(0).now_touch_y );
		//PlayerMng.players.get(0).now_position_x += 3;
		//PlayerMng.players.get(0).now_position_y += 3;
		// 再描画の指示
		invalidate();

		return true;

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

