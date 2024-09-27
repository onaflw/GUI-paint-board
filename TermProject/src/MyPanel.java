import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class MyPanel extends JPanel implements MouseListener {
  
   JMenuBar menuBar = new JMenuBar();
   JToolBar toolBar = new JToolBar();
   
   JButton btnRect, btnOval, btnLine, btnGroup, btnMove, btnCopy, btnUngroup, btnErase, btnFirst; // �簢��, Ÿ��, ����, �׷�ȭ, �����̵�, ����, �׷�Ǯ�� + ���찳, �ʱ�ȭ ��ư
   JLabel lineColor, thickness; // ��, �� �β� ��
   ButtonGroup groupLine, groupThickness; // ��, �β� ��ư �׷�
   JRadioButton blackLine, redLine, greenLine, blueLine, tempLine1; // ����, ����, �ʷ�, �Ķ� ���������� ���� temp ������ư
   JRadioButton thinLine, thickLine, thickLine2, tempLine2; // ������, �β��, ���������� ���� temp ������ư
   JCheckBox fillCheckBox; // ä��� üũ�ڽ�
   
   ArrayList<MyFigure> figureList = new ArrayList<>(); // ���� ����Ʈ
   MyFigure myFigure;  // ���� ����
   MyFigure tempFigure;
   MyActionListener btnListener = new MyActionListener(); // ��ư ������
   MouseEvent start = null, end = null;
   String buttonType = null; // ��ư Ÿ��
   MyGroup figureGroup; // �׷� ����
   ArrayList<MyGroup> groupList = new ArrayList<>(); // �׷� ����Ʈ
   MyFigure[] figuretoDelete = new MyFigure[100]; // ������ ���� �迭
   int count = 0; // ������ ���� ����
   
   int lineStatus = 0, thicknessStatus = 0; // �� ����, �β� ����
   
   public MyPanel() {
      // �⺻ ����
      super(true);
      setBackground(Color.white);
      setLayout(null);
      addMouseListener(this);
      
      
      
      // menubar ����
      JMenu menu = new JMenu("File");
      menuBar.add(menu);
      JMenu edit = new JMenu("Edit");
      menuBar.add(edit);
      
      // menubar �ȿ� menuitem ����
      JMenuItem save = new JMenuItem("����"); menu.add(save);
      JMenuItem open = new JMenuItem("����"); menu.add(open);

      // ��ư ����, ���ٿ� ��ư �߰�
    
      btnRect = new JButton(new ImageIcon("./�簢��.jpg")); toolBar.add(btnRect);
      btnOval = new JButton(new ImageIcon("./Ÿ��.jpg")); toolBar.add(btnOval);
      btnLine = new JButton(new ImageIcon("./����.jpg")); toolBar.add(btnLine);
      btnErase = new JButton(new ImageIcon("./���찳.png")); toolBar.add(btnErase);
      btnFirst = new JButton("�ʱ�ȭ"); toolBar.add(btnFirst);
      fillCheckBox = new JCheckBox("���� ä���"); toolBar.add(fillCheckBox);
      btnGroup = new JButton("�׷�ȭ"); toolBar.add(btnGroup);
      btnUngroup = new JButton("�׷�Ǯ��"); toolBar.add(btnUngroup);
      btnMove = new JButton("�����̵�"); toolBar.add(btnMove);
      btnCopy = new JButton("����"); toolBar.add(btnCopy);

      // �� ��ư�� ��ư������ �߰�
      btnRect.addActionListener(btnListener);
      btnOval.addActionListener(btnListener);
      btnLine.addActionListener(btnListener);
      fillCheckBox.addActionListener(btnListener);
      btnGroup.addActionListener(btnListener);
      btnUngroup.addActionListener(btnListener);
      btnMove.addActionListener(btnListener);
      btnCopy.addActionListener(btnListener);
      btnErase.addActionListener(btnListener);
      btnFirst.addActionListener(btnListener);
      // �� ����
      lineColor = new JLabel("     ��  "); toolBar.add(lineColor);
      // �󺧿� �˸´� ������ư ����, ���ٿ� ������ư �߰�
      blackLine = new JRadioButton("����"); toolBar.add(blackLine);
      redLine = new JRadioButton("����"); toolBar.add(redLine);
      greenLine = new JRadioButton("�ʷ�"); toolBar.add(greenLine);
      blueLine = new JRadioButton("�Ķ�"); toolBar.add(blueLine);
      tempLine1 = new JRadioButton("temp");
      // �󺧿� �´� ��ư�׷� �����ϰ�, ��ư�׷쿡 ��ư �߰�
      groupLine = new ButtonGroup();
      groupLine.add(blackLine); groupLine.add(redLine); groupLine.add(blackLine); groupLine.add(greenLine); groupLine.add(blueLine); groupLine.add(tempLine1);
      // ��ư������ �߰�
      blackLine.addActionListener(btnListener);
      redLine.addActionListener(btnListener);
      greenLine.addActionListener(btnListener);
      blueLine.addActionListener(btnListener);
     
      
      // ���� ����
      thickness = new JLabel("     �β�  "); toolBar.add(thickness);
      thinLine = new JRadioButton("5"); toolBar.add(thinLine);
      thickLine = new JRadioButton("10"); toolBar.add(thickLine);
      thickLine2 = new JRadioButton("15"); toolBar.add(thickLine2);
      tempLine2 = new JRadioButton("temp");
      groupThickness = new ButtonGroup();
      groupThickness.add(thinLine); groupThickness.add(thickLine); groupThickness.add(thickLine2); groupThickness.add(tempLine2);
      thinLine.addActionListener(btnListener);
      thickLine.addActionListener(btnListener);
      thickLine2.addActionListener(btnListener);
      
   }
   
   class MyActionListener implements ActionListener { // ActionListener

      @Override
      public void actionPerformed(ActionEvent e) {
         
         // �� ��ư�� Ÿ�Կ� �°� ��ưŸ���� �����Ѵ�.
         
         // �簢�� ��ư
         if (e.getSource() == btnRect) {
            buttonType = "�簢��";
            System.out.println("�簢�� Ŭ����");
         // Ÿ�� ��ư
         } else if (e.getSource() == btnOval) {
            buttonType = "Ÿ��";
            System.out.println("Ÿ�� Ŭ����");
         // ���� ��ư
         } else if (e.getSource() == btnLine) {
            buttonType = "����";
            System.out.println("���� Ŭ����");
         // �׷�ȭ ��ư
         } else if (e.getSource() == btnGroup) {
            buttonType = "�׷�ȭ";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("�׷�ȭ Ŭ����");
         // �����̵� ��ư
         } else if (e.getSource() == btnMove) {
            buttonType = "�����̵�";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("�����̵� Ŭ����");
         // ���� ��ư
         } else if (e.getSource() == btnCopy) {
            buttonType = "����";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("���� Ŭ����");
         // �׷�Ǯ�� ��ư
         } else if (e.getSource() == btnUngroup) {
            buttonType = "�׷�Ǯ��";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("�׷�Ǯ�� Ŭ����");
         } else if (e.getSource() == btnErase) {
             buttonType = "���찳";
             tempLine1.setSelected(true); tempLine2.setSelected(true);
             System.out.println("���찳 Ŭ����");
          }else if (e.getSource() == btnFirst) {
              buttonType = "�ʱ�ȭ";
              System.out.println("�ʱ�ȭ Ŭ����");
           }
         
         // �� ������ư
         
         // �� ��ư�� �°� �� ���¸� �������ش�.
         // ���� 0  ���� 1  �ʷ� 2  �Ķ� 3
         
         // ����
         if (e.getSource() == blackLine) {
            lineStatus = 0;
            System.out.println("���� �� Ŭ����");
         // ����
         } else if (e.getSource() == redLine) {
            lineStatus = 1;
            System.out.println("���� �� Ŭ����");
         // �ʷ�
         } else if (e.getSource() == greenLine) {
            lineStatus = 2;
            System.out.println("�ʷ� �� Ŭ����");
         // �Ķ�
         } else if (e.getSource() == blueLine) {
            lineStatus = 3;
            System.out.println("�Ķ� �� Ŭ����");
         }
         
         // �� �β� ������ư
         
         // �� ��ư�� �°� �� �β� ���¸� �������ش�.
         
         // 5 ������
         if (e.getSource() == thinLine) {
            thicknessStatus = 0;
            System.out.println("5 ���� �� Ŭ����");
         // 10 ������
         } else if (e.getSource() == thickLine) {
            thicknessStatus = 1;
            System.out.println("10 ���� �� Ŭ����");
         // 15 ������
         } else if (e.getSource() == thickLine2) {
             thicknessStatus = 2;
             System.out.println("15 ���� �� Ŭ����");
          }
      }
      
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      // TODO Auto-generated method stub
   
   }

   @Override
   public void mousePressed(MouseEvent e) {
      // �������� �����Ѵ�.
      start = e;
      // ������ ������ �� ������ tempFigure�� �����Ѵ�.
      tempFigure = findFigure(e.getX(), e.getY());
      System.out.println("���� ���õ�");
   }
   
   
   @Override
   public void mouseReleased(MouseEvent e) {
      // ������ ���� �����Ѵ�.
      end = e;
      // ��ư Ÿ���� �簢���� ��
      if (buttonType.equals("�簢��")) {
         if (fillCheckBox.isSelected()) {
            myFigure = new MyFillRectangle(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
            myFigure.setFigureType(3);
         } else {
            myFigure = new MyRectangle(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
            myFigure.setFigureType(0);
         }
         myFigure.setLineStatus(lineStatus);
         myFigure.setThicknessStatus(thicknessStatus);
         add(myFigure);
         repaint();
      // ��ư Ÿ���� Ÿ���� ��
      } else if (buttonType.equals("Ÿ��")) {
         if (fillCheckBox.isSelected()) {
            myFigure = new MyFillOval(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
            myFigure.setFigureType(4);
         } else {
            myFigure = new MyOval(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
            myFigure.setFigureType(1);
         }
         myFigure.setLineStatus(lineStatus);
         myFigure.setThicknessStatus(thicknessStatus);
         add(myFigure);
         repaint();
      // ��ư Ÿ���� ������ ��
      } else if (buttonType.equals("����")) {
         myFigure = new MyLine(start.getX(), start.getY(), end.getX(), end.getY());
         myFigure.setFigureType(2);
         myFigure.setLineStatus(lineStatus);
         myFigure.setThicknessStatus(thicknessStatus);
         add(myFigure);
         repaint();
         
         
      // ��ư Ÿ���� ������ �ƴϸ�
         
      // ��ư Ÿ���� �׷�ȭ�� ��
      }  else if (buttonType.equals("�׷�ȭ")) {
         // ���ο� ���� �׷��� �����.
         figureGroup = new MyGroup(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
         figureGroup.setFigureType(5);
         // �׷� �ȿ� �ִ� �������� �׷츮��Ʈ�� �׷� Ŭ������ ���� ����Ʈ�� �߰��Ѵ�.
         addFiguretoGroup(start.getX(), start.getY(), end.getX(), end.getY());
         // ��������Ʈ�� �׷��� �߰��Ѵ�.
         add(figureGroup);
         // �׷� �ȿ� �ִ� �������� ��������Ʈ���� �����Ѵ�.
         for (int i=0; i<count; i++) {
             figureList.remove(figuretoDelete[i]);
          }
         repaint();
      // ��ư Ÿ���� �����̵��� ��
      } else if (buttonType.equals("�����̵�")) {
         // ������ ������ tempFigure�� ��ġ�� �����Ѵ�.
         tempFigure.changePosition(end.getX() - start.getX(), end.getY() - start.getY());
         // �׷�ȭ�ϸ鼭 �߰��� �׷츮��Ʈ�� �׷���� tempFigure�̸�
         for (MyGroup g : groupList) {
            if (tempFigure == g) {
               // �� �׷�鵵 ��ġ�� �����Ѵ�.
               g.changePosition(end.getX() - start.getX(), end.getY() - start.getY());
            }
         }
         repaint();
      // ������ ��
      } else if (buttonType.equals("����")) {
         // �� ���� Ÿ�Կ� �°� ���ο� ��ġ�� ���ο� ������ �����Ѵ�.
         if (tempFigure.getFigureType() == 0) {
            myFigure = new MyRectangle(tempFigure.getX()+end.getX()-start.getX(), tempFigure.getY()+end.getY()-start.getY(), tempFigure.getWidth(), tempFigure.getHeight());
         } else if (tempFigure.getFigureType() == 1) {
            myFigure = new MyOval(tempFigure.getX()+end.getX()-start.getX(), tempFigure.getY()+end.getY()-start.getY(), tempFigure.getWidth(), tempFigure.getHeight());
         } else if (tempFigure.getFigureType() == 2) {
            myFigure = new MyLine(tempFigure.getX()+end.getX()-start.getX(), tempFigure.getY()+end.getY()-start.getY(), tempFigure.getWidth(), tempFigure.getHeight());
         } else if (tempFigure.getFigureType() == 3) {
            myFigure = new MyFillRectangle(tempFigure.getX()+end.getX()-start.getX(), tempFigure.getY()+end.getY()-start.getY(), tempFigure.getWidth(), tempFigure.getHeight());
         } else if (tempFigure.getFigureType() == 4) {
            myFigure = new MyFillOval(tempFigure.getX()+end.getX()-start.getX(), tempFigure.getY()+end.getY()-start.getY(), tempFigure.getWidth(), tempFigure.getHeight());
         } 
         // ������ ����(tempFigure)�� �� �� ���¿� �� �β� ���¸� �������ְ�,
         // ���� ����Ʈ�� �߰��Ѵ�.  
             myFigure.setLineStatus(tempFigure.getLineStatus());
            myFigure.setThicknessStatus(tempFigure.getThicknessStatus());
            add(myFigure);
            repaint();
          }  else if (buttonType.equals("�׷�Ǯ��")) {
            //������ ���� �׷��̸�
            if (tempFigure instanceof MyGroup) {
              MyGroup group = (MyGroup)tempFigure;
              ArrayList<MyFigure> addfigure = group.get();
                // ���� ����Ʈ�� �߰��Ѵ�.
                for (MyFigure figure : addfigure) 
                    figureList.add(figure);      
                // �׷��� ����Ʈ���� �����Ѵ�.
                figureList.remove(group);
            }
            //�ٽ� �׸���
           repaint(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
          }else if (buttonType.equals("�ʱ�ȭ")) {
         //List�� �ִ� ������� �׷���� �����Ѵ�. 
         figureList.clear();
         repaint();
         figureGroup.clear(figureGroup);
          repaint();
         
          //�Ͼ� ���簢���� ũ�⿡ �°� �����Ѵ�.
        myFigure = new MyFillRectangle(0, 0, 1000, 800);
        myFigure.setFigureType(3);
          myFigure.setLineStatus(4);
          add(myFigure);
         repaint();
          }else if (buttonType.equals("���찳")) {
          // ������ �ִ� ������� ������ �Ͼ�� ���� �̿��Ͽ� �����.
         myFigure = new MyEraser(start.getX(), start.getY(), end.getX(), end.getY());
          myFigure.setFigureType(2);
          myFigure.setThicknessStatus(thicknessStatus);
          add(myFigure);
          repaint();
      }   
   }

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }
   
   @Override 
   public void paint(Graphics g) {
      super.paint(g);
      // �׷�ȭ�ϰ� �׷� �ȿ� �ִ� �������� ��������Ʈ���� ����������, ������ ��� �����־���ϹǷ�
      // ���� �������� �� �׷��ش�.
      for (int i=0; i<count; i++) {
         figuretoDelete[i].draw(g);
      }
      // ���� ����Ʈ�� �ִ� �����鵵 �� �׷��ش�.
      for (MyFigure f : figureList) {
         f.draw(g);
      }
   }
   
   public void add(MyFigure f) { // ��������Ʈ�� �������� �߰��ϴ� �Լ�
      figureList.add(f);
   }
   
   public MyFigure findFigure(int x, int y) { // ���õ� ������ ã�� �Լ�
      for (MyFigure f : figureList) {
         // ������ ���� ��ġ�� ������ �� ������ ��ȯ�Ѵ�.
         if (f.contains(x, y)) {
            return f;
         }
      }
      // �� ��ġ�� ������ ������ null ��ȯ�Ѵ�.
      return null;
   }
   
   public void addFiguretoGroup(int x, int y, int w, int h) { // �׷�ȭ�� ��  �׷�Ŭ������ ��������Ʈ�� �׷쿡 ���� ������ �߰��ϴ� �Լ�
      for (MyFigure f : figureList) {
         // ������ �巡�� �� ���� �ȿ� ���ԵǾ� �ִٸ�
         if (f.contains(x, y, w, h)) {
            // �׷츮��Ʈ�� ������ �߰��Ѵ�.
            figureGroup.add(f);
            // �� ������ ��������Ʈ���� ������ �����̹Ƿ� ������ ���� �迭�� �߰��Ѵ�.
            figuretoDelete[count++] = f;
            System.out.println("���� �߰���");
         }
      }
   }
}