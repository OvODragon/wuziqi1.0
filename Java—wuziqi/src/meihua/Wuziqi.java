package meihua;

import javax.swing.*;
import java.awt.*;


public class Wuziqi extends JFrame {
        MyPanel1 mp1;
        MyPanel2 mp2;
        int uiwidth=1300;
        int uiheight=800;
    public Wuziqi(){
        this.setBounds(350,50,uiwidth,uiheight);
        this.setLayout(new BorderLayout());
        this.setTitle("五子棋-单机版");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        mp1=new MyPanel1();
        mp1.addMouseListener(new PanelListener2(mp1));
        this.add(mp1,BorderLayout.CENTER);
        mp2=new MyPanel2(mp1);
        this.add(mp2,BorderLayout.EAST);
    }
}



