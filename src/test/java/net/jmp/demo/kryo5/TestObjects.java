package net.jmp.demo.kryo5;

/*
 * (#)TestObjects.java  0.3.0   05/16/2024
 * (#)TestObjects.java  0.2.0   05/15/2024
 *
 * @author   Jonathan Parker
 * @version  0.3.0
 * @since    0.2.0
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.esotericsoftware.kryo.kryo5.Kryo;

import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestObjects {
    /** The configuration file name. */
    private static final String APP_CONFIG_FILE = "config/config.json";

    /** The Kryo5 object. */
    private final Kryo kryo = new Kryo();

    /** The output object. */
    private Output output;

    /** The input object. */
    private Input input;

    /**
     * Get the application configuration.
     *
     * @return  java.lang.Optional&lt;net.jmp.demo.kryo5.Config&gt;
     */
    private Optional<Config> getAppConfig() {
        Config appConfig = null;

        try {
            appConfig = new Gson().fromJson(Files.readString(Paths.get(APP_CONFIG_FILE)), Config.class);
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        return Optional.ofNullable(appConfig);
    }

    /**
     * Execute before each test.
     */
    @Before
    public void before() {
        this.getAppConfig().ifPresent(config -> {
            final var outputLocation = config.getConfigFiles().getTest();

            try {
                this.output = new Output(new FileOutputStream(outputLocation));
                this.input = new Input(new FileInputStream(outputLocation));
            } catch (final FileNotFoundException fnfe) {
                fnfe.printStackTrace(System.err);
            }
        });
    }

    /**
     * Test a single object.
     */
    @Test
    public void testSingleObject() {
        final Object someObject = "Some string";

        this.kryo.register(Object.class);

        this.kryo.writeClassAndObject(output, someObject);
        this.output.close();

        final Object theObject = kryo.readClassAndObject(input);

        this.input.close();

        assertEquals(someObject, theObject);
    }

    /**
     * Test multiple objects.
     */
    @Test
    public void testMultipleObjects() {
        final String someString = "Multiple Objects";
        final Date someDate = new Date(915170400000L);

        this.kryo.register(String.class);
        this.kryo.register(Date.class);

        this.kryo.writeObject(output, someString);
        this.kryo.writeObject(output, someDate);
        this.output.close();

        final String readString = kryo.readObject(input, String.class);
        final Date readDate = kryo.readObject(input, Date.class);

        this.input.close();

        assertEquals(someString, readString);
        assertEquals(someDate, readDate);
    }
}
