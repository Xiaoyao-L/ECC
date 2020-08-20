package Main;

import Functions.CodeFunc;
import Functions.DistanceFunc;
import Functions.GaussChannel;
import Jama.Matrix;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Xiaoyao.L
 * @date 2020/8/16 17:55
 * @project ECC
 */
public class Main{
    public static CodeFunc codeGenerate = new CodeFunc();
    public static DistanceFunc distanceFunc = new DistanceFunc();
    public static GaussChannel gaussChannel = new GaussChannel();
    /*public static void main(String[] args) {



        double G[][] = {{1,0,1,1,1,1,0,0},{0,1,1,1,0,0,1,1}};
        Matrix G_matrix = new Matrix(G);
        double C[][] = {{0,0},{0,1},{1,0},{1,1}};
        Matrix C_matrix = new Matrix(C);

        Matrix codeword = codeGenerate.dex2binary(C_matrix.times(G_matrix));
        Matrix codewords = codeGenerate.dex2binary(C_matrix.times(G_matrix));
        codewords.print(0,7);
        Matrix matrix = codeGenerate.bpsk(codewords);
        codewords.print(0,7);
        double[] noise = gaussChannel.noiseGenerate(1, 0, 8);
        System.out.println(Arrays.toString(noise));
        double[] addNoise =gaussChannel.addNoise(matrix.getArray()[0],noise);
        System.out.println(Arrays.toString(addNoise));
        double[] dem = codeGenerate.demodulator(addNoise);
        System.out.println(Arrays.toString(dem));
        int hammingDistance = distanceFunc.hammingDistance(dem,codeword.getArray()[0]);
        System.out.println(hammingDistance);
        System.out.println(Arrays.toString(codeword.getArray()[0]));

    }*/

}
