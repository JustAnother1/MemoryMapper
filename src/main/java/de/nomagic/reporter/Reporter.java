package de.nomagic.reporter;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.input.mapfile.MapFile;
import de.nomagic.input.mapfile.MemoryAreaCollection;
import de.nomagic.input.mapfile.SectionCollection;

public class Reporter
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private boolean valid = true;
    private String[] sectionNames;
    private SectionCollection allSections;
    private MemoryAreaCollection allMemoryAreas;

    public Reporter(MapFile map)
    {
        allSections = map.getSectionCollection();
        allMemoryAreas = map.getAllMemoryAreas();
        sectionNames = allSections.getSectionNames();
        if(1 > sectionNames.length)
        {
            log.error("Found no section in map file !");
            valid = false;
        }
        Arrays.sort(sectionNames);
    }

    public boolean isValid()
    {
        return valid;
    }

    public void listSectionSizes()
    {
        System.out.println("Section sizes:");
        for(int i = 0; i < sectionNames.length; i++)
        {
            long size  = allSections.getSizeOfSection(sectionNames[i]);
            System.out.println(String.format("%-40s : %10d", sectionNames[i], size));
        }
    }

    public void showMemoryAreaUsage()
    {
        String[] names = allMemoryAreas.getMemoryAreaNames();
        for(int i = 0; i < names.length; i++)
        {
            long sum = 0;
            System.out.println("");
            System.out.println(names[i] + ":");
            System.out.println("=============");
            long max = allMemoryAreas.getMemoryAreaSize(names[i]);
            long addrMin = allMemoryAreas.getMemoryAreaAddressMin(names[i]);
            long addrMax = addrMin + max;
            String[] sections = allSections.getSectionNames();
            for(int k = 0; k < sections.length; k++)
            {
                long addr = allSections.getSectionAddress(sections[k]);
                if((addr >= addrMin) && (addr <= addrMax))
                {
                    long size  = allSections.getSizeOfSection(sections[k]);
                    System.out.println(String.format("%-40s : %10d (%3.2f%%)", sections[k], size, ((float)size*100)/(float)max));
                    sum = sum + size;
                }
                // else skip as not in this memory area
            }
            System.out.println(String.format("%-40s : %10s", "-----", "----------"));
            System.out.println(String.format("%-40s : %10d (%3.2f%%) (free : %d)", "total", sum, ((float)sum*100)/(float)max, max-sum));

            System.out.println("");
        }
    }

}
