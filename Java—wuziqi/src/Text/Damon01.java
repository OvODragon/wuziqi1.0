package Text;

import javax.swing.*;
import java.awt.*;

public class Damon01 extends JFrame {
    Mypanel mp;
    public Damon01(){
        mp=new Mypanel();
        Thread t=new Thread(mp);
        t.start();
        this.setBounds(400,400,500,400);
        this.add(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String [] args){
        Damon01 damon01=new Damon01();
    }
}
class Mypanel extends JPanel implements Runnable{
    private Font font;
    private String str;
    private int fontx;
    private int fonty;
    private int speed;
    public Mypanel(){
        fontx=125;
        fonty=300;
        speed=5;
        font=new Font("华文彩云",Font.BOLD,30);
        str="游戏结束";
    }
    public void paint(Graphics g){
        g.fill3DRect(0,0,400,300,false);
        g.setColor(Color.red);
        g.setFont(font);
        if(fonty>150){
            g.drawString(str,fontx,fonty-=speed);
        }
        else{
            g.drawString(str,fontx,fonty);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
            if (fonty < 150)
                break;
        }
    }
}