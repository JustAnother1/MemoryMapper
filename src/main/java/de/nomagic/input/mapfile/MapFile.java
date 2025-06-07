package de.nomagic.input.mapfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import de.nomagic.input.mapfile.parser.LinkerDotParser;
import de.nomagic.input.mapfile.parser.ParserFactory;
import de.nomagic.input.mapfile.parser.StartParser;
import de.nomagic.input.mapfile.parser.SectionParser;

public class MapFile
{
    private final boolean valid;
    private final LinkerDotParser linkerdot;

    public MapFile(Reader in) throws IOException
    {
        ParserFactory fac = new ParserFactory();
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
            linkerdot = (LinkerDotParser) curSection;
            valid = true;
        }
        else
        {
            System.err.println("Missing last section!");
            valid = false;
            linkerdot = null;
        }
    }

    public boolean isValid()
    {
        return valid;
    }

    public String[] getSectionNames()
    {
        return linkerdot.getSectionNames();
    }

    public int getSizeOfSection(String sectionName)
    {
        return linkerdot.getSizeOfSection(sectionName);
    }

}
