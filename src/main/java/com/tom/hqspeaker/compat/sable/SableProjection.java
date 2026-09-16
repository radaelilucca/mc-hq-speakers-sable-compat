package com.tom.hqspeaker.compat.sable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Optional bridge to Sable. Reflection keeps this compatibility add-on safe to
 * load in packs that do not install Sable.
 */
public final class SableProjection {
    private static final AtomicBoolean PROJECTED = new AtomicBoolean();
    private static final AtomicBoolean WARNED = new AtomicBoolean();
    private static volatile Method projectOutOfSubLevel;
    private static volatile Object helper;
    private static volatile boolean resolved;

    private SableProjection() {
    }

    /**
     * Returns the position in the parent level, or {@code null} when the block
     * is not inside a Sable sublevel (or Sable is absent).
     */
    public static Vec3 project(Level level, Vec3 localPosition) {
        if (level == null || localPosition == null || !resolve()) return null;

        try {
            Object result = projectOutOfSubLevel.invoke(helper, level, localPosition);
            if (result instanceof Vec3 position) {
                if (PROJECTED.compareAndSet(false, true)) {
                    System.out.println("[HQ Speakers Sable Compat] Sable projection active: "
                        + localPosition + " -> " + position);
                }
                return position;
            }
            return null;
        } catch (ReflectiveOperationException exception) {
            warnOnce("Could not project HQ Speakers audio out of a Sable sublevel", exception);
            return null;
        }
    }

    private static boolean resolve() {
        if (resolved) return helper != null && projectOutOfSubLevel != null;

        synchronized (SableProjection.class) {
            if (resolved) return helper != null && projectOutOfSubLevel != null;
            resolved = true;

            try {
                Class<?> sable = Class.forName("dev.ryanhcode.sable.Sable");
                Field helperField = sable.getField("HELPER");
                Object candidate = helperField.get(null);
                Method projection = candidate.getClass().getMethod("projectOutOfSubLevel", Level.class, Vec3.class);
                helper = candidate;
                projectOutOfSubLevel = projection;
            } catch (ClassNotFoundException ignored) {
                // Sable is optional; world coordinates supplied by HQ Speakers remain correct.
            } catch (ReflectiveOperationException exception) {
                warnOnce("Could not resolve Sable's sublevel projection API", exception);
            }

            return helper != null && projectOutOfSubLevel != null;
        }
    }

    private static void warnOnce(String message, Exception exception) {
        if (WARNED.compareAndSet(false, true)) {
            System.err.println("[HQ Speakers Sable Compat] " + message + ": " + exception.getMessage());
        }
    }
}
