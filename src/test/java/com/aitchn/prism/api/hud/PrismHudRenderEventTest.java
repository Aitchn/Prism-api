package com.aitchn.prism.api.hud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.EnumSet;
import java.util.Set;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

class PrismHudRenderEventTest {
    private final Player player = mock(Player.class);
    private final HudTarget.Entity target = new HudTarget.Entity(mock(LivingEntity.class), Component.text("Mob"), 5, 20);

    private PrismHudRenderEvent event() {
        return new PrismHudRenderEvent(player, target, Component.text("Mob | 5 / 20"), .25F,
                BossBar.Color.RED, BossBar.Overlay.PROGRESS);
    }

    @Test
    void exposesCapturedTargetAndUsesSynchronousBukkitCancellationContract() {
        var event = event();
        assertSame(player, event.getPlayer());
        assertSame(target, event.getTarget());
        assertFalse(event.isAsynchronous());
        assertSame(PrismHudRenderEvent.getHandlerList(), event.getHandlers());
        assertFalse(event.isCancelled());
        event.setCancelled(true);
        assertTrue(event.isCancelled());
        event.setCancelled(false);
        assertFalse(event.isCancelled());
    }

    @Test
    void overridesPresentationWithoutChangingCapturedHealth() {
        var event = event();
        event.setTitle(target.name());
        event.setProgress(1);
        event.setColor(BossBar.Color.WHITE);
        event.setOverlay(BossBar.Overlay.NOTCHED_10);
        assertEquals(target.name(), event.getTitle());
        assertEquals(1, event.getProgress());
        assertEquals(BossBar.Color.WHITE, event.getColor());
        assertEquals(BossBar.Overlay.NOTCHED_10, event.getOverlay());
        assertEquals(5, target.health());
        assertEquals(20, target.maximumHealth());
    }

    @Test
    void rejectsInvalidOverridesBeforeChangingAcceptedValues() {
        var event = event();
        for (float invalid : new float[]{Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, -.01F, 1.01F}) {
            assertThrows(IllegalArgumentException.class, () -> event.setProgress(invalid));
            assertEquals(.25F, event.getProgress());
        }
        assertThrows(NullPointerException.class, () -> event.setTitle(null));
        assertThrows(NullPointerException.class, () -> event.setColor(null));
        assertThrows(NullPointerException.class, () -> event.setOverlay(null));
        assertThrows(NullPointerException.class, () -> event.setFlags(null));
    }

    @Test
    void flagsAreDefensivelyCopiedAndCannotLeakBetweenRefreshes() {
        var event = event();
        var flags = EnumSet.of(BossBar.Flag.DARKEN_SCREEN);
        event.setFlags(flags);
        flags.clear();
        assertEquals(Set.of(BossBar.Flag.DARKEN_SCREEN), event.getFlags());
        assertThrows(UnsupportedOperationException.class, () -> event.getFlags().clear());
        assertTrue(event().getFlags().isEmpty());
    }

    @Test
    void rejectsInvalidTargetSnapshots() {
        assertThrows(IllegalArgumentException.class,
                () -> new HudTarget.Entity(target.entity(), target.name(), 21, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new HudTarget.Entity(target.entity(), target.name(), Double.NaN, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new HudTarget.Entity(target.entity(), target.name(), 0, 0));
        assertThrows(NullPointerException.class, () -> new HudTarget.Block(null));
    }
}
