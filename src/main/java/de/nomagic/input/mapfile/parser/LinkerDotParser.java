package de.nomagic.input.mapfile.parser;

import java.util.HashMap;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.input.mapfile.SectionCollection;
import de.nomagic.memory.MemoryLocation;
import de.nomagic.memory.MemorySection;

public class LinkerDotParser extends ParserBase implements SectionParser, SectionCollection
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private MemorySection mem;
    private HashMap<String, MemorySection> sections = new HashMap<String, MemorySection>();

    public LinkerDotParser(ParserFactory fac)
    {
        super(fac);
        mem = null;
    }

    @Override
    public SectionParser parse(String line)
    {
        if(0 < line.length())
        {
            char c = line.charAt(0);
            if('.' == c)
            {
                // new section
                int idx = line.indexOf(' ');
                String name;
                if(-1 == idx)
                {
                    // no space after section name
                    name = line.substring(1);
                }
                else
                {
                    name = line.substring(1, line.indexOf(' '));
                }
                log.trace("found section '{}' !", name);
                mem = new MemorySection(line);
                sections.put(name, mem);
            }
            else if(' ' == c)
            {
                // another line for this section
                mem.addLine(line);
            }
            else if(true == line.startsWith("OUTPUT"))
            {
                // ignore for now
            }
            else if(true == line.startsWith("LOAD"))
            {
                // ignore for now
            }
            else
            {
                log.error("unexpected line {} in section {}", line, mem.getName());
            }
        }
        else
        {
            // empty line
        }
        return this;
    }

    @Override
    public String[] getSectionNames()
    {
        return sections.keySet().toArray(new String[0]);
    }

    @Override
    public long getSizeOfSection(String sectionName)
    {
        MemorySection curSec = sections.get(sectionName);
        if(null != curSec)
        {
            return curSec.getSize();
        }
        else
        {
            return -1;
        }
    }

    @Override
    public long getSectionAddress(String sectionName)
    {
        MemorySection curSec = sections.get(sectionName);
        if(null != curSec)
        {
            return curSec.getAddress();
        }
        else
        {
            return -1;
        }
    }

    public Vector<MemoryLocation> getLocationsOfSection(String sectionName)
    {
        MemorySection curSec = sections.get(sectionName);
        if(null != curSec)
        {
            return curSec.getLocations();
        }
        else
        {
            return null;
        }
    }

}
