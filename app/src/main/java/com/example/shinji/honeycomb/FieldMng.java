package com.example.shinji.honeycomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shinji on 2017/06/09.
 */

public class FieldMng{

	// 六角形の半径の長さ
	static float HEX_LENGTH_DP = 12.0f;
	static float HEX_LENGTH_PX;

	// 六角形の線の太さ
	static float HEX_WIDHT_DP = 1.0f;
	static float HEX_WIDHT_PX;

	// 六角形の一辺の長さの比率
	static float HEX_RATIO = 0.86f;

	// 六角形の縦、横の数
//	final static int HEX_NUM_ROW = 15;
//	final static int HEX_NUM_COL = 17;
	final static int HEX_NUM_ROW = 19;
	final static int HEX_NUM_COL = 20;

	// 四角形の半径の長さ
	static float TET_LENGTH_DP = 5.0f;
	static float TET_LENGTH_PX;

	final static int CLEAN_NO = 0; // 白
	final static int COUNT_NO = 7;
	final static int WALL_NO = 8;
	final static int DALETE_NO = 9;

	static boolean countHitFlg = false;

	// 六角形の塗りつぶし確認
	// ２桁の場合は1桁目が奪おうとするNO,１桁目が奪われようとしているNO
	static int hex_color_num[][];

	static int hex_color_rgb_main[][] = {
			{255,255,255},
//			{127 ,255 ,127}, //黄緑
			{255 ,193 ,255}, //ピンク
//		{255 ,188 ,188}, //薄赤
			{127 ,255 ,255},//水色
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{255 ,193 ,255},
			{129 ,129 ,129},
			{129 ,129 ,129}
	};

	static int hex_color_rgb_sub[][] = {
			{255,255,255},
//			{127 ,255 ,127}, //黄緑
			{255 ,222 ,255}, //ピンク
//		{255 ,188 ,188}, //薄赤
			{222 ,255 ,255},//水色
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{255 ,193 ,255},
			{129 ,129 ,129},
			{129 ,129 ,129}
	};


