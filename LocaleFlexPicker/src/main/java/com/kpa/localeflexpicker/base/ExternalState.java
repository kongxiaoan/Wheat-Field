package com.kpa.localeflexpicker.base;

/**
 * @author: kpa
 * @date: 2024/2/29
 * @description:
 */
public class ExternalState {
    private boolean isCommon;

    public ExternalState(boolean isCommon) {
        this.isCommon = isCommon;
    }

    public boolean isCommon() {
        return isCommon;
    }
}
