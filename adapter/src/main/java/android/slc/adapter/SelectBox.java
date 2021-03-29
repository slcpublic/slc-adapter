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

    public SelectBox(T data, int index, boolean select) {
        setData(data);
        setIndex(index);
        setSelect(select);
    }

    public T getData() {
        return (T) get("data");
    }

    public void setData(T data) {
        put("data", data);
    }

    public int getIndex() {
        return (int) get("index");
    }

    public void setIndex(int index) {
        put("index", index);
    }

    public boolean isSelect() {
        return (boolean) get("select");
    }

    public void setSelect(boolean select) {
        put("select", select);
    }
}
