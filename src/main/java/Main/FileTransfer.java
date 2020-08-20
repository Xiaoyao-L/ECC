package Main;

import Functions.CodeFunc;
import Functions.DistanceFunc;
import Functions.FileFunc;
import Functions.GaussChannel;
import Jama.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;


/**
 * @author Xiaoyao.L
 * @date 2020/8/16 19:53
 * @project ECC
 */
public class FileTransfer {
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JLabel jLabel1 = new JLabel("Original Data");
    private JLabel jLabel2 = new JLabel("Soft Decision");
    private JLabel jLabel3 = new JLabel("Hard Decision");
    private JFileChooser chooser = new JFileChooser();
    private JTextArea textArea_1 = new JTextArea();
    private JTextArea textArea_2 = new JTextArea();
    private JTextArea textArea_3 = new JTextArea();


    public void createAndShowGUI() {
        JFrame frame = new JFrame("Real File Simulator based on (7,4,3)");

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("file");
        JMenuItem item1 = new JMenuItem("import");
        JMenuItem item2 = new JMenuItem("exit");
        jMenuBar.add(jMenu);
        jMenu.add(item1);
        jMenu.add(item2);
        item1.addActionListener(actionEvent -> {
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                String name = chooser.getSelectedFile().getPath();

                String temp = "";
                byte[] fileByte = new FileFunc().getBytes(name);
                for (int i = 0; i < fileByte.length; i++) {
                    temp = temp + FileFunc.byteToBit(new FileFunc().getBytes(name)[i]);

                }
                textArea_1.setText(temp);
                double[][] data_generate = new double[temp.length()/4][4];
                for (int i = 0; i <temp.length()/4; i = i+4)
                {
                    System.out.println(temp.substring(i,i+4));
                    for (int j = 0; j < 4; j++)
                    {
                        data_generate[i][0] = Double.parseDouble(temp.substring(i,i+4).substring(0,1));
                        data_generate[i][1] = Double.parseDouble(temp.substring(i,i+4).substring(1,2));
                        data_generate[i][2] = Double.parseDouble(temp.substring(i,i+4).substring(2,3));
                        data_generate[i][3] = Double.parseDouble(temp.substring(i,i+4).substring(3,4));
                    }
                }

                double[] code_data = new double[temp.length()];
                for (int i = 0; i <temp.length(); i++)
                {
                    code_data[i] = Double.parseDouble(temp.substring(i,i+1));
                }

                double[][] gene = {{1,0,0,0,1,1,1},{0,1,0,0,1,1,0},{0,0,1,0,1,0,1},{0,0,0,1,0,1,1}};
                Matrix generate = new Matrix(gene);
                Matrix codeData = new Matrix(data_generate);
                Matrix codeword_real = new CodeFunc().dex2binary(codeData.times(generate));
                Matrix matrix_bpsk = new CodeFunc().bpsk(codeword_real);
                double [][] noise_file = new double[matrix_bpsk.getRowDimension()][];
                double [][] codewithnoise = new double[matrix_bpsk.getRowDimension()][];
                double [][] demodulator = new double[matrix_bpsk.getRowDimension()][];

                for (int i = 0;i <matrix_bpsk.getRowDimension(); i++)
                {
                    noise_file[i] = new GaussChannel().noiseGenerate(1,0,matrix_bpsk.getColumnDimension());
                    codewithnoise[i] = new GaussChannel().addNoise(matrix_bpsk.getArray()[i],noise_file[i]);
                    demodulator[i] = new CodeFunc().demodulator(codewithnoise[i]);
                }



                double C_[][] = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,0,1,1},
                        {0,1,0,0},{0,1,0,1},{0,1,1,0},{0,1,1,1},
                        {1,0,0,0},{1,0,0,1},{1,0,1,0},{1,0,1,1},
                        {1,1,0,0},{1,1,0,1},{1,1,1,0},{1,1,1,1}
                };
                Matrix C_matrix = new Matrix(C_);
                Matrix codeword_decoder = new CodeFunc().dex2binary(C_matrix.times(generate));

                double[][] distance__eu = new double[codeword_real.getRowDimension()][codeword_decoder.getRowDimension()];
                double[][] distance__hamming = new double[codeword_real.getRowDimension()][codeword_decoder.getRowDimension()];
                for (int j = 0; j < codeword_real.getRowDimension(); j++)
                {
                    for (int i = 0; i < codeword_decoder.getRowDimension(); i++) {

                        double tmpeu = new DistanceFunc().euclideanDistance(codewithnoise[j], new CodeFunc().bpsk(codeword_decoder.getArray()[i]));

                        distance__eu[j][i] = tmpeu;

                        distance__hamming[j][i] = new DistanceFunc().hammingDistance(demodulator[j], codeword_decoder.getArray()[i]);
                    }
                }

                int[] index__eu = new int[codeword_real.getRowDimension()];
                double after_eu[][] = new double[codeword_real.getRowDimension()][];
                String after_eu_ = "";
                for (int m = 0; m < codeword_real.getRowDimension(); m++) {
                    double[] tem = distance__eu[m];
                    index__eu[m] = IntStream.range(0, tem.length).reduce((i, j) -> tem[i] > tem[j] ? j : i).getAsInt();
                    after_eu[m] = C_matrix.getArray()[index__eu[m]];
                }
                System.out.println(Arrays.toString(after_eu[0]));
                int count_eu = 0;
                for(int i = 0; i < codeword_real.getRowDimension(); i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        after_eu_ = after_eu_ + (int) after_eu[i][j];
                        if (data_generate[i][j] == after_eu[i][j])
                        {
                            continue;
                        }
                        else
                        {
                            count_eu++;
                        }
                    }
                }
                textArea_2.setText(after_eu_);
                jLabel2.setText("Soft Decision Bit Error Rate is " + (double)count_eu/temp.length());

