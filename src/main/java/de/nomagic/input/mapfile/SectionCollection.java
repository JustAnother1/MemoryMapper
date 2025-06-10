package de.nomagic.input.mapfile;

import java.util.Vector;

import de.nomagic.memory.MemoryLocation;

public interface SectionCollection
{
    String[] getSectionNames();
    long getSizeOfSection(String sectionName);
    long getSectionAddress(String sectionName);
    Vector<MemoryLocation> getLocationsOfSection(String sectionName);
}
