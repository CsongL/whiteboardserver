package client;

import sun.security.provider.SHA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * @Author: SongLin Chang
 * @Description: Create the Client Frame
 * @Date: Created in 13:52 2021/5/16
 */
public class ClientFrame extends JFrame {
    private static JTextArea jTextArea1;
    private JTextArea jTextArea2;
    public static Graphics g;
    public static ArrayList<Shape> shapeList = new ArrayList<Shape>();
    public static String textPaint;
    private PrintWriter pw;
    private BufferedReader bufferedReader;
    private String userName;
    private  DrawListener drawListener = new DrawListener();
    public static Boolean menuFlag = false;

    public void setClient(String address, int port, String name){
                try{
                    Socket socket = new Socket(address, port);
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    pw = new PrintWriter(socket.getOutputStream(), true);
                    showFrame();
                    jTextArea1.append("Successfully Connect..\n");
                    pw.println("InitialName:"+name);  //  this is important can not be changed because it is associated with whiteboard shown to others
                    while(true){
                        String message = bufferedReader.readLine();
                        handleMessage(message);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    //  close the server there will be a problem
                    System.out.println("Can not connect to the server");
                    jTextArea1.append("Can not connect to the server\n");
                }

        };

    public void handleMessage(String message) throws IOException, ClassNotFoundException {
        if(message.indexOf("{") != -1 && message.indexOf("\"")!=-1){
            System.out.println(message);
            Shape s = JSON.parseObject(message, Shape.class);
            s.draw((Graphics2D) g);
            shapeList.add(s);
        }else if(message.equals("InitialManager")){
            menuFlag = true;
        }
        else{
            System.out.println(message);
            jTextArea1.append(message+"\n");
            jTextArea1.selectAll();
        }
    }

    public void showFrame(){
        // add the listener

        MenuListener menuListener = new MenuListener();
        JFrame jFrame = new JFrame("WhiteBoard");
        jFrame.setSize(1000,500);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        GridLayout grid =new GridLayout(1,2);
        jFrame.setLayout(grid);


        //  Menu Bar
        JMenuBar bar = new JMenuBar();
        //  Menu
        JMenu menu = new JMenu("File");
        JMenuItem item0 = new JMenuItem("New");
        JMenuItem item1 = new JMenuItem("Open");
        JMenuItem item2 = new JMenuItem("Save");
        JMenuItem item3 = new JMenuItem("Close");
        item0.addActionListener(menuListener);
        item1.addActionListener(menuListener);
        item2.addActionListener(menuListener);
        item3.addActionListener(menuListener);
        jFrame.setJMenuBar(bar);
        bar.add(menu);
        menu.add(item0);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);

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
                pw.println(userName+":"+text);
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

        String[] buttonName = {"Line", "Rect", "Oval", "Circle","Pencil","Brush","Easier"};
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


        DrawSpace drawSpace = new DrawSpace();
        JPanel ds = drawSpace.createDrawSpace();
        ds.setPreferredSize(new Dimension(400, 500));
        ds.setBackground(Color.white);
        // transmit the parameter
        menuListener.setDrawSpace(ds);
        menuListener.setjFrame(jFrame);

        jpLeft.add(ds, BorderLayout.CENTER);
        jpLeft.add(buttonBoard, BorderLayout.WEST);

        jFrame.add(jpLeft);
        jFrame.add(jpRight);
        jFrame.setVisible(true);

        ds.addMouseMotionListener(drawListener);
        ds.addMouseListener(drawListener);

        g = ds.getGraphics();
        drawListener.setG(g, pw);
        shapeList = drawListener.getShapeList();
    }
    public class DrawSpace extends JPanel{
        public void paint(Graphics g){
            super.paint(g);
            for(int i=0; i<shapeList.size(); i++){
                Shape s =shapeList.get(i);
                s.draw((Graphics2D) g);
            }
        }
        public JPanel createDrawSpace(){
            JPanel drawSpace = new DrawSpace();
            drawSpace.setPreferredSize(new Dimension(400, 500));
            drawSpace.setBackground(Color.white);
            return drawSpace;
        }
        public void drawImages(BufferedImage image) {
            g.drawImage(image,0,0,null);
        }
    }
    class TextFrame extends Frame{
        public TextFrame(int screenX, int screenY){
            super();
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400,200);
            setLocation(screenX+100, screenY+100);
            setTitle("Text Message");

            JPanel contentPanel = new JPanel();
            contentPanel.setBorder(new EmptyBorder(5,5,5,5));
            setContentPane(contentPanel);

            JLabel wordLabel = new JLabel("Text");
            JTextField wordTextField = new JTextField(10);
            contentPanel.add(wordLabel);
            contentPanel.add(wordTextField);
            JButton submitButton = new JButton("type");
            submitButton.setBounds(100,100,100,100);
            contentPanel.add(submitButton);
            submitButton.addActionListener(drawListener);
        }
    }
}
