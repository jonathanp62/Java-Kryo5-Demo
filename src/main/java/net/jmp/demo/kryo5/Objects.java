package net.jmp.demo.kryo5;

/*
 * (#)Objects.java  0.2.0   05/15/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
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
import com.esotericsoftware.kryo.kryo5.Registration;

import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.Date;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

import net.jmp.demo.kryo5.config.Config;

/**
 * The objects class.
 */
final class Objects {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The configuration object. */
    private final Config config;

    /** The Kryo5 object. */
    private final Kryo kryo = new Kryo();

    /**
     * The constructor.
     *
     * @param   config  net.jmp.demo.kryo5.Config
     */
    Objects(final Config config) {
        super();

        assert config != null;

        this.config = config;
    }

    /**
     * The execute method.
     */
    void execute() {
        this.logger.entry();

        this.singleObject();
        this.multipleObjects();

        this.logger.exit();
    }

    /**
     * Serialize/deserialize a single object.
     */
    private void singleObject() {
        this.logger.entry();

        /*
         * Use write/readClassAndObject when:
         *  1. Serializing a base type like an interface or a class with subclasses
         *  2. And needs the type (class) of the deserialized object to reconstruct
         */

        final Object object = "This is really a string";
        final String outputFileName = this.config.getConfigFiles().getMain();

        this.kryo.register(Object.class);

        /* Registration */

        final Registration registration = this.kryo.getRegistration(Object.class);

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Registration ID for Object: {}", registration.getId());
        }

        try (final var output = new Output(new FileOutputStream(outputFileName))) {
            this.kryo.writeClassAndObject(output, object);
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        /*
         * The serialization must complete (output closed) before it
         * can be read from or a buffer underflow will likely occur.
         */

        try (final var input = new Input(new FileInputStream(outputFileName))) {
            final Object deserialized = this.kryo.readClassAndObject(input);

            if (deserialized.equals(object))
                this.logger.info("Serialized object and deserialized object match");
            else
                this.logger.warn("Serialized object and deserialized object do not match");
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        this.logger.exit();
    }

    /**
     * Serialize/deserialize multiple objects.
     */
    private void multipleObjects() {
        this.logger.entry();

        /*
         * Use write/readObject when:
         *  1. Serializing exactly one type that can be instantiated
         *  2. Or serializes more than one type but the type can be deduced
         */

        final var myString = "This is really another string, accompanied by a date";
        final var myDate = new Date(915170400000L);
        final var outputFileName = this.config.getConfigFiles().getMain();

        /* Serialize */

        this.kryo.register(String.class);
        this.kryo.register(Date.class);

        try (final var output = new Output(new FileOutputStream(outputFileName))) {
            this.kryo.writeObject(output, myString);
            this.kryo.writeObject(output, myDate);
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        /* Registration */

        final var stringRegistration = this.kryo.getRegistration(String.class);
        final var dateRegistration = this.kryo.getRegistration(Date.class);

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Registration ID for Date  : {}", dateRegistration.getId());
            this.logger.info("Registration ID for String: {}", stringRegistration.getId());
        }

        /* Deserialize */

        try (final var input = new Input(new FileInputStream(outputFileName))) {
            final var deserializedString = this.kryo.readObject(input, String.class);
            final var deserializedDate = this.kryo.readObject(input, Date.class);

            if (deserializedString.equals(myString))
                this.logger.info("Serialized string and deserialized string match");
            else
                this.logger.warn("Serialized string and deserialized string do not match");

            if (deserializedDate.equals(myDate))
                this.logger.info("Serialized date and deserialized date match");
            else
                this.logger.warn("Serialized date and deserialized date do not match");
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        this.logger.exit();
    }
}
