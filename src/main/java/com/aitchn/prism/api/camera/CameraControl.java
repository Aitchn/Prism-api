package com.aitchn.prism.api.camera;

/**
 * Who decides where a viewer looks from and where they look.
 *
 * @since 3.24
 */
public enum CameraControl {
    /**
     * The server places the viewer's eye; the viewer turns their own head. For menus, previews and showrooms built in
     * the world, where the player looks around a scene they cannot walk out of.
     */
    ANCHORED,
    /**
     * The server places the camera and turns it; the viewer's mouse does not move the view. For cutscenes,
     * fly-throughs, boss introductions and other shots framed by the server.
     */
    DIRECTED,
    /** Fixed rendered camera with an independent packet-only passenger input seat. Java clients
     * continue sending their own look while the view follows the rig. Packet rigs only; spectator
     * options and an already mounted viewer are rejected. Does not capture mouse buttons. */
    DIRECTED_INPUT
}
