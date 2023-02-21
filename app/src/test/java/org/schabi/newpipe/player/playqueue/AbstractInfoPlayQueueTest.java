package org.schabi.newpipe.player.playqueue;

import junit.framework.TestCase;

import org.junit.Test;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.channel.ChannelInfo;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public class AbstractInfoPlayQueueTest extends TestCase {
    AbstractInfoPlayQueue<ChannelInfo> playQueue;

    public AbstractInfoPlayQueue<ChannelInfo> createPlayQueue() {
        return new AbstractInfoPlayQueue<ChannelInfo>(0, "url", null, new ArrayList<>(), 0) {
            @Override
            protected String getTag() {
                return "TestAbstractInfoPlayQueue";
            }

            @Override
            public void fetch() {
            }
        };
    }

    @Test
    public void testGetHeadListObserver() {
        playQueue = createPlayQueue();
        ChannelInfo channelInfo = new ChannelInfo(0, "", "url", "", "",
                new ListLinkHandler("", "", "", new ArrayList<>(), "")
        );
        channelInfo.setRelatedItems(new ArrayList<>());
        final Single<ChannelInfo> source = Single.just(channelInfo);
        source.subscribe(playQueue.getHeadListObserver());
        assertFalse(playQueue.isInitial);
    }

    @Test
    public void testGetNextPageObserver() {
        playQueue = createPlayQueue();
        playQueue.isInitial = false;
        ListExtractor.InfoItemsPage<StreamInfoItem> infoItemsPage = new ListExtractor.InfoItemsPage<>(
                new ArrayList<>(), new Page(""), new ArrayList<>()
        );
        final Single<ListExtractor.InfoItemsPage<StreamInfoItem>> source = Single.just(infoItemsPage);
        source.subscribe(playQueue.getNextPageObserver());
        assertTrue(playQueue.isComplete());
    }
}
