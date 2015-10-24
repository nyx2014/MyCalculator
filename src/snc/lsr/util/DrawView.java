package snc.lsr.util;

import android.annotation.SuppressLint; 
import android.content.Context; 
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory; 
import android.graphics.Canvas; 
import android.graphics.Color; 
import android.graphics.Paint; 
import android.graphics.Rect; 
import android.view.View;
import snc.lsr.mycalculator.R;

public class DrawView extends View  
{ 
	public DrawView(Context mContext){
		super(mContext);
		setBackgroundColor(Color.WHITE);
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas mCanvas){
        super.onDraw(mCanvas);
        Paint mPaint=new Paint();
        //mPaint.setColor(Color.argb(128,255,255,0));
        //Bitmap mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.test);
        //Bitmap mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.tests);
        Bitmap mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        float t= ((float)(mCanvas.getWidth()))/((float)(mCanvas.getHeight()));
        float splashRatio = (float)(mBitmap.getWidth()/mBitmap.getHeight());
        int c=(int) (t*mBitmap.getHeight());
        int f=(int)( mBitmap.getWidth()/t);
        if( splashRatio>t){
            mCanvas.drawBitmap(mBitmap,
                    new Rect((mBitmap.getWidth()-c)/2,0,mBitmap.getWidth()-(mBitmap.getWidth()-c)/2, mBitmap.getHeight()),
                    new Rect(0,0,mCanvas.getWidth(),mCanvas.getHeight()),
                    mPaint);
        }else if (splashRatio<t){
            mCanvas.drawBitmap(mBitmap,
                    new Rect(0,(mBitmap.getHeight()-f)/2,mBitmap.getWidth(),mBitmap.getHeight()-(mBitmap.getHeight()-f)/2),
                    new Rect(0,0,mCanvas.getWidth(),mCanvas.getHeight()),
                    mPaint);
        }
    }
}
