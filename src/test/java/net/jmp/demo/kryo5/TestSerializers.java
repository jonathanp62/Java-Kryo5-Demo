package net.jmp.demo.kryo5;

/*
 * (#)TestSerializers.java  0.4.0   05/17/2024
 * (#)TestSerializers.java  0.3.0   05/16/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
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

import com.esotericsoftware.kryo.kryo5.serializers.JavaSerializer;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import net.jmp.demo.kryo5.config.Config;

import net.jmp.demo.kryo5.custom.PersonSerializer;

import net.jmp.demo.kryo5.objects.Chair;
import net.jmp.demo.kryo5.objects.Person;
import net.jmp.demo.kryo5.objects.Pet;
import net.jmp.demo.kryo5.objects.Recording;

public class TestSerializers {
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
     * Test the default FieldSerializer.
     */
    @Test
    public void testDefaultSerializer() {
        final var name = "Jonathan Martin";
        final var person = new Person();

        try {
            person.setAge(62);
            person.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse("02/05/1962"));
            person.setName(name);
        } catch (final ParseException pe) {
            pe.printStackTrace(System.err);
        }

        assertEquals(62, person.getAge());
        assertNotNull(person.getBirthday());
        assertEquals(name, person.getName());

        this.kryo.register(Person.class);
        this.kryo.register(String.class);
        this.kryo.register(Date.class);

        this.kryo.writeObject(this.output, person);
        this.output.close();

        final var thePerson = kryo.readObject(this.input, Person.class);

        this.input.close();

        assertEquals(person, thePerson);
    }

    /**
     * Test a custom serializer.
     */
    @Test
    public void testCustomSerializer() {
        final var name = "Wendy Carol";
        final var person = new Person();

        try {
            person.setAge(60);
            person.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse("12/08/1963"));
            person.setName(name);
        } catch (final ParseException pe) {
            pe.printStackTrace(System.err);
        }

        /* Make sure that person is fully set up */

        assertEquals(60, person.getAge());
        assertNotNull(person.getBirthday());
        assertEquals(name, person.getName());

        /* Register the custom serializer */

        this.kryo.register(Person.class, new PersonSerializer());

        /* Serialize person */

        this.kryo.writeObject(output, person);
        this.output.close();

        /* Deserialize */

        final var deserializedPerson = this.kryo.readObject(input, Person.class);

        this.input.close();

        assertEquals(person, deserializedPerson);
    }

    /**
     * Test an object annotated with a default serializer.
     */
    @Test
    public void testAnnotatedDefaultSerializer() {
        final var pet = new Pet();

        pet.setAge(12);
        pet.setName("Lady");
        pet.setColor("Black & Tan");
        pet.setType("German Shepherd Dog");

        this.kryo.register(Pet.class);

        /* Serialize pet */

        this.kryo.writeObject(output, pet);
        this.output.close();

        /* Deserialize */

        final var deserializedPet = this.kryo.readObject(input, Pet.class);

        this.input.close();

        assertEquals(pet, deserializedPet);
    }

    /**
     * Test an object that implements the KryoSerializable interface.
     */
    @Test
    public void testKryoSerializable() {
        final var chair = new Chair();

        chair.setColor("Green");
        chair.setHasWheels(false);

        this.kryo.register(Chair.class);

        /* Serialize chair */

        this.kryo.writeObject(output, chair);
        this.output.close();

        /* Deserialize */

        final var deserializedChair = this.kryo.readObject(input, Chair.class);

        this.input.close();

        assertEquals(chair, deserializedChair);
    }

    /**
     * Test an object that implements the standard Java serializable interface.
     */
    @Test
    public void testJavaSerializable() {
        final Recording recording = new Recording();
        final List<String> artists = new ArrayList<>();

        artists.add("Elsa Dreisig");
        artists.add("Mathilde Calderini");
        artists.add("Anna Besson");
        artists.add("Scarlett Strallen");

        recording.setArtists(artists);
        recording.setLabel("Decca Classics");
        recording.setTitle("A Musical Potpurri");
        recording.setTimeInMinutes(69);

        this.kryo.register(Recording.class, new JavaSerializer());

        /* Serialize recording */

        this.kryo.writeObject(output, recording);
        this.output.close();

        /* Deserialize */

        final var deserializedRecording = this.kryo.readObject(input, Recording.class);

        this.input.close();

        assertEquals(recording, deserializedRecording);
    }
}
