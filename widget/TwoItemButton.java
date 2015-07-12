import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by michael.wheeler on 6/8/2015.
 * <p/>
 * Based on a Stacked overflow answer by atomicode
 * <p/>
 * http://stackoverflow.com/questions/4817449/how-to-have-image-and-text-center-within-a-button
 */
public class TwoItemButton
        extends Button{
    private static final int LEFT = 0, RIGHT = 2;

    // Pre-allocate objects for layout measuring
    private Rect textBounds = new Rect();
    private Rect drawableBounds = new Rect();

    public TwoItemButton(Context context){
        super(context, null);
    }

    public TwoItemButton(Context context, AttributeSet attrs){
        super(context, attrs, android.R.attr.buttonStyle);
    }

    public TwoItemButton(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);

        if(!changed){ return; }

        final CharSequence text = getText();
        if(!TextUtils.isEmpty(text)){
            TextPaint textPaint = getPaint();
            textPaint.getTextBounds(text.toString(), 0, text.length(), textBounds);
        }
        else{
            textBounds.setEmpty();
        }

        final int width = getWidth() - (getPaddingLeft() + getPaddingRight());

        final Drawable[] drawables = getCompoundDrawables();

        if(drawables[LEFT] != null){
            drawables[LEFT].copyBounds(drawableBounds);
            int leftOffset =
                    (width - (textBounds.width() + drawableBounds.width()) + getRightPaddingOffset()) / 2 - getCompoundDrawablePadding();
            drawableBounds.offset(leftOffset, 0);
            drawables[LEFT].setBounds(drawableBounds);
        }

        if(drawables[RIGHT] != null){
            drawables[RIGHT].copyBounds(drawableBounds);
            int rightOffset =
                    ((textBounds.width() + drawableBounds.width()) - width + getLeftPaddingOffset()) / 2 + getCompoundDrawablePadding();
            drawableBounds.offset(rightOffset, 0);
            drawables[RIGHT].setBounds(drawableBounds);
        }
    }

    public void setDrawableLeft(int imageId){
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(imageId, 0, 0, 0);
    }
}
