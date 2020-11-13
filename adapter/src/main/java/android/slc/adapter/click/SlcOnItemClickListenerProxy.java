package android.slc.adapter.click;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

/**
 * 防止重复点击点击
 */
public class SlcOnItemClickListenerProxy implements OnItemClickListener {

    private OnItemClickListener origin;
    private long lastClick = 0;
    private long timers; //ms
    private IAgain mIAgain;

    public SlcOnItemClickListenerProxy(OnItemClickListener origin) {
        this(origin, 280);
    }

    public SlcOnItemClickListenerProxy(OnItemClickListener origin, long timers) {
        this(origin, null, timers);
    }


    public SlcOnItemClickListenerProxy(OnItemClickListener origin, IAgain again, long timers) {
        this.origin = origin;
        this.mIAgain = again;
        this.timers = timers;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (System.currentTimeMillis() - lastClick >= timers) {
            origin.onItemClick(adapter,view,position);
            lastClick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}