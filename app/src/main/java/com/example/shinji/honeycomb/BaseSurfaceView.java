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

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 * Created by shinji on 2017/04/06.
 */

public class BaseSurfaceView extends SurfaceView implements  Runnable,SurfaceHolder.Callback{

	// 四角の縦、横の数
	final static int SQUARE_NUM = 11;

	// 六角形の縦、横の数
	final static int HEX_NUM = 21;

	// スクリーンの大きさ(px)
	int screen_width, screen_height;

	// 現在タッチしている位置
	int p1_now_touch_x = 0, p1_now_touch_y = 0;
	int p2_now_touch_x = 0, p2_now_touch_y = 0;

	// セーブしたタッチ位置
	int p1_save_touch_x = 0, p1_save_touch_y = 0;
	int p2_save_touch_x = 0, p2_save_touch_y = 0;

	// タッチしたデータID
	int p1_dataId = -1;
	int p2_dataId = -1;

	// Canvas 中心点
	float center_x = 0.0f;
	float center_y = 0.0f;

	// 全体の移動位置
	int p1_move_x = 0, p1_move_y = 0;
	int p2_move_x = 0, p2_move_y = 0;

	// 背景RGB
	final static int BACK_R = 188;
	final static int BACK_G = 189;
	final static int BACK_B = 194;

	final static int PLAYER_NO = 1;

	// 六角形の塗りつぶし確認
	int hex_color_num[][] = {
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
	};

	int hex_color_rgb[][] = {
			{255,255,255},
			{0,0,255},
			{255,0,0},
			{0,0,0}
	};


	// 六角形の半径の長さ
	static float HEX_LENGTH = 50.0f;

	// 六角形の線の太さ
	static float HEX_WIDHT;

	// 六角形の一辺の長さの比率
	static float HEX_RATIO;

	// 移動マーカーの半径
	static int DIRECTION_RADIUS;

	// プレイヤーの半径
	static int PLAYER_RADIUS;

	// プレイヤーのスピード
	final static int PLAYER_SPEED = 5;
//	final static int PLAYER_SPEED = 5;

	// プレイヤーの色
	final static int P1_R = 0;
	final static int P1_G = 45;
	final static int P1_B = 230;

	final static int P2_R = 220;
	final static int P2_G = 0;
	final static int P2_B = 21;

	// ひとつ前の塗りつぶし座標
	int p1_before_fill_i = -1;
	int p1_before_fill_j = -1;
	int p2_before_fill_i = -1;
	int p2_before_fill_j = -1;

	// 指示器のXY位置
	int p1_indicatorXY[] = {0,0};
	int p2_indicatorXY[] = {0,0};

	// セーブ位置と指示器の差分
	int p1_indicatorDiff[] = {0,0};
	int p2_indicatorDiff[] = {0,0};

	// 現在タッチ中かのフラグ
	boolean p1_touch_flg = false;
	boolean p2_touch_flg = false;

	final static int SQUARE_LENGTH = 100;

	// FPS
	long run_start_time = 0, run_end_time = 0;
	final static long FPS = 180;
	final static long FPS_MSEC = 1000/FPS;


	SurfaceHolder surfaceHolder;
	Thread thread;

	public BaseSurfaceView(Context context){
		super(context);

		// 端末に合わせた各サイズの調整
		if( MainActivity.real.x >= 1080 ) {
			// 六角形の半径の長さ
			HEX_LENGTH = 50.0f;
			// 六角形の線の太さ
			HEX_WIDHT = 5.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 80;
			// プレイヤーの半径
			PLAYER_RADIUS = 40;
		}
		else if( MainActivity.real.x >= 720 ){
			// 六角形の半径の長さ
			HEX_LENGTH = 25.0f;
			// 六角形の線の太さ/2
			HEX_WIDHT = 5.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 40;
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
		}
		else{
			// 六角形の半径の長さ
			HEX_LENGTH = 50.0f;
			// 六角形の線の太さ
			HEX_WIDHT = 10.0f;
			// 六角形の一辺の長さの比率
			HEX_RATIO = 0.86f;
			// 移動マーカーの半径
			DIRECTION_RADIUS = 40;
			// プレイヤーの半径
			PLAYER_RADIUS = 20;
		}


		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

//		hex_color_num = new int[SQUARE_NUM][SQUARE_NUM];


	}

