package classifier;

import data.Image;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.NDArrayFactory;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.List;

/**
 * Created by nik on 10/09/17.
 */
public class KNearestNeighbourClassifier
    extends AbstractClassifier
{
    protected INDArray images;

    public KNearestNeighbourClassifier(
        List<Integer> possibleLabels)
    {
        super(possibleLabels);
    }

    @Override
    void innerTrain(List<Image> data)
    {
        Image image = data.iterator().next();
        int pixelsPerImage = image.getWidth() * image.getHeight() * 3;

        images = Nd4j.zeros(data.size(), pixelsPerImage);

        INDArray red, green, blue;
        for(int i = 0; i < data.size(); i++)
        {
            image = data.get(i);
            red = Nd4j.create(image.getRed());
            green = Nd4j.create(image.getGreen());
            blue = Nd4j.create(image.getBlue());
            images.putRow(i, Nd4j.concat(0, red, green , blue));
        }
    }

    @Override
    public int predict(Image image)
    {
        return 0;
    }

    private double distance(Image x, Image y)
    {
        double distance = 0;
        distance += distanceL1(x.getRed(), y.getRed());
        distance += distanceL1(x.getGreen(), y.getGreen());
        distance += distanceL1(x.getBlue(), y.getBlue());

        return distance;
    }

    private double distanceL1(int[] ar1, int[] ar2)
    {
        if(ar1.length != ar2.length)
        {
            throw new IllegalArgumentException("Arrays should be of equal length");
        }

        double d = 0;
        for(int i = 0; i < ar1.length; i++)
        {
            d += Math.sqrt(Math.pow(ar1[i] - ar2[i], 2));
        }

        return d;
    }

    @Override
    public String name()
    {
        return null;
    }
}
