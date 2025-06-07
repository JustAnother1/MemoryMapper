package de.nomagic.input.mapfile.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartParser extends ParserBase
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public StartParser(ParserFactory fac)
    {
        super(fac);
    }

    @Override
    public SectionParser parse(String line)
    {
        if(line.length() > 0)
        {
            // parse line
            if(line.contains("Discarded input sections"))
            {
                return fac.getParserFor(Section.DISCARDED);
            }
            else
            {
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