	@Override public void run() {

		// グリッドの位置
		int i,j;

		// キャンバスを設定
		Canvas canvas;

		// ペイントを設定
		Paint paint = new Paint();
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.argb(255, BACK_R, BACK_G, BACK_B));

		// 起動時間
		long StartTimeMillis = System.currentTimeMillis();
		// 現在時間
		long CurrentTimeMillis;
		// 前回時間
		long BeforeTimeMillis = StartTimeMillis;

		while(thread != null){
			try{


				Log.w( "IIIIIIIIIIIIIIIIIII", "p1_touch_flg[" + p1_touch_flg + "]");
				Log.w( "IIIIIIIIIIIIIIIIIII", "p2_touch_flg[" + p2_touch_flg + "]");

				run_start_time = System.currentTimeMillis();

				canvas = surfaceHolder.lockCanvas();
				canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);

				// Canvas 中心点
				center_x = canvas.getWidth()/2;
				center_y = canvas.getHeight()/2;

				if( p1_touch_flg ){
					// タップ移動比率xyと指示マーカーのxyを取得
					getIndicatorXY(p1_save_touch_x, p1_save_touch_y, p1_now_touch_x, p1_now_touch_y, p1_indicatorDiff, p1_indicatorXY);
					p1_move_x = p1_move_x - (p1_indicatorDiff[0] / PLAYER_SPEED);
					p1_move_y = p1_move_y - (p1_indicatorDiff[1] / PLAYER_SPEED);
				}
				if( p2_touch_flg ){
					// タップ移動比率xyと指示マーカーのxyを取得
					getIndicatorXY(p2_save_touch_x, p2_save_touch_y, p2_now_touch_x, p2_now_touch_y, p2_indicatorDiff, p2_indicatorXY);
					p2_move_x = p2_move_x - (p2_indicatorDiff[0] / PLAYER_SPEED);
					p2_move_y = p2_move_y - (p2_indicatorDiff[1] / PLAYER_SPEED);
				}

				// 基本六角形
				DrawHex(paint,canvas);

				// 中心円の表示
				DrawPlayer(paint,canvas,1);
				DrawPlayer(paint,canvas,2);

				// 指示器の表示
				DrawIndicator(paint,canvas,1);
				DrawIndicator(paint,canvas,2);

				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

				// FPS
				run_end_time = System.currentTimeMillis();
//                Log.w( "FPS", String.valueOf( 1000 / (run_end_time - run_start_time) ) );
				if(run_end_time - run_start_time < FPS_MSEC){ // 1000 / 60 = 16.6666
					try {
						Thread.sleep(FPS_MSEC - (run_end_time - run_start_time));
					} catch (InterruptedException e) {
					}
				}

			} catch(Exception e){}
		}
	}

	public void DrawHex(Paint paint,Canvas canvas){
		float add_x,add_y;

		// パスを設定
		Path path = new Path();

		for( int i = 0; i < HEX_NUM; i++ ){
			for( int j = 0; j < HEX_NUM; j++ ){

				// 移動分
				// i - ( HEX_NUM / 2 ),j - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH * (3.0f/2.0f) * (float)(i - ( HEX_NUM / 2 ));
				if( (i - ( HEX_NUM / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH * HEX_RATIO) * 2 * (j - ( HEX_NUM / 2 ));
				else  add_y = HEX_LENGTH * HEX_RATIO + ( (HEX_LENGTH * HEX_RATIO) * 2 * (j - ( HEX_NUM / 2 )));

				// すでにペイント済み、枠内に中心点が入ったら
				// 一旦、円で計算
				if( ((add_x + p1_move_x) * (add_x + p1_move_x) + (add_y + p1_move_y) * (add_y + p1_move_y)) < Math.pow(HEX_LENGTH,2) ){
					// 壁にぶつかったら
					if( hex_color_num[i][j] == 3 ){
						p1_move_x = 0;
						p1_move_y = 0;

					}
					// 新規塗りだったら
					else if( hex_color_num[i][j] != PLAYER_NO ){
						// 色を記録
						hex_color_num[i][j] = PLAYER_NO;
						// 囲まれていたら色を塗る
//								CheckCloseAndFill(i,j,canvas);
						p1_before_fill_i = i;
						p1_before_fill_j = j;
					}
				}
				if( ((add_x + p2_move_x) * (add_x + p2_move_x) + (add_y + p2_move_y) * (add_y + p2_move_y)) < Math.pow(HEX_LENGTH,2) ){
					// 壁にぶつかったら
					if( hex_color_num[i][j] == 3 ){
						p2_move_x = 0;
						p2_move_y = 0;

					}
					// 新規塗りだったら
					else if( hex_color_num[i][j] != 2 ){
						// 色を記録
						hex_color_num[i][j] = 2;
						// 囲まれていたら色を塗る
//								CheckCloseAndFill(i,j,canvas);
						p2_before_fill_i = i;
						p2_before_fill_j = j;
					}
				}


				// 六角形の描画
				paint.setColor(Color.argb(255, hex_color_rgb[hex_color_num[i][j]][0], hex_color_rgb[hex_color_num[i][j]][1], hex_color_rgb[hex_color_num[i][j]][2]));
				paint.setStrokeWidth(HEX_WIDHT);
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
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


	public void DrawPlayer(Paint paint,Canvas canvas,int player_no){
		float p1_start_x,p1_start_y,p2_start_x,p2_start_y;

		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		if( player_no == 1 ){
			p1_start_x = center_x - 30;
			p1_start_y = center_y - 30;
			paint.setColor(Color.argb(255, P1_R, P1_G, P1_B));
			// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
			canvas.drawCircle(p1_start_x - p1_move_x, p1_start_y - p1_move_y, PLAYER_RADIUS, paint);
		}
		else if( player_no == 2 ){
			p2_start_x = center_x - 30;
			p2_start_y = center_y - 30;
			paint.setColor(Color.argb(255, P2_R, P2_G, P2_B));
			// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
			canvas.drawCircle(p2_start_x - p2_move_x, p2_start_y - p2_move_y, PLAYER_RADIUS, paint);
		}
		else return;



	}

	public void DrawIndicator(Paint paint,Canvas canvas,int player_no){

		int save_touch_x,save_touch_y;
		int indicatorXY[] = {0,0};

		if(player_no == 1){
			if(!p1_touch_flg) return;
			save_touch_x = p1_save_touch_x;
			save_touch_y = p1_save_touch_y;
			indicatorXY = p1_indicatorXY;
		}
		else if(player_no == 2){
			if(!p2_touch_flg) return;
			save_touch_x = p2_save_touch_x;
			save_touch_y = p2_save_touch_y;
			indicatorXY = p2_indicatorXY;
		}
		else return;

		// セーブタップ位置に〇を表示
		paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		canvas.drawCircle(save_touch_x, save_touch_y, DIRECTION_RADIUS, paint);

		// セーブタップ位置を中心にタップ〇移動範囲を表示
		paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		canvas.drawCircle(save_touch_x, save_touch_y, DIRECTION_RADIUS * 3, paint);

		// 移動方向に〇を表示
		paint.setColor(Color.argb(120, 235, 121, 136)); // ピンク
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		// 計算が完了していたら表示可能
		if( indicatorXY[0] != 0 && indicatorXY[1] != 0){
			canvas.drawCircle(indicatorXY[0], indicatorXY[1], DIRECTION_RADIUS, paint);
		}
		//Log.w( "DEBUG_DATA", "CENTER p1_save_touch_x " + p1_save_touch_x );
		//Log.w( "DEBUG_DATA", "CENTER p1_save_touch_y " + p1_save_touch_y );
		//Log.w( "DEBUG_DATA", "CENTER direXY[0] " + p1_indicatorXY[0] );
		//Log.w( "DEBUG_DATA", "CENTER direXY[1] " + p1_indicatorXY[1] );

	}
	public void CheckCloseAndFill(int i,int j,Canvas canvas){

		boolean tabun_close_flg = false;
		boolean kakujituni_close_flg = false;

		// 前の塗りつぶしがなければエラー
		if( p1_before_fill_i == -1 || p1_before_fill_j == -1 ) return;

		Log.w( "CheckCloseAndFill", "i " + i );
		Log.w( "CheckCloseAndFill", "j " + j );
		Log.w( "CheckCloseAndFill", "p1_before_fill_i " + p1_before_fill_i);
		Log.w( "CheckCloseAndFill", "p1_before_fill_j " + p1_before_fill_j);

		// PLAYARが上端じゃなくて、上のマスがBEFOREじゃなく埋まっていたら、閉じた可能性あり
		if( i != 0 ){
			Log.w( "CheckCloseAndFill", "hex_color_num[i-1][j] " + hex_color_num[i-1][j] );
			if( hex_color_num[i-1][j] == 1 && !( i-1 == p1_before_fill_i && j == p1_before_fill_j) ){
				tabun_close_flg = true;

				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE1"  );
			}
			Log.w( "CheckCloseAndFill", "hex_color_num[i-1][j] " + hex_color_num[i-1][j] );
		}
		if( j != 0 ){
			Log.w( "CheckCloseAndFill", "hex_color_num[i][j-1] " + hex_color_num[i][j-1] );
			if( hex_color_num[i][j-1] == 1 && !( i == p1_before_fill_i && j-1 == p1_before_fill_j) ){
				tabun_close_flg = true;

				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE2"  );
			}
			Log.w( "CheckCloseAndFill", "hex_color_num[i][j-1] " + hex_color_num[i][j-1] );
		}
		if( i != SQUARE_NUM - 1 ){
			Log.w( "CheckCloseAndFill", "hex_color_num[i+1][j] " + hex_color_num[i+1][j] );
			if( hex_color_num[i+1][j] == 1 && !( i+1 == p1_before_fill_i && j == p1_before_fill_j) ){
				tabun_close_flg = true;

				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE3"  );
			}
			Log.w( "CheckCloseAndFill", "hex_color_num[i+1][j] " + hex_color_num[i+1][j] );
		}
		if( j != SQUARE_NUM - 1 ){
			Log.w( "CheckCloseAndFill", "hex_color_num[i][j+1] " + hex_color_num[i][j+1] );
			if( hex_color_num[i][j+1] == 1 && !( i == p1_before_fill_i && j+1 == p1_before_fill_j) ){
				tabun_close_flg = true;

				Log.w( "CheckCloseAndFill", "TRUEEEEEEEEEE4"  );
			}
			Log.w( "CheckCloseAndFill", "hex_color_num[i][j+1] " + hex_color_num[i][j+1] );
		}

		Log.w( "CheckCloseAndFill", "ssssssssssssssssssss1 " );

		if(!tabun_close_flg) return;

		Log.w( "CheckCloseAndFill", "ssssssssssssssssssss2 " );

		// 左が開いていたら、閉じているか確認
		if( i != 0 && hex_color_num[i-1][j] == 0 ) {
			hex_color_num[i-1][j] = 2;
			Log.w( "CheckCloseAndFill", "I AM 2 i" + i  );
			Log.w( "CheckCloseAndFill", "I AM 2 j" + j  );
			// 完全に閉じているかチェック、閉じてる範囲を3に書き換え
			kakujituni_close_flg = CheckCloseComp(i-1,j);

			Log.w( "CheckCloseAndFill", "kanzenni_tojita　" + kakujituni_close_flg);
			// 閉じられているところを塗る
			FillClose(kakujituni_close_flg,canvas);
		}


	}

	// 完全に閉じらているか確認し、３番をセットする
	public boolean CheckCloseComp(int check_i,int check_j){
		Log.w( "CheckCloseComp", "CheckCloseFull");
		// ループフラグ
		boolean roop_flg = true;
		// 停止フラグ
		boolean stop_flg = true;
		// コンプリートフラグ
		boolean comp_flg = false;
		// 検索対象データが１つでもあったフラグ
		boolean data_flg = false;


		int i,j;

		while(roop_flg){
			Log.w( "CheckCloseComp", "ROOP");

			data_flg = false;
			for( i = 0; i < SQUARE_NUM; i++ ) {
				for (j = 0; j < SQUARE_NUM; j++) {
					Log.w( "CheckCloseComp", "i " + i);
					Log.w( "CheckCloseComp", "j " + j);
					if( hex_color_num[i][j] == 2 ){
						//１個でも2があれば、再検索するよ
						comp_flg = true;
						data_flg = true;

						Log.w( "CheckCloseComp", "TOOTTAAAA");

						// 検索対象の左が0番だったら検索対象に追加
						if( i != 0 ){
							Log.w( "CheckCloseComp", "1");
							if( hex_color_num[i-1][j] == 0 ) hex_color_num[i-1][j] = 2;
						}
						if( j != 0 ){
							Log.w( "CheckCloseComp", "2");
							if( hex_color_num[i][j-1] == 0 ) hex_color_num[i][j-1] = 2;
						}
						if( i != ( SQUARE_NUM - 1 ) ){
							Log.w( "CheckCloseComp", "3");
							if( hex_color_num[i+1][j] == 0 ) hex_color_num[i+1][j] = 2;
						}
						if( j != ( SQUARE_NUM - 1 ) ){
							Log.w( "CheckCloseComp", "4");
							if( hex_color_num[i][j+1] == 0 ) hex_color_num[i][j+1] = 2;
						}
						// チェック済み
						Log.w( "CheckCloseComp", "I AM 3 i" + i);
						Log.w( "CheckCloseComp", "I AM 3 j" + j);
						hex_color_num[i][j] = 3;
						Log.w( "CheckCloseComp", "aaa1");

						// 検索対象が画面端に来たら、囲まれていない
						if( i == 0 || j == 0 || i == ( SQUARE_NUM - 1 ) || j == ( SQUARE_NUM - 1 ) ){
							Log.w( "CheckCloseComp", "ERRRRRRRRRRRRRRRRRRRRRR");
							roop_flg = false;
							stop_flg = false;
							comp_flg = false;

							break;
						}
						Log.w( "CheckCloseComp", "aaa2");
					}
				}
				if(!stop_flg) break;
			}
			Log.w( "CheckCloseComp", "aaa3");

			if( !data_flg || !stop_flg ){
				roop_flg = false;
			}

		}

		return comp_flg;
	}

	//mode 0:閉じられていない、1:閉じられている
	public void FillClose(boolean mode,Canvas canvas){

		int i,j;

		Log.w( "FillClose", "");

		Paint paint = new Paint();

		for( i = 0; i < SQUARE_NUM; i++ ){
			for( j = 0; j < SQUARE_NUM; j++ ){
				if( hex_color_num[i][j] == 3 ){
					if( mode == true){
						Log.w( "FillClose", "i " + i);
						Log.w( "FillClose", "j " + j);

						paint.setColor(Color.argb(255, 255, 0, 0));
						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa1");
						paint.setStrokeWidth(8);
						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa2");
						paint.setStyle(Paint.Style.FILL);
						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa3 center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * i) + p1_move_x " + (center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * i) + p1_move_x);
						canvas.drawRect(
								( center_x - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( i - ( SQUARE_NUM / 2 ) ) ) + p1_move_x,
								( center_y - (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( j - ( SQUARE_NUM / 2 ) ) ) + p1_move_y,
								( center_x + (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( i - ( SQUARE_NUM / 2 ) ) ) + p1_move_x,
								( center_y + (SQUARE_LENGTH / 2) ) + (SQUARE_LENGTH * ( j - ( SQUARE_NUM / 2 ) ) ) + p1_move_y,
								paint);

						// 色を記録
						hex_color_num[i][j] = 1;

						Log.w( "FillClose", "aaaaaaaaaaaaaaaaaa4");

					}
					// 0に戻しておく
					else{
						hex_color_num[i][j] = 0;
					}
				}
			}
		}
	}

	// タッチイベントを処理するためOverrideする
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// タッチしている数を取得
		int count = event.getPointerCount();
		//タッチアクションの情報を取得
		int action = event.getAction();
		int dataId;
		int pointId;
		float x,y;

		for(int i=0; i<count; i++) {
			// ポインタID
			pointId = event.getPointerId(i);
			// データID
			dataId = event.findPointerIndex(pointId);
			x = event.getX(dataId);
			y = event.getY(dataId);

			if (dataId == -1) continue;
			Log.w( "AAAAAxx1zzz", "p1_now_touch_x " + p1_now_touch_x);
			Log.w( "AAAAAxx1zzz", "p1_save_touch_x " + p1_save_touch_x);
			Log.w( "AAAAAxx1", "p1_dataId " + p1_dataId);
			Log.w( "AAAAAxx1", "MainActivity.real.y " + MainActivity.real.y);
			Log.w( "AAAAAxx1", "y " + y);

			// Player1の情報
			if(dataId == p1_dataId){
				// タッチしている位置取得
				p1_now_touch_x = (int)x;
				p1_now_touch_y = (int)y;
			}
			// 画面上半分の位置をタップ
			else if(dataId == p2_dataId){
				// タッチしている位置取得
				p2_now_touch_x = (int)x;
				p2_now_touch_y = (int)y;
			}
			Log.i("tag2", "DataIndex[" + dataId + "] PointIndex[" + pointId + "] x[" + x + "]");
			Log.i("tag2", "DataIndex[" + dataId + "] PointIndex[" + pointId + "] y[" + y + "]");
		}

		dataId = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		x = event.getX(dataId);
		y = event.getY(dataId);

		switch(action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				Log.w( "aaaAAAAAxx21 DOWN", "dataId " + dataId );
				Log.w( "aaaAAAAAxx21 DOWN", "p1_dataId " + p1_dataId );
				Log.w( "aaaAAAAAxx21 DOWN", "p2_dataId " + p2_dataId );

				if(MainActivity.real.y / 2 < y && p1_dataId == -1){
					p1_save_touch_x = (int)x;
					p1_save_touch_y = (int)y;
					p1_now_touch_x = (int)x;
					p1_now_touch_y = (int)y;
					p1_touch_flg = true;
					p1_dataId = dataId;
				}
				else if(MainActivity.real.y / 2 > y && p2_dataId == -1){
					p2_save_touch_x = (int)x;
					p2_save_touch_y = (int)y;
					p2_now_touch_x = (int)x;
					p2_now_touch_y = (int)y;
					p2_touch_flg = true;
					p2_dataId = dataId;
				}

				Log.i("tag1", "Touch Down" + " count=" + count + ", DataIndex=" + dataId);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.w( "aaaAAAAAxx21 DOWN2", "dataId " + dataId );
				Log.w( "aaaAAAAAxx21 DOWN2", "p1_dataId " + p1_dataId );
				Log.w( "aaaAAAAAxx21 DOWN2", "p2_dataId " + p2_dataId );

				if(MainActivity.real.y / 2 < y && p1_dataId == -1){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					p1_save_touch_x = (int)x;
					p1_save_touch_y = (int)y;
					p1_now_touch_x = (int)x;
					p1_now_touch_y = (int)y;
					p1_touch_flg = true;
					p1_dataId = dataId;
				}
				else if(MainActivity.real.y / 2 > y && p2_dataId == -1){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					p2_save_touch_x = (int)x;
					p2_save_touch_y = (int)y;
					p2_now_touch_x = (int)x;
					p2_now_touch_y = (int)y;
					p2_touch_flg = true;
					p2_dataId = dataId;
				}

				Log.i("tag1", "Touch PTR Down" + " count=" + count + ", DataIndex=" + dataId);
				break;
			case MotionEvent.ACTION_UP:
				Log.w( "aaaAAAAAxx21 UP", "dataId " + dataId );
				Log.w( "aaaAAAAAxx21 UP", "p1_dataId " + p1_dataId );
				Log.w( "aaaAAAAAxx21 UP", "p2_dataId " + p2_dataId );

				if(p1_dataId == dataId){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					p1_touch_flg = false;
					p1_dataId = -1;
					p1_indicatorXY[0] = 0;
					p1_indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(p2_dataId == 1) p2_dataId = 0;
				}
				else if(p2_dataId == dataId){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					p2_touch_flg = false;
					p2_dataId = -1;
					p2_indicatorXY[0] = 0;
					p2_indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(p1_dataId == 1) p1_dataId = 0;
				}

				Log.i("tag1", "Touch Up" + " count=" + count + ", DataIndex=" + dataId);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				Log.w( "aaaAAAAAxx21 UP2", "dataId " + dataId );
				Log.w( "aaaAAAAAxx21 UP2", "p1_dataId " + p1_dataId );
				Log.w( "aaaAAAAAxx21 UP2", "p2_dataId " + p2_dataId );

				if(p1_dataId == dataId){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa1 ");
					p1_touch_flg = false;
					p1_dataId = -1;
					p1_indicatorXY[0] = 0;
					p1_indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(p2_dataId == 1) p2_dataId = 0;
				}
				else if(p2_dataId == dataId){
					Log.w( "AAAAAxx21", "aaaaaaaaaaaaaaaa2 ");
					p2_touch_flg = false;
					p2_dataId = -1;
					p2_indicatorXY[0] = 0;
					p2_indicatorXY[1] = 0;
					// 0番がなくなるとデータID1番は0番に変更になる
					if(p1_dataId == 1) p1_dataId = 0;
				}

				Log.i("tag1", "Touch PTR Up" + " count=" + count + ", DataIndex=" + dataId);
				break;
		}

		//	Log.w( "DEBUG_DATA", "tauch x " + p1_now_touch_x );
		//	Log.w( "DEBUG_DATA", "tauch y " + p1_now_touch_y );
		//p1_move_x += 3;
		//p1_move_y += 3;
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


	// 指示マーカーの位置を取得
	public void getIndicatorXY(int save_touch_x,int save_touch_y,int now_touch_x,int now_touch_y,int[] indicatorDiff,int[] indicatorXY){
		// 移動方向の正、負
		boolean positive_x = true,positive_y = true;
		// セーブ位置と現在位置の絶対値差分
		double sa_x,sa_y;
		// 絶対値差分と表示位置の比率
		double ratio;

		// 移動方向の正、負を取得
		if( save_touch_x > now_touch_x ){
			positive_x = false;
		}
		if( save_touch_y > now_touch_y ){
			positive_y = false;
		}

		// セーブ位置と現在位置の絶対値差分を取得
		sa_x = abs(save_touch_x - now_touch_x);
		sa_y = abs(save_touch_y - now_touch_y);

		//Log.w( "DEBUG_DATA", "p1_save_touch_x " + p1_save_touch_x  );
		//Log.w( "DEBUG_DATA", "p1_save_touch_y " + p1_save_touch_y  );
		//Log.w( "DEBUG_DATA", "p1_now_touch_x " + p1_now_touch_x  );
		//Log.w( "DEBUG_DATA", "p1_now_touch_y " + p1_now_touch_y  );

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
		indicatorXY[0] = save_touch_x + indicatorDiff[0];
		indicatorXY[1] = save_touch_y + indicatorDiff[1];

		//Log.w( "DEBUG_DATA", "(int)round(sa_x * ratio) " + (int)round(sa_x * ratio)  );
		//Log.w( "DEBUG_DATA", "(int)round(sa_y * ratio) " + (int)round(sa_y * ratio)  );

		//Log.w( "DEBUG_DATA", "p1_indicatorXY[0] " + p1_indicatorXY[0]  );
		//Log.w( "DEBUG_DATA", "p1_indicatorXY[1] " + p1_indicatorXY[1]  );

		//Log.w( "DEBUG_DATA", "結果 " + ( pow(p1_indicatorXY[0],2) + pow(p1_indicatorXY[1],2) )  );

	}



}

