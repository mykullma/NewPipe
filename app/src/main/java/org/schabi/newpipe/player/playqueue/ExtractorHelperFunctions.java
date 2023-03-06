package org.schabi.newpipe.player.playqueue;

import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.channel.ChannelInfo;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;

public interface ExtractorHelperFunctions {

    Single<ChannelInfo> getChannelInfo(final int serviceId, final String url, final boolean forceLoad);

    Single<ListExtractor.InfoItemsPage<StreamInfoItem>> getMoreChannelItems(final int serviceId, final String url, final Page nextPage);

    Scheduler mainThread();

    Scheduler io();
}
