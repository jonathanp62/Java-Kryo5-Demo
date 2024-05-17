package net.jmp.demo.kryo5.objects;

/*
 * (#)Chair.java    0.3.0   05/16/2024
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
import com.esotericsoftware.kryo.kryo5.KryoSerializable;

import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import java.util.Objects;

public final class Chair implements KryoSerializable {
    private String color;
    private boolean hasWheels;

    public Chair() {
        super();
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public boolean isHasWheels() {
        return this.hasWheels;
    }

    public void setHasWheels(final boolean hasWheels) {
        this.hasWheels = hasWheels;
    }

    /**
     * Serialize the chair object.
     *
     * @param   kryo    com.esotericsoftware.kryo.kryo5.Kryo
     * @param   output  com.esotericsoftware.kryo.kryo5.io.Output
     */
    @Override
    public void write(final Kryo kryo, final Output output) {
        output.writeString(this.color);
        output.writeBoolean(this.hasWheels);
    }

    /**
     * Deserialize the chair object.
     *
     * This is important - The sequence of the
     * reads from input must be the same as the
     * writes to output.
     *
     * @param   kryo    com.esotericsoftware.kryo.kryo5.Kryo
     * @param   input   com.esotericsoftware.kryo.kryo5.io.Input
     */
    @Override
    public void read(final Kryo kryo, final Input input) {
        this.color = input.readString();
        this.hasWheels = input.readBoolean();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Chair chair = (Chair) o;

        return this.hasWheels == chair.hasWheels && Objects.equals(this.color, chair.color);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.color);

        result = 31 * result + Boolean.hashCode(this.hasWheels);

        return result;
    }

    @Override
    public String toString() {
        return "Chair{" +
                "color='" + this.color + '\'' +
                ", hasWheels=" + this.hasWheels +
                '}';
    }
}
