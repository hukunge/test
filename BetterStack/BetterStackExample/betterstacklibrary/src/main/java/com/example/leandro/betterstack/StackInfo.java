package com.example.leandro.betterstack;

import java.util.Map;

/**
 * Created by Leandro on 07/01/2016.
 * An interface for fragments which state should be saved and restored. Fragments that don't implement
 * {@link StackInfo StackInfo} can still be used with this stack.
 */
public interface StackInfo {
    /**
     * Called when retrieving a fragment from stack.
     * @param info A map with values set at {@link StackInfo#saveState()}
     */
    void restoreState(Map<String,Object> info);

    /**
     * Called when a fragment is about to be pushed into stack.
     * @return A map with values to be used at {@link StackInfo#restoreState(Map)}
     */
    Map<String,Object> saveState();
}
