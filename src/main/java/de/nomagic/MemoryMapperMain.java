package de.nomagic;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter2;
import de.nomagic.input.mapfile.MapFile;
import de.nomagic.reporter.Reporter;

public class MemoryMapperMain
{
    private MapFile map;

    public MemoryMapperMain()
    {

    }

    private void startLogging(final String[] args)
    {
        int numOfV = 0;
        for(int i = 0; i < args.length; i++)
        {
            if(true == "-v".equals(args[i]))
            {
                numOfV ++;
            }
        }

        // configure Logging
        switch(numOfV)
        {
        case 0: setLogLevel("warn"); break;
        case 1: setLogLevel("debug");break;
        case 2:
        default:
            setLogLevel("trace");
            System.err.println("Build from " + Tool.getCommitID());
            break;
        }
    }

    private void setLogLevel(String LogLevel)
    {
        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try
        {
            final JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            final String logCfg =
            "<configuration>" +
              "<appender name='STDERR' class='ch.qos.logback.core.ConsoleAppender'>" +
              "<target>System.err</target>" +
                "<encoder>" +
                  "<pattern>%highlight(%-5level) [%logger{36}]%line %msg%n</pattern>" +
                "</encoder>" +
              "</appender>" +
              "<root level='" + LogLevel + "'>" +
                "<appender-ref ref='STDERR' />" +
              "</root>" +
            "</configuration>";
            ByteArrayInputStream bin;
            bin = new ByteArrayInputStream(logCfg.getBytes(StandardCharsets.UTF_8));
            configurator.doConfigure(bin);
        }
        catch (JoranException je)
        {
          // StatusPrinter will handle this
        }
        StatusPrinter2 sp = new StatusPrinter2();
        sp.printInCaseOfErrorsOrWarnings(context);
    }

    private boolean parseCommandLineParameters(String[] args)
    {
        // TODO
        return true;
    }

    private boolean readAndParse() throws IOException
    {
        FileReader fin = new FileReader("input/rp2040_nomagic_probe.map");
        map = new MapFile(fin);
        return map.isValid();
    }

    private boolean report()
    {
        Reporter rep = new Reporter(map);
        if(false == rep.isValid())
        {
            return false;
        }
        rep.listSectionSizes();
        rep.showMemoryAreaUsage();
        return true;
    }

    public static void main(String[] args) throws IOException
    {
        MemoryMapperMain m = new MemoryMapperMain();
        m.startLogging(args);
        if(true == m.parseCommandLineParameters(args))
        {
            if(false == m.readAndParse())
            {
                // ERROR
                System.err.println("ERROR: could not understand input data!");
                System.exit(1);
            }
            // else:
            if(true == m.report())
            {
                // OK
                System.exit(0);
            }
            else
            {
                // ERROR
                System.err.println("ERROR: could not report on input data!");
                System.exit(1);
            }
        }
        else
        {
            System.exit(1);
        }
    }
}
