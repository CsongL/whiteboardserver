package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 15:16 2021/5/18
 */
public class MenuListener implements ActionListener {
    private JPanel drawSpace;
    private JFrame jFrame;
    private PrintWriter pw;

    public void setDrawSpace(JPanel drawSpace) {
        this.drawSpace = drawSpace;
    }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //  get the command that indicates execute which kind of actions
        if(!ClientFrame.menuFlag){
            JOptionPane.showMessageDialog(jFrame,"Only the manager has the right to use the function");
            return;
        }
        String command = e.getActionCommand();

        if("Close".equals(command)){
            System.exit(0);
        }
        // Create a blank drawsplace
        if("New".equals(command)){
            int value = JOptionPane.showConfirmDialog(jFrame,"Do you want to save the current file?", "Warning",0,JOptionPane.QUESTION_MESSAGE);
            if(value==0){
                saveFile();
            }
            if(value == 1){
                pw.println("FileCommond_New");
                pw.flush();
                ClientFrame.shapeList.removeAll(ClientFrame.shapeList);
                drawSpace.repaint();
            }
        }
        // open a file
        if("Open".equals(command)){
            int value = JOptionPane.showConfirmDialog(jFrame,"Do you want to save the current file?","Warning",0,JOptionPane.QUESTION_MESSAGE);
            if(value ==0){
                saveFile();
            }
            if(value==1){
                openPic();
            }
        }
        if("Save".equals(command)){
            savePic();
        }
    }

    public void openfile(){
        ClientFrame.shapeList.removeAll(ClientFrame.shapeList);
                drawSpace.repaint();
                try{
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(jFrame);
                    File file = chooser.getSelectedFile();
                    if(file == null){
                        JOptionPane.showMessageDialog(jFrame,"Do not choose a file");
                    } else{
                        FileInputStream fileInputStream = new FileInputStream(file);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        ArrayList<Shape> shapeArrayList = (ArrayList<Shape>) objectInputStream.readObject();
                        for(int i =0; i<shapeArrayList.size(); i++){
                            Shape shape = shapeArrayList.get(i);
                            ClientFrame.shapeList.add(shape);
                        }
                        drawSpace.repaint();
                        objectInputStream.close();
                        fileInputStream.close();
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
    }
    public void openPic()  {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(jFrame);
        File file = chooser.getSelectedFile();
        if(file==null){
            JOptionPane.showMessageDialog(jFrame, "Do not choose a file");
        } else{
            try{
                BufferedImage image = ImageIO.read(file);
                ClientFrame.g.drawImage(image,0,0,null);
            }catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
    public void savePic() {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(jFrame);
        File file = chooser.getSelectedFile();
        if(file==null){
            JOptionPane.showMessageDialog(jFrame, "Do not choose a file");
        } else{
            try {
                Dimension iamgeSize = drawSpace.getSize();
                BufferedImage image = new BufferedImage(iamgeSize.width, iamgeSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                drawSpace.paint(graphics);
                graphics.dispose();
                BufferedImage myImage = new BufferedImage(iamgeSize.width, iamgeSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics graphics2 = myImage.getGraphics();
                graphics2.drawImage(image, 0, 0, null);
                graphics2.dispose();
                ImageIO.write(myImage, "jpg", file);
                JOptionPane.showMessageDialog(jFrame, "Successfully save");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
    public void saveFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();

        if(file==null){
            JOptionPane.showMessageDialog(jFrame, "Do not choose a file");
        } else{
            try{
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
                objectOutputStream.writeObject(ClientFrame.shapeList);
                JOptionPane.showMessageDialog(null, "Successfully save");
                objectOutputStream.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
