package android.slc.adapter.i;

public interface OnLoadMoreListener {

        /**
         * 加载更多完成
         */
        void loadMoreComplete();

        /**
         * 加载更多失败
         */
        void loadMoreFail();

        /**
         * 加载结束
         */
        void loadMoreEnd();
    }