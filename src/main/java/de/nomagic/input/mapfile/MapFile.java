package de.nomagic.input.mapfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import de.nomagic.input.mapfile.parser.LinkerDotParser;
import de.nomagic.input.mapfile.parser.ParserFactory;
import de.nomagic.input.mapfile.parser.Section;
import de.nomagic.input.mapfile.parser.StartParser;
import de.nomagic.input.mapfile.parser.SectionParser;

public class MapFile
{
    private final boolean valid;
    private final ParserFactory fac = new ParserFactory();

    public MapFile(Reader in) throws IOException
    {
        SectionParser curSection = new StartParser(fac);
        BufferedReader reader = new BufferedReader(in);
        // read line by line
        String line = reader.readLine();
        while(line != null)
        {
            curSection = curSection.parse(line);
            // read next line
            line = reader.readLine();
        }
        if(curSection instanceof LinkerDotParser)
        {
            valid = true;
        }
        else
        {
            System.err.println("Missing last section!");
            valid = false;
        }
    }

    public boolean isValid()
    {
        return valid;
    }

    public SectionCollection getSectionCollection()
    {
        return (SectionCollection)fac.getParserFor(Section.LINKER_DOT);
    }

    public MemoryAreaCollection getAllMemoryAreas()
    {
        return (MemoryAreaCollection)fac.getParserFor(Section.MEMORY_CONFIG);
    }

}
