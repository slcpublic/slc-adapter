package android.slc.adapter.i;

public interface OnRefreshListener {

        /**
         * 刷新并设置是否完成下拉状态
         *
         * @param refreshing 是否完成下拉状态
         */
        void refresh(boolean refreshing);
    }