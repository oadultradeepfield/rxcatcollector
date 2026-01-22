package com.oadultradeepfield.rxcatcollector;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A utility class for saving images to disk.
 */
public class FileSaver {
    /**
     * Private constructor to prevent instantiation.
     */
    private FileSaver() {
    }

    /**
     * Saves the given image bytes to a file with the specified cat ID.
     *
     * @param catId      the ID of the cat
     * @param imageBytes the image bytes to save
     * @return the name of the saved file
     * @throws IOException if an I/O error occurs
     */
    public static String saveImage(String catId, byte[] imageBytes) throws IOException {
        String fileName = "cat_" + catId + ".jpg";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(imageBytes);
        }
        System.out.println("[Disk IO] Saved image to " + fileName);
        return fileName;
    }
}
