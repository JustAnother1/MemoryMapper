package de.nomagic.input.mapfile.parser;

public class ParserFactory
{
    private LinkerDotParser ldp = null;
    private LinkerParser lp = null;
    private MemoryConfigurationParser mcp = null;
    private DiscardedParser dp = null;

    public ParserFactory()
    {
    }

    public SectionParser getParserFor(Section sec)
    {
        switch(sec)
        {
        case LINKER_DOT    : if(null == ldp) {ldp = new LinkerDotParser(this);} return ldp;
        case LINKER        : if(null == lp ) {lp  = new LinkerParser(this);} return lp;
        case MEMORY_CONFIG : if(null == mcp) {mcp = new MemoryConfigurationParser(this);} return mcp;
        case DISCARDED     : if(null == dp ) {dp  = new DiscardedParser(this);} return dp;
        default: return null;
        }
    }

}
