package Main;

import Functions.CodeFunc;
import Functions.DistanceFunc;
import Functions.GaussChannel;
import Jama.Matrix;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * @author Xiaoyao.L
 * @date 2020/8/17 14:45
 * @project ECC
 */
public class Gui extends JFrame implements ActionListener, ItemListener{

  /*  public static CodeFunc codeGenerate = new CodeFunc();
    public static DistanceFunc distanceFunc = new DistanceFunc();
    public static GaussChannel gaussChannel = new GaussChannel();*/
    private JPanel p = new JPanel();


    private JLabel label1 = new JLabel("Source code: ");
    private JLabel label2 = new JLabel("Generate Matrix: ");
    private JLabel label3 = new JLabel("Codewords: ");
    private JLabel label4 = new JLabel("Select one codeword: ");
    private JLabel label5 = new JLabel("BPSK Modulator: ");
    private JLabel label6 = new JLabel("AWGN Channel: ");
    private JLabel label7 = new JLabel("BPSK Demodulator: ");
    private JLabel label8 = new JLabel("Decoder: ");
    private JLabel label9 = new JLabel("Soft Decision: ");
    private JLabel label10 = new JLabel("Hard Decision: ");
    private JComboBox comboBox1 = new JComboBox();
    private JComboBox comboBox2 = new JComboBox();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu1 = new JMenu("Run");
    private JMenu menu2 = new JMenu("About");
    private JMenuItem run = new JMenuItem("Run Default Code Example");
    private JMenuItem simulator = new JMenuItem ("Use a real file to simulate");
    private JMenuItem about = new JMenuItem("About");
    private JTextArea generateMatrix = new JTextArea("Generate Matrix");
    private JTextArea codewords = new JTextArea("Codewords");
    private JTextField textField1 = new JTextField("BPSK Modulator");
    private JTextField textField2 = new JTextField("Codeword with noise");
    private JTextField textField3 = new JTextField("BPSK Demodulator");
    private JTextField textField4 = new JTextField("Soft Decision");
    private JTextField textField5 = new JTextField("Hard Decision");

