package classifier;

import data.Image;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.NDArrayFactory;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.Arrays;
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
            images.putRow(i, pixelColumnVectorFromImage(data.get(i)));
        }
    }

    @Override
    public int predict(Image image)
    {
        INDArray vector = pixelColumnVectorFromImage(image);
        System.out.println(Arrays.toString(vector.shape()));

        return 0;
    }

    @Override
    public String name()
    {
        return null;
    }


    private INDArray pixelColumnVectorFromImage(Image image)
    {
        INDArray red, green, blue;

        red = Nd4j.create(copyFromIntArray(image.getRed()));
        green = Nd4j.create(copyFromIntArray(image.getGreen()));
        blue = Nd4j.create(copyFromIntArray(image.getBlue()));

        return Nd4j.concat(1, red, green , blue);
    }

    private static double[] copyFromIntArray(int[] source)
    {
        double[] dest = new double[source.length];

        for(int i=0; i<source.length; i++)
        {
            dest[i] = source[i];
        }

        return dest;
    }

}
