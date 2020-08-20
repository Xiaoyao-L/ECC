package Functions;

import Jama.Matrix;

/**
 * @author Xiaoyao.L
 * @date 2020/7/25 16:28
 * @project ECC
 */
public class CodeFunc {
    public Matrix dex2binary(Matrix matrix)
    {
        double temp[][] = matrix.getArray();
        for (int i = 0; i < matrix.getRowDimension(); i++)
        {
            for (int j = 0; j <matrix.getColumnDimension();j++)
            {
                if (temp[i][j] == 2)
                    temp[i][j] = 0;
                if (temp[i][j] == 3)
                    temp[i][j] = 1;
            }
        }
        return new Matrix(temp);
    }

    public  Matrix bpsk(Matrix matrix){
        double temp[][] = matrix.getArray();
        for (int i = 0; i < matrix.getRowDimension(); i++)
        {
            for (int j = 0; j <matrix.getColumnDimension();j++)
            {
                if (temp[i][j] == 0)
                    temp[i][j] = 1;
                else
                    temp[i][j] = -1;
            }
        }
        return new Matrix(temp);
    }

    public  double[] bpsk(double[] data){
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++)
        {

                if (data[i] == 0)
                    temp[i]= 1;
                else
                    temp[i]= -1;

        }
        return temp;
    }
    public  String[] bpsk2String(double[] data){
        String[] temp = new String[data.length];
        for (int i = 0; i < data.length; i++)
        {

            if (data[i] == 0)
                temp[i]= "+1";
            else
                temp[i]= "-1";

        }
        return temp;
    }


    public double[] demodulator(double[] withNoise){
        double temp[] = new double[withNoise.length];
        for (int i = 0; i < temp.length; i++)
        {
            if (withNoise[i] >= 0)
                temp[i] = 0;
            else
                temp[i] = 1;
        }
        return temp;
    }

}
