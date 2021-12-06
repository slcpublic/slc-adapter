package android.slc.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: sunlunchang
 * @CreateDate: 2021/12/06 下午 5:47
 */
public class SelectUtils {

    public static <T> void fillSelect(List<T> selectList, List<SelectBox<T>> selectBoxList) {
        fillSelect(selectList, selectBoxList, Objects::equals, false);
    }

    public static <T> void fillSelect(List<T> selectList, List<SelectBox<T>> selectBoxList, boolean defFalse) {
        fillSelect(selectList, selectBoxList, Objects::equals, defFalse);
    }

    public static <T> void fillSelect(List<T> selectList, List<SelectBox<T>> selectBoxList, Predicate<T> predicate) {
        fillSelect(selectList, selectBoxList, predicate, false);
    }

    public static <T> void fillSelect(List<T> selectList, List<SelectBox<T>> selectBoxList, Predicate<T> predicate, boolean maintain) {
        SelectUtils.forAllDo(selectBoxList, (index, item) -> {
            if (!maintain) {
                item.setChecked(false);
            }
            item.setChecked(false);
            if (selectList == null || predicate == null) return;
            for (T itemByExists : selectList) {
                if (predicate.evaluate(item.getData(), itemByExists)) {
                    item.setChecked(true);
                    break;
                }
            }
        });
    }

    private static <E> void forAllDo(Collection<E> collection, Closure<E> closure) {
        if (collection == null || closure == null) return;
        int index = 0;
        for (E e : collection) {
            closure.execute(index++, e);
        }
    }

    private interface Closure<E> {
        void execute(int index, E item);
    }

    public interface Predicate<T> {
        boolean evaluate(T src, T beCompared);
    }
}
