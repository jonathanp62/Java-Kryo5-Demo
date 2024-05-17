package net.jmp.demo.kryo5.objects;

/*
 * (#)Recording.java    0.4.0   05/17/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
 * @since    0.4.0
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

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

public final class Recording implements Serializable {
    private String title;
    private String label;
    private List<String> artists;
    private int timeInMinutes;

    public Recording() {
        super();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<String> getArtists() {
        return this.artists;
    }

    public void setArtists(final List<String> artists) {
        this.artists = artists;
    }

    public int getTimeInMinutes() {
        return this.timeInMinutes;
    }

    public void setTimeInMinutes(final int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Recording recording = (Recording) o;

        return this.timeInMinutes == this.timeInMinutes && Objects.equals(this.title, recording.title) && Objects.equals(this.label, recording.label) && Objects.equals(this.artists, recording.artists);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.title);

        result = 31 * result + Objects.hashCode(this.label);
        result = 31 * result + Objects.hashCode(this.artists);
        result = 31 * result + this.timeInMinutes;

        return result;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "title='" + this.title + '\'' +
                ", label='" + this.label + '\'' +
                ", artists=" + this.artists +
                ", timeInMinutes=" + this.timeInMinutes +
                '}';
    }
}
