package com.mtoo.bbmath.helpers;
/**
 * Provides a square layout
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLinearLayout extends LinearLayout {

	public SquareLinearLayout(Context context) {
		super(context);
	}

	public SquareLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//This call requires API11
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public SquareLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		double ASPECT_RATIO = 1.0;

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		if (width > (int)((ASPECT_RATIO * height) + 0.5)) {
			width = (int)((ASPECT_RATIO * height) + 0.5);
		} else {
			height = (int)((width / ASPECT_RATIO) + 0.5);
		}

		super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

	}
}
