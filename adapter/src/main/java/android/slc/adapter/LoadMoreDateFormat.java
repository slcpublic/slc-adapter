package android.slc.adapter;

import android.content.Context;
import android.slc.adapter.i.SwipeRecycler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class LoadMoreDateFormat {
    protected Context applicationContext;
    protected int offset = 1;

    public LoadMoreDateFormat(Context applicationContext) {
        this.applicationContext = applicationContext.getApplicationContext();
    }

    public <T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                              @NonNull PageModel<D> pageModel) {
        formatSimple(swipeRecycler, targetList, pageModel, true);
    }

    public <T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                              @NonNull PageModel<D> pageModel, boolean isShowToast) {
        formatSimple(swipeRecycler, targetList, new ArrayList<>(), pageModel, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           H head, @NonNull PageModel<D> pageModel) {
        formatSimple(swipeRecycler, targetList, head, pageModel, true);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           H head, @NonNull PageModel<D> pageModel, boolean isShowToast) {
        formatSimple(swipeRecycler, targetList, objToList(head), pageModel, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           List<H> headList, @NonNull PageModel<D> pageModel) {
        formatSimple(swipeRecycler, targetList, headList, pageModel, true);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           List<H> headList, @NonNull PageModel<D> pageModel,
                                                           boolean isShowToast) {
        formatSimple(swipeRecycler, targetList, headList, pageModel.getListNoNull(), pageModel.getIsLastPage(), isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        formatSimple(swipeRecycler, targetList, head, sourceList, isLastPage, true);
    }

    public <T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                              @NonNull List<D> sourceList,
                                              boolean isLastPage) {
        formatSimple(swipeRecycler, targetList, sourceList, isLastPage, true);
    }

    public <T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                              @NonNull List<D> sourceList,
                                              boolean isLastPage,
                                              boolean isShowToast) {
        formatSimple(swipeRecycler, targetList, new ArrayList<>(), sourceList, isLastPage, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage,
                                                           boolean isShowToast) {
        formatSimple(swipeRecycler, targetList, objToList(head), sourceList, isLastPage, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           List<H> headList,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        formatSimple(swipeRecycler, targetList, headList, sourceList, isLastPage, true);
    }

    /**
     * 简单格式化全部参数
     *
     * @param swipeRecycler
     * @param targetList
     * @param headList
     * @param sourceList
     * @param isLastPage
     * @param isShowToast
     * @param <T>
     * @param <D>
     */
    public <T, H extends T, D extends T> void formatSimple(@NonNull SwipeRecycler swipeRecycler, @NonNull List<T> targetList,
                                                           List<H> headList,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage,
                                                           boolean isShowToast) {
        if (offset == 1) {
            targetList.clear();
            if (headList != null) {
                targetList.addAll(headList);
            }
        }
        targetList.addAll(sourceList);
        if (offset == 1) {
            swipeRecycler.refresh();
        }
        if (isLastPage) {
            swipeRecycler.loadMoreEnd();
        } else {
            swipeRecycler.loadMoreComplete();
        }
        if (targetList.isEmpty() && isShowToast) {
            toast(R.string.label_there_is_no_data_at_present);
            return;
        }
        offset++;
    }

    protected <H> List<H> objToList(H obj) {
        List<H> dList = new ArrayList<>();
        if (obj != null) {
            dList.add(obj);
        }
        return dList;
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler) {
        loadMoreFail(swipeRecycler, false);
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler, boolean isShowToast) {
        loadMoreFail(swipeRecycler, R.string.label_data_acquisition_failed, isShowToast);
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler, @StringRes int toastId) {
        loadMoreFail(swipeRecycler, toastId, true);
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler, @StringRes int toastId, boolean isShowToast) {
        if (isShowToast)
            toast(toastId);
        swipeRecycler.setRefreshing(false);
        swipeRecycler.loadMoreFail();
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler, @NonNull String toastText) {
        loadMoreFail(swipeRecycler, toastText, true);
    }

    public void loadMoreFail(SwipeRecycler swipeRecycler, @NonNull String toastText, boolean isShowToast) {
        if (isShowToast)
            toast(toastText);
        swipeRecycler.setRefreshing(false);
        swipeRecycler.loadMoreFail();
    }

    private void toast(@StringRes int toastId) {
        Toast.makeText(applicationContext, toastId, Toast.LENGTH_SHORT).show();
    }

    private void toast(String toastText) {
        Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show();
    }

    /**
     * 刷新，做清楚数据操作
     *
     * @param swipeRecycler
     */
    public void refresh(SwipeRecycler swipeRecycler) {
        offset = 1;
        swipeRecycler.setRefreshing(true);
    }

    public int getOffset() {
        return offset;
    }
}
