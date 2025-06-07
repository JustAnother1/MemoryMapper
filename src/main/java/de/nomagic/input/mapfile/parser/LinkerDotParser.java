package de.nomagic.input.mapfile.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkerDotParser extends ParserBase implements SectionParser
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public LinkerDotParser(ParserFactory fac)
    {
        super(fac);
    }

    @Override
    public SectionParser parse(String line)
    {
        // TODO Auto-generated method stub
        return this;
    }

}
