package com.oadultradeepfield.rxcatcollector;

/**
 * Exception thrown to indicate that a cat with a specific ID or tag was not found.
 */
public class CatNotFoundException extends RuntimeException {
    /**
     * Private constructor to create a CatNotFoundException with the specified message.
     *
     * @param message The detail message.
     */
    private CatNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception thrown to indicate that a cat with a specific ID was not found.
     */
    public static class ById extends CatNotFoundException {
        public ById(String id) {
            super("Cat with id '" + id + "' not found.");
        }
    }

    /**
     * Exception thrown to indicate that a cat with a specific tag was not found.
     */
    public static class ByTag extends CatNotFoundException {
        public ByTag(String tag) {
            super("Cat with tag '" + tag + "' not found.");
        }
    }
}