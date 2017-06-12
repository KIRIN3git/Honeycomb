package com.example.shinji.honeycomb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * Created by shinji on 2017/06/09.
 */

public class FieldMng{

	// 六角形の縦、横の数
	final static int HEX_NUM_ROW = 15;
	final static int HEX_NUM_COL = 16;


	final static int WALL_NO = 8;
	final static int DALETE_NO = 9;



	// 六角形の塗りつぶし確認
	static int hex_color_num[][];

//	int hex_color_num[][] = {
//			{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
//			{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
//	};

	static int hex_color_rgb[][] = {
			{255,255,255},
			{127 ,255 ,127}, //黄緑
//		{255 ,193 ,255}, //ピンク
//		{255 ,188 ,188}, //薄赤
			{127 ,255 ,255},//水色
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129},
			{129 ,129 ,129}
	};


	// 六角形の半径の長さ
	static float HEX_LENGTH = 50.0f;

	// 六角形の線の太さ
	static float HEX_WIDHT;

	// 六角形の一辺の長さの比率
	static float HEX_RATIO;

	public static void fieldInit(){

		hex_color_num = new int[][]{
				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
		};

		// 端末に合わせた各サイズの調整
		if( MainActivity.real.x >= 1080 ) {
			// 六角形の半径の長さ
			HEX_LENGTH = 50.0f;
			// 六角形の線の太さ
			HEX_WIDHT = 5.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
		}
		else if( MainActivity.real.x >= 720 ){
			// 六角形の半径の長さ
			HEX_LENGTH = 25.0f;
			// 六角形の線の太さ/2
			HEX_WIDHT = 5.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
		}
		else{
			// 六角形の半径の長さ
			HEX_LENGTH = 50.0f;
			// 六角形の線の太さ
			HEX_WIDHT = 10.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
		}
	}

	public static void DrawHex(Paint paint, Canvas canvas){
		float add_x,add_y;

		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		// パスを設定
		Path path = new Path();

		paint.reset();
		paint.setStrokeWidth(HEX_WIDHT);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for( int col_i = 0; col_i < HEX_NUM_COL; col_i++ ){
			for( int row_i = 0; row_i < HEX_NUM_ROW; row_i++ ){

				// 移動分
				// i - ( HEX_NUM / 2 ),j - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH * (3.0f/2.0f) * (float)(row_i - ( HEX_NUM_ROW / 2 ));
				if( (row_i - ( HEX_NUM_ROW / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH * HEX_RATIO) * 2 * (col_i - ( HEX_NUM_COL / 2 ));
				else  add_y = HEX_LENGTH * HEX_RATIO + ( (HEX_LENGTH * HEX_RATIO) * 2 * (col_i - ( HEX_NUM_COL / 2 )));
//				Log.w( "LOG1", "col_i[" + col_i + "] add_x[" + add_x + "]");
//				Log.w( "LOG1", "row_i[" + row_i + "] add_y[" + add_y + "]");

				// すでにペイント済み、枠内に中心点が入ったら
				// 一旦、円で計算
				for( int i = 0; i < PlayerMng.playerNum; i++ ){
					if( ((add_x + PlayerMng.players.get(i).now_position_x) * (add_x + PlayerMng.players.get(i).now_position_x) + (add_y + PlayerMng.players.get(i).now_position_y) * (add_y + PlayerMng.players.get(i).now_position_y)) < Math.pow(HEX_LENGTH, 2) ){
						// 壁にぶつかったら
						if( hex_color_num[col_i][row_i] == WALL_NO ){
							PlayerMng.players.get(i).now_position_x = 0;
							PlayerMng.players.get(i).now_position_y = 0;

						}
						// 新規塗りだったら
						else if( hex_color_num[col_i][row_i] != PlayerMng.playerColorNo[i] ){
							// 色を記録
							hex_color_num[col_i][row_i] = PlayerMng.playerColorNo[i];
							// 囲まれていたら色を塗る
//								CheckCloseAndFill(i,j,canvas);
							PlayerMng.players.get(i).before_fill_i = col_i;
							PlayerMng.players.get(i).before_fill_j = row_i;
						}
					}
				}

				// 表示なし
				if( hex_color_num[col_i][row_i] == DALETE_NO ) continue;

				// 六角形の描画
				paint.setColor(Color.argb(255, hex_color_rgb[hex_color_num[col_i][row_i]][0], hex_color_rgb[hex_color_num[col_i][row_i]][1], hex_color_rgb[hex_color_num[col_i][row_i]][2]));
				path.reset();
				// 右
				path.moveTo(center_x + HEX_LENGTH - HEX_WIDHT + add_x, center_y + add_y);
				// 右下
				path.lineTo(center_x + (HEX_LENGTH / 2) - (HEX_WIDHT / 2) + add_x, center_y + (HEX_LENGTH * HEX_RATIO) - (HEX_WIDHT * HEX_RATIO) + add_y);
				// 左下
				path.lineTo(center_x - (HEX_LENGTH / 2) + (HEX_WIDHT / 2) + add_x, center_y + (HEX_LENGTH * HEX_RATIO) - (HEX_WIDHT * HEX_RATIO) + add_y);
				// 左
				path.lineTo(center_x - HEX_LENGTH + HEX_WIDHT + add_x, center_y + add_y);
				// 左上
				path.lineTo(center_x - (HEX_LENGTH / 2) + (HEX_WIDHT / 2) + add_x, center_y - (HEX_LENGTH * HEX_RATIO) + (HEX_WIDHT * HEX_RATIO) + add_y);
				// 右上
				path.lineTo(center_x + (HEX_LENGTH / 2) - (HEX_WIDHT / 2) + add_x, center_y - (HEX_LENGTH * HEX_RATIO) + (HEX_WIDHT * HEX_RATIO) + add_y);
				path.close();
				canvas.drawPath(path, paint);

			}
		}
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