	public static void fieldInit(Context context){

		// dp→px変換
		float density = context.getResources().getDisplayMetrics().density;
		HEX_LENGTH_PX = CommonMng.PxToDp(HEX_LENGTH_DP,density);
		HEX_WIDHT_PX = CommonMng.PxToDp(HEX_WIDHT_DP,density);
		TET_LENGTH_PX = CommonMng.PxToDp(TET_LENGTH_DP,density);

//		//六角形1 19 20
		hex_color_num = new int[][]{
				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,8,0,8,0,8,0,8,0,8,0,8,0,8,0,8,0,8},
				{9,8,9,8,9,8,9,8,9,8,9,8,9,8,9,8,9,8,9}
		};
		//六角形1 30 32
//		hex_color_num = new int[][]{
//				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
//		};
		//六角形1 15 17
//		hex_color_num = new int[][]{
//				{9,9,9,9,9,9,8,8,8,9,9,9,9,9,9},
//				{9,9,9,9,8,8,0,0,0,8,8,9,9,9,9},
//				{9,9,8,8,0,0,0,0,0,0,0,8,8,9,9},
//				{8,8,0,0,0,0,0,0,0,0,0,0,0,8,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{9,8,8,0,0,0,0,0,0,0,0,0,8,8,9},
//				{9,9,9,8,8,0,0,0,0,0,8,8,9,9,9},
//				{9,9,9,9,9,8,8,0,8,8,9,9,9,9,9},
//				{9,9,9,9,9,9,9,8,9,9,9,9,9,9,9}
//		};
		//四角形
//		hex_color_num = new int[][]{
//				{8,9,8,9,8,9,8,9,8,9,8,9,8,9,8},
//				{8,8,0,8,0,8,0,8,0,8,0,8,0,8,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
//		};

//		hex_color_num = new int[][]{
//				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
//				{8,1,1,1,1,1,2,2,2,2,2,1,1,1,8},
//				{8,1,0,0,0,0,1,1,1,1,1,1,1,1,8},
//				{8,1,2,2,2,2,2,2,2,1,1,1,1,0,8},
//				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,2,2,2,2,2,2,2,2,2,8},
//				{8,2,2,2,2,1,1,1,1,1,1,1,1,1,8},
//				{8,1,0,0,0,0,0,0,0,0,0,0,0,0,8},
//				{8,0,0,0,0,0,1,1,1,1,1,1,1,1,8},
//				{8,1,1,1,1,1,2,2,2,2,1,1,1,1,8},
//				{8,1,1,1,1,1,1,1,1,0,0,0,0,0,8},
//				{8,0,0,0,0,1,1,1,1,1,1,1,1,1,8},
//				{8,1,0,1,2,2,2,2,2,2,2,2,2,2,8},
//				{8,2,2,2,2,2,2,2,2,0,0,0,0,0,8},
//				{8,0,0,0,0,0,0,0,0,0,2,2,2,2,8},
//				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
//		};

	}

	public static void DrawHex(Paint paint, Canvas canvas){
		float add_x,add_y;

		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;
		// パスを設定
		Path path = new Path();

		paint.reset();
		paint.setStrokeWidth(HEX_WIDHT_PX);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for( int col = 0; col < HEX_NUM_COL; col++ ){

			for( int row = 0; row < HEX_NUM_ROW; row++ ){
				// 移動分
				// row - ( HEX_NUM / 2 ),col - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH_PX * (3.0f/2.0f) * (float)(row - ( HEX_NUM_ROW / 2 ));
				if( (row - ( HEX_NUM_ROW / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 ));
				else  add_y = HEX_LENGTH_PX * HEX_RATIO + ( (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 )));

				// すでにペイント済み、枠内に中心点が入ったら
				// 一旦、円で計算
				for( int i = 0; i < PlayerMng.playerNum; i++ ){
					if( ((add_x + PlayerMng.players.get(i).now_position_x) * (add_x + PlayerMng.players.get(i).now_position_x) + (add_y + PlayerMng.players.get(i).now_position_y) * (add_y + PlayerMng.players.get(i).now_position_y)) < Math.pow(HEX_LENGTH_PX, 2) ){

						//一つ前位置(col,row)を記録
						if( PlayerMng.players.get(i).now_position_col != col ){
							PlayerMng.players.get(i).before_position_col = PlayerMng.players.get(i).before_position_col;
							PlayerMng.players.get(i).before_position_row = PlayerMng.players.get(i).before_position_row;
						}
						//現在位置(col,row)を記録
						PlayerMng.players.get(i).now_position_col = col;
						PlayerMng.players.get(i).now_position_row = row;

						// 初期エリア色塗り
						if( PlayerMng.players.get(i).erea_flg == false ){
							SetRoundColoer(i,col,row);
						}

						// 壁にぶつかったら
						if( hex_color_num[col][row] == WALL_NO ){
							PlayerMng.players.get(i).now_position_x = 0;
							PlayerMng.players.get(i).now_position_y = 0;
						}
						// 自分の領域に入ったら（仮色の場合はダメ）
						else if( hex_color_num[col][row] % 10 == PlayerMng.playerColorNo[i] && hex_color_num[col][row] / 10 == CLEAN_NO ){
							// 侵入中だったら
							if( PlayerMng.players.get(i).status == 1 ){
								// 生還
//								ChengeHex(PlayerMng.playerColorNo[i]);
//								CheckClosed(i);
								PlayerMng.players.get(i).status = 0; //侵入中でない
							}
						}
						// 自分の領域でなかったら
						else if( hex_color_num[col][row] % 10 != PlayerMng.playerColorNo[i] ){
							// Log.w( "DEBUG_DATA", "OTHER");
							// 侵入中フラグ
							PlayerMng.players.get(i).status = 1;
							// 色を記録
							// 他プレイヤーの色番号に、10倍した自プレイヤー番号を追加
							hex_color_num[col][row] = ( hex_color_num[col][row] % 10 ) + ( PlayerMng.playerColorNo[i] * 10 );


							// 色を記録
//							hex_color_num[col][row] = PlayerMng.playerColorNo[i];


							// 新規塗りだったら
							if( hex_color_num[col][row] == CLEAN_NO ) {

								// Log.w( "DEBUG_DATA", "新規");
								// 色を記録
								hex_color_num[col][row] = PlayerMng.playerColorNo[i];
								// 囲まれていたら色を塗る
								// CheckCloseAndFill(i,j,canvas);
//								PlayerMng.players.get(i).before_fill_i = col;
//								PlayerMng.players.get(i).before_fill_j = row;
							}
							// 自プレイヤーが他プレイヤー領域に侵入中の場所だったら
							else if( hex_color_num[col][row] / 10 ==  PlayerMng.playerColorNo[i] ){
								// Log.w( "DEBUG_DATA", "侵入中");
								//NOOP
							}
							// 他プレイヤーの領域に侵入したら
							else{
								// 侵入中フラグ
								PlayerMng.players.get(i).status = 1;

//								Log.w( "DEBUG_DATA", "侵入した");
								// 色を記録
								// 他プレイヤーの色番号に、10倍した自プレイヤー番号を追加
//								hex_color_num[col][row] = hex_color_num[col][row] + ( PlayerMng.playerColorNo[i] * 10 );

//								Log.w( "DEBUG_DATA", " hex_color_num " + hex_color_num[col][row]);
							}
						}
					}
				}

				// 表示なし
				if( hex_color_num[col][row] == DALETE_NO ) continue;

				// ○六角形の描画
				// 色（一桁目の数字）
				if( hex_color_num[col][row] / 10 != 0 ){
					paint.setColor(Color.argb(255, hex_color_rgb_sub[hex_color_num[col][row] / 10][0], hex_color_rgb_sub[hex_color_num[col][row] / 10][1], hex_color_rgb_sub[hex_color_num[col][row] / 10][2]));
				}
				else {
					paint.setColor(Color.argb(255, hex_color_rgb_main[hex_color_num[col][row] % 10][0], hex_color_rgb_main[hex_color_num[col][row] % 10][1], hex_color_rgb_main[hex_color_num[col][row] % 10][2]));
				}
				path.reset();
				// 右
				path.moveTo(center_x + HEX_LENGTH_PX - HEX_WIDHT_PX + add_x, center_y + add_y);
				// 右下
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左下
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左
				path.lineTo(center_x - HEX_LENGTH_PX + HEX_WIDHT_PX + add_x, center_y + add_y);
				// 左上
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 右上
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				path.close();
				canvas.drawPath(path, paint);

				// ○四角形の描画
				// 色（二桁目の数字）
				//Log.w( "DEBUG_DATA", "四角形 1 = " + hex_color_num[col][row]);
				/*
				if( hex_color_num[col][row] > 10 ) {
					//Log.w( "DEBUG_DATA", "四角形 2 =" + hex_color_num[col][row] / 10);
					//Log.w( "DEBUG_DATA", "四角形 3 =" + hex_color_rgb_main[hex_color_num[col][row] / 10][0] + hex_color_rgb_main[hex_color_num[col][row] / 10][1] + hex_color_rgb_main[hex_color_num[col][row] / 10][2]);
					paint.setColor(Color.argb(255, hex_color_rgb_main[hex_color_num[col][row] / 10][0], hex_color_rgb_main[hex_color_num[col][row] / 10][1], hex_color_rgb_main[hex_color_num[col][row] / 10][2]));
					canvas.drawRect(center_x + add_x - TET_LENGTH_PX, center_y + add_y - TET_LENGTH_PX, center_x + add_x + TET_LENGTH_PX, center_y + add_y + TET_LENGTH_PX, paint);

					//Log.w( "DEBUG_DATA", "四角形 4 =" + hex_color_rgb_main[hex_color_num[col][row] / 10][0] + hex_color_rgb_main[hex_color_num[col][row] / 10][1] + hex_color_rgb_main[hex_color_num[col][row] / 10][2]);
				}
				*/
			}
		}
	}

	public static void SetRoundColoer( int user_no,int col,int row ){
		List<List<Integer>> connect;
		connect = GetConnect(col,row);
		Log.w( "DEBUG_DATA1", "user_no" + user_no);
		Log.w( "DEBUG_DATA1", "connect.size()" + connect.size());

		hex_color_num[col][row] = PlayerMng.playerColorNo[user_no];
		for(int i = 0; i < connect.size(); i++){
			hex_color_num[connect.get(i).get(0)][connect.get(i).get(1)] = PlayerMng.playerColorNo[user_no];
		}

		PlayerMng.players.get(user_no).erea_flg = true;
	}


	// 侵略中の部分を侵略完了に変更
	public static void ChengeHex(int color_num) {
		for( int col = 0; col < HEX_NUM_COL; col++ ) {
			for (int row = 0; row < HEX_NUM_ROW; row++) {
				if( hex_color_num[col][row] / 10 ==  color_num ){
					hex_color_num[col][row] = color_num;
				}
			}
		}
	}

	public static void CountHex(Paint paint, Canvas canvas, int col_check,int row_check){
		float add_x,add_y;

		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		// パスを設定
		Path path = new Path();

		paint.reset();
		paint.setStrokeWidth(HEX_WIDHT_PX);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for( int col = 0; col < HEX_NUM_COL; col++ ){
			for( int row = 0; row < HEX_NUM_ROW; row++ ){

				if( col_check == col && row_check == row ){
					for( int user_i = 0; user_i < PlayerMng.playerNum; user_i++ ){
						if( hex_color_num[col][row] % 10 == PlayerMng.playerColorNo[user_i] ){
							hex_color_num[col][row] = COUNT_NO;
							countHitFlg = true;
							PlayerMng.players.get(user_i).score++;
							TimeMng.setFps(ScoreMng.SCORE_FPS);
						}
					}
				}

				// 移動分
				// row - ( HEX_NUM / 2 ),col - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH_PX * (3.0f/2.0f) * (float)(row - ( HEX_NUM_ROW / 2 ));
				if( (row - ( HEX_NUM_ROW / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 ));
				else  add_y = HEX_LENGTH_PX * HEX_RATIO + ( (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 )));
//				Log.w( "LOG1", "col[" + col + "] add_x[" + add_x + "]");
//				Log.w( "LOG1", "row[" + row + "] add_y[" + add_y + "]");

				// 表示なし
				if( hex_color_num[col][row] == DALETE_NO ) continue;

				// 六角形の描画
				paint.setColor(Color.argb(255, hex_color_rgb_main[hex_color_num[col][row] % 10][0], hex_color_rgb_main[hex_color_num[col][row] % 10][1], hex_color_rgb_main[hex_color_num[col][row] % 10][2]));
				path.reset();

				// 右
				path.moveTo(center_x + HEX_LENGTH_PX - HEX_WIDHT_PX + add_x, center_y + add_y);
				// 右下
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左下
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左
				path.lineTo(center_x - HEX_LENGTH_PX + HEX_WIDHT_PX + add_x, center_y + add_y);
				// 左上
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 右上
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				path.close();
				canvas.drawPath(path, paint);
			}
		}
	}

	// 侵略中の部分を侵略完了に変更
	public static void CheckClosed(int player_no) {

		// 一つ前に色を塗る
		hex_color_num[PlayerMng.players.get(player_no).before_position_col][PlayerMng.players.get(player_no).before_position_row] = PlayerMng.playerColorNo[player_no];

		// i,jが隣接する i,jを一覧取得
//		ArrayList<Integer> num = GetConnect(PlayerMng.players.get(player_no).before_position_col,PlayerMng.players.get(player_no).before_position_row);
	}

	public static List<List<Integer>> GetConnect(int col,int row){

		Log.w( "DEBUG_DATA", "col " + col);
		Log.w( "DEBUG_DATA", "row " + row);
		Log.w( "DEBUG_DATA", "hex_color_num[0].length " + hex_color_num[0].length);
		Log.w( "DEBUG_DATA", "hex_color_num.length " + hex_color_num.length);


		List<List<Integer>> list = new ArrayList<>();


		// 左端でなければ
		Log.w( "bAAAAA5", "col ");
		if( col != 0 ){
			list.add(Arrays.asList(col-1,row));
			Log.w( "DEBUG_DATA2228", "connect.get(0).get(0)" + list.get(0).get(0));

		}
		// 右端でなければ
		if( col != hex_color_num[0].length - 1 ){
			list.add(Arrays.asList(col+1,row));

		}
		// 上端でなければ
		if( row != 0 ){
			list.add(Arrays.asList(col,row - 1));
		}
		// 上端でなければ
		if( row != hex_color_num.length - 1 ){
			list.add(Arrays.asList(col,row + 1));
		}

		if( col != 0 && row % 2 == 0 ){
			Log.w( "DEBUG_DATA", "1111");
			if( col != 0 ){
				list.add(Arrays.asList(col + 1,row - 1));
			}
			if( col != hex_color_num[0].length - 1 ){
				list.add(Arrays.asList(col + 1,row + 1));
			}
		}
		else if( col != hex_color_num[0].length - 1 && row % 2 == 1 ){
			Log.w( "DEBUG_DATA", "222");
			if( col != 0 ){
				list.add(Arrays.asList(col - 1,row - 1));
			}
			if( col != hex_color_num[0].length - 1 ){
				list.add(Arrays.asList(col - 1,row + 1));
			}
		}


		/*
		// 左端でなければ
		Log.w( "bAAAAA5", "col ");
		if( col != 0 ){
			data.add(col-1);
			data.add(row);
			list.add(data);
			Log.w( "DEBUG_DATA222", "connect.get(0).get(0)" + list.get(0).get(0));
			data.clear();
			Log.w( "DEBUG_DATA2228", "connect.get(0).get(0)" + list.get(0).get(0));

		}
		// 右端でなければ
		if( col != hex_color_num[0].length - 1 ){
			Log.w( "bAAAAA7", "col ");
			data.add(col+1);
			data.add(row);
			list.add(data);
			data.clear();
			Log.w( "bAAAAA8", "col ");
		}
		// 上端でなければ
		if( row != 0 ){
			data.add(col);
			data.add(row - 1);
			list.add(data);
			data.clear();
		}
		// 上端でなければ
		if( row != hex_color_num.length - 1 ){
			data.add(col);
			data.add(row + 1);
			list.add(data);
			data.clear();
		}
		*/
		Log.w( "DEBUG_DATA2229", "connect.get(0).get(0)" + list.get(0).get(0));
		return list;
	}

