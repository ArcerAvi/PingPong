/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import obj.ball;

/**
 *
 * @author USER
 */
public class BallGamer extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form BallGamer
     */
    Thread t;
    int fps=0;
    Container c;
    JFrame jf;
    private ball ball1,ball2;
    private paddle p;
    public boolean runningh;
    public BallGamer() {
           addMouseMotionListener(new MouseMotionListener(){
           public void mousePressed(MouseEvent e)
                {
                    System.out.println(e.getX()+"   "+e.getY());
                    
                }

               @Override
               public void mouseDragged(MouseEvent e) {
                   System.out.println("drgged");
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void mouseMoved(MouseEvent e) {
                   System.out.println("moved");
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               
           
           });
         jf=new JFrame("kjfksld");
        c=jf.getContentPane(); 
        jf.setSize(300, 300);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(300,300));      
         c.add(this);
        jf.pack();
       ball1=new ball(150,150,Color.GRAY,this);
       // ball2=new ball(250,250,Color.GRAY,this);
        p=new paddle(150,280,Color.RED,this);
        jf.setVisible(true);
        start();
        
    }
    public static void main(String...args)
    {
        BallGamer bg=new BallGamer();
       // bg.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void run() {
            
          runningh=true;
        long h =System.currentTimeMillis();
        while(runningh)
        {
         fps++;
         repaint();
         update();
          
         if(System.currentTimeMillis()-h>=1000)
         {   
             //System.out.println(fps);
             h=System.currentTimeMillis();
             fps=0;
         }
              try {
                  Thread.sleep(15);
              } catch (InterruptedException ex) {
                  Logger.getLogger(BallGamer.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
    }
    public void paint(Graphics g)
            
    {
        super.paintComponents(g);
        ball1.paint(g);
        p.paint(g);
    }

    private void start() {
     
        t=new Thread(this);
        t.start();
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     
    private void update() {
            //move the button content accordingly as fps changes
            ball1.move();
          
        
            
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
