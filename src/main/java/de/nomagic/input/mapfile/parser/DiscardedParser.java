package de.nomagic.input.mapfile.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardedParser extends ParserBase
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public DiscardedParser(ParserFactory fac)
    {
        super(fac);
    }

    @Override
    public SectionParser parse(String line)
    {
        if(line.length() > 0)
        {
            // parse line
            if(line.contains("Memory Configuration"))
            {
                return fac.getParserFor(Section.MEMORY_CONFIG);
            }
            else
            {
                if(' ' == line.charAt(0))
                {
                    // ignore valid entry
                }
                else
                {
                    log.error("unspected line {}", line);
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

}
