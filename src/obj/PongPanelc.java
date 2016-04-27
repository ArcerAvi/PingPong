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
import java.net.SocketException;
import java.util.ArrayList;
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

public class PongPanelc extends JPanel {
    private Racket racket,racket2;
    static DatagramSocket pc;
    static byte[] rdata,sdata;
    private Ball ball;
    private int a=0,b=0;
private JLabel scoreLabel;
private int score = 0,score1=0;
String[] ee;

public PongPanelc(Pongc game) {
    racket2 = new Racket(game.getFrame(), 0);
    racket = new Racket(game.getFrame(), game.getFrame().getHeight()-50);
     ball = new Ball();

    scoreLabel = new JLabel(Integer.toString(score));
    scoreLabel.setFont(new Font("sansserif", Font.PLAIN, 30));
    //add(scoreLabel);

    Timer timer = new Timer(5, new TimerHandler());
    timer.start();
    rdata=new byte[1024];
    sdata=new byte[1024];


    addKeyListener((KeyListener) new KeyHandler());
    setFocusable(true);
    
    
    
}


private void update() {
    racket.updatePosition();
    racket2.updatePosition();
    ball.updatePosition();
    checkCollisionBallSides();
    checkCollisionBallRacket();
    checkCollisionBallRacket2();
    repaint();
}


private void checkCollisionBallSides() {
    if (ball.getX() < 0 || ball.getX() > getWidth() - ball.getWidth() - (getInsets().left + getInsets().right))
        ball.setXA(-ball.getXA());
    else if (ball.getY() < 0)
        {
        JOptionPane.showMessageDialog(null,"Player1 : "+Integer.toString(score)+"\n"+"Player2 :"+Integer.toString(score1) , "Pong", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        }
        //ball.setYA(-ball.getYA());   //go back
    else if (ball.getY() > getHeight() - ball.getHeight()) {
        JOptionPane.showMessageDialog(null, "Player1 : "+Integer.toString(score)+"\n"+"Player2 : "+Integer.toString(score1), "Pong", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);}
}

private void checkCollisionBallRacket() {
    if (ball.getBounds().y + ball.getHeight() == racket.getBounds().y &&
        ball.getBounds().x + ball.getWidth() > racket.getBounds().x &&
        racket.getBounds().x + racket.getWidth() > ball.getBounds().x) {
        ball.setYA(-ball.getYA());
        a=0;
        b=1;
        score++;
        scoreLabel.setText(Integer.toString(score));
    }
}

@Override
public void paint(Graphics g) {
    super.paint(g);
    racket2.paint(g);
    racket.paint(g);
    ball.paint(g);
}

    private void checkCollisionBallRacket2() {
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

private class KeyHandler implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
         if(a==0 && b==1)
        {
        racket2.pressed(e);    
        }
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        racket2.released(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {          
    }   
}

private class TimerHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //code of networking
            
            pc=new DatagramSocket(4243);
        } catch (SocketException ex) {
            Logger.getLogger(PongPanelc.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println(pc.getPort());
            System.out.println(pc.getLocalPort());
        
        
        rdata=new byte[1024];
        sdata=new byte[1024];
            //read from datapacket
           
           DatagramPacket receivePacket = new DatagramPacket(rdata, rdata.length); 
               
        try {
            pc.receive(receivePacket);
        } catch (IOException ex) {
            Logger.getLogger(PongPanelc.class.getName()).log(Level.SEVERE, null, ex);
        }
             
           String modifiedSentence = new String(receivePacket.getData());  
           System.out.println("FROM SERVER:" + modifiedSentence); 
           
           //modify the acguired string
            //ee=map(int, inputstring.split(','))
            ee = modifiedSentence.split(",");
            //System.out.println(ee[0]);
            racket.setX(Integer.parseInt(ee[0]));
            a=Integer.parseInt(ee[12]);
            b=Integer.parseInt(ee[13].trim());
            //System.out.println(ee.length);
            //System.out.println(ee[4]+ee[5]+ee[6]+ee[7]);
            //System.out.println(ee[7].trim());
            int a,b,c,d=0;
            a=Integer.parseInt(ee[4]);
            b=Integer.parseInt(ee[5]);
            //fresh attempt
           
            if(ee[6].charAt(0)=='-')
            {    
                c=(-1)*Integer.parseInt(ee[6].substring(1));
            }
            else
            {
                c=Integer.parseInt(ee[6]);
            }
            
             if(ee[7].trim().charAt(0)=='-')
            {    
                System.out.println(ee[7].trim().substring(1));
                
                d=(-1)*Integer.parseInt(ee[7].trim().substring(1));
            }
            else
            {
                d=Integer.parseInt(ee[7].trim());
            }
             
            
             
            
           /* if(ee[6].equals("-1"))
            {
                c=-1;
            }
            else
            {
                c=1;
            }
             if(ee[7].equals("-1"))
            {
                d=-1;
            }
            else
            {
                d=1;
            }*/
            /*try
            {
                  c=Integer.parseInt(ee[6]);
             //     d=Integer.parseInt(ee[7]);
            }
            catch(NumberFormatException ev)
            {
               c=-1;    
            }
            try
            {
                  d=Integer.parseInt(ee[7]);
             //     d=Integer.parseInt(ee[7]);
            }
            catch(NumberFormatException ev)
            {
               d=-1;    
            }
           /* if(ee[6].charAt(0)=='-') 
            {
              c=Integer.parseInt(Character.toString(ee[6].charAt(1)));  
            }
            else
            {
                c=Integer.parseInt(ee[6]);
            }
            if(ee[7].charAt(0)=='-') 
            {
              d=Integer.parseInt(Character.toString(ee[7].charAt(1)));  
            }
            else
            {
                d=Integer.parseInt(ee[7]);
            }*/
            
           // c=Integer.parseInt(ee[6]);
           // d=Integer.parseInt(ee[7]);
          ball.setX(a);
            ball.setY(b);
           ball.setXA(c);
            ball.setYA(d);
            //IPAddress = receivePacket.getAddress();
            //add.add(IPAddress);
            //port = receivePacket.getPort(); 
            //por.add(port);
           
           pc.close(); 
        
       // System.out.println("timer started");
       update();
    }
    
}
    
}

