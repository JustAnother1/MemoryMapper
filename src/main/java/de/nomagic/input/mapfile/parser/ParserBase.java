package de.nomagic.input.mapfile.parser;

public abstract class ParserBase implements SectionParser
{
    protected final ParserFactory fac;

    public ParserBase(ParserFactory fac)
    {
        this.fac = fac;
    }

}
