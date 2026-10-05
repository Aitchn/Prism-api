package com.aitchn.prism.api.camera;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/** Pure logical unit-cell ray picking; no world reads or display entity hitboxes. @since 3.25 */
public final class ScenePicking {
    private ScenePicking() { }
    public record Cell<T>(int x, int y, int z, T value) {
        public Cell { Objects.requireNonNull(value); }
    }
    public record Hit<T>(Cell<T> cell, double distance) { }

    /** Nearest included cell, stable input-order ties; range/distance in world units.
     * Cells occupy [x,x+1] etc. A ray beginning inside hits at distance zero. */
    public static <T> Optional<Hit<T>> pick(CameraPose view, SceneTransform transform,
            Collection<Cell<T>> cells, Predicate<Cell<T>> included, double range) {
        if (!Double.isFinite(range) || range < 0) throw new IllegalArgumentException("Invalid range");
        Objects.requireNonNull(cells); Objects.requireNonNull(included);
        double yaw = Math.toRadians(view.yaw()), pitch = Math.toRadians(view.pitch());
        double dx = -Math.sin(yaw)*Math.cos(pitch), dy = -Math.sin(pitch), dz = Math.cos(yaw)*Math.cos(pitch);
        var a = transform.inverse(new ScenePoint(view.x(), view.y(), view.z()));
        // Transform the direction directly: subtracting nearby world points loses precision far from zero.
        var d = transform.inverseDirection(new ScenePoint(dx,dy,dz));
        double[] origin = {a.x(), a.y(), a.z()}, direction = {d.x(),d.y(),d.z()};
        Hit<T> best = null;
        for (var cell : cells) {
            if (!included.test(cell)) continue;
            double near = 0, far = range;
            double[] min = {cell.x(), cell.y(), cell.z()};
            for (int i=0; i<3 && near<=far; i++) {
                if (direction[i] == 0) {
                    if (origin[i] < min[i] || origin[i] > min[i]+1) far = -1;
                } else {
                    double t0=(min[i]-origin[i])/direction[i], t1=(min[i]+1-origin[i])/direction[i];
                    near=Math.max(near, Math.min(t0,t1)); far=Math.min(far, Math.max(t0,t1));
                }
            }
            if (near<=far && (best==null || near<best.distance())) best=new Hit<>(cell,near);
        }
        return Optional.ofNullable(best);
    }
}
