package com.bingo.activityresult;

import android.content.Intent;

public interface OnResultCallback {

    /**
     * Called while activity result is responded.
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
