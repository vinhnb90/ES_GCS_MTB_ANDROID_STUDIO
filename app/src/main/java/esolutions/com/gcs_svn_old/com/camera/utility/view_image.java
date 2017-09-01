package esolutions.com.gcs_svn_old.com.camera.utility;

import esolutions.com.gcs_svn_old.com.camera.ui.Activity_Camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class view_image extends View{

	public Bitmap bm;
	public Bitmap bm_crop;
	Paint paint_rec;
	Path path_rec_show;
	float X;
	float Y;
	int width;
	int height;
	float top_begin = 0f;
	float top_touch = 0f;
	
	public view_image(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint_rec = new Paint();
		paint_rec.setAntiAlias(true);
		paint_rec.setStrokeWidth(3f);
		paint_rec.setColor(Color.WHITE);
		paint_rec.setStyle(Paint.Style.STROKE);
		paint_rec.setStrokeJoin(Paint.Join.ROUND);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		try{
			width = getMeasuredWidth();
			height = getMeasuredHeight();
			Activity_Camera.left = 80;
			Activity_Camera.right = width  - 80;
			
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setDither(true);
			
			canvas.drawBitmap(bm, null, new Rect(0, 0, width, height), paint);
//			canvas.drawRect(Activity_Camera.left, Activity_Camera.top, Activity_Camera.right, Activity_Camera.bottom, paint_rec);
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	public Bitmap getBitmap(Bitmap bm){
		try{
			float h_bm = bm.getHeight();
			float h_v = getMeasuredHeight();
			float w_bm = bm.getWidth();
//			float w_v = getMeasuredWidth();
			Bitmap croppedBmp = Bitmap.createBitmap(bm, Activity_Camera.left, (int) (Activity_Camera.top*h_bm/h_v), (int) (w_bm - Activity_Camera.left), (int) (Activity_Camera.h*h_bm/h_v));
			return getResizedBitmap(croppedBmp, croppedBmp.getHeight()*3, croppedBmp.getWidth()*3);
		} catch(Exception ex) {
			ex.toString();
			return null;
		}
	}
	
	public Bitmap getBitmap(){
		try{
			float h_bm = bm.getHeight();
			float h_v = getMeasuredHeight();
			float w_bm = bm.getWidth();
//			float w_v = getMeasuredWidth();
			Bitmap croppedBmp = Bitmap.createBitmap(bm, Activity_Camera.left, (int) (Activity_Camera.top*h_bm/h_v), (int) (w_bm - Activity_Camera.left), (int) (Activity_Camera.h*h_bm/h_v));
			return getResizedBitmap(croppedBmp, croppedBmp.getHeight()*3, croppedBmp.getWidth()*3);
		} catch(Exception ex) {
			ex.toString();
			return null;
		}
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();
		drawRec(event, eventX, eventY);
		return true;
	}

	/** Vẽ hình chữ nhật
	 * @param event
	 * @param eventX
	 * @param eventY
	 * @return
	 */
	private boolean drawRec(MotionEvent event, float eventX, float eventY){
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			top_begin = Activity_Camera.top;
			top_touch = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float scale = event.getY() - top_touch;
			Activity_Camera.top = (int) (top_begin + scale);
			Activity_Camera.bottom = Activity_Camera.top + Activity_Camera.h;
			if(Activity_Camera.top <= 0){
				Activity_Camera.top = 0;
				Activity_Camera.bottom = Activity_Camera.h;
			}
			
			if(Activity_Camera.bottom >= height){
				Activity_Camera.top = height - Activity_Camera.h;
				Activity_Camera.bottom = height;
			}
			invalidate();
			break;
		default:
			return false;
		}
		return true;
	}

	public void setImageBitmap(Bitmap bm){
		try{
			this.bm = bm;
			invalidate();
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	public void setImageDrawable(Drawable d){
		try{
			this.bm = ((BitmapDrawable)d).getBitmap();
			invalidate();
		} catch(Exception ex) {
			ex.toString();
		}
	}
}
