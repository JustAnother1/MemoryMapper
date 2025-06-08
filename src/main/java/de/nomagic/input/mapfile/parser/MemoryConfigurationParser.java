package de.nomagic.input.mapfile.parser;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.input.mapfile.MemoryAreaCollection;

public class MemoryConfigurationParser extends ParserBase implements SectionParser, MemoryAreaCollection
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private boolean sawHeader = false;
    private HashMap<String, Long> startAddressMap = new HashMap<String, Long>();
    private HashMap<String, Long> sizeMap = new HashMap<String, Long>();

    public MemoryConfigurationParser(ParserFactory fac)
    {
        super(fac);
    }

    /*
    Name             Origin             Length             Attributes
    FLASH            0x10000000         0x00200000         xr
    RAM              0x20000000         0x00042000         xrw
    *default*        0x00000000         0xffffffff
    */

    @Override
    public SectionParser parse(String line)
    {
        if(line.length() > 0)
        {
            // parse line
            if(line.contains("Linker script and memory map"))
            {
                return fac.getParserFor(Section.LINKER);
            }
            else
            {
                if(false == sawHeader)
                {
                    if((line.contains("Name")) && (line.contains("Origin")) && (line.contains("Length")) && (line.contains("Attributes")))
                    {
                        sawHeader = true;
                    }
                    else
                    {
                        log.error("unspected line {}", line);
                    }
                }
                else
                {
                    // we saw the header so this is a entry for a memory area
                    int i = 0;
                    String areaName = null;
                    String[] parts = line.split(" ");
                    for(String part : parts)
                    {
                        if(0 < part.length())
                        {
                            switch(i)
                            {
                            // Name
                            case 0 : areaName = part; break;

                            // Origin
                            case 1 :
                                if(true == part.startsWith("0x"))
                                {
                                    long startAddress = Long.parseLong(part.substring(2), 16);
                                    startAddressMap.put(areaName, startAddress);
                                }
                                else
                                {
                                    log.error("Expected start address but saw '{}' in area {} !", part, areaName);
                                }
                                break;

                            // Length
                            case 2 :
                                if(true == part.startsWith("0x"))
                                {
                                    long size = Long.parseLong(part.substring(2), 16);
                                    sizeMap.put(areaName, size);
                                }
                                else
                                {
                                    log.error("Expected size but saw '{}' in area {} !", part, areaName);
                                }
                                break;

                            // Attributes
                            default:
                                    // ignore for now
                                    break;
                            }
                            i++;
                        }
                        // else an empty string
                    }
                }
                return this;
            }
        }
        else
        {
            // empty line
            return this;
        }
    }

    @Override
    public String[] getMemoryAreaNames()
    {
        return startAddressMap.keySet().toArray(new String[0]);
    }

    @Override
    public long getMemoryAreaSize(String areaName)
    {
        return sizeMap.get(areaName);
    }

    @Override
    public long getMemoryAreaAddressMax(String areaName)
    {
        return startAddressMap.get(areaName) + sizeMap.get(areaName);
    }

    @Override
    public long getMemoryAreaAddressMin(String areaName)
    {
        return startAddressMap.get(areaName);
    }

}
