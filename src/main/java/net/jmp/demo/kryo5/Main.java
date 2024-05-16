package net.jmp.demo.kryo5;

/*
 * (#)Main.java 0.2.0   05/15/2024
 * (#)Main.java 0.1.0   05/15/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.1.0
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

import com.google.gson.Gson;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Optional;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The main class.
 */
public final class Main {
    /** The default configuration file name. */
    private static final String DEFAULT_APP_CONFIG_FILE = "config/config.json";

    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /**
     * The default constructor.
     */
    private Main() {
        super();
    }

    /**
     * The execute method.
     */
    private void execute() {
        this.logger.entry();

        this.logger.info("Kryo5-Demo {}", Version.VERSION_STRING);

        this.getAppConfig().ifPresent(config -> {
            new Objects(config).execute();
        });

        this.logger.exit();
    }

    /**
     * Get the application configuration.
     *
     * @return  java.lang.Optional&lt;net.jmp.demo.kryo5.Config&gt;
     */
    private Optional<Config> getAppConfig() {
        this.logger.entry();

        final var configurationFile = System.getProperty("app.configurationFile", DEFAULT_APP_CONFIG_FILE);

        assert configurationFile != null;
        assert !configurationFile.isBlank();

        this.logger.info("Configuration: {}", configurationFile);

        Config appConfig = null;

        try {
            appConfig = new Gson().fromJson(Files.readString(Paths.get(configurationFile)), Config.class);
        } catch (final IOException ioe) {
            this.logger.catching(ioe);
        }

        this.logger.exit(appConfig);

        return Optional.ofNullable(appConfig);
    }

    /**
     * The main method.
     *
     * @param   args    java.lang.String[]
     */
    public static void main(final String[] args) {
        new Main().execute();
    }
}
