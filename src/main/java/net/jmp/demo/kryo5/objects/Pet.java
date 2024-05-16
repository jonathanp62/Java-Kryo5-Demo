package net.jmp.demo.kryo5.objects;

/*
 * (#)Pet.java  0.3.0   05/16/2024
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

import java.util.Objects;

public final class Pet {
    private String type;
    private String name;
    private String color;
    private int age;

    public Pet() {
        super();
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Pet pet = (Pet) o;

        return this.age == pet.age && Objects.equals(this.type, pet.type) && Objects.equals(this.name, pet.name) && Objects.equals(this.color, pet.color);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);

        result = 31 * result + Objects.hashCode(this.name);
        result = 31 * result + Objects.hashCode(this.color);
        result = 31 * result + this.age;

        return result;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "type='" + this.type + '\'' +
                ", name='" + this.name + '\'' +
                ", color='" + this.color + '\'' +
                ", age=" + this.age +
                '}';
    }
}
