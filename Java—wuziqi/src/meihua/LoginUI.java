package meihua;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    int uiwidth=1300;
    int uiheight=800;
    Image image1=new ImageIcon("pic/LoginPicture.jpg").getImage();
    public LoginUI(){
        this.setTitle("五子棋登录界面");
        this.setBounds(350,50,uiwidth,uiheight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        JButton jButton=new JButton("进入游戏", new ImageIcon("pic/进入游戏.png"));
        jButton.setBounds(550,680,120,30);
        jButton.addActionListener(e->{
            if(e.getActionCommand().equals("进入游戏")){
                new Wuziqi();
                this.dispose();
                JOptionPane.showMessageDialog(this,"请先选择游戏模式（默认人人对战）");
            }
        });
        this.add(jButton);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        g.drawImage(image1,0,0,this.getWidth(),this.getHeight(),this);
    }

    public static void main(String[] args) {
        new LoginUI();
    }

}
