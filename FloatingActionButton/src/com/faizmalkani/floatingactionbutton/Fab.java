package com.faizmalkani.floatingactionbutton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class Fab extends View
{
    Context _context;
    Paint mButtonPaint, mDrawablePaint;
    Bitmap  mBitmap;
    int mScreenHeight;
	float currentY;;
    boolean mHidden = false;

    public Fab(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        _context = context;
        init(Color.WHITE);
    }
    
	public Fab(Context context)
    {
        super(context);
        _context = context;
        init(Color.WHITE);
    }
    
    public void setFabColor(int fabColor)
    {
    	init(fabColor);
    }
    
    public void setFabDrawable(Drawable fabDrawable)
    {
    	Drawable myDrawable = fabDrawable;
        mBitmap = ((BitmapDrawable) myDrawable).getBitmap();
        invalidate();
    }
    
    
	public void init(int fabColor)
    {
    	setWillNotDraw(false);
        setLayerTypeForView(this);
        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(fabColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        invalidate();
        
        WindowManager mWindowManager = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
		Display display = mWindowManager.getDefaultDisplay();
		Point size = new Point(display.getWidth(), display.getHeight());
//		display.getSize(size);
		mScreenHeight = size.y;
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
    	setClickable(true);
        canvas.drawCircle(getWidth()/2, getHeight()/2,(float) (getWidth()/2.6), mButtonPaint);
    	canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2, (getHeight() - mBitmap.getHeight()) / 2, mDrawablePaint);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			setAlphaForView(this, 1.0f);
	    }
		else if(event.getAction() == MotionEvent.ACTION_DOWN)
	    {
			setAlphaForView(this, 0.6f);
	    }
		return super.onTouchEvent(event);
	}

    public int dpToPx(int dp)
	{
	    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
	public void hideFab()
	{
		if(mHidden == false)
		{
            currentY = getYForView(this);
			animateY(this, currentY, mScreenHeight);
			mHidden = true;
		}
	}

    public void showFab()
	{
		if(mHidden == true)
		{
            animateY(this, mScreenHeight, currentY);
			mHidden = false;
		}
	}

    private static void animateY(View view, float current, float target) {
        FabUtils.animateY(view, current, target);
    }

    private static void setAlphaForView(View v, float alpha) {
        FabUtils.setAlphaForView(v, alpha);
    }

    private static void setLayerTypeForView(View view) {
        FabUtils.setLayerTypeForView(view);
    }

    private float getYForView(View view) {
        return FabUtils.getYForView(view);
    }
}