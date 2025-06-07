package de.nomagic.input.mapfile.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkerParser extends ParserBase implements SectionParser
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public LinkerParser(ParserFactory fac)
    {
        super(fac);
    }

    @Override
    public SectionParser parse(String line)
    {
        if(line.length() > 0)
        {
            // parse line
            if(line.startsWith("LOAD"))
            {
                // LOAD a *.o file
                // -> ignore for now
                return this;
            }
            else if(line.startsWith("TARGET"))
            {
                // TARGET(binary) line
                // -> ignore for now
                return this;
            }
            else if('.' == line.charAt(0))
            {
                return fac.getParserFor(Section.LINKER_DOT);
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
