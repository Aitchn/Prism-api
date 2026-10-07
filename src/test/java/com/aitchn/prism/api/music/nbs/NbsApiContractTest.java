package com.aitchn.prism.api.music.nbs;

import com.aitchn.prism.api.music.MusicPlayback;
import com.aitchn.prism.api.music.MusicService;
import com.aitchn.prism.api.music.MusicTrack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NbsApiContractTest {
    @Test
    void oldMusicServiceImplementorsKeepTheirOriginalMethodAndRejectNbsByDefault() {
        MusicService legacy = new MusicService() {
            public MusicPlayback play(Plugin owner, MusicTrack track, String section) { return null; }
        };
        assertFalse(legacy.supportsNbsImport());
        assertThrows(UnsupportedOperationException.class, () -> legacy.importNbs(null, null, null));
        assertThrows(UnsupportedOperationException.class, () -> legacy.playNbs(null, null, null));
    }

    @Test
    void cueCopiesItsCollectionsAndRejectsUnsafeResourceKeys() {
        var sections = new ArrayList<>(List.of(new NbsCue.Section("main", 0, 16, 4, 4, null)));
        var cue = new NbsCue(sections, Map.of(), Map.of(), false);
        sections.clear();
        assertEquals(1, cue.sections().size());
        assertThrows(UnsupportedOperationException.class, () -> cue.sections().clear());
        assertThrows(UnsupportedOperationException.class, () -> cue.layerStems().put(0, "bad"));
        assertThrows(NbsException.class, () -> new NbsCue(cue.sections(), Map.of(), Map.of(16, Key.key("a:a/../b")), false));
    }

    @Test
    void reportCopiesDetailedListsAndPreservesTheExplicitTimingPolicy() {
        var changes = new ArrayList<>(List.of(new NbsImport.TimingChange(0, 1, 0, "main", 1, 25)));
        var indices = new ArrayList<>(List.of(0, 1));
        var collisions = new ArrayList<>(List.of(new NbsImport.Collision("main", 1, indices)));
        var report = new NbsImport.Report(2, 2, 0, 1, 1, 25, 12.5, 0, List.of(),
                NbsCue.TimingPolicy.NEAREST_TICK, changes, collisions);
        indices.clear(); changes.clear(); collisions.clear();
        assertEquals(List.of(0, 1), report.collisions().getFirst().noteIndices());
        assertEquals(1, report.timingChanges().size());
        assertThrows(UnsupportedOperationException.class, () -> report.timingChanges().clear());
        assertThrows(UnsupportedOperationException.class, () -> report.collisions().getFirst().noteIndices().clear());
        assertThrows(IllegalArgumentException.class, () -> new NbsImport.Report(2, 2, 0, 1, 1, 25, 12.5, 0, List.of(),
                NbsCue.TimingPolicy.EXACT, report.timingChanges(), report.collisions()));
    }

    @Test
    void versionAndInputLimitsArePublicAndTypedErrorsCarryStableContext() {
        assertEquals(8 * 1024 * 1024, NbsSong.MAX_BYTES);
        assertEquals(65536, NbsSong.MAX_NOTES);
        assertEquals(256, NbsSong.MAX_LAYERS);
        NbsException failure = new NbsException(NbsException.Code.TRUNCATED, 9, "name", "Truncated name");
        assertEquals(9, failure.offset());
        assertEquals("name", failure.field());
        assertEquals(NbsException.Code.TRUNCATED, failure.code());
    }
}
