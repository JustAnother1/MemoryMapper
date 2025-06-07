package de.nomagic.cfg;

public class McuConfig
{

    public McuConfig()
    {
        // TODO read from configuration
    }

    public String[] getRamSectionNames()
    {
        String[] res = new String[4];
        res[0] = "code";
        res[1] = "rodata";
        res[2] = "data";
        res[3] = "bss";
        return res;
    }

    public String[] getFlashSectionNames()
    {
        String[] res = new String[5];
        res[0] = "text";
        res[1] = "third_stage_boot";
        res[2] = "code";
        res[3] = "rodata";
        res[4] = "data";
        return res;
    }

    public int getRamSize()
    {
        return 264*1024;
    }

    public int getFlashSize()
    {
        return 2*1024*1024;
    }

}
