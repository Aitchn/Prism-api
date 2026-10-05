package com.aitchn.prism.api.camera;

/**
 * How a camera session presents the viewer to themselves. Like the session itself, both options exist only on the
 * viewer's client; other players and the server see the viewer as usual.
 *
 * @param standIn   show the viewer a copy of themselves (skin, held items and armour) where their server position is
 *                  held, because a client does not draw its own player while its camera is elsewhere or its eye is
 *                  moved; the copy turns its head as the viewer does and follows teleports
 * @param spectator show the viewer's client spectator mode for the session, which hides the hotbar, hands, health
 *                  and hunger and lets nothing the viewer clicks reach the world; the server keeps the viewer's real
 *                  game mode, which the client gets back when the session ends
 * @since 3.24
 */
public record CameraOptions(boolean standIn, boolean spectator) {
    /** A stand-in, and the viewer's own game mode. */
    public static final CameraOptions DEFAULT = new CameraOptions(true, false);

    public CameraOptions withStandIn(boolean value) {
        return new CameraOptions(value, spectator);
    }

    public CameraOptions withSpectator(boolean value) {
        return new CameraOptions(standIn, value);
    }
}