                int[] index__hamming = new int[codeword_real.getRowDimension()];
                double after_hamming[][] = new double[codeword_real.getRowDimension()][];
                for (int m = 0; m < codeword_real.getRowDimension(); m++) {
                    double[] tem = distance__hamming[m];
                    index__hamming[m] = IntStream.range(0, tem.length).reduce((i, j) -> tem[i] > tem[j] ? j : i).getAsInt();
                    after_hamming[m] = C_matrix.getArray()[index__hamming[m]];
                }
                System.out.println(Arrays.toString(after_hamming[0]));
                int count_hamming = 0;
                String after_hamming_ = "";
                for(int i = 0; i < codeword_real.getRowDimension(); i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        after_hamming_ = after_hamming_ + (int)after_hamming[i][j];
                        if (data_generate[i][j] == after_hamming[i][j])
                        {
                            continue;
                        }
                        else
                        {
                            count_hamming++;
                        }
                    }
                }
                textArea_3.setText(after_hamming_);
                jLabel3.setText("Hard Decision Bit Error Rate is " + (double)count_hamming/temp.length());
            }
        });
        item2.addActionListener(actionEvent -> System.exit(0));
        frame.setJMenuBar(jMenuBar);
        frame.setLayout(new GridLayout(1,3));
        frame.add(p1);p1.setLayout(new BorderLayout());
        frame.add(p2);p2.setLayout(new BorderLayout());
        frame.add(p3);p3.setLayout(new BorderLayout());

        p1.add(jLabel1,BorderLayout.SOUTH);p2.add(jLabel2,BorderLayout.SOUTH);p3.add(jLabel3,BorderLayout.SOUTH);

        JScrollPane js_1 = new JScrollPane(textArea_1);
        textArea_1.setLineWrap(true);
        js_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p1.add(js_1);
        JScrollPane js_2 = new JScrollPane(textArea_2);
        textArea_2.setLineWrap(true);
        js_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p2.add(js_2);
        JScrollPane js_3 = new JScrollPane(textArea_3);
        textArea_3.setLineWrap(true);
        js_3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js_3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p3.add(js_3);




        frame.setVisible(true);

        frame.setSize(900, 400);

        frame.setLocation(200, 100);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

/*    public static void main(String[] args){
        new FileTransfer().createAndShowGUI();
    }*/
}