    private String[] tableTitle = {"Codewords","Euclidean Distance","Hamming Distance"};
    private Object[][] tableData = {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}};
    private JTable table = new JTable();

    private static Matrix code1;
    public Gui(){
        super("Error Correct Coding - Soft-Decision Decoding");

        init();
    }

    public void init(){

        p.setLayout(null);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(run);menu1.add(simulator);
        menu2.add(about);
        run.addActionListener(this);
        simulator.addActionListener(this);
        about.addActionListener(this);
        this.setJMenuBar(menuBar);
        this.add(p);

        comboBox1.addItem("please select the code");
        comboBox1.addItem("(8,2,5)");
        comboBox1.addItem("(7,4,3)");
        comboBox1.addItem("(5,2,3)");
        comboBox1.addItem("(8,3,4)");
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    if (itemEvent.getItem().equals("(8,2,5)")){
                        code1();
                    }
                    else if(itemEvent.getItem().equals("(7,4,3)")){
                        code2();
                }
                    else if(itemEvent.getItem().equals("(5,2,3)")) {
                        code3();
                    }
                    else if(itemEvent.getItem().equals("(8,3,4)")) {
                        code4();
                    }
                }
            }
        });
        p.add(label1);label1.setBounds(40,20,200,20);
        p.add(comboBox1);comboBox1.setBounds(200,20,450,20);

        p.add(label2);label2.setBounds(40,100,200,20);
        generateMatrix.setLineWrap(true);
        //generateMatrix.setEnabled(false);
        generateMatrix.setForeground(Color.BLACK);
        generateMatrix.setEditable(false);
        JScrollPane js1 = new JScrollPane(generateMatrix);
        js1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p.add(js1);js1.setBounds(150,60,200,100);

        p.add(label3);label3.setBounds(440,100,200,20);
        codewords.setLineWrap(true);
        //codewords.setEnabled(false);
        codewords.setForeground(Color.BLACK);
        codewords.setEditable(false);
        JScrollPane js2 = new JScrollPane(codewords);
        js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p.add(js2);js2.setBounds(550,60,200,100);

        p.add(label4);label4.setBounds(40,180,200,20);
        comboBox2.addItem("please choose the codeword");
        p.add(comboBox2);comboBox2.setBounds(200,180,450,20);
        comboBox2.addItemListener(this::itemStateChanged);
        //comboBox1.addItem("(8,2,5)");
        //comboBox1.addItem("(7,4,3)");
        p.add(label5);label5.setBounds(40,220,200,20);
        textField1.setEditable(false);
        p.add(textField1);textField1.setBounds(200,220,450,20);

        p.add(label6);label6.setBounds(40,260,200,20);
        p.add(textField2);textField2.setBounds(200,260,450,20);
        textField2.setEditable(false);

        p.add(label7);label7.setBounds(40,300,200,20);
        p.add(textField3);textField3.setBounds(200,300,450,20);
        textField3.setEditable(false);

        p.add(label8);label8.setBounds(40,400,200,20);
        DefaultTableModel model = new DefaultTableModel(tableData,tableTitle);
        table.setModel(model);
        JScrollPane js3 = new JScrollPane(table);
        js3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p.add(js3);js3.setBounds(200,340,450,150);

        p.add(label9);p.add(label10);p.add(textField4);p.add(textField5);
        label9.setBounds(40,530,200,20);
        textField4.setBounds(200,530,450,20);
        textField4.setEditable(false);
        label10.setBounds(40,570,200,20);
        textField5.setBounds(200,570,450,20);
        textField5.setEditable(false);


        this.setBounds(200,100,800,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    public static int[] roundUp(double[] array) {
        return DoubleStream.of(array).mapToInt(d -> (int) Math.ceil(d)).toArray();
    }

    public void code1(){
        double G[][] = {{1,0,1,1,1,1,0,0},{0,1,1,1,0,0,1,1}};
        //System.out.println(G[0].length);
        //double G[][] = {{1,0,0,0,1,1,1},{0,1,0,0,1,1,0},{0,0,1,0,1,0,1},{0,0,0,1,0,1,1}};

        Matrix G_matrix = new Matrix(G);
        String GM = "";
        for (int i = 0; i < G_matrix.getRowDimension(); i++) {
            GM = GM + Arrays.toString(roundUp(G[i])).replace("["," ").replace("]"," ")
                    .replace(","," ") + "\n";
        }
        generateMatrix.setText(GM);
        double C[][] = {{0,0},{0,1},{1,0},{1,1}};
        Matrix C_matrix = new Matrix(C);
        Matrix codeword = new CodeFunc().dex2binary(C_matrix.times(G_matrix));
        code1 = codeword;
        String codeword_ = "";
        comboBox2.removeAllItems();
        for (int i = 0; i < codeword.getRowDimension(); i++) {
            codeword_ = codeword_ + Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ").
                    replace("]"," ").replace(","," ") + "\n";
            comboBox2.addItem(Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ")
                    .replace("]"," ").replace(","," "));
        }

/*        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // 选择的下拉框选项
                System.out.println(Arrays.toString(e.getItem().toString().split("  ")));
                String[] temp = e.getItem().toString().split("  ");
                double[] data = new double[temp.length];
                for (int i = 0; i < temp.length; i++)
                {
                    data[i] = Double.parseDouble(temp[i]);
                }
                //System.out.println(Arrays.toString(data));
                double[] code_bpsk = new CodeFunc().bpsk(data);
                String[] code_show = new CodeFunc().bpsk2String(data);
                textField1.setText(Arrays.toString(code_show).replace("["," ")
                        .replace("]"," ").replace(","," "));

                double[] noise = new GaussChannel().noiseGenerate(1, 0, code_bpsk.length);
                double[] withNoise = new GaussChannel().addNoise(code_bpsk,noise);
                textField2.setText(Arrays.toString(new GaussChannel().double2Stirng(withNoise)).replace("["," ")
                        .replace("]"," ").replace(","," "));
                double[] dem = new CodeFunc().demodulator(withNoise);
                String[] dem_show = new CodeFunc().bpsk2String(dem);
                textField3.setText(Arrays.toString(dem_show).replace("["," ")
                        .replace("]"," ").replace(","," "));
                setTable(new CodeFunc().dex2binary(C_matrix.times(G_matrix)),dem,withNoise);
            }
        });*/
        codewords.setText(codeword_);
    }

    public void code2(){
        //double G[][] = {{1,0,1,1,1,1,0,0},{0,1,1,1,0,0,1,1}};
        //System.out.println(G[0].length);
        double G[][] = {{1,0,0,0,1,1,1},{0,1,0,0,1,1,0},{0,0,1,0,1,0,1},{0,0,0,1,0,1,1}};

        Matrix G_matrix = new Matrix(G);
        String GM = "";
        for (int i = 0; i < G_matrix.getRowDimension(); i++) {
            GM = GM + Arrays.toString(roundUp(G[i])).replace("["," ").replace("]"," ")
                    .replace(","," ") + "\n";
        }
        generateMatrix.setText(GM);
        double C[][] = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,0,1,1},
                {0,1,0,0},{0,1,0,1},{0,1,1,0},{0,1,1,1},
                {1,0,0,0},{1,0,0,1},{1,0,1,0},{1,0,1,1},
                {1,1,0,0},{1,1,0,1},{1,1,1,0},{1,1,1,1}
        };
        Matrix C_matrix = new Matrix(C);
        Matrix codeword = new CodeFunc().dex2binary(C_matrix.times(G_matrix));
        code1 = codeword;
        String codeword_ = "";
        comboBox2.removeAllItems();
        for (int i = 0; i < codeword.getRowDimension(); i++) {
            codeword_ = codeword_ + Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ").
                    replace("]"," ").replace(","," ") + "\n";
            comboBox2.addItem(Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ")
                    .replace("]"," ").replace(","," "));
        }

