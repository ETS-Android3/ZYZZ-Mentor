package com.dhaini.zyzz;

import org.json.JSONException;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition) throws JSONException;
    void onItemSwiped(int position);
}
