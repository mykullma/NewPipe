package org.schabi.newpipe.player.playqueue;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.channel.ChannelInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;


public class ChannelPlayQueueTest1 extends TestCase {
    ChannelPlayQueue queue;
    Single<ChannelInfo> ob;
    ChannelInfo info;

    @Test
    public void testGetHeadListObserver() {
        queue=new ChannelPlayQueue(0, "", new Page(""), new ArrayList<>(), 0);
        assertFalse(queue.isComplete());
        info=mock(ChannelInfo.class);
        ob=Single.just(info);
        when(info.hasNextPage()).thenReturn(false);
        ob.subscribe(queue.getHeadListObserver());
        assertTrue(queue.isComplete());
    }
}
