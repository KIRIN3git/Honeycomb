package com.example.shinji.honeycomb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

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
	final static int PLAYER_SPEED = 10;

	static PlayerStatus player;

	// プライヤーデータ
	public static ArrayList<PlayerStatus> players = new ArrayList<PlayerStatus>();



	public static void createUser(){

		// 端末に合わせた各サイズの調整
		if( MainActivity.real.x >= 1080 ) {
			// プレイヤーの半径
			PLAYER_RADIUS = 40;
		}
		else if( MainActivity.real.x >= 720 ){
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
		}
		else{
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
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
}
