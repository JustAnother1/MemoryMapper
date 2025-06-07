package de.nomagic.input.mapfile.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryConfigurationParser extends ParserBase implements SectionParser
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public MemoryConfigurationParser(ParserFactory fac)
    {
        super(fac);
    }

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
                /*
                Name             Origin             Length             Attributes
                FLASH            0x10000000         0x00200000         xr
                RAM              0x20000000         0x00042000         xrw
                *default*        0x00000000         0xffffffff
                */
                // -> ignore for now
                log.error("unspected line {}", line);
                return this;
            }
        }
        else
        {
            // empty line
            return this;
        }
    }

}
