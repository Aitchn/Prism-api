package com.aitchn.prism.api.input;

@FunctionalInterface
public interface InputListener {
    InputPropagation onInput(InputEvent event);
}
