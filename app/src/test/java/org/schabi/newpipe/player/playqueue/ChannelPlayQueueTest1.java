package org.schabi.newpipe.player.playqueue;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.channel.ChannelInfo;

import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;


public class ChannelPlayQueueTest1 extends TestCase {
    ChannelPlayQueue queue;
    SingleObserver ob;
    ChannelInfo info;

    @Test
    public void testGetHeadListObserver() {
        queue=mock(ChannelPlayQueue.class);
        ob=mock(SingleObserver.class);
        info=mock(ChannelInfo.class);
        when(queue.getHeadListObserver()).thenReturn(ob);
        when(info.hasNextPage()).thenReturn(true);
        queue.getHeadListObserver().onSuccess(info);
        assertFalse(queue.isComplete());

    }

}
