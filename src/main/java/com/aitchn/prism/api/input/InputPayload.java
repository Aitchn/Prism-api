package com.aitchn.prism.api.input;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

/** Immutable values only. Packet targets are untrusted references, not resolved Bukkit objects. */
public sealed interface InputPayload {
    enum Hand { MAIN, OFF }
    record Empty() implements InputPayload { }
    record Look(float yaw, float pitch, double deltaYaw, double deltaPitch) implements InputPayload {
        public Look {
            if (!Float.isFinite(yaw) || !Float.isFinite(pitch) || !Double.isFinite(deltaYaw)
                    || !Double.isFinite(deltaPitch)) throw new IllegalArgumentException("Non-finite look");
        }
    }
    record Movement(boolean forward, boolean backward, boolean left, boolean right) implements InputPayload { }
    record Flag(boolean active) implements InputPayload { }
    record Point(double x, double y, double z) {
        public Point {
            if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z))
                throw new IllegalArgumentException("Non-finite point");
        }
    }
    record Block(int x, int y, int z, String face) {
        public Block { Objects.requireNonNull(face); }
    }
    record Swing(Hand hand) implements InputPayload {
        public Swing { Objects.requireNonNull(hand); }
    }
    record EntityTarget(int entityId, Optional<Hand> hand, Optional<Point> hit,
                        Optional<Boolean> secondaryAction) implements InputPayload {
        public EntityTarget { Objects.requireNonNull(hand); Objects.requireNonNull(hit); Objects.requireNonNull(secondaryAction); }
    }
    record BlockTarget(Block block, Hand hand, Point hit, Optional<Boolean> insideBlock,
                       Optional<Boolean> worldBorderHit) implements InputPayload {
        public BlockTarget {
            Objects.requireNonNull(block); Objects.requireNonNull(hand); Objects.requireNonNull(hit);
            Objects.requireNonNull(insideBlock); Objects.requireNonNull(worldBorderHit);
        }
    }
    record UseRequest(Hand hand, float yaw, float pitch) implements InputPayload {
        public UseRequest {
            Objects.requireNonNull(hand);
            if (!Float.isFinite(yaw) || !Float.isFinite(pitch)) throw new IllegalArgumentException("Non-finite use look");
        }
    }
    /** The release packet has no hand. Only an observed active use can supply this association. */
    record UseRelease(Optional<Hand> hand, OptionalLong sessionId) implements InputPayload {
        public UseRelease { Objects.requireNonNull(hand); Objects.requireNonNull(sessionId); }
    }
    /** Native server use, not right-button state; request correlation is deliberately unknown in v1. */
    record UseState(long sessionId, Hand hand, String itemType, int ticksUsed,
                    OptionalLong requestEventId) implements InputPayload {
        public UseState { Objects.requireNonNull(hand); Objects.requireNonNull(itemType); Objects.requireNonNull(requestEventId); }
    }
    enum DigAction { START, ABORT, FINISH }
    record Dig(DigAction action, Block block) implements InputPayload {
        public Dig { Objects.requireNonNull(action); Objects.requireNonNull(block); }
    }
    record Hotbar(int slot, OptionalInt previousSlot) implements InputPayload {
        public Hotbar {
            if (slot < 0 || slot > 8) throw new IllegalArgumentException("Slot outside hotbar");
            Objects.requireNonNull(previousSlot);
        }
    }
    record Drop(boolean entireStack) implements InputPayload { }
    enum ContainerAction { PICKUP, QUICK_MOVE, SWAP, CLONE, THROW, QUICK_CRAFT, PICKUP_ALL }
    /** Native transaction metadata only; prediction hashes and real inventory are never rewritten. */
    record ContainerClick(int windowId, OptionalInt stateId, int slot, int button,
                          ContainerAction action) implements InputPayload {
        public ContainerClick { Objects.requireNonNull(stateId); Objects.requireNonNull(action); }
    }
    record ContainerButton(int windowId, int button) implements InputPayload { }
    record ContainerClose(int windowId) implements InputPayload { }
    record VehicleMove(Point position, float yaw, float pitch, boolean onGround) implements InputPayload {
        public VehicleMove {
            Objects.requireNonNull(position);
            if (!Float.isFinite(yaw) || !Float.isFinite(pitch)) throw new IllegalArgumentException("Non-finite vehicle look");
        }
    }
    record Paddles(boolean left, boolean right) implements InputPayload { }
    enum CommandKind { START_SPRINTING, STOP_SPRINTING, START_RIDING_JUMP, STOP_RIDING_JUMP,
        STOP_SLEEPING, OPEN_INVENTORY, START_FALL_FLYING }
    record Command(CommandKind kind, int data) implements InputPayload {
        public Command { Objects.requireNonNull(kind); }
    }
    record Reset(InputResetReason reason) implements InputPayload {
        public Reset { Objects.requireNonNull(reason); }
    }
}
