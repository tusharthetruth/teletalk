package com.chatapp.pixly.interfaces;

import android.view.View;

import com.chatapp.pixly.modals.Img;

/**
 * Created by akshay on 07/05/18.
 */


public interface OnSelectionListener {
    void onClick(Img Img, View view, int position);

    void onLongClick(Img img, View view, int position);
}
