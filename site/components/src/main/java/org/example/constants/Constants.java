package org.example.constants;

/**
 * Constants
 */
public class Constants {
    private Constants() { throw new IllegalStateException("Constants class"); }
    public static final String PATH_REGEX = "/?([A-z0-9]+)(/[A-z0-9])*";
    public static final String UUID_REGEX = "[a-z0-9&]{8,}+[&|-]+[a-z0-9&]{4,}+[&|-]+[a-z0-9&]{4,}+[&|-]+[a-z0-9&]{4,}+[&|-]+[a-z0-9&]{4,12}";
}
