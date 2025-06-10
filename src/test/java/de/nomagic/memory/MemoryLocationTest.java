package de.nomagic.memory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemoryLocationTest {

    @Test
    void testMemoryLocation()
    {
        MemoryLocation cut = new MemoryLocation(null);
        assertFalse(cut.isValid());

        cut = new MemoryLocation("");
        assertFalse(cut.isValid());

        cut = new MemoryLocation("bla bla");
        assertFalse(cut.isValid());

        cut = new MemoryLocation("bla.bla.bla");
        assertFalse(cut.isValid());

        cut = new MemoryLocation(".b.l.a.b.l.a");
        assertFalse(cut.isValid());

        cut = new MemoryLocation(".text.bla");
        assertTrue(cut.isValid());
        assertEquals("text", cut.getType());
        assertEquals("bla", cut.getName());
    }

    @Test
    void testAddSizeFile()
    {
        MemoryLocation cut = new MemoryLocation(".text.file_system_write");
        assertTrue(cut.isValid());
        assertEquals("text", cut.getType());
        assertEquals("file_system_write", cut.getName());
        cut.add("        0x20001824      0x278 build/nomagic_probe/src/file/file_system.o");
        assertTrue(cut.isValid());
        assertEquals(Long.valueOf("20001824", 16), cut.getAddress());
        assertEquals("build/nomagic_probe/src/file/file_system.o", cut.getFile());
        assertEquals(Integer.valueOf("278", 16), cut.getSize());
    }

    @Test
    void testAddName()
    {
        MemoryLocation cut = new MemoryLocation(".text.file_system_write");
        assertTrue(cut.isValid());
        assertEquals("text", cut.getType());
        assertEquals("file_system_write", cut.getName());
        cut.add("        0x20001824      file_system_write");
        assertTrue(cut.isValid());
        assertEquals(Long.valueOf("20001824", 16), cut.getAddress());
        assertEquals("file_system_write", cut.getName());
    }

    @Test
    void testAdd()
    {
        MemoryLocation cut = new MemoryLocation(".text.file_system_write");
        assertTrue(cut.isValid());
        assertEquals("text", cut.getType());
        assertEquals("file_system_write", cut.getName());
        cut.add("        30001824      bla/blubb");
        assertFalse(cut.isValid());
    }

}
