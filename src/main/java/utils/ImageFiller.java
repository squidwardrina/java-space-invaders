package utils;

import sprites.Fill;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handles image filling.
 */
public class ImageFiller {
    /**
     * Creates a fill from image file specified.
     *
     * @param file the name of the file with the image
     * @return the new fill
     * @throws IOException problem with file
     */
    public static Fill createImageFill(String file) throws IOException {
        Image backgImage;

        // Read the image
        InputStream stream =
                ClassLoader.getSystemClassLoader().getResourceAsStream(file);
        if (stream == null) {
            throw new IOException();
        }
        backgImage = ImageIO.read(stream);

        // If succeeded reading the image - return the new fill
        if (backgImage != null) {
            return new Fill(backgImage);
        } else {
            throw new IOException();
        }
    }
}
