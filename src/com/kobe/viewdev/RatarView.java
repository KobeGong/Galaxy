package com.kobe.viewdev;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RatarView extends View {

	private Paint paint;
	private Paint shadowPaint;
	private Paint bitmapPaint;
	private int ringGap = 120;
	private int centerX, centerY;
	private float angle;

	public RatarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		bitmapPaint.setAntiAlias(true);
		setLayerType(View.LAYER_TYPE_SOFTWARE, bitmapPaint);
	}

	public RatarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RatarView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		Log.d("RatarView", "in onDraw");

		shadowPaint.setAlpha((int) (0.4 * 255));
		for (int i = 4; i >= 0; i--) {
			int r = 240 + i * ringGap;
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.FILL);
			shadowPaint.setShader(new RadialGradient(centerX, centerY, r + 10,
					new int[] { Color.parseColor("#55000000"),
							Color.parseColor("#44000000"),
							Color.parseColor("#03000000") }, new float[] { 0f,
							1 - 10f / r, 1f }, Shader.TileMode.CLAMP));

			Path path = new Path();
			path.setFillType(Path.FillType.EVEN_ODD);
			path.addCircle(centerX, centerY, r, Direction.CW);
			path.addCircle(centerX, centerY, r + 10, Direction.CW);
			path.close();

			canvas.save();
			canvas.translate(-3, 3);

			canvas.drawPath(path, shadowPaint);
			canvas.restore();
			canvas.drawCircle(centerX, centerY, r+2, paint);
			paint.setColor(Color.parseColor("#333331"));
			for (int j = 0; j < 3; j++) {
				if (i > 0) {
					float cy = (float) (centerY + (r - 35)
							* Math.cos(2 * Math.PI / 3 * (1 + j) + Math.PI / 6
									* i+Math.PI/180*angle*Math.sqrt(i)));
					float cx =  (float) (centerX + (r - 35)
							* Math.sin(2 * Math.PI / 3 * (1 + j) + Math.PI / 6
									* i+Math.PI/180*angle*Math.sqrt(i)));
					drawIcon(canvas, cx, cy, 104, R.drawable.app_icon);
				}
			}
		}
		paint.setColor(Color.WHITE);
		drawIcon(canvas, centerX, centerY, 134, R.drawable.app_icon2);

	}

	private void drawIcon(Canvas canvas, float centerX, float centerY, float ROfIcon,
			int drawableID) {
		canvas.save();
		canvas.translate(centerX - (ROfIcon + 16), centerY - (ROfIcon + 16));
		Path path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);
		path.addCircle(ROfIcon + 16, ROfIcon + 16, ROfIcon + 6, Direction.CW);
		path.addCircle(ROfIcon + 16, ROfIcon + 16, ROfIcon + 16, Direction.CW);
		path.close();

		canvas.save();
		canvas.translate(-2, 2);
		shadowPaint.setShader(new RadialGradient(ROfIcon + 16, ROfIcon + 16,
				ROfIcon + 16, new int[] { Color.parseColor("#55000000"),
						Color.parseColor("#44000000"),
						Color.parseColor("#03000000") }, new float[] { 0f,
						1f - 10f / (ROfIcon + 6), 1f }, Shader.TileMode.CLAMP));
		canvas.drawPath(path, shadowPaint);
		canvas.restore();
		canvas.drawCircle(ROfIcon + 16, ROfIcon + 16, ROfIcon + 6, paint);

		int mWidth = (int) (2 * ROfIcon);
		Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(
				getResources(), drawableID, mWidth, mWidth);
		BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		Matrix m = new Matrix();
		float scale = 1;
		int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
		scale = mWidth * 1.0f / bSize;

		m.setScale(scale, scale);
		bitmapShader.setLocalMatrix(m);
		bitmapPaint.setShader(bitmapShader);
		canvas.drawCircle(ROfIcon + 16, ROfIcon + 16, ROfIcon, bitmapPaint);
		canvas.restore();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d("RatarView", "in onLayout:" + l + "," + t + "," + r + "," + b);
//		Log.d("RatarView", "childcount:" + getChildCount());
		int width = getWidth();
		int height = getHeight();
		centerX = width / 3;
		centerY = height / 2;
//		if (getChildCount() > 0) {
//			int childIndex = 0;
//			for (int i = 4; i >= 1; i--) {
//				int radius = 240 + i * ringGap;
//				for (int j = 0; j < 3; j++) {
//					if (childIndex < getChildCount()){
//						
//					int cy = (int) (centerY + (radius - 20)
//							* Math.cos(2 * Math.PI / 3 * (1 + j) + Math.PI / 6
//									* i));
//					int cx = (int) (centerX + (radius - 20)
//							* Math.sin(2 * Math.PI / 3 * (1 + j) + Math.PI / 6
//									* i));
//					View child = getChildAt(childIndex++);
//					child.layout(cx - 84, cy - 84, cx + 84, cy + 84);
//					}
//				}
//			}
//
//		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d("RatarView", "in onMeasure");
	}
	
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		if(angle > 360 || angle < 0)
			angle = 0;
		this.angle = angle;
		invalidate();
	}
	
}
