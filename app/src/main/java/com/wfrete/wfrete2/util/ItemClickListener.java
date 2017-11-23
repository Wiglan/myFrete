package com.wfrete.wfrete2.util;

import android.view.View;

import com.wfrete.wfrete2.model.Frete;

/**
 * Created by Desenvolvimento 11 on 16/11/2017.
 */

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}