package data;

/**
 * Created by nik on 09/09/17.
 */
public class Image
{
    /**
     * The width of the image
     */
    private final int width;

    /**
     * The height of the image
     */
    private final int height;

    /**
     * Label of the image. Negative values are invalid labels
     */
    private final int label;

    /**
     * Pixel values of the image in row major order
     */
    private final int[] red;
    private final int[] green;
    private final int[] blue;

    /**
     * Create a data.Image object
     *
     * @param width the width of the image
     * @param height the height of the image
     * @param red a width*height array of the red pixel values of the image
     * @param green a width*height array of the blue pixel values of the image
     * @param blue a width*height array of the green pixel values of the image
     */
    public Image(int width, int height, int[] red, int[] green, int[] blue)
    {
        this(width, height, red, green, blue, -1);
    }


    /**
     * Create a data.Image object with a label
     *
     * @param width the width of the image
     * @param height the height of the image
     * @param red a width*height array of the red pixel values of the image
     * @param green a width*height array of the blue pixel values of the image
     * @param blue a width*height array of the green pixel values of the image
     * @param label a integer value representing a label for this image
     */
    public Image(int width, int height, int[] red, int[] green, int[] blue,
                 int label)
    {
        this.width = width;
        this.height = height;
        this.label = label;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getLabel()
    {
        return label;
    }

    public int[] getRed()
    {
        return red;
    }

    public int[] getGreen()
    {
        return green;
    }

    public int[] getBlue()
    {
        return blue;
    }
}
