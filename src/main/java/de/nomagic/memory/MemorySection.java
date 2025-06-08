package de.nomagic.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemorySection
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private boolean valid = true;
    private String name = null;
    private int startAddress = -1;
    private int size = -1;
    private String description = null;

    // line is something like this ".text           0x10000000      0x200" or this ".third_stage_boot"
    public MemorySection(String line)
    {
        if(null == line)
        {
            valid = false;
            return;
        }
        String[] parts = line.split(" ");
        addParts(parts);
    }

    private void addParts(String[] parts)
    {
        for(int i = 0; i < parts.length; i++)
        {
            String cur = parts[i];
            if(0 < cur.length())
            {
                if(null == name)
                {
                    if('.' == cur.charAt(0))
                    {
                        name = cur;
                    }
                    else
                    {
                        log.error("Expected section name but saw '{}' !", cur);
                    }
                }
                else if(-1 == startAddress)
                {
                    if(true == cur.startsWith("0x"))
                    {
                        startAddress = Integer.parseInt(cur.substring(2), 16);
                    }
                    else
                    {
                        log.error("Expected start address but saw '{}' in section {} !", cur, name);
                        valid = false;
                    }
                }
                else if(-1 == size)
                {
                    if(true == cur.startsWith("0x"))
                    {
                        size = Integer.parseInt(cur.substring(2), 16);
                        if(size > 100000000)
                        {
                            // to big for size -> must be an address !
                            log.error("Found {} as size for {} !", cur, name);
                        }
                    }
                    else
                    {
                        // this is just a label for something
                        size = 0;
                        description = cur;
                    }
                }
                else if(null == description)
                {
                    description = cur;
                }
                else
                {
                    // next line
                    // if()
                }
            }
        }
    }

    public void addLine(String line)
    {
        if(null == line)
        {
            return;
        }
        if(1 > line.length())
        {
            return;
        }
        String[] parts = line.split(" ");
        addParts(parts);
    }

    public int getSize()
    {
        if(false == valid)
        {
            return -1;
        }
        // else :

        return size;
    }

    public Object getName()
    {
        return name;
    }

    public int getAddress()
    {
        return startAddress;
    }

}
