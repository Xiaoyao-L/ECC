package Functions;

import java.util.Random;

/**
 * @author Xiaoyao.L
 * @date 2020/8/17 1:17
 * @project ECC
 */


public class GaussChannel {
    public double[] noiseGenerate(double variance, double mean, int length){
        Random r = new java.util.Random();
        double[] data = new double[length];
        for (int i = 0; i < length;i++) {
            //double noise = r.nextGaussian() * Math.sqrt(variance) + mean;
            double noise = r.nextGaussian() * variance + mean;
            data[i] = noise;
            //System.out.println(noise);
        }
        return data;
    }

    public double[] addNoise(double[] code, double[] noise){
        double[] withNoise = new double[code.length];

        for (int i = 0; i < code.length; i++)
        {
            withNoise[i] = code[i] + noise[i];
        }
        return withNoise;
    }
    public String[] double2Stirng(double[] data){
        String[] withNoise = new String[data.length];

        for (int i = 0; i < data.length; i++)
        {
            withNoise[i] = String.format("%.2f",data[i]);
        }
        return withNoise;
    }


}

