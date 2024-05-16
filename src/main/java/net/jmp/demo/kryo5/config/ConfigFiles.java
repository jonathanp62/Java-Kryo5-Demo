package net.jmp.demo.kryo5.config;

/*
 * (#)ConfigFiles.java  0.2.0   05/15/2024
 *
 * @author    Jonathan Parker
 * @version   0.2.0
 * @since     0.2.0
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

import com.google.gson.annotations.SerializedName;

/**
 * The files section of the configuration class.
 */
public final class ConfigFiles {
    /** The data location for the main classes. */
    @SerializedName("main")
    private String main;

    /** The data location for the test classes. */
    @SerializedName("test")
    private String test;

    /**
     * Get the main data location.
     *
     * @return  java.lang.String
     */
    public String getMain() {
        return this.main;
    }

    /**
     * Set the main data location.
     *
     * @param   test    java.lang.String
     */
    public void setMain(final String main) {
        this.main = main;
    }

    /**
     * Get the test data location.
     *
     * @return  java.lang.String
     */
    public String getTest() {
        return this.test;
    }

    /**
     * Set the test data location.
     *
     * @param   test    java.lang.String
     */
    public void setTest(final String test) {
        this.test = test;
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "ConfigFiles{" +
                "main='" + this.main + '\'' +
                ", test='" + this.test + '\'' +
                '}';
    }
}
