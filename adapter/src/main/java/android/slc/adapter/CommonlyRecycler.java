package android.slc.adapter;

import android.app.Activity;
import android.content.Context;
import android.slc.adapter.i.OnRefreshListener;
import android.slc.adapter.i.OnRefreshingListener;
import android.slc.adapter.i.Recycler;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public abstract class CommonlyRecycler<T> implements Recycler<T> {
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected OnRefreshingListener mOnRefreshingListener;
    protected OnRefreshListener mOnRefreshListener;
    protected Context mContext;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected DiffUtil.ItemCallback<T> mItemCallback;
    protected boolean mIsAllowTouch = true;

    /**
     * 更具activity加载，自定义swipeRefreshLayoutId和recyclerViewId
     *
     * @param activity
     * @param recyclerViewId
     */
    public CommonlyRecycler(@NonNull Activity activity, @IdRes int recyclerViewId) {
        mContext = activity;
        mRecyclerView = activity.findViewById(recyclerViewId);
    }

    /**
     * 更具view加载，自定义swipeRefreshLayoutId和recyclerViewId
     *
     * @param view
     * @param recyclerViewId
     */
    public CommonlyRecycler(@NonNull View view, @IdRes int recyclerViewId) {
        mContext = view.getContext();
        mRecyclerView = view.findViewById(recyclerViewId);
    }

    /**
     * 初始化视图
     */
    public CommonlyRecycler<T> initView() {
        if (this.mLayoutManager != null) {
            mRecyclerView.setLayoutManager(this.mLayoutManager);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return super.canScrollVertically() && mIsAllowTouch;
                }

                @Override
                public boolean canScrollHorizontally() {
                    return super.canScrollHorizontally() && mIsAllowTouch;
                }
            });
        }

        return this;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public void setOnRefreshingListener(OnRefreshingListener onRefreshingListener) {
        this.mOnRefreshingListener = onRefreshingListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public void setItemCallback(DiffUtil.ItemCallback<T> mItemCallback) {
        this.mItemCallback = mItemCallback;
    }

    public void setIsAllowTouch(boolean isAllowTouch) {
        this.mIsAllowTouch = isAllowTouch;
    }

    public boolean isIsAllowTouch() {
        return this.mIsAllowTouch;
    }

    /**
     * 获取适配器
     *
     * @return
     */
    public BaseQuickAdapter<T, BaseViewHolder> getAdapter() {
        return mAdapter;
    }

    /**
     * 初始化适配器
     *
     * @return
     */
    @NonNull
    public abstract BaseQuickAdapter<T, BaseViewHolder> initAdapter();

    public abstract void initAdapterLater(BaseQuickAdapter<T, BaseViewHolder> adapter);

    @Override
    public void refresh() {
        refresh(false);
    }

    @Override
    public void refresh(List<T> data) {
        refresh(false, data);
    }

    @Override
    public void refresh(boolean refreshing) {
        setRefreshing(refreshing);
        if (mAdapter == null) {
            mAdapter = initAdapter();
            mRecyclerView.setAdapter(mAdapter);
            initAdapterLater(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        if (mOnRefreshListener != null) {
            mOnRefreshListener.refresh(refreshing);
        }
    }

    @Override
    public void refresh(boolean refreshing, List<T> data) {
        setRefreshing(refreshing);
        if (mAdapter == null) {
            mAdapter = initAdapter();
            if (this.mItemCallback != null) {
                mAdapter.setDiffCallback(this.mItemCallback);
            }
            mRecyclerView.setAdapter(mAdapter);
            initAdapterLater(mAdapter);
            mAdapter.setDiffNewData(data);
        } else {
            mAdapter.setDiffNewData(data);
        }
        if (mOnRefreshListener != null) {
            mOnRefreshListener.refresh(refreshing);
        }
    }

    @Override
    public void notifyItemChanged(int position) {
        if (mAdapter == null) {
            throw new IllegalStateException("Adapter没有初始化");
        } else {
            mAdapter.notifyItemChanged(position);
        }
    }


    @Override
    public void notifyItemChanged(int positionStart, int itemCount) {
        if (mAdapter == null) {
            throw new IllegalStateException("Adapter没有初始化");
        } else {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }
    }


    @Override
    public void setRefreshing(boolean refreshing) {
        mIsAllowTouch = !refreshing;
        if (mOnRefreshingListener != null) {
            mOnRefreshingListener.setRefreshing(refreshing);
        }
    }

    public interface OnLoadAdapterListener<T> {
        BaseQuickAdapter<T, BaseViewHolder> loadAdapter();
    }

    public interface OnLoadAdapterLaterListener<T> {
        void loadAdapterLater(BaseQuickAdapter<T, BaseViewHolder> adapter);
    }

    public static class CommonlyRecyclerBuilder<T> {
        protected Activity mActivity;
        protected View mView;
        protected int mRecyclerViewId = R.id.recyclerView;
        protected RecyclerView.LayoutManager mLayoutManager;
        protected OnRefreshingListener mOnRefreshingListener;
        protected OnRefreshListener mOnRefreshListener;
        protected OnLoadAdapterLaterListener<T> mOnLoadAdapterLaterListener;
        protected DiffUtil.ItemCallback<T> mItemCallback;
        protected boolean mIsAllowTouch = true;

        public CommonlyRecyclerBuilder(@NonNull Activity activity) {
            mActivity = activity;
        }

        public CommonlyRecyclerBuilder(@NonNull View view) {
            mView = view;
        }

        public CommonlyRecyclerBuilder<T> setRecyclerViewId(@IdRes int recyclerViewId) {
            mRecyclerViewId = recyclerViewId;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setOnRefreshingListener(OnRefreshingListener mOnRefreshingListener) {
            this.mOnRefreshingListener = mOnRefreshingListener;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
            this.mOnRefreshListener = mOnRefreshListener;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setOnLoadAdapterLaterListener(OnLoadAdapterLaterListener<T> onLoadAdapterLaterListener) {
            this.mOnLoadAdapterLaterListener = onLoadAdapterLaterListener;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setItemCallback(DiffUtil.ItemCallback<T> mItemCallback) {
            this.mItemCallback = mItemCallback;
            return this;
        }

        public CommonlyRecyclerBuilder<T> setIsAllowTouch(boolean mIsAllowTouch) {
            this.mIsAllowTouch = mIsAllowTouch;
            return this;
        }

        public CommonlyRecycler<T> build(@NonNull final OnLoadAdapterListener<T> onLoadAdapterListener) {
            CommonlyRecycler<T> commonlyRecycler;
            if (mActivity != null) {
                commonlyRecycler = new CommonlyRecycler<T>(mActivity, mRecyclerViewId) {
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
                }.initView();
            } else if (mView != null) {
                commonlyRecycler = new CommonlyRecycler<T>(mView, mRecyclerViewId) {
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
                }.initView();
            } else {
                throw new NullPointerException("mActivity 或 mView必须其中一个不为空");
            }
            commonlyRecycler.setLayoutManager(mLayoutManager);
            commonlyRecycler.setOnRefreshingListener(mOnRefreshingListener);
            commonlyRecycler.setOnRefreshListener(mOnRefreshListener);
            commonlyRecycler.setItemCallback(mItemCallback);
            commonlyRecycler.setIsAllowTouch(mIsAllowTouch);
            return commonlyRecycler;
        }
    }
}
