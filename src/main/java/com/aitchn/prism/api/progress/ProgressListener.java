package com.aitchn.prism.api.progress;

@FunctionalInterface
public interface ProgressListener {
    void changed(ProgressChange change);
}
