/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author USER
 */
//contains the two components ball and paddle

public class PongPanel extends JPanel{
    private Racket racket,racket2;
private Ball ball;
private int a=0,b=0;
private JLabel scoreLabel;
private int score = 0,score1=0;
//networking variables
static DatagramSocket ps;
static byte[] rdata,sdata;
static InetAddress IPAddress;
static int port;

public PongPanel(Pong game) {
   
    racket = new Racket(game.getFrame(), game.getFrame().getHeight()-50);
     racket2 = new Racket(game.getFrame(), 0);
    ball = new Ball();
    a=1;
    b=0;
    scoreLabel = new JLabel(Integer.toString(score));
    scoreLabel.setFont(new Font("sansserif", Font.PLAIN, 30));
    //add(scoreLabel);

    Timer timer = new Timer(5, new TimerHandler());
    timer.start();
    
        try {
            //initilaise server
            ps=new DatagramSocket(4242);
        } catch (SocketException ex) {
            Logger.getLogger(PongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    rdata=new byte[1024];
    sdata=new byte[1024];

    addKeyListener((KeyListener) new KeyHandler());
    setFocusable(true);
}

private void update() {
    racket2.updatePosition();
    racket.updatePosition();
    ball.updatePosition();
    checkCollisionBallSides();
    checkCollisionBallRacket();
    checkCollsionBallRacket2();
    repaint();
}

private void checkCollisionBallSides() {
    if (ball.getX() < 0 || ball.getX() > getWidth() - ball.getWidth() - (getInsets().left + getInsets().right))
        ball.setXA(-ball.getXA());  //neg value go opposite
    else if (ball.getY() < 0)
        {
        JOptionPane.showMessageDialog(null,"Player1 : "+Integer.toString(score)+"\n"+"Player2 :"+Integer.toString(score1) , "Pong", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        }
        //ball.setYA(-ball.getYA());   //go back
    else if (ball.getY() > getHeight() - ball.getHeight()) {
        JOptionPane.showMessageDialog(null, "Player1 : "+Integer.toString(score)+"\n"+"Player2 : "+Integer.toString(score1), "Pong", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}

private void checkCollisionBallRacket() {
    if (ball.getBounds().y + ball.getHeight() == racket.getBounds().y &&
        ball.getBounds().x + ball.getWidth() > racket.getBounds().x &&
        racket.getBounds().x + racket.getWidth() > ball.getBounds().x) {
        ball.setYA(-ball.getYA());
        a=0;
        b=1;
        score++;
        scoreLabel.setText("Player1 : "+Integer.toString(score)+"\n"+"Player2 :"+Integer.toString(score1));
        //scoreLabel.setText(Integer.toString(score));
    }
}


private void checkCollsionBallRacket2() {
    if (ball.getBounds().y == racket2.getBounds().y+ + racket2.getHeight() &&
        ball.getBounds().x + ball.getWidth() > racket2.getBounds().x &&
        racket2.getBounds().x + racket2.getWidth() > ball.getBounds().x) {
        ball.setYA(-ball.getYA());
        score1++;
        a=1;
        b=0;
        scoreLabel.setText("Player1 : "+Integer.toString(score)+"\n"+"Player2 :"+Integer.toString(score1));
    }    
    }


@Override
public void paint(Graphics g) {
    super.paint(g);
    racket2.paint(g);
    racket.paint(g);
    ball.paint(g);
}

    

   

private class KeyHandler implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if(a==1 && b==0)
        {
        racket.pressed(e);    
        }
       
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        racket.released(e);
        //racket2.released(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {          
    }   
}

private class TimerHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //networking part to be sent every 5 seconds
            
            
         update();    
         int x=racket.getX();
         int y=racket.getY();
         int x2=racket2.getX();
         int y2=racket2.getY();
         int vx=racket.getXA();
         int vy=racket.getYA();
         int vx2=racket2.getXA();
         int vy2=racket2.getYA();
         int x1=ball.getX();
         int y1=ball.getY();
         int vx1=ball.getXA();
         int vy1=ball.getYA();
         int a1=a;
         int b1=b;
        String capitalizedSentence = x+","+y+","+vx+","+vy+","+x1+","+y1+","+vx1+","+vy1+","+x2+","+y2+","+vx2+","+vy2+","+a1+","+b1;  
        sdata = capitalizedSentence.getBytes();  
        //send to server only
         DatagramPacket sendPacket;
        
        try {
            IPAddress=InetAddress.getByName("10.192.48.207");
        } catch (UnknownHostException ex) {
            Logger.getLogger(PongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            sendPacket = new DatagramPacket(sdata, sdata.length,IPAddress  ,4243);  
        
             
        try {
            ps.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(PongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
}
    
}
