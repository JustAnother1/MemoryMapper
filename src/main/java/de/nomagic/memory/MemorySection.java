package de.nomagic.memory;

import java.util.Vector;

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
    private MemoryLocation curLocation = null;
    private Vector<MemoryLocation> locations = new Vector<MemoryLocation>();

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
        if(true == line.startsWith(" ."))
        {
            // start of new memory location
            curLocation = new MemoryLocation(line);
            locations.add(curLocation);
        }
        else if(true == line.startsWith(" *"))  // *fill* or heading
        {
            // if it has a address and size -> section -> else ignore
            int numHex = 0;
            String[] parts = line.split(" ");
            for(String part : parts)
            {
                if(true == part.startsWith("0x"))
                {
                    numHex++;
                }
            }
            if(1 < numHex)
            {
                curLocation = new MemoryLocation(line);
                locations.add(curLocation);
            }
            else
            {
                // probably just a header like " *(.third_stage_boot .third_stage_boot.*)"
                // -> ignore for now
            }
        }
        else if(true == line.startsWith("  "))
        {
            // add to current memory location
            if(null == curLocation)
            {
                // might belong to the section specification
                String[] parts = line.split(" ");
                addParts(parts);
            }
            else
            {
                curLocation.add(line);
            }
        }
        else
        {
            log.error("Expected line in memory secions ({})!", line);
        }
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

    public String getName()
    {
        return name;
    }

    public int getAddress()
    {
        return startAddress;
    }

    public Vector<MemoryLocation> getLocations()
    {
        return locations;
    }

}
