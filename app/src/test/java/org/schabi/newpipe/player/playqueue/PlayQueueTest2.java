package org.schabi.newpipe.player.playqueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class PlayQueueTest2 {

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
        public void case0() {
            assertFalse(nonEmptyQueue.isComplete());
            assertFalse(nonEmptyQueue.isShuffled());
        }

        @Test
        public void case1() {
            nonEmptyQueue.fetch();
            assertTrue(nonEmptyQueue.isComplete());
            assertFalse(nonEmptyQueue.isShuffled());
        }

        @Test
        public void case2() {
            nonEmptyQueue.shuffle();
            nonEmptyQueue.fetch();
            assertTrue(nonEmptyQueue.isShuffled());
            assertTrue(nonEmptyQueue.isComplete());
        }

        @Test
        public void case3() {
            nonEmptyQueue.shuffle();
            assertTrue(nonEmptyQueue.isShuffled());
            assertFalse(nonEmptyQueue.isComplete());
        }

        @Test
        public void case4() {
            nonEmptyQueue.shuffle();
            nonEmptyQueue.unshuffle();
            assertFalse(nonEmptyQueue.isShuffled());
            assertFalse(nonEmptyQueue.isComplete());
        }

        @Test
        public void case5() {
            nonEmptyQueue.fetch();
            final PlayQueueItem currentItem = nonEmptyQueue.getItem();
            nonEmptyQueue.shuffle();
            assertTrue(nonEmptyQueue.isComplete());
            assertTrue(nonEmptyQueue.isShuffled());
            assertEquals(currentItem, nonEmptyQueue.getItem());
        }

        @Test
        public void case6() {
            nonEmptyQueue.shuffle();
            nonEmptyQueue.fetch();
            nonEmptyQueue.unshuffle();
            assertFalse(nonEmptyQueue.isShuffled());
            assertTrue(nonEmptyQueue.isComplete());
        }

    }
}
