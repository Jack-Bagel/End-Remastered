package com.teamremastered.endrem.platform.services;

public interface IConfigHelper {

    //TODO: Compatible config system for both Forge and Fabric
    boolean throwEnderEye();

    boolean useEnderEye();

    boolean frameHasEye();

    int eyeBreakChance();

    boolean canRemoveEye();

    boolean isCrypticObtainable();

    boolean isEvilObtainable();
}