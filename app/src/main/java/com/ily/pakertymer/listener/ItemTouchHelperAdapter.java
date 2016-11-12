package com.ily.pakertymer.listener;

/**
 * Created by ily on 21.10.2016.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
