package android.slc.adapter.click;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

/**
 * 防止重复点击点击
 */
public class SlcOnItemChildClickListenerProxy implements OnItemChildClickListener {

    private OnItemChildClickListener origin;
    private long lastClick = 0;
    private long timers; //ms
    private IAgain mIAgain;

    public SlcOnItemChildClickListenerProxy(OnItemChildClickListener origin) {
        this(origin, 280);
    }

    public SlcOnItemChildClickListenerProxy(OnItemChildClickListener origin, long timers) {
        this(origin, null, timers);
    }


    public SlcOnItemChildClickListenerProxy(OnItemChildClickListener origin, IAgain again, long timers) {
        this.origin = origin;
        this.mIAgain = again;
        this.timers = timers;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (System.currentTimeMillis() - lastClick >= timers) {
            origin.onItemChildClick(adapter, view, position);
            lastClick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}