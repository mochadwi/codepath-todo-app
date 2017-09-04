package io.mochadwi.todo_go.utils;

import android.view.View;

/**
 * Created by mochadwi on 9/4/17.
 */

public interface ClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}
