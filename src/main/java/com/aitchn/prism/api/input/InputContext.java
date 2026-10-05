package com.aitchn.prism.api.input;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import com.aitchn.prism.api.camera.CameraControl;

/** Last owner-tick context; not a packet-time world snapshot or an authorization token. */
public record InputContext(UUID world, String gameMode, Optional<CameraControl> camera,
                           boolean spectatorPresentation, boolean mounted, String containerType,
                           Optional<UUID> vehicleId) {
    public InputContext {
        Objects.requireNonNull(world); Objects.requireNonNull(gameMode); Objects.requireNonNull(camera);
        Objects.requireNonNull(containerType);
        Objects.requireNonNull(vehicleId);
    }
    public InputContext(UUID world, String gameMode, Optional<CameraControl> camera,
                        boolean spectatorPresentation, boolean mounted, String containerType) {
        this(world,gameMode,camera,spectatorPresentation,mounted,containerType,Optional.empty());
    }
    public boolean spectator() { return spectatorPresentation || gameMode.equals("SPECTATOR"); }
}
