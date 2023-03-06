package org.schabi.newpipe.player.playqueue;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.channel.ChannelInfo;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChannelPlayQueueTest {

    @Test
    public void fetch() {
        ExtractorHelperFunctions extractorHelper = new ExtractorHelperFunctions() {
            @Override
            public Single<ChannelInfo> getChannelInfo(int serviceId, String url, boolean forceLoad) {
                ChannelInfo channelInfo = new ChannelInfo(0, "", "url", "", "",
                        new ListLinkHandler("", "", "", new ArrayList<>(), "")
                );
                channelInfo.setRelatedItems(new ArrayList<>());
                return Single.just(channelInfo);
            }

            @Override
            public Single<ListExtractor.InfoItemsPage<StreamInfoItem>> getMoreChannelItems(int serviceId, String url, Page nextPage) {
                ListExtractor.InfoItemsPage<StreamInfoItem> infoItemsPage = new ListExtractor.InfoItemsPage<>(
                        new ArrayList<>(), new Page("next page"), new ArrayList<>()
                );
                return Single.just(infoItemsPage);
            }

            @Override
            public Scheduler mainThread() {
                return Schedulers.trampoline();
            }

            @Override
            public Scheduler io() {
                return Schedulers.trampoline();
            }
        };

        ChannelPlayQueue channelPlayQueue = new ChannelPlayQueue(0, "", new Page("page"), new ArrayList<>(), 0);
        channelPlayQueue.isInitial = false;
        assertEquals("page", channelPlayQueue.nextPage.getUrl());
        channelPlayQueue.fetch(extractorHelper);
        assertEquals("next page", channelPlayQueue.nextPage.getUrl());
    }
}