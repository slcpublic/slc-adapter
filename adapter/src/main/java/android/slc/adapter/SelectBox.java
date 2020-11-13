package android.slc.adapter;

/**
 * @author slc
 * @date 2020-08-28 15:53
 */
public class SelectBox<T> {
    private T data;
    private boolean select;

    public SelectBox(T data) {
        this.data = data;
    }

    public SelectBox(T data, boolean select) {
        this.data = data;
        this.select = select;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
