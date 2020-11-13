package android.slc.adapter.i;

import java.util.List;

public interface Recycler<T> extends OnRefreshListener, OnRefreshingListener {
    /**
     * 刷新
     */
    void refresh();

    void refresh(List<T> data);

    void refresh(boolean refreshing, List<T> data);

    void notifyItemChanged(int position);

    void notifyItemChanged(int positionStart, int itemCount);

}
