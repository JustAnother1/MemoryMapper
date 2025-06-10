package de.nomagic.memory;

public class MemoryLocation
{
    private boolean valid;
    private String name;
    private String type;
    private long address = -1;
    private int size;
    private String file;

    // line is something like ".text.time_get_ms"
    public MemoryLocation(String line)
    {
        if(null == line)
        {
            valid = false;
            return;
        }
        line = line.trim();
        String[] parts = line.split("\\.");
        if(3 != parts.length)
        {
            valid = false;
            return;
        }
        if(0 < parts[0].length())
        {
            valid = false;
            return;
        }
        type = parts[1];
        name = parts[2];
        valid = true;
    }

    /*
     .text.file_system_write
                0x20001824      0x278 build/nomagic_probe/src/file/file_system.o
                0x20001824                file_system_write
     */

    public void add(String line)
    {
        if(null == line)
        {
            // another job well done
            return;
        }
        line = line.trim();
        int pos = 0;
        String[] parts = line.split(" ");
        for(String part : parts)
        {
            if(1 > part.length())
            {
                continue;
            }
            if(true == part.startsWith("0x"))
            {
                if(0 == pos)
                {
                    // address
                    long addr = Long.valueOf(part.substring(2), 16);
                    if(-1 == address)
                    {
                        address = addr;
                    }
                    else
                    {
                        if(addr != address)
                        {
                            valid = false;
                            return;
                        }
                        // else ok
                    }
                    pos++;
                }
                else if(1 == pos)
                {
                    // size
                    long val = Long.valueOf(part.substring(2), 16);
                    size = (int)val;
                    pos++;
                }
                else
                {
                    // a third hex value?
                    valid = false;
                    return;
                }
            }
            else
            {
                if(0 == pos)
                {
                    // missing address
                    valid = false;
                    return;
                }
                else
                {
                    // OK
                    if(true == part.contains("/"))
                    {
                        file = part;
                    }
                    else if(part.equals(name))
                    {
                        // OK
                    }
                    else
                    {
                        // what is this ?
                        valid = false;
                        return;
                    }
                }
            }

        }
    }

    public boolean isValid()
    {
        return valid;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public long getAddress()
    {
        return address;
    }

    public int getSize()
    {
        return size;
    }

    public String getFile()
    {
        return file;
    }

}
