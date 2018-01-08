package org.vontech.rollout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import org.vontech.rollout.R;

/**
 * An ImageView which provides options for rounded corners
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

public class RoundedImageView extends android.support.v7.widget.AppCompatImageView {

    private boolean topLeftSquare = false;
    private boolean topRightSquare = false;
    private boolean bottomLeftSquare = false;
    private boolean bottomRightSquare = false;
    private int cornerRadius = 0;

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoundedImageView,
                0, 0);

        try {
            topLeftSquare = a.getBoolean(R.styleable.RoundedImageView_topLeftSquare, false);
            topRightSquare = a.getBoolean(R.styleable.RoundedImageView_topRightSquare, false);
            bottomLeftSquare= a.getBoolean(R.styleable.RoundedImageView_bottomLeftSquare, false);
            bottomRightSquare = a.getBoolean(R.styleable.RoundedImageView_bottomRightSquare, false);
            cornerRadius = a.getInt(R.styleable.RoundedImageView_cornerRadius, 0);
        } finally {
            a.recycle();
        }
    }

    private Bitmap getRoundedCornerBitmap() {

        int w = getWidth();
        int h = getHeight();

        Drawable drawable = getDrawable();
        Context context = getContext();

        Bitmap b =  ((BitmapDrawable) drawable).getBitmap() ;
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = cornerRadius*densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


        //draw rectangles over the corners we want to be square
        if (topLeftSquare){
            canvas.drawRect(0, h/2, w/2, h, paint);
        }
        if (topRightSquare){
            canvas.drawRect(w/2, h/2, w, h, paint);
        }
        if (bottomLeftSquare){
            canvas.drawRect(0, 0, w/2, h/2, paint);
        }
        if (bottomRightSquare){
            canvas.drawRect(w/2, 0, w, h/2, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0,0, paint);

        return output;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap roundBitmap = getRoundedCornerBitmap();
        canvas.drawBitmap(roundBitmap, 0,0 , null);
    }



    public boolean isTopLeftSquare() {
        return topLeftSquare;
    }

    public boolean isTopRightSquare() {
        return topRightSquare;
    }

    public boolean isBottomLeftSquare() {
        return bottomLeftSquare;
    }

    public boolean isBottomRightSquare() {
        return bottomRightSquare;
    }

    public void setTopLeftSquare(boolean isSquare) {
        topLeftSquare = isSquare;
        invalidate();
        requestLayout();
    }

    public void setBottomLeftSquare(boolean isSquare) {
        bottomLeftSquare = isSquare;
        invalidate();
        requestLayout();
    }
    public void setTopRightSquare(boolean isSquare) {
        topRightSquare = isSquare;
        invalidate();
        requestLayout();
    }
    public void setBottomRightSquare(boolean isSquare) {
        bottomRightSquare = isSquare;
        invalidate();
        requestLayout();
    }

}
