package classifier;

import data.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by nik on 10/09/17.
 */
public class RandomClassifier extends AbstractClassifier
{
    private Random rng = new Random(System.currentTimeMillis());
    private List<Integer> possibleLabels;

    public RandomClassifier(List<Image> trainingData,
                            List<Integer> possibleLabels)
    {
        super(trainingData, possibleLabels);
    }

    @Override
    protected void train(List<Image> data, List<Integer> possibleLabels)
    {
        this.possibleLabels = possibleLabels;
    }

    @Override
    public int predict(Image image)
    {
        return possibleLabels.get(rng.nextInt(possibleLabels.size()));
    }

    @Override
    public String name()
    {
        return "Random";
    }
}
