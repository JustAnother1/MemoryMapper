package de.nomagic.input.mapfile;

public interface MemoryAreaCollection
{
    String[] getMemoryAreaNames();
    long getMemoryAreaSize(String areaName);
    long getMemoryAreaAddressMax(String areaName);
    long getMemoryAreaAddressMin(String areaName);

}
