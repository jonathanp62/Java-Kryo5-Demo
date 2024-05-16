package net.jmp.demo.kryo5.custom;

/*
 * (#)PetSerializer.java    0.3.0   05/16/2024
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
import com.esotericsoftware.kryo.kryo5.Serializer;

import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import net.jmp.demo.kryo5.objects.Pet;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

public final class PetSerializer extends Serializer<Pet>{
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /**
     * The default constructor.
     */
    public PetSerializer() {
        super();
    }

    /**
     * Write the serialized pet.
     *
     * @param   kryo    com.esotericsoftware.kryo.kryo5.Kryo
     * @param   output  com.esotericsoftware.kryo.kryo5.io.Output
     * @param   pet     net.jmp.demo.kryo5.Pet
     */
    @Override
    public void write(final Kryo kryo, final Output output, final Pet pet) {
        this.logger.entry(kryo, output, pet);

        output.writeString(pet.getType());
        output.writeString(pet.getName());
        output.writeString(pet.getColor());
        output.writeInt(pet.getAge());

        this.logger.exit();
    }

    /**
     * Read and return the deserialized pet.
     *
     * This is important - The sequence of the
     * reads from input must be the same as the
     * writes to output.
     *
     * @param   kryo    com.esotericsoftware.kryo.kryo5.Kryo
     * @param   input   com.esotericsoftware.kryo.kryo5.io.Output
     * @param   type    java.lang.Class
     * @return          net.jmp.demo.kryo5.Pet
     */
    public Pet read(final Kryo kryo, final Input input, final Class<? extends Pet> type) {
        this.logger.entry(kryo, input, type);

        final Pet pet = new Pet();

        pet.setType(input.readString());
        pet.setName(input.readString());
        pet.setColor(input.readString());
        pet.setAge(input.readInt());

        this.logger.exit(pet);

        return pet;
    }
}
