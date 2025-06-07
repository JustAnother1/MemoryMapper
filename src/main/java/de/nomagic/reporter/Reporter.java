package de.nomagic.reporter;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.cfg.McuConfig;
import de.nomagic.input.mapfile.MapFile;

public class Reporter
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private boolean valid = true;
    private String[] sectionNames;
    private MapFile map;
    private McuConfig mcfg;

    public Reporter(MapFile map)
    {
        this.map = map;
        sectionNames = map.getSectionNames();
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

    public void addMcuConfig(McuConfig mcfg)
    {
        this.mcfg = mcfg;
    }

    public void listSectionSizes()
    {
        System.out.println("Section sizes:");
        for(int i = 0; i < sectionNames.length; i++)
        {
            int size  = map.getSizeOfSection(sectionNames[i]);
            System.out.println(String.format("%-40s : %10d", sectionNames[i], size));
        }
    }

    public void showRamUsage()
    {
        int sum = 0;
        System.out.println("");
        System.out.println("RAM:");
        System.out.println("====");
        if(null != mcfg)
        {
            int max = mcfg.getRamSize();
            String[] sections = mcfg.getRamSectionNames();

            for(int i = 0; i < sections.length; i++)
            {
                int size  = map.getSizeOfSection(sections[i]);
                System.out.println(String.format("%-40s : %10d (%3.2f%%)", sections[i], size, ((float)size*100)/(float)max));
                sum = sum + size;
            }
            System.out.println(String.format("%-40s : %10s", "-----", "----------"));
            System.out.println(String.format("%-40s : %10d (%3.2f%%) (free : %d)", "total", sum, ((float)sum*100)/(float)max, max-sum));
        }
        System.out.println("");
    }

    public void showFlashUsage()
    {
        System.out.println("");
        System.out.println("Flash:");
        System.out.println("======");
        int sum = 0;
        if(null != mcfg)
        {
            int max = mcfg.getFlashSize();
            String[] sections = mcfg.getFlashSectionNames();

            for(int i = 0; i < sections.length; i++)
            {
                int size  = map.getSizeOfSection(sections[i]);
                System.out.println(String.format("%-40s : %10d (%3.2f%%)", sections[i], size, ((float)size*100)/(float)max));
                sum = sum + size;
            }
            System.out.println(String.format("%-40s : %10s", "-----", "----------"));
            System.out.println(String.format("%-40s : %10d (%3.2f%%)", "total", sum, ((float)sum*100)/(float)max));
        }
        System.out.println("");
    }

}
