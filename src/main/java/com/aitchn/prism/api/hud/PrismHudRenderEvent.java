package com.aitchn.prism.api.hud;

import java.util.Objects;
import java.util.Set;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called on the viewer's entity scheduler before each eligible targeted HUD refresh,
 * including unchanged displays. Both viewer and target are owned by the current region.
 * Cancellation hides Prism's existing HUD for this viewer for this refresh. Overrides
 * expire at the next refresh and never modify the entity's actual health or the world.
 * Register using Bukkit's normal listener lifecycle and priority rules; MONITOR is
 * observation-only. Do not retain this mutable event or use its handles asynchronously.
 *
 * @since 3.24
 */
public final class PrismHudRenderEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final HudTarget target;
    private Component title;
    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;
    private Set<BossBar.Flag> flags = Set.of();
    private boolean cancelled;

    public PrismHudRenderEvent(Player viewer, HudTarget target, Component title, float progress,
                               BossBar.Color color, BossBar.Overlay overlay) {
        super(Objects.requireNonNull(viewer, "viewer"));
        this.target = Objects.requireNonNull(target, "target");
        setTitle(title);
        setProgress(progress);
        setColor(color);
        setOverlay(overlay);
    }

    public HudTarget getTarget() {
        return target;
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = Objects.requireNonNull(title, "title");
    }

    public float getProgress() {
        return progress;
    }

    /** Sets presentation progress only; rejects non-finite values and values outside 0..1. */
    public void setProgress(float progress) {
        if (!Float.isFinite(progress) || progress < 0 || progress > 1) {
            throw new IllegalArgumentException("Progress must be finite and within 0..1");
        }
        this.progress = progress;
    }

    public BossBar.Color getColor() {
        return color;
    }

    public void setColor(BossBar.Color color) {
        this.color = Objects.requireNonNull(color, "color");
    }

    public BossBar.Overlay getOverlay() {
        return overlay;
    }

    public void setOverlay(BossBar.Overlay overlay) {
        this.overlay = Objects.requireNonNull(overlay, "overlay");
    }

    /** Returns an immutable set. Use setFlags to replace it. */
    public Set<BossBar.Flag> getFlags() {
        return flags;
    }

    public void setFlags(Set<BossBar.Flag> flags) {
        this.flags = Set.copyOf(flags);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
