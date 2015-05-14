package com.kobe.viewdev;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleImageView extends View {
	private final Matrix mShaderMatrix = new Matrix();
	private int mBitmapWidth;
	private int mBitmapHeight;
	private BitmapShader bitmapShader;
	private Bitmap bitmap;
	private final RectF mDrawableRect = new RectF();
	private Paint bitmapPaint;

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		bitmapPaint = new Paint();
		InputStream in = getResources().openRawResource(R.drawable.app_icon2);
		bitmap = BitmapFactory.decodeStream(in);
		mBitmapWidth = bitmap.getWidth();
		mBitmapHeight = bitmap.getHeight();
		mDrawableRect.set(0, 0, getWidth(), getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);
		float scale = 1;
		int mWidth = 280;
		int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
		scale = mWidth * 1.0f / bSize;
		Log.d("RatarView", "scale:" + scale);
		Log.d("RatarView",
				"bw:" + bitmap.getWidth() + ",bh:" + bitmap.getHeight());

		Matrix m = new Matrix();
		m.setScale(scale, scale);
		bitmapShader.setLocalMatrix(m);
		bitmapPaint.setShader(bitmapShader);
//		updateShaderMatrix();
		// paint1.setColor(Color.BLACK);
		float mDrawableRadius = Math.min(mDrawableRect.height() / 2,
				mDrawableRect.width() / 2);
		canvas.translate(getWidth() / 3, getHeight() / 2);
		canvas.drawCircle(140, 140, 140, bitmapPaint);

		// int radius = bitmap.getWidth() / 2;
		// BitmapShader bitmapShader = new BitmapShader(bitmap,
		// Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		// Paint paint = new Paint();
		// paint.setAntiAlias(true);
		// paint.setShader(bitmapShader);
		// canvas.translate(getWidth() / 3, getHeight() / 2);
		// canvas.drawCircle(140, 140, 140, paint);

		// canvas.drawBitmap(dest, 0, 0, paint);
	}

	private void updateShaderMatrix() {
		float scale;
		float dx = 0;
		float dy = 0;

		mShaderMatrix.set(null);

		if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()
				* mBitmapHeight) {
			scale = mDrawableRect.height() / (float) mBitmapHeight;
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
			scale = mDrawableRect.width() / (float) mBitmapWidth;
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}

		mShaderMatrix.setScale(scale, scale);
		mShaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

		bitmapShader.setLocalMatrix(mShaderMatrix);
	}
}
