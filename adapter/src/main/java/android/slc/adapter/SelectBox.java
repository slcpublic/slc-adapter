package android.slc.adapter;

/**
 * @author slc
 * @date 2020-08-28 15:53
 */
public class SelectBox<T> {
    private T data;
    private int index;

    public SelectBox(T data) {
        this.data = data;
    }

    public SelectBox(T data, int index) {

        this.data = data;
        this.index = index;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
