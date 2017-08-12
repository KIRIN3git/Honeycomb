package com.example.shinji.honeycomb;

/**
 * Created by shinji on 2017/06/07.
 */

public class PlayerStatus{

	// プレイヤーの開始位置
	int start_position_x,start_position_y;
	// プレイヤーの現在位置
	int now_position_x,now_position_y;
	// プレイヤーカラー
	int r,g,b;
	// プレイヤーの状態
	// 0:通常,1:相手に侵入中,2:死亡
	int status = 0;


	// タッチの開始位置
	int start_touch_x,start_touch_y;
	// タッチの現在位置
	int now_touch_x,now_touch_y;
	// タッチ中かのフラグ
	boolean touch_flg;
	// タッチ用ポイントID
	int point_id;
	// 一つ前の塗りつぶしi,j
	int before_fill_i,before_fill_j;
	// 指示器のXY位置
	int indicatorXY[] = {0,0};
	// 開始位置と指示器の差分
	int indicatorDiff[] = {0,0};

	int score;

	PlayerStatus( int x,int y,int color_no ){
		start_position_x = x;
		start_position_y = y;
		now_position_x = x;
		now_position_y = y;
		point_id = -1;
		score = 0;

		if( color_no == 1 ){
//			r = 50;
//			g = 205;
//			b = 50;
			r = 255;
			g = 127;
			b = 127;
		}
		else if( color_no == 2 ){
			r = 127;
			g = 127;
			b = 255;
		}

		// 赤
//    final static int P2_R = 255 ;
//    final static int P2_G = 127 ;
//    final static int P2_B = 127;
	}




}
