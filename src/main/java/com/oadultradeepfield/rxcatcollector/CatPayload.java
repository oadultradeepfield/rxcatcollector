package com.oadultradeepfield.rxcatcollector;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents the payload of a cat event, including its unique ID and associated tags.
 */
public class CatPayload {
    /**
     * The unique identifier for the cat event.
     */
    @SerializedName("id")
    public String id;

    /**
     * A list of tags associated with the cat event.
     */
    public List<String> tags;

    /**
     * Returns a string representation of the cat payload, including its ID and tags.
     *
     * @return A string in the format "Cat ID: {id}, Tags: {tags}"
     */
    @Override
    public String toString() {
        return "Cat ID: " + id + ", Tags: " + tags;
    }
}