//	public void CheckCloseAndFill(int i,int j,Canvas canvas){
//
//		boolean tabun_close_flg = false;
//		boolean kakujituni_close_flg = false;
//
//		// 前の塗りつぶしがなければエラー
//		if( PlayerMng.players.get(0).before_fill_i == -1 || PlayerMng.players.get(0).before_fill_j == -1 ) return;
//
//		Log.w( "CheckCloseAndFill", "i " + i );
//		Log.w( "CheckCloseAndFill", "j " + j );
//		Log.w( "CheckCloseAndFill", "PlayerMng.players.get(0).before_fill_i " + PlayerMng.players.get(0).before_fill_i);
//		Log.w( "CheckCloseAndFill", "PlayerMng.players.get(0).before_fill_j " + PlayerMng.players.get(0).before_fill_j);
//
//		// PLAYARが上端じゃなくて、上のマスがBEFOREじゃなく埋まっていたら、閉じた可能性あり
//		if( i != 0 ){
//			Log.w( "CheckCloseAndFill", "hex_color_num[i-1][j] " + hex_color_num[i-1][j] );
//			if( hex_color_num[i-1][j] == 1 && !( i-1 == PlayerMng.players.get(0).before_fill_i && j == PlayerMng.players.get(0).before_fill_j) ){
//				tabun_close_flg = true;
//
//				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE1"  );
//			}
//			Log.w( "CheckCloseAndFill", "hex_color_num[i-1][j] " + hex_color_num[i-1][j] );
//		}
//		if( j != 0 ){
//			Log.w( "CheckCloseAndFill", "hex_color_num[i][j-1] " + hex_color_num[i][j-1] );
//			if( hex_color_num[i][j-1] == 1 && !( i == PlayerMng.players.get(0).before_fill_i && j-1 == PlayerMng.players.get(0).before_fill_j) ){
//				tabun_close_flg = true;
//
//				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE2"  );
//			}
//			Log.w( "CheckCloseAndFill", "hex_color_num[i][j-1] " + hex_color_num[i][j-1] );
//		}
//		if( i != SQUARE_NUM - 1 ){
//			Log.w( "CheckCloseAndFill", "hex_color_num[i+1][j] " + hex_color_num[i+1][j] );
//			if( hex_color_num[i+1][j] == 1 && !( i+1 == PlayerMng.players.get(0).before_fill_i && j == PlayerMng.players.get(0).before_fill_j) ){
//				tabun_close_flg = true;
//
//				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE3"  );
//			}
//			Log.w( "CheckCloseAndFill", "hex_color_num[i+1][j] " + hex_color_num[i+1][j] );
//		}
//		if( j != SQUARE_NUM - 1 ){
//			Log.w( "CheckCloseAndFill", "hex_color_num[i][j+1] " + hex_color_num[i][j+1] );
//			if( hex_color_num[i][j+1] == 1 && !( i == PlayerMng.players.get(0).before_fill_i && j+1 == PlayerMng.players.get(0).before_fill_j) ){
//				tabun_close_flg = true;
//
//				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE4"  );
//			}
//			Log.w( "CheckCloseAndFill", "hex_color_num[i][j+1] " + hex_color_num[i][j+1] );
//		}
//
//		Log.w( "CheckCloseAndFill", "ssssssssssssssssssss1 " );
//
//		if(!tabun_close_flg) return;
//
//		Log.w( "CheckCloseAndFill", "ssssssssssssssssssss2 " );
//
//		// 左が開いていたら、閉じているか確認
//		if( i != 0 && hex_color_num[i-1][j] == 0 ) {
//			hex_color_num[i-1][j] = 2;
//			Log.w( "CheckCloseAndFill", "I AM 2 i" + i  );
//			Log.w( "CheckCloseAndFill", "I AM 2 j" + j  );
//			// 完全に閉じているかチェック、閉じてる範囲を3に書き換え
//			kakujituni_close_flg = CheckCloseComp(i-1,j);
//
//			Log.w( "CheckCloseAndFill", "kanzenni_tojita　" + kakujituni_close_flg);
//			// 閉じられているところを塗る
//			FillClose(kakujituni_close_flg,canvas);
//		}
//	}

//	// 完全に閉じらているか確認し、３番をセットする
//	public boolean CheckCloseComp(int check_i,int check_j){
//		Log.w( "CheckCloseComp", "CheckCloseFull");
//		// ループフラグ
//		boolean roop_flg = true;
//		// 停止フラグ
//		boolean stop_flg = true;
//		// コンプリートフラグ
//		boolean comp_flg = false;
//		// 検索対象データが１つでもあったフラグ
//		boolean data_flg = false;
//
//
//		int i,j;
//
//		while(roop_flg){
//			Log.w( "CheckCloseComp", "ROOP");
//
//			data_flg = false;
//			for( i = 0; i < SQUARE_NUM; i++ ) {
//				for (j = 0; j < SQUARE_NUM; j++) {
//					Log.w( "CheckCloseComp", "i " + i);
//					Log.w( "CheckCloseComp", "j " + j);
//					if( hex_color_num[i][j] == 2 ){
//						//１個でも2があれば、再検索するよ
//						comp_flg = true;
//						data_flg = true;
//
//						Log.w( "CheckCloseComp", "TOOTTAAAA");
//
//						// 検索対象の左が0番だったら検索対象に追加
//						if( i != 0 ){
//							Log.w( "CheckCloseComp", "1");
//							if( hex_color_num[i-1][j] == 0 ) hex_color_num[i-1][j] = 2;
//						}
//						if( j != 0 ){
//							Log.w( "CheckCloseComp", "2");
//							if( hex_color_num[i][j-1] == 0 ) hex_color_num[i][j-1] = 2;
//						}
//						if( i != ( SQUARE_NUM - 1 ) ){
//							Log.w( "CheckCloseComp", "3");
//							if( hex_color_num[i+1][j] == 0 ) hex_color_num[i+1][j] = 2;
//						}
//						if( j != ( SQUARE_NUM - 1 ) ){
//							Log.w( "CheckCloseComp", "4");
//							if( hex_color_num[i][j+1] == 0 ) hex_color_num[i][j+1] = 2;
//						}
//						// チェック済み
//						Log.w( "CheckCloseComp", "I AM 3 i" + i);
//						Log.w( "CheckCloseComp", "I AM 3 j" + j);
//						hex_color_num[i][j] = 3;
//						Log.w( "CheckCloseComp", "aaa1");
//
//						// 検索対象が画面端に来たら、囲まれていない
//						if( i == 0 || j == 0 || i == ( SQUARE_NUM - 1 ) || j == ( SQUARE_NUM - 1 ) ){
//							Log.w( "CheckCloseComp", "ERRRRRRRRRRRRRRRRRRRRRR");
//							roop_flg = false;
//							stop_flg = false;
//							comp_flg = false;
//
//							break;
//						}
//						Log.w( "CheckCloseComp", "aaa2");
//					}
//				}
//				if(!stop_flg) break;
//			}
//			Log.w( "CheckCloseComp", "aaa3");
//
//			if( !data_flg || !stop_flg ){
//				roop_flg = false;
//			}
//
//		}
//
//		return comp_flg;
//	}

