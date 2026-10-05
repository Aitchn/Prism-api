package com.aitchn.prism.api.input;

/** SNAPSHOT is a baseline, not a press; RESET is not a physical release. */
public enum InputPhase { PULSE, SNAPSHOT, BEGIN, CHANGE, END, HOLD, RESET }
