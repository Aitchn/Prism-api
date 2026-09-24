package com.aitchn.prism.api.machine;

import java.util.List;

/** Pure PREPARE callback. Return one plan per declared output, or reject before any inputs are consumed. */
@FunctionalInterface
public interface MachineProcessPreparer {
    List<MachineProcessOutput> prepare(MachineProcessPreparation context);
}
