package de.nomagic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/** collection of general utility functions.
 *
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public final class Tool
{
    private Tool()
    {
        // Not used !
    }

    public static String getXMLRepresentationFor(Content tag)
    {
        if(null == tag)
        {
            return "";
        }
        if(tag instanceof CDATA)
        {
            CDATA data = (CDATA)tag;
            return "<![CDATA[" + data.getText() + "]]>";
        }
        else if(tag instanceof Element)
        {
            Element ele = (Element)tag;
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            String res = xmlOutput.outputString(ele);
            return res;
        }
        else
        {
            return "<unknown>" + tag.getValue() + "</unknown>";
        }
    }

    public static Document getXmlDocumentFrom(String xmlData)
    {
        if(null == xmlData)
        {
            return null;
        }
        StringReader stringReader = new StringReader(xmlData);
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try
        {
            doc = builder.build(stringReader);
            return doc;
        }
        catch (JDOMException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String curentDateTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String fromExceptionToString(final Throwable e)
    {
        if(null == e)
        {
            return "Exception [null]";
        }
        String res = e.getLocalizedMessage() + "\n" ;
        final StringWriter s = new StringWriter();
        final PrintWriter p = new PrintWriter(s);
        e.printStackTrace(p);
        p.flush();
        res = res + s.toString();
        return "Exception [" +res + "]";
    }

    public static String fromByteBufferToHexString(final byte[] buf)
    {
        if(null == buf)
        {
            return "[]";
        }
        else
        {
            return fromByteBufferToHexString(buf, buf.length, 0);
        }
    }

    public static String fromByteBufferToHexString(final int[] buf)
    {
        if(null == buf)
        {
            return "[]";
        }
        else
        {
            return fromByteBufferToHexString(buf, buf.length, 0);
        }
    }

    public static String fromByteBufferToHexString(final byte[] buf, int length)
    {
        return fromByteBufferToHexString(buf, length, 0);
    }

    public static String fromByteBufferToHexString(int[] buf, int length, int offset)
    {
        if(null == buf)
        {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            sb.append(String.format("%02X", (0xff & buf[i + offset])));
            sb.append(" ");
        }
        return "[" + (sb.toString()).trim() + "]";
    }

    public static String fromByteBufferToHexString(final byte[] buf, int length, int offset)
    {
        if(null == buf)
        {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            sb.append(String.format("%02X", buf[i + offset]));
            sb.append(" ");
        }
        return "[" + (sb.toString()).trim() + "]";
    }

    public static String fromByteBufferToUtf8String(final byte[] buf)
    {
        if(null == buf)
        {
            return "[]";
        }
        return fromByteBufferToUtf8String(buf, buf.length, 0);
    }

    public static String fromByteBufferToUtf8String(final byte[] buf, int length, int offset)
    {
        if(null == buf)
        {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            sb.append((char)buf[i + offset]);
        }
        return "[" + (sb.toString()).trim() + "]";
    }

    public static boolean isValidChar(final char c)
    {
        final char[] validChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                                   'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                   'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                   'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                   '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                                   ' ',};
        for(int i = 0; i < validChars.length; i++)
        {
            if(c == validChars[i])
            {
                return true;
            }
        }
        return false;
    }

    public static String onlyAllowedChars(final String src)
    {
        final StringBuilder dst = new StringBuilder();
        for(int i = 0; i < src.length(); i++)
        {
            final char cur = src.charAt(i);
            if(true == isValidChar(cur))
            {
                dst.append(cur);
            }
        }
        return dst.toString();
    }

    public static String getStacTrace()
    {
        // Thread.dumpStack();
        final StackTraceElement[] trace = (Thread.currentThread()).getStackTrace();
        if(0 == trace.length)
        {
            return "This task has not been started yet !";
        }
        else
        {
            final StringBuilder res = new StringBuilder();
            for(int i = 0; i < trace.length; i++)
            {
                res.append(trace[i].toString());
                res.append("\n");
            }
            return res.toString();
        }
    }

    public static String validatePath(String path)
    {
        if(null == path)
        {
            return "";
        }
        if(1 > path.length())
        {
            return "";
        }
        if(false == path.endsWith(File.separator))
        {
            return path + File.separator;
        }
        return path;
    }

    public static String getCommitID()
    {
        try
        {
            final InputStream s = Tool.class.getResourceAsStream("/git.properties");
            final BufferedReader in = new BufferedReader(new InputStreamReader(s));

            String id = "";

            String line = in.readLine();
            while(null != line)
            {
                if(line.startsWith("git.commit.id.full"))
                {
                    id = line.substring(line.indexOf('=') + 1);
                }
                line = in.readLine();
            }
            in.close();
            s.close();
            return id;
        }
        catch( Exception e )
        {
            return e.toString();
        }
    }

    public static String basename(String fileName)
    {
        if(null == fileName)
        {
            return "";
        }
        if(false == fileName.contains(File.separator))
        {
            return "";
        }
        String[] parts = fileName.split(File.separator);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < parts.length -1; i++)
        {
            sb.append(parts[i]);
            sb.append(File.separator);
        }
        return sb.toString();
    }

    public static String limitLength(String longText, int maxLength)
    {
        if(null == longText)
        {
            return null;
        }
        if(longText.length() > maxLength)
        {
            String res = longText.substring(0, maxLength -3);
            res = res + "...";
            return res;
        }
        else
        {
            // longText is not too long
            return longText;
        }
    }

    public static String convertUnitToInt(String value)
    {
        // convert "1 MHz"   to "1000000"
        //         "5kHz"    to    "5000"
        //         "4,73MHz" to "4730000"
        // leave everything else untouched !
        //         "bla"     to "bla"

        if(null == value)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean inExtra = false;
        StringBuilder extra = new StringBuilder();
        int max = value.length();
        if(max > 100)
        {
            max = 100;
        }
        for(int i = 0; i < max; i++)
        {
            char c = value.charAt(i);
            switch(c)
            {
            case ' ':
                // ignore;
                break;

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if(true == inExtra)
                {
                    extra.append(c);
                }
                else
                {
                    sb.append(c);
                }
                break;

            case '.':
            case ',': // Localization only not thousands
                if(false == inExtra)
                {
                    inExtra = true;
                }
                else
                {
                    // something wrong here
                    return sb.toString();
                }
                break;

            case 'k':
            {
                if(true == inExtra)
                {
                    int maxExtra = extra.length();
                    if(3 < maxExtra)
                    {
                        maxExtra = 3;
                    }
                    for(int k = 0; k < maxExtra; k++)
                    {
                        sb.append(extra.charAt(k));
                    }
                    for(int k = maxExtra; k < 3; k++)
                    {
                        sb.append('0');
                    }
                }
                int v = Integer.valueOf(sb.toString());
                if(false == inExtra)
                {
                    v = v * 1000;
                }
                return "" + v;
            }

            case 'M':
            {
                if(true == inExtra)
                {
                    int maxExtra = extra.length();
                    if(6 < maxExtra)
                    {
                        maxExtra = 6;
                    }
                    for(int k = 0; k < maxExtra; k++)
                    {
                        sb.append(extra.charAt(k));
                    }
                    for(int k = maxExtra; k < 6; k++)
                    {
                        sb.append('0');
                    }
                }
                int v = Integer.valueOf(sb.toString());
                if(false == inExtra)
                {
                    v = v * 1000000;
                }
                return "" + v;
            }

            default:
                // start of Unit?
                StringBuilder unit = new StringBuilder();
                unit.append(c);
                for(i++; i < max; i++)
                {
                    c = value.charAt(i);
                    unit.append(c);
                }
                String unitStr = unit.toString();
                unitStr = unitStr.toLowerCase();
                if("hz".equals(unitStr))
                {
                    // start of Unit -> we are done here
                    return sb.toString();
                }
                else
                {
                    // this is not a Value with unit -> do not change it
                    return value;
                }

            } // end of switch
        }
        return sb.toString();
    }

}
