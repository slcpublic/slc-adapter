package android.slc.adapter;

import android.content.Context;
import android.slc.adapter.i.SwipeRecycler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;


public class LoadMoreDateFormat2 {
    protected Context applicationContext;
    protected SwipeRecycler swipeRecycler;
    protected int offset = 1;

    public LoadMoreDateFormat2(Context applicationContext, SwipeRecycler swipeRecycler) {
        this.applicationContext = applicationContext.getApplicationContext();
        this.swipeRecycler = swipeRecycler;
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, pageModel, true);
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull PageModel<D> pageModel, boolean isShowToast) {
        formatSimple(targetList, hToList(), pageModel, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head, @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, head, pageModel, true);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head, @NonNull PageModel<D> pageModel, boolean isShowToast) {
        formatSimple(targetList, hToList(head), pageModel, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           List<H> headList, @NonNull PageModel<D> pageModel) {
        formatSimple(targetList, headList, pageModel, true);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           List<H> headList, @NonNull PageModel<D> pageModel,
                                                           boolean isShowToast) {
        formatSimple(targetList, headList, pageModel.getListNoNull(), pageModel.getIsLastPage(), isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        formatSimple(targetList, head, sourceList, isLastPage, true);
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull List<D> sourceList,
                                              boolean isLastPage) {
        formatSimple(targetList, sourceList, isLastPage, true);
    }

    public <T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                              @NonNull List<D> sourceList,
                                              boolean isLastPage,
                                              boolean isShowToast) {
        formatSimple(targetList, hToList(), sourceList, isLastPage, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           H head,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage,
                                                           boolean isShowToast) {
        formatSimple(targetList, hToList(head), sourceList, isLastPage, isShowToast);
    }

    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
                                                           List<H> headList,
                                                           @NonNull List<D> sourceList,
                                                           boolean isLastPage) {
        formatSimple(targetList, headList, sourceList, isLastPage, true);
    }

    /**
     * 简单格式化全部参数
     *
     * @param targetList
     * @param headList
     * @param sourceList
     * @param isLastPage
     * @param isShowToast
     * @param <T>
     * @param <D>
     */
    public <T, H extends T, D extends T> void formatSimple(@NonNull List<T> targetList,
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
        loadMoreFail(false);
    }

    public void loadMoreFail(boolean isShowToast) {
        loadMoreFail(R.string.label_data_acquisition_failed, isShowToast);
    }

    public void loadMoreFail(@StringRes int toastId) {
        loadMoreFail(toastId, true);
    }

    public void loadMoreFail(@StringRes int toastId, boolean isShowToast) {
        if (isShowToast) {
            toast(toastId);
        }
        swipeRecycler.setRefreshing(false);
        swipeRecycler.loadMoreFail();
    }

    public void loadMoreFail(@NonNull String toastText) {
        loadMoreFail(toastText, true);
    }

    public void loadMoreFail(@NonNull String toastText, boolean isShowToast) {
        if (isShowToast) {
            toast(toastText);
        }
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
     */
    public void refresh() {
        offset = 1;
        swipeRecycler.setRefreshing(true);
    }

    public int getOffset() {
        return offset;
    }
}
