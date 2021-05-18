package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 15:16 2021/5/18
 */
public class MenuListener implements ActionListener {
    private JPanel drawSpace;

    public void setDrawSpace(JPanel drawSpace) {
        this.drawSpace = drawSpace;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //  get the command that indicates execute which kind of actions
        String command = e.getActionCommand();

        Object[] options = {"Yes", "No"};
        // Create a blank drawsplace
        if("New".equals(command)){
            int value = JOptionPane.showConfirmDialog(null,"Do you want to save the current file?", "Warning",0,JOptionPane.QUESTION_MESSAGE);
            if(value==0){
                saveFile();
            }
            if(value == 1){
                ClientFrame.shapeList.removeAll(ClientFrame.shapeList);
                drawSpace.repaint();
            }
        }
        // open a file
        if("Open".equals(command)){
            int value = JOptionPane.showConfirmDialog(null,"Do you need to save the current file?","Warning",0,JOptionPane.QUESTION_MESSAGE);
            if(value ==0){
                saveFile();
            }
            if(value ==1){
                ClientFrame.shapeList.removeAll(ClientFrame.shapeList);
                drawSpace.repaint();
                try{
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(null);
                    File file = chooser.getSelectedFile();
                    if(file == null){
                        JOptionPane.showMessageDialog(null,"Do not choose a file");
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
        }
        if("Save".equals(command)){
            saveFile();
        }
    }
    public void saveFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();

        if(file==null){
            JOptionPane.showMessageDialog(null, "Do not choose a file");
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
