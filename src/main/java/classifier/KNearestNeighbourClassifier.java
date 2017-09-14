package classifier;

import data.Image;

import java.util.List;

/**
 * Created by nik on 10/09/17.
 */
public class KNearestNeighbourClassifier
    extends AbstractClassifier
{
    public KNearestNeighbourClassifier(
        List<Integer> possibleLabels)
    {
        super(possibleLabels);
    }

    @Override
    void innerTrain(List<Image> data)
    {
        
    }

    @Override
    public int predict(Image image)
    {
        return 0;
    }

    @Override
    public String name()
    {
        return null;
    }
}
