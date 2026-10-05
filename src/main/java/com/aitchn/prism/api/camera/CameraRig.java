package com.aitchn.prism.api.camera;

import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * What carries a viewer's camera.
 *
 * @since 3.24
 */
public sealed interface CameraRig {
    /**
     * A camera entity that exists only on the viewer's client. Prism creates it at {@code start} and the owner moves
     * it with {@link CameraSession#move} and {@link CameraSession#play}; no other player and no server state is
     * affected.
     *
     * @param world the world the camera is in, which must be the viewer's
     */
    record Packet(UUID world, CameraPose start) implements CameraRig {
        public Packet {
            Objects.requireNonNull(world, "world");
            Objects.requireNonNull(start, "start");
        }
    }

    /**
     * A server entity the camera follows, such as an armor stand or display the owner moves, a mob for a boss's
     * point of view, or a vehicle. The server moves and tracks the entity as usual; Prism attaches the viewer's
     * camera to it on their client, and again whenever the client is sent the entity anew.
     */
    record Following(Entity entity) implements CameraRig {
        public Following {
            Objects.requireNonNull(entity, "entity");
        }
    }

    /** A client-only camera starting at {@code location} (its world, position, yaw and pitch). */
    static CameraRig at(Location location) {
        Objects.requireNonNull(location, "location");
        Objects.requireNonNull(location.getWorld(), "location world");
        return new Packet(location.getWorld().getUID(), CameraPose.of(location));
    }

    /** A camera that follows {@code entity}. */
    static CameraRig following(Entity entity) {
        return new Following(entity);
    }
}
