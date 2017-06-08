package com.example.shinji.honeycomb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 * Created by shinji on 2017/06/07.
 * プレイヤー管理クラス
 */

public class PlayerMng{

	// プレイヤー人数
	static int playerNum = 2;
	// プレイヤースタート位置
	static int playerXY[][] = {{0,-100},{0,100}};
	// プレイヤーカラー
	static int playerColorOption[] = {1,2};

	// プレイヤーの半径
	static int PLAYER_RADIUS;

	// プレイヤーのスピード
	final static int playerSpeed[] = {10,10};

	static PlayerStatus player;

	// 移動マーカーの半径
	static int DIRECTION_RADIUS;

	// プライヤーデータ
	public static ArrayList<PlayerStatus> players = new ArrayList<PlayerStatus>();



	public static void playerInit(){

		// 端末に合わせた各サイズの調整
		if( MainActivity.real.x >= 1080 ) {
			// プレイヤーの半径
			PLAYER_RADIUS = 40;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 80;
		}
		else if( MainActivity.real.x >= 720 ){
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 40;
		}
		else{
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 40;
		}

		players.clear();
		for( int i = 0; i < playerNum; i++ ){
			player = new PlayerStatus( playerXY[i][0],playerXY[i][1],playerColorOption[i] );
			players.add(player);
		}
	}

	public static void DrawPlayer(Paint paint, Canvas canvas){
		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		paint.reset();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

//		paint.setColor(Color.argb(255, PlayerMng.players.get(1).r, PlayerMng.players.get(1).g, PlayerMng.players.get(1).b));
//		canvas.drawCircle(0,0, 800, paint);

		for( int i = 0; i < playerNum; i++ ){
			paint.setColor(Color.argb(255, PlayerMng.players.get(i).r, PlayerMng.players.get(i).g, PlayerMng.players.get(i).b));
			// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
			canvas.drawCircle(center_x - PlayerMng.players.get(i).now_position_x, center_y - PlayerMng.players.get(i).now_position_y, PLAYER_RADIUS, paint);
//			canvas.drawCircle(0,0, PLAYER_RADIUS, paint);

		}
	}

	public static void DrawIndicator(Paint paint,Canvas canvas){

		int start_touch_x = 0,start_touch_y = 0;
		int indicatorXY[] = {0,0};

		for( int i = 0; i < playerNum; i++ ){
			if(!players.get(i).touch_flg) continue;
			start_touch_x = players.get(i).start_touch_x;
			start_touch_y = players.get(i).start_touch_y;
			indicatorXY = players.get(i).indicatorXY;

			// セーブタップ位置に〇を表示
			paint.reset();
			paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
			paint.setStrokeWidth(20);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS, paint);

			// セーブタップ位置を中心にタップ〇移動範囲を表示
			paint.reset();
			paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
			paint.setStrokeWidth(20);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS * 3, paint);

			// 移動方向に〇を表示
			paint.reset();
			paint.setColor(Color.argb(120, 235, 121, 136)); // ピンク
			paint.setStrokeWidth(20);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			// 計算が完了していたら表示可能
			if( indicatorXY[0] != 0 && indicatorXY[1] != 0){
				canvas.drawCircle(indicatorXY[0], indicatorXY[1], DIRECTION_RADIUS, paint);
			}
		}


		//Log.w( "DEBUG_DATA", "CENTER players.get(i).start_touch_x " + players.get(i).start_touch_x );
		//Log.w( "DEBUG_DATA", "CENTER players.get(i).start_touch_y " + players.get(i).start_touch_y );
		//Log.w( "DEBUG_DATA", "CENTER direXY[0] " + players.get(i).indicatorXY[0] );
		//Log.w( "DEBUG_DATA", "CENTER direXY[1] " + players.get(i).indicatorXY[1] );

	}

	public static void GetMoveXY(){

		for( int i = 0; i < playerNum; i++ ){
			if( players.get(i).touch_flg ){
				// タップ移動比率xyと指示マーカーのxyを取得
				getIndicatorXY(players.get(i).start_touch_x, players.get(i).start_touch_y, players.get(i).now_touch_x, players.get(i).now_touch_y, players.get(i).indicatorDiff, players.get(i).indicatorXY);
				players.get(i).now_position_x = players.get(i).now_position_x - (players.get(i).indicatorDiff[0] / playerSpeed[i]);
				players.get(i).now_position_y = players.get(i).now_position_y - (players.get(i).indicatorDiff[1] / playerSpeed[i]);
			}
		}
	}


	// 指示マーカーの位置を取得
	public static void getIndicatorXY(int start_touch_x,int start_touch_y,int now_touch_x,int now_touch_y,int[] indicatorDiff,int[] indicatorXY){
		// 移動方向の正、負
		boolean positive_x = true,positive_y = true;
		// セーブ位置と現在位置の絶対値差分
		double sa_x,sa_y;
		// 絶対値差分と表示位置の比率
		double ratio;

		// 移動方向の正、負を取得
		if( start_touch_x > now_touch_x ){
			positive_x = false;
		}
		if( start_touch_y > now_touch_y ){
			positive_y = false;
		}

		// セーブ位置と現在位置の絶対値差分を取得
		sa_x = abs(start_touch_x - now_touch_x);
		sa_y = abs(start_touch_y - now_touch_y);

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).start_touch_x " + PlayerMng.players.get(0).start_touch_x  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).start_touch_y " + PlayerMng.players.get(0).start_touch_y  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).now_touch_x " + PlayerMng.players.get(0).now_touch_x  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).now_touch_y " + PlayerMng.players.get(0).now_touch_y  );

		//Log.w( "DEBUG_DATA", "sa_x " + sa_x  );
		//Log.w( "DEBUG_DATA", "sa_y " + sa_y  );

		// 三平方の定理で絶対値差分と表示位置の比率を取得
		ratio = sqrt( pow(DIRECTION_RADIUS * 2,2) / ( pow(sa_x,2) + pow(sa_y,2) ) );

		//Log.w( "DEBUG_DATA", "pow(160,2) " + pow(DIRECTION_RADIUS * 2,2)  );
		//Log.w( "DEBUG_DATA", "pow(sa_x,2) " + pow(sa_x,2) );
		//Log.w( "DEBUG_DATA", "pow(sa_y,2) " + pow(sa_y,2) );
		//Log.w( "DEBUG_DATA", "ratio " + ratio  );

		// 指示マーカーとセーブ位置の差分を取得（四捨五入のため誤差あり）
		if( positive_x ) indicatorDiff[0] = (int)round(sa_x * ratio);
		else indicatorDiff[0] = - (int)round(sa_x * ratio);
		if( positive_y ) indicatorDiff[1] = (int)round(sa_y * ratio);
		else indicatorDiff[1] = - (int)round(sa_y * ratio);

		// 四捨五入して指示マーカーの位置を取得
		indicatorXY[0] = start_touch_x + indicatorDiff[0];
		indicatorXY[1] = start_touch_y + indicatorDiff[1];

		//Log.w( "DEBUG_DATA", "(int)round(sa_x * ratio) " + (int)round(sa_x * ratio)  );
		//Log.w( "DEBUG_DATA", "(int)round(sa_y * ratio) " + (int)round(sa_y * ratio)  );

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[0] " + PlayerMng.players.get(0).indicatorXY[0]  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[1] " + PlayerMng.players.get(0).indicatorXY[1]  );

		//Log.w( "DEBUG_DATA", "結果 " + ( pow(PlayerMng.players.get(0).indicatorXY[0],2) + pow(PlayerMng.players.get(0).indicatorXY[1],2) )  );

	}
}