/*        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // 选择的下拉框选项
                System.out.println(Arrays.toString(e.getItem().toString().split("  ")));
                String[] temp = e.getItem().toString().split("  ");
                double[] data = new double[temp.length];
                for (int i = 0; i < temp.length; i++)
                {
                    data[i] = Double.parseDouble(temp[i]);
                }
                //System.out.println(Arrays.toString(data));
                double[] code_bpsk = new CodeFunc().bpsk(data);
                String[] code_show = new CodeFunc().bpsk2String(data);
                textField1.setText(Arrays.toString(code_show).replace("["," ")
                        .replace("]"," ").replace(","," "));

                double[] noise = new GaussChannel().noiseGenerate(1, 0, code_bpsk.length);
                double[] withNoise =new GaussChannel().addNoise(code_bpsk,noise);
                textField2.setText(Arrays.toString(new GaussChannel().double2Stirng(withNoise)).replace("["," ")
                        .replace("]"," ").replace(","," "));
                double[] dem = new CodeFunc().demodulator(withNoise);
                String[] dem_show = new CodeFunc().bpsk2String(dem);
                textField3.setText(Arrays.toString(dem_show).replace("["," ")
                        .replace("]"," ").replace(","," "));
                setTable(codeword,dem,withNoise);
            }
        });*/
        codewords.setText(codeword_);
    }

    public void code3(){
        //double G[][] = {{1,0,1,1,1,1,0,0},{0,1,1,1,0,0,1,1}};
        //System.out.println(G[0].length);
        double G[][] = {{1,0,1,0,1},{0,1,0,1,1}};

        Matrix G_matrix = new Matrix(G);
        String GM = "";
        for (int i = 0; i < G_matrix.getRowDimension(); i++) {
            GM = GM + Arrays.toString(roundUp(G[i])).replace("["," ").replace("]"," ")
                    .replace(","," ") + "\n";
        }
        generateMatrix.setText(GM);
        double C[][] = {{0,0},{0,1},{1,0},{1,1}};
        Matrix C_matrix = new Matrix(C);
        Matrix codeword = new CodeFunc().dex2binary(C_matrix.times(G_matrix));
        code1 = codeword;
        String codeword_ = "";
        comboBox2.removeAllItems();
        for (int i = 0; i < codeword.getRowDimension(); i++) {
            codeword_ = codeword_ + Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ").
                    replace("]"," ").replace(","," ") + "\n";
            comboBox2.addItem(Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ")
                    .replace("]"," ").replace(","," "));
        }

/*        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // 选择的下拉框选项
                System.out.println(Arrays.toString(e.getItem().toString().split("  ")));
                String[] temp = e.getItem().toString().split("  ");
                double[] data = new double[temp.length];
                for (int i = 0; i < temp.length; i++)
                {
                    data[i] = Double.parseDouble(temp[i]);
                }
                //System.out.println(Arrays.toString(data));
                double[] code_bpsk = new CodeFunc().bpsk(data);
                String[] code_show = new CodeFunc().bpsk2String(data);
                textField1.setText(Arrays.toString(code_show).replace("["," ")
                        .replace("]"," ").replace(","," "));

                double[] noise = new GaussChannel().noiseGenerate(1, 0, code_bpsk.length);
                double[] withNoise =new GaussChannel().addNoise(code_bpsk,noise);
                textField2.setText(Arrays.toString(new GaussChannel().double2Stirng(withNoise)).replace("["," ")
                        .replace("]"," ").replace(","," "));
                double[] dem = new CodeFunc().demodulator(withNoise);
                String[] dem_show = new CodeFunc().bpsk2String(dem);
                textField3.setText(Arrays.toString(dem_show).replace("["," ")
                        .replace("]"," ").replace(","," "));
                setTable(codeword,dem,withNoise);
            }
        });*/
        codewords.setText(codeword_);
    }

    public void code4(){
        //double G[][] = {{1,0,1,1,1,1,0,0},{0,1,1,1,0,0,1,1}};
        //System.out.println(G[0].length);
        double G[][] = {{1,1,0,0,1,1,0,0},{0,1,1,0,0,1,1,0},{0,0,1,1,0,0,1,1}};

        Matrix G_matrix = new Matrix(G);
        String GM = "";
        for (int i = 0; i < G_matrix.getRowDimension(); i++) {
            GM = GM + Arrays.toString(roundUp(G[i])).replace("["," ").replace("]"," ")
                    .replace(","," ") + "\n";
        }
        generateMatrix.setText(GM);
        double C[][] = {{0,0,0},{0,0,1},{0,1,0},{0,1,1},
                {1,0,0},{1,0,1},{1,1,0},{1,1,1}
        };
        Matrix C_matrix = new Matrix(C);
        Matrix codeword = new CodeFunc().dex2binary(C_matrix.times(G_matrix));
        code1 = codeword;
        String codeword_ = "";
        comboBox2.removeAllItems();
        for (int i = 0; i < codeword.getRowDimension(); i++) {
            codeword_ = codeword_ + Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ").
                    replace("]"," ").replace(","," ") + "\n";
            comboBox2.addItem(Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ")
                    .replace("]"," ").replace(","," "));
        }

/*        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // 选择的下拉框选项
                System.out.println(Arrays.toString(e.getItem().toString().split("  ")));
                String[] temp = e.getItem().toString().split("  ");
                double[] data = new double[temp.length];
                for (int i = 0; i < temp.length; i++)
                {
                    data[i] = Double.parseDouble(temp[i]);
                }
                //System.out.println(Arrays.toString(data));
                double[] code_bpsk = new CodeFunc().bpsk(data);
                String[] code_show = new CodeFunc().bpsk2String(data);
                textField1.setText(Arrays.toString(code_show).replace("["," ")
                        .replace("]"," ").replace(","," "));

                double[] noise = new GaussChannel().noiseGenerate(1, 0, code_bpsk.length);
                double[] withNoise =new GaussChannel().addNoise(code_bpsk,noise);
                textField2.setText(Arrays.toString(new GaussChannel().double2Stirng(withNoise)).replace("["," ")
                        .replace("]"," ").replace(","," "));
                double[] dem = new CodeFunc().demodulator(withNoise);
                String[] dem_show = new CodeFunc().bpsk2String(dem);
                textField3.setText(Arrays.toString(dem_show).replace("["," ")
                        .replace("]"," ").replace(","," "));
                setTable(codeword,dem,withNoise);
            }
        });*/
        codewords.setText(codeword_);
    }

    public void setTable(Matrix codeword, double[] dem, double[] withnoise){
        
        String[][] data_ = new String[codeword.getRowDimension()][3];
        //double code_dem =
        double[] distance_eu = new double[codeword.getRowDimension()];
        double[] distance_hamming = new double[codeword.getRowDimension()];

        for(int i = 0; i < codeword.getRowDimension(); i++)
        {
            //System.out.println(codeword.getRowDimension());
                data_[i][0] = Arrays.toString(roundUp(codeword.getArrayCopy()[i])).replace("["," ")
                        .replace("]"," ").replace(","," ");
                //data_[i][0] = "0";
                data_[i][1] = String.valueOf(new DistanceFunc().euclideanDistance(withnoise,new CodeFunc().bpsk(codeword.getArrayCopy()[i])));
                distance_eu[i] = new DistanceFunc().euclideanDistance(withnoise,new CodeFunc().bpsk(codeword.getArrayCopy()[i]));
                data_[i][2] = String.valueOf(new DistanceFunc().hammingDistance(dem,codeword.getArrayCopy()[i]));
                distance_hamming[i] = new DistanceFunc().hammingDistance(dem,codeword.getArrayCopy()[i]);
        }

        int index_eu = IntStream.range(0, distance_eu.length).reduce((i, j) -> distance_eu[i] > distance_eu[j] ? j : i).getAsInt();
        textField4.setText(data_[index_eu][0]);

        int index_hamming = IntStream.range(0, distance_eu.length).reduce((i, j) -> distance_hamming[i] > distance_hamming[j] ? j : i).getAsInt();
        textField5.setText(data_[index_hamming][0]);

        DefaultTableModel model = new DefaultTableModel(data_,tableTitle);
        table.setModel(model);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == run){
            code2();
        }
        else if (actionEvent.getSource() == simulator)
        {
            new FileTransfer().createAndShowGUI();
        }
        else if(actionEvent.getSource() == about)
        {
            JOptionPane.showInternalMessageDialog(null, "Author: Xiaoyao Luo","About", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void main(String[] args) {
        new Gui();
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // 选择的下拉框选项
            System.out.println(Arrays.toString(e.getItem().toString().split("  ")));
            String[] temp = e.getItem().toString().split("  ");
            double[] data = new double[temp.length];
            for (int i = 0; i < temp.length; i++)
            {
                data[i] = Double.parseDouble(temp[i]);
            }
            //System.out.println(Arrays.toString(data));
            double[] code_bpsk = new CodeFunc().bpsk(data);
            String[] code_show = new CodeFunc().bpsk2String(data);
            textField1.setText(Arrays.toString(code_show).replace("["," ")
                    .replace("]"," ").replace(","," "));

            double[] noise = new GaussChannel().noiseGenerate(1, 0, code_bpsk.length);
            double[] withNoise = new GaussChannel().addNoise(code_bpsk,noise);
            textField2.setText(Arrays.toString(new GaussChannel().double2Stirng(withNoise)).replace("["," ")
                    .replace("]"," ").replace(","," "));
            double[] dem = new CodeFunc().demodulator(withNoise);
            String[] dem_show = new CodeFunc().bpsk2String(dem);
            textField3.setText(Arrays.toString(dem_show).replace("["," ")
                    .replace("]"," ").replace(","," "));
            setTable(code1,dem,withNoise);
        }

    }
}


