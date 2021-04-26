package android.slc.adapter;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreDateFormat3 {
    protected Application application;
    protected int offset;
    protected LoadModelCallback loadModelCallback;

    public LoadMoreDateFormat3(Application application) {
        this.application = application;
    }

    public void setLoadModelCallback(LoadModelCallback loadModelCallback) {
        this.loadModelCallback = loadModelCallback;
    }

    public LoadModelCallback getLoadModelCallback() {
        return loadModelCallback;
    }

    public LoadMoreDateFormat3() {
        this(1);
    }

    public LoadMoreDateFormat3(int offset) {
        this.offset = offset;
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, hToList(), pageModel);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, hToList(head), pageModel);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           List<H> headList,
                                                           @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, headList, pageModel.getListNoNull(), pageModel.getIsLastPage());
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull List<D> sourceList,
                                              boolean isLastPage) {
        formatSimple(targetList, hToList(), sourceList, isLastPage);
    }


    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        formatSimple(targetList, hToList(head), sourceList, isLastPage);
    }

    /**
     * 简单格式化全部参数
     *
     * @param targetList
     * @param headList
     * @param sourceList
     * @param isLastPage
     * @param <T>
     * @param <D>
     */
    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           List<H> headList,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        if (offset == 1) {
            targetList.clear();
            if (headList != null) {
                targetList.addAll(headList);
            }
        }
        targetList.addAll(sourceList);
        if (offset == 1) {
            if (loadModelCallback != null) {
                loadModelCallback.refresh(targetList);
            }
        }
        if (isLastPage) {
            if (loadModelCallback != null) {
                loadModelCallback.loadMoreEnd();
            }
        } else {
            if (loadModelCallback != null) {
                loadModelCallback.loadMoreComplete();
            }
        }
        if (targetList.isEmpty()) {
            return;
        }
        offset++;
    }

    protected <H> List<H> hToList(H obj) {
        List<H> dList = new ArrayList<>();
        if (obj != null) {
            dList.add(obj);
        }
        return dList;
    }

    protected <H> List<H> hToList() {
        return new ArrayList<>();
    }

    public void loadMoreFail() {
        if (loadModelCallback != null) {
            loadModelCallback.loadMoreFail();
            loadModelCallback.setRefreshing(false);
        }
    }

    /**
     * 刷新，做清楚数据操作
     */
    public void refresh() {
        offset = 1;
        if (loadModelCallback != null) {
            loadModelCallback.setRefreshing(true);
        }
    }

    public int getOffset() {
        return offset;
    }


    public interface LoadModelCallback {
        void refresh(List<?> list);

        void loadMoreEnd();

        void loadMoreComplete();

        void loadMoreFail();

        void setRefreshing(boolean refresh);
    }
}
