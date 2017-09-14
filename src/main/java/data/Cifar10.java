package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * A class able to read cifar10 data into memory
 *
 * @author Nik Vaessen
 */
public class Cifar10
{

    /**
     * Path containing the cifar10 data
     */
    private static final Path DIRECTORY
        = Paths.get("src/main/resources/cifar10/cifar-10-batches-bin/");

    /**
     * File names for the data_batch bin files in cifar10 dir
     */
    private static final String BIN_NAMES = "data_batch_%d.bin";
    private static final int MIN_BIN_NUMBER = 1;
    private static final int MAX_BIN_NUMBER = 1;

    /**
     * File name for the meta data file
     */
    private static final String META_FILE_NAME = "batches.meta.txt";

    /**
     * File name for the test data file
     */
    private static final String TEST_BIN_NAME = "test_batch.bin";

    /**
     * File name for the README file
     */
    private static final String README_NAME = "readme.html";

    /**
     * The dimensions of each image
     */
    private static final int IMAGE_WIDTH = 32;
    private static final int IMAGE_HEIGHT = 32;

    /**
     * The amount of images per bin file
     */
    private static final int ROWS_PER_FILE = 10000;

    /**
     * The amount of bytes per image file
     */
    private static final int BYTES_PER_ROW = 3073;

    /**
     * List containing all training images
     */
    private List<Image> trainImages = new ArrayList<>();

    /**
     * List containing all test images
     */
    private List<Image> testImages = new ArrayList<>();

    /**
     * Maps the integer label to a String label
     */
    private Map<Integer, String> labelNames = new HashMap<>();

    /**
     * Create a object holding all the cifar10 data
     *
     * @throws IOException when the directory with the data can not be found
     */
    public Cifar10()
        throws IOException
    {
        loadCifar();
    }

    private void loadCifar()
        throws IOException
    {
        File dir = DIRECTORY.toFile();

        if(!dir.isDirectory())
        {
            throw new IOException("Could not find cifar10 directory at " +
                                  DIRECTORY.toString());
        }

        File[] filesInDir = dir.listFiles();
        if(filesInDir == null || !verifyDirectoryContent(filesInDir))
        {
            throw new IOException("Directory containing cifar10 data is empty" +
                                  " or incomplete");
        }

        ArrayList<String> binNames = new ArrayList<>();
        for(int i = MIN_BIN_NUMBER; i <= MAX_BIN_NUMBER; i++)
        {
            binNames.add(String.format(BIN_NAMES, i));
        }

        for(File f: filesInDir)
        {
            String fileName = f.getName();

            if(binNames.contains(fileName))
            {
                trainImages.addAll(handleBin(f));
            }
            else if(TEST_BIN_NAME.equals(fileName))
            {
                testImages.addAll(handleBin(f));
            }
            else if(META_FILE_NAME.equals(fileName))
            {
                handleMetaFile(f);
            }
        }

    }

    private void handleMetaFile(File metaFile)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(metaFile)))
        {
            int count = 0;
            while(reader.ready())
            {
                String line = reader.readLine();
                if(line.isEmpty())
                {
                    continue;
                }

                labelNames.put(count, line);
                count++;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private List<Image> handleBin(File f)
        throws IOException
    {
        byte[] readData = Files.readAllBytes(f.toPath());

        int[] data = new int[readData.length];
        for(int i = 0; i < data.length; i++)
        {
            data[i] = unsignedToBytes(readData[i]);
        }

        if(data.length != ROWS_PER_FILE * BYTES_PER_ROW)
        {
            throw new IllegalArgumentException("Given bin file does not have" +
                                               " correct length");
        }

        List<Image> images = new ArrayList<>(ROWS_PER_FILE);
        int length = IMAGE_HEIGHT * IMAGE_WIDTH;

        for(int i = 0, idx = 0; i < ROWS_PER_FILE; i++, idx += BYTES_PER_ROW)
        {
            int labelIdx = idx;
            int redIdx = labelIdx + 1;
            int greenIdx = redIdx + length;
            int blueIdx = greenIdx + length;

            int label = data[labelIdx];
            int[] red = new int[length];
            int[] green = new int[length];
            int[] blue = new int[length];

            System.arraycopy(data, redIdx, red, 0, length);
            System.arraycopy(data, greenIdx, green, 0, length);
            System.arraycopy(data, blueIdx, blue, 0, length);

            images.add(new Image(IMAGE_WIDTH, IMAGE_HEIGHT,
                                 red, green, blue, label));
        }

        return images;
    }

    public static int unsignedToBytes(byte b)
    {
        return b & 0xFF;
    }


    /**
     * Verify that all files are in the cifar10 directory
     *
     * @param filesInDir the array of files in the directory
     * @return true when all expected files are present, false otherwise
     */
    private boolean verifyDirectoryContent(File[] filesInDir)
    {
        ArrayList<String> expectedNames = new ArrayList<>();
        expectedNames.add(TEST_BIN_NAME);
        expectedNames.add(META_FILE_NAME);
        expectedNames.add(README_NAME);

        for(int i = MIN_BIN_NUMBER; i <= MAX_BIN_NUMBER; i++)
        {
            expectedNames.add(String.format(BIN_NAMES, i));
        }

        for(File f: filesInDir)
        {
            expectedNames.remove(f.getName());
        }

        return expectedNames.size() == 0;
    }

    public List<Image> getTrainImages()
    {
        return Collections.unmodifiableList(trainImages);
    }

    public List<Image> getTestImages()
    {
        return Collections.unmodifiableList(testImages);
    }

    public Map<Integer, String> getLabelNames()
    {
        return Collections.unmodifiableMap(labelNames);
    }
}
