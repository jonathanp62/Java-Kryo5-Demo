package net.jmp.demo.kryo5;

/*
 * (#)Serializers.java  0.3.0   05/16/2024
 *
 * @author   Jonathan Parker
 * @version  0.3.0
 * @since    0.3.0
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The serializers class.
 */
final class Serializers {
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
    Serializers(final Config config) {
        super();

        assert config != null;

        this.config = config;
    }

    /**
     * The execute method.
     */
    void execute() {
        this.logger.entry();

        this.defaultSerializer();

        this.logger.exit();
    }

    /**
     * Use the default FieldSerializer.
     */
    private void defaultSerializer() {
        this.logger.entry();

        final var name = "Jonathan Martin";
        final var person = new Person();

        try {
            person.setAge(62);
            person.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse("02/05/1962"));
            person.setName(name);
        } catch (final ParseException pe) {
            this.logger.catching(pe);
        }

        /* Make sure that person is fully set up */

        assert person.getAge() == 62;
        assert person.getBirthday() != null;
        assert person.getName().equals(name);

        final var outputFileName = this.config.getConfigFiles().getMain();

        /* Serialize person */

        this.kryo.register(Person.class);
        this.kryo.register(Date.class);
        this.kryo.register(String.class);

        try (final var output = new Output(new FileOutputStream(outputFileName))) {
            this.kryo.writeObject(output, person);
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        /* Deserialize */

        try (final var input = new Input(new FileInputStream(outputFileName))) {
            final var deserializedPerson = this.kryo.readObject(input, Person.class);

            if (deserializedPerson.equals(person))
                this.logger.info("Serialized person and deserialized person match");
            else
                this.logger.warn("Serialized person and deserialized person do not match");
        } catch (final FileNotFoundException fnfe) {
            this.logger.catching(fnfe);
        }

        this.logger.exit();
    }
}
