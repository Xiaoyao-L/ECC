package Functions;

/**
 * @author Xiaoyao.L
 * @date 2020/8/17 16:07
 * @project ECC
 */
public class DistanceFunc {

    public int hammingDistance(double[] a,double[] b){
        int distance = 0;

        for (int i = 0; i< a.length; i++)
        {
            if (a[i] != b[i])
            {

                distance ++;
            }

        }
        return distance;
    }

    public double euclideanDistance(double[] a, double[] b){
        double distance = 0;
        for (int i = 0; i< a.length; i++)
        {
            distance = distance + (a[i]-b[i])*(a[i]-b[i]);
        }
        return Math.sqrt(distance);
    }
}
