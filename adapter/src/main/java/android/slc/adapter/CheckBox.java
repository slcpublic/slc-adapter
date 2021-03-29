package android.slc.adapter;

/**
 * @author slc
 * @date 2020-08-28 15:53
 */
public class CheckBox<T> {
    private T data;
    private boolean select;

    public CheckBox(T data) {
        this.data = data;
    }

    public CheckBox(T data, boolean select) {

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
