package android.slc.adapter;

import androidx.collection.SimpleArrayMap;

/**
 * @author slc
 * @date 2020-08-28 15:53
 */
public class SelectBox<T> extends SimpleArrayMap<String, Object> {

    public SelectBox(T data) {
        this(data, -1);
    }

    public SelectBox(T data, int index) {
        this(data, index, false);
    }

    public SelectBox(T data, boolean select) {
        this(data, -1, select);
    }

    public SelectBox(T data, int index, boolean select) {
        setData(data);
        setSelectIndex(index);
        setChecked(select);
    }

    public T getData() {
        return (T) get("data");
    }

    public void setData(T data) {
        put("data", data);
    }

    public int getSelectIndex() {
        return (int) get("selectIndex");
    }

    public void setSelectIndex(int index) {
        put("selectIndex", index);
    }

    public boolean isChecked() {
        return (boolean) get("checked");
    }

    public void setChecked(boolean checked) {
        put("checked", checked);
    }
}
