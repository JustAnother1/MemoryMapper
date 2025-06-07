package de.nomagic.input.mapfile.parser;

public class ParserFactory
{

    public ParserFactory()
    {
    }

    public SectionParser getParserFor(Section sec)
    {
        switch(sec)
        {
        case LINKER_DOT: return new LinkerDotParser(this);
        case LINKER: return new LinkerParser(this);
        case MEMORY_CONFIG : return new MemoryConfigurationParser(this);
        case DISCARDED : return (SectionParser) new DiscardedParser(this);
        default: return null;
        }
    }

}
