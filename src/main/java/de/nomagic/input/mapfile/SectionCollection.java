package de.nomagic.input.mapfile;

public interface SectionCollection
{
    String[] getSectionNames();
    long getSizeOfSection(String sectionName);
    long getSectionAddress(String sectionName);
}
