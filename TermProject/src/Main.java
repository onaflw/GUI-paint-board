import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

         public static void main(String[] args) {
            MyFrame frame = new MyFrame();
            frame.repaint();
         }
                  
         public static class MyFrame extends JFrame {
         MyPanel myPanel = new MyPanel();
            
            public MyFrame() {
              setJMenuBar(myPanel.menuBar);
               add(myPanel.toolBar, BorderLayout.NORTH);
               add(myPanel, BorderLayout.CENTER);
               
               setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               setSize(1000, 800);
               setVisible(true);
            }
         }
}