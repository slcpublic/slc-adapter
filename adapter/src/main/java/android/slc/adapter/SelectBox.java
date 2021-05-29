package android.slc.adapter;

import java.util.LinkedHashMap;

/**
 * @author slc
 * @date 2020-08-28 15:53
 */
public class SelectBox<T> extends LinkedHashMap<String, Object> {

    public SelectBox(T data) {
        this(data, -1);
    }

    public SelectBox(T data, int index) {
        this(data, index, false);
    }

    public SelectBox(T data, boolean checked) {
        this(data, -1, checked);
    }

    public SelectBox(T data, int index, boolean checked) {
        setData(data);
        setSelectIndex(index);
        setChecked(checked);
    }

    public T getData() {
        return (T) get("data");
    }

    public void setData(T data) {
        put("data", data);
    }

    public int getSelectIndex() {
        Object value = get("selectIndex");
        if (value instanceof Integer) {
            return (int) value;
        }
        return -1;
    }

    public void setSelectIndex(int index) {
        put("selectIndex", index);
    }

    public boolean isChecked() {
        Object value = get("checked");
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        return false;
    }

    public void setChecked(boolean checked) {
        put("checked", checked);
    }
}
