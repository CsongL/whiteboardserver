package client;

import sun.security.provider.SHA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.alibaba.fastjson.JSON;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 13:52 2021/5/16
 */
public class ClientFrame extends JFrame {
    private static JTextArea jTextArea1;
    private JTextArea jTextArea2;
    private Graphics g;
    private ArrayList<Shape> shapeList = new ArrayList<Shape>();
    private PrintWriter pw;
    private BufferedReader bufferedReader;

    public static void main(String[] arguments){
       ClientFrame clientFrame = new ClientFrame();
       clientFrame.setClient("localhost", 1234);
    }
    @Override
    public void paint(Graphics g) {
        System.out.println("paint");
        super.paint(g);
        for(int i =0; i <shapeList.size(); i++){
            Shape s = shapeList.get(i);
            s.draw((Graphics2D) g);
        }
    }
    public void setClient(String address, int port){
                try{
                    Socket socket = new Socket(address, port);
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    pw = new PrintWriter(socket.getOutputStream(), true);
                    showFrame();
                    jTextArea1.append("Successfully Connect..\n");
                    while(true){
                        String message = bufferedReader.readLine();
                        handleMessage(message);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

        };

    public void handleMessage(String message) throws IOException, ClassNotFoundException {
        if(message.indexOf("{") != -1 && message.indexOf("\"")!=-1){
            Shape s = JSON.parseObject(message, Shape.class);
            s.draw((Graphics2D) g);
        }else{
            jTextArea1.append(message+"\n");
            jTextArea1.selectAll();
        }
    }

    public void showFrame(){
        // add the listener
        DrawListener drawListener = new DrawListener();
        JFrame jFrame = new JFrame("WhiteBoard");
        jFrame.setSize(1000,500);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        GridLayout grid =new GridLayout(1,2);
        jFrame.setLayout(grid);

        JPanel jpRight = new JPanel();
        jpRight.setPreferredSize(new Dimension(400, 0));
        JLabel jlMessage = new JLabel("Message");
        jpRight.add(jlMessage);
        jTextArea1 = new JTextArea(9, 40);
        jTextArea1.setLineWrap(true); //  used to change a line
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setEditable(false);

        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jpRight.add(jScrollPane1);

        jTextArea2 = new JTextArea(9, 40);
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setEditable(true);
        JScrollPane jScrollPane2 = new JScrollPane(jTextArea2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jpRight.add(jScrollPane2);

        JButton submitButton = new JButton("Submite");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jTextArea2.getText();
                jTextArea2.setText("");
                jTextArea1.append("Me:"+text + "\n");
                jTextArea1.selectAll(); //put the scroll in the last position
                pw.println("Message:"+text);
            }
        });
        jpRight.add(submitButton);

        JPanel jpLeft = new JPanel();
        BorderLayout board = new BorderLayout();
        jpLeft.setLayout(board);

        JPanel paintBoard = new JPanel();
        paintBoard.setPreferredSize(new Dimension(400, 500));
        paintBoard.setBackground(Color.white);

        JPanel buttonBoard = new JPanel();
        buttonBoard.setPreferredSize(new Dimension(80,0));

        String[] buttonName = {"Line", "Rect", "Oval", "Circle","Pencil", "Text"};
        JButton[] jButtons = new JButton[buttonName.length];
        for(int i =0; i <buttonName.length; i++){
            jButtons[i] = new JButton(buttonName[i]);
            buttonBoard.add(jButtons[i]);
            jButtons[i].addActionListener(drawListener);
        }

        JLabel colorLabel = new JLabel();
        colorLabel.setBackground(Color.BLACK);
        colorLabel.setPreferredSize(new Dimension(80,0));
        colorLabel.setOpaque(true);
        colorLabel.setOpaque(true);
        buttonBoard.add(colorLabel);

        JButton colorButton = new JButton();
        colorButton.setOpaque(true);
        colorButton.setBackground(Color.BLACK);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(jFrame, "Chosse a color", null);
                if(color == null) {
                    return;
                }
                colorButton.setBackground(color);
                drawListener.setColor(color);
            }
        });
        buttonBoard.add(colorButton);


        jpLeft.add(paintBoard, BorderLayout.CENTER);
        jpLeft.add(buttonBoard, BorderLayout.WEST);

        jFrame.add(jpLeft);
        jFrame.add(jpRight);

        jFrame.setVisible(true);


        paintBoard.addMouseMotionListener(drawListener);
        paintBoard.addMouseListener(drawListener);

        g = paintBoard.getGraphics();
        drawListener.setG(g, pw);
        shapeList = drawListener.getShapeList();
    }

}
