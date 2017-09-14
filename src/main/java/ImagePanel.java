import javax.swing.*;
import java.awt.*;

/**
 * Created by nik on 09/09/17.
 */
public class ImagePanel
    extends JPanel
{
    private final data.Image image;

    ImagePanel(data.Image image)
    {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        int[] red = image.getRed();
        int[] green = image.getGreen();
        int[] blue = image.getBlue();

        int counter = 0;
        for(int y = 0; y < image.getWidth(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                int idx = counter;
                counter++;

                int r = red[idx];
                int g = green[idx];
                int b = blue[idx];

                graphics.setColor(new Color(r, g, b));
                graphics.drawRect(x, y, 1, 1);
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}