//	//mode 0:閉じられていない、1:閉じられている
//	public void FillClose(boolean mode,Canvas canvas){
//
//		int i,j;
//
//		Log.w( "FillClose", "");
//
//		Paint paint = new Paint();
//
//		for( i = 0; i < SQUARE_NUM; i++ ){
//			for( j = 0; j < SQUARE_NUM; j++ ){
//				if( hex_color_num[i][j] == 3 ){
//					if( mode == true){
//						Log.w( "FillClose", "i " + i);
//						Log.w( "FillClose", "j " + j);
//
//						paint.setColor(Color.argb(255, 255, 0, 0));
//						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa1");
//						paint.setStrokeWidth(8);
//						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa2");
//						paint.setStyle(Paint.Style.FILL);
//						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa3 center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * i) + PlayerMng.players.get(0).now_position_x " + (center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * i) + PlayerMng.players.get(0).now_position_x);
//						canvas.drawRect(
//								( center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( i - ( SQUARE_NUM / 2 ) ) ) + PlayerMng.players.get(0).now_position_x,
//								( center_y - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( j - ( SQUARE_NUM / 2 ) ) ) + PlayerMng.players.get(0).now_position_y,
//								( center_x + (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( i - ( SQUARE_NUM / 2 ) ) ) + PlayerMng.players.get(0).now_position_x,
//								( center_y + (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( j - ( SQUARE_NUM / 2 ) ) ) + PlayerMng.players.get(0).now_position_y,
//								paint);
//
//						// 色を記録
//						hex_color_num[i][j] = 1;
//
//						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa4");
//
//					}
//					// 0に戻しておく
//					else{
//						hex_color_num[i][j] = 0;
//					}
//				}
//			}
//		}
//	}
}
