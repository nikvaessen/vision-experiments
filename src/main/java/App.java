import classifier.AbstractClassifier;
import classifier.KNearestNeighbourClassifier;
import classifier.RandomClassifier;
import data.Cifar10;
import data.Image;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 09/09/17.
 */
public class App
{

    public static void main(String[] args)
    {
        Cifar10 cifar10 = null;
        try
        {
            cifar10 = new Cifar10();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        List<Image> trainingSet = cifar10.getTrainImages();
        List<Image> testSet = cifar10.getTestImages();
        List<Integer> possibleLabels
            = new ArrayList<>(cifar10.getLabelNames().keySet());
        List<String> labelDescriptions = new ArrayList<>(cifar10.getLabelNames().values());

        cifar10 = null;
        System.runFinalization();

        System.out.println("Cifar 10 data loading in %d ms!");
        System.out.println(possibleLabels);
        System.out.println(labelDescriptions);

        AbstractClassifier classifier = new KNearestNeighbourClassifier(possibleLabels);

        System.out.println("Training Random classifier!");
        long start = System.currentTimeMillis();
        classifier.train(trainingSet);
        long end = System.currentTimeMillis();

        System.out.printf("Training took %d ms%n", end-start);
        System.out.println("Starting to test...");

        TestResult testResult = testAccuracy(testSet, classifier);

        System.out.printf("Accuracy: %f%nAverage time to predict: %f ms%n",
                           testResult.getAccuracy(),
                           testResult.getRunningTime());

//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new ImagePanel(images.get(1)));
//        frame.pack();
//        frame.setVisible(true);
    }

    private static TestResult testAccuracy(List<Image> testSet,
                                     AbstractClassifier classifier)
    {
        double correct = 0;
        double total = testSet.size();
        int count = 0;

        long start = System.currentTimeMillis();
        for(Image i : testSet)
        {
            count++;
            System.out.printf("Image %d/%d. Currently correct: %f%n",
                              count,
                              new Double(total).intValue(),
                              correct/total);

            if(classifier.predict(i) == i.getLabel())
            {
                correct++;
            }
        }
        long end = System.currentTimeMillis();

        return new TestResult(correct / total, (end - start) / total);
    }

    private static class TestResult
    {
        private double accuracy;
        private double runningTime;

        public TestResult(double accuracy, double runningTime)
        {
            this.accuracy = accuracy;
            this.runningTime = runningTime;
        }

        public double getAccuracy()
        {
            return accuracy;
        }

        public double getRunningTime()
        {
            return runningTime;
        }
    }

}
