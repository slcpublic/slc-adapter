package android.slc.adapter;

import android.app.Activity;
import android.slc.adapter.i.OnLoadMoreListener;
import android.slc.adapter.i.OnRefreshListener;
import android.slc.adapter.i.OnRefreshingListener;
import android.slc.adapter.i.SwipeRecycler;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * 常用的下拉刷新的SwipeRecycler
 */
public abstract class CommonlySwipeRecycler<T> extends CommonlyRecycler<T> implements SwipeRecycler<T> {
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 更具activity加载，自定义swipeRefreshLayoutId和recyclerViewId
     *
     * @param activity
     * @param swipeRefreshLayoutId
     * @param recyclerViewId
     */
    public CommonlySwipeRecycler(@NonNull Activity activity, @IdRes int swipeRefreshLayoutId, @IdRes int recyclerViewId) {
        super(activity, recyclerViewId);
        mSwipeRefreshLayout = activity.findViewById(swipeRefreshLayoutId);
    }

    /**
     * 更具view加载，自定义swipeRefreshLayoutId和recyclerViewId
     *
     * @param view
     * @param swipeRefreshLayoutId
     * @param recyclerViewId
     */
    public CommonlySwipeRecycler(@NonNull View view, @IdRes int swipeRefreshLayoutId, @IdRes int recyclerViewId) {
        super(view, recyclerViewId);
        mSwipeRefreshLayout = view.findViewById(swipeRefreshLayoutId);
    }

    /**
     * 初始化视图
     */
    public CommonlySwipeRecycler<T> initView() {
        //SwipeRefreshLayout是否需要具有不确定性 故在此处做非空判断
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        }
        super.initView();
        return this;
    }

    /**
     * 获取SwipeRefreshLayout
     *
     * @return
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setOnSwipeRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
        super.setRefreshing(refreshing);
    }

    @Override
    public void loadMoreComplete() {
        mAdapter.getLoadMoreModule().loadMoreComplete();
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreFail() {
        if (mAdapter == null) {
            refresh();
        }
        if (mAdapter != null) {//首次加载失败时mAdapter可能为空
            mAdapter.getLoadMoreModule().loadMoreFail();
        }
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.loadMoreFail();
        }
    }

    @Override
    public void loadMoreEnd() {
        mAdapter.getLoadMoreModule().loadMoreEnd();
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.loadMoreEnd();
        }
    }

    public static class CommonlySwipeRecyclerBuilder<T> {
        protected Activity mActivity;
        protected View mView;
        protected int mRecyclerViewId = R.id.recyclerView;
        protected int mSwipeRefreshId = R.id.swipeRefreshLayout;
        protected RecyclerView.LayoutManager mLayoutManager;
        protected OnRefreshingListener mOnRefreshingListener;
        protected OnRefreshListener mOnRefreshListener;
        protected OnLoadAdapterLaterListener<T> mOnLoadAdapterLaterListener;
        protected SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener;
        protected OnLoadMoreListener mOnLoadMoreListener;
        protected DiffUtil.ItemCallback<T> mItemCallback;
        protected boolean mIsAllowTouch = true;

        public CommonlySwipeRecyclerBuilder(@NonNull Activity activity) {
            mActivity = activity;
        }

        public CommonlySwipeRecyclerBuilder(@NonNull View view) {
            mView = view;
        }

        public CommonlySwipeRecyclerBuilder<T> setRecyclerViewId(@IdRes int recyclerViewId) {
            mRecyclerViewId = recyclerViewId;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setSwipeRefreshId(@IdRes int swipeRefreshId) {
            mSwipeRefreshId = swipeRefreshId;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setOnRefreshingListener(OnRefreshingListener mOnRefreshingListener) {
            this.mOnRefreshingListener = mOnRefreshingListener;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
            this.mOnRefreshListener = mOnRefreshListener;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setOnLoadAdapterLaterListener(OnLoadAdapterLaterListener<T> onLoadAdapterLaterListener) {
            this.mOnLoadAdapterLaterListener = onLoadAdapterLaterListener;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setSwipeRefreshListener(SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener) {
            this.mSwipeRefreshListener = mSwipeRefreshListener;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setItemCallback(DiffUtil.ItemCallback<T> mItemCallback) {
            this.mItemCallback = mItemCallback;
            return this;
        }

        public CommonlySwipeRecyclerBuilder<T> setIsAllowTouch(boolean mIsAllowTouch) {
            this.mIsAllowTouch = mIsAllowTouch;
            return this;
        }

        public CommonlySwipeRecycler<T> build(@NonNull final OnLoadAdapterListener<T> onLoadAdapterListener) {
            CommonlySwipeRecycler<T> commonlySwipeRecycler;
            if (mActivity != null) {
                commonlySwipeRecycler = new CommonlySwipeRecycler<T>(mActivity, mSwipeRefreshId, mRecyclerViewId) {
                    @NonNull
                    @Override
                    public BaseQuickAdapter<T, BaseViewHolder> initAdapter() {
                        return onLoadAdapterListener.loadAdapter();
                    }

                    @Override
                    public void initAdapterLater(BaseQuickAdapter<T, BaseViewHolder> adapter) {
                        if (mOnLoadAdapterLaterListener != null) {
                            mOnLoadAdapterLaterListener.loadAdapterLater(adapter);
                        }
                    }
                };
            } else if (mView != null) {
                commonlySwipeRecycler = new CommonlySwipeRecycler<T>(mView, mSwipeRefreshId, mRecyclerViewId) {
                    @NonNull
                    @Override
                    public BaseQuickAdapter<T, BaseViewHolder> initAdapter() {
                        return onLoadAdapterListener.loadAdapter();
                    }

                    @Override
                    public void initAdapterLater(BaseQuickAdapter<T, BaseViewHolder> adapter) {
                        if (mOnLoadAdapterLaterListener != null) {
                            mOnLoadAdapterLaterListener.loadAdapterLater(adapter);
                        }
                    }
                };
            } else {
                throw new NullPointerException("mActivity 或 mView必须其中一个不为空");
            }
            commonlySwipeRecycler.setLayoutManager(mLayoutManager);
            commonlySwipeRecycler.setOnRefreshingListener(mOnRefreshingListener);
            commonlySwipeRecycler.setOnRefreshListener(mOnRefreshListener);
            commonlySwipeRecycler.setOnSwipeRefreshListener(mSwipeRefreshListener);
            commonlySwipeRecycler.setOnLoadMoreListener(mOnLoadMoreListener);
            commonlySwipeRecycler.setItemCallback(mItemCallback);
            commonlySwipeRecycler.setIsAllowTouch(mIsAllowTouch);
            commonlySwipeRecycler.initView();
            return commonlySwipeRecycler;
        }
    }
}
