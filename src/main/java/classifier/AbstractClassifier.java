package classifier;

import data.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 09/09/17.
 */
public abstract class AbstractClassifier
{
    protected List<Integer> possibleLabels;

    AbstractClassifier(List<Integer> possibleLabels)
    {
        this.possibleLabels = new ArrayList<>(possibleLabels);
    }

    private void verify(List<Image> trainingData)
    {
        for(Image i : trainingData)
        {
            if(!possibleLabels.contains(i.getLabel()))
            {
                throw new IllegalArgumentException("An image in the training" +
                                                   " set has an unknown label");
            }
        }
    }

    public final void train(List<Image> data)
    {
        verify(data);
        innerTrain(data);
    }

    abstract void innerTrain(List<Image> data);

    public abstract int predict(Image image);

    public abstract String name();
}
