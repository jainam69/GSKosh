package com.gskose.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class DownOnlyAutoCompleteTextView  extends AppCompatAutoCompleteTextView {

    private final static int MINIMAL_HEIGHT = 55;

    public DownOnlyAutoCompleteTextView(Context context) {
        super(context);
    }

    public DownOnlyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownOnlyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void showDropDown() {
        Rect displayFrame = new Rect();
        getWindowVisibleDisplayFrame(displayFrame);

        int[] locationOnScreen = new int[2];
        getLocationOnScreen(locationOnScreen);

        int bottom = locationOnScreen[1] + getHeight();
        int availableHeightBelow = displayFrame.bottom - bottom;
        if (availableHeightBelow >= MINIMAL_HEIGHT) {
            setDropDownHeight(availableHeightBelow);
        }

        super.showDropDown();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)) {
                return true;
            }
        }

        return super.onKeyPreIme(keyCode, event);
    }
}