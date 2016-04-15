package com.tlongdev.hexle.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class ModuleTest {

    @Test
    public void testModulo() {
        Assert.assertEquals(3, Util.modulo(11, 8));
    }

    @Test
    public void testModuloNegative() {
        Assert.assertEquals(3, Util.modulo(-5, 8));
    }
}
