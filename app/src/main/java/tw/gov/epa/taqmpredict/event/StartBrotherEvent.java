package tw.gov.epa.taqmpredict.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by user on 2017/2/14.
 */

public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
