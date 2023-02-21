package org.schabi.newpipe.player.playqueue;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class PlayQueueTest3 {

    static PlayQueue makePlayQueue(final int index, final List<PlayQueueItem> streams) {
        // I tried using Mockito, but it didn't work for some reason
        return new PlayQueue(index, streams) {
            private boolean isComplete;
            @Override
            public boolean isComplete() {
                return isComplete;
            }
            @Override
            public void fetch() {
                final List<PlayQueueItem> items = new ArrayList<>(5);
                for (int i = 5; i < 10; ++i) {
                    items.add(makeItemWithUrl("URL_" + i));
                }
                this.append(items);
                isComplete = true;
            }
        };
    }

    static PlayQueueItem makeItemWithUrl(final String url) {
        final StreamInfoItem infoItem = new StreamInfoItem(
                0, url, "", StreamType.VIDEO_STREAM
        );
        return new PlayQueueItem(infoItem);
    }

    public static class FunctionalModelTests {
        private static final int SIZE = 5;
        private PlayQueue nonEmptyQueue;

        @Before
        public void setup() {
            final List<PlayQueueItem> streams = new ArrayList<>(5);
            for (int i = 0; i < 5; ++i) {
                streams.add(makeItemWithUrl("URL_" + i));
            }
            nonEmptyQueue = spy(makePlayQueue(0, streams));
        }

        @Test
        public void move() {
                final PlayQueueItem item = nonEmptyQueue.getItem(0);
                nonEmptyQueue.move(0, 4);
                assertEquals(item, nonEmptyQueue.getItem(4));
        }


    }
}
