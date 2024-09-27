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
   
   JButton btnRect, btnOval, btnLine, btnGroup, btnMove, btnCopy, btnUngroup, btnErase, btnFirst; // 사각형, 타원, 선분, 그룹화, 도형이동, 복사, 그룹풀기 + 지우개, 초기화 버튼
   JLabel lineColor, thickness; // 색, 선 두께 라벨
   ButtonGroup groupLine, groupThickness; // 색, 두께 버튼 그룹
   JRadioButton blackLine, redLine, greenLine, blueLine, tempLine1; // 검정, 빨강, 초록, 파랑 선택해제를 위한 temp 라디오버튼
   JRadioButton thinLine, thickLine, thickLine2, tempLine2; // 얇은선, 두꺼운선, 선택해제를 위한 temp 라디오버튼
   JCheckBox fillCheckBox; // 채우기 체크박스
   
   ArrayList<MyFigure> figureList = new ArrayList<>(); // 도형 리스트
   MyFigure myFigure;  // 도형 변수
   MyFigure tempFigure;
   MyActionListener btnListener = new MyActionListener(); // 버튼 리스너
   MouseEvent start = null, end = null;
   String buttonType = null; // 버튼 타입
   MyGroup figureGroup; // 그룹 변수
   ArrayList<MyGroup> groupList = new ArrayList<>(); // 그룹 리스트
   MyFigure[] figuretoDelete = new MyFigure[100]; // 삭제할 도형 배열
   int count = 0; // 삭제할 도형 개수
   
   int lineStatus = 0, thicknessStatus = 0; // 색 상태, 두께 상태
   
   public MyPanel() {
      // 기본 설정
      super(true);
      setBackground(Color.white);
      setLayout(null);
      addMouseListener(this);
      
      
      
      // menubar 설정
      JMenu menu = new JMenu("File");
      menuBar.add(menu);
      JMenu edit = new JMenu("Edit");
      menuBar.add(edit);
      
      // menubar 안에 menuitem 설정
      JMenuItem save = new JMenuItem("저장"); menu.add(save);
      JMenuItem open = new JMenuItem("열기"); menu.add(open);

      // 버튼 설정, 툴바에 버튼 추가
    
      btnRect = new JButton(new ImageIcon("./사각형.jpg")); toolBar.add(btnRect);
      btnOval = new JButton(new ImageIcon("./타원.jpg")); toolBar.add(btnOval);
      btnLine = new JButton(new ImageIcon("./선분.jpg")); toolBar.add(btnLine);
      btnErase = new JButton(new ImageIcon("./지우개.png")); toolBar.add(btnErase);
      btnFirst = new JButton("초기화"); toolBar.add(btnFirst);
      fillCheckBox = new JCheckBox("도형 채우기"); toolBar.add(fillCheckBox);
      btnGroup = new JButton("그룹화"); toolBar.add(btnGroup);
      btnUngroup = new JButton("그룹풀기"); toolBar.add(btnUngroup);
      btnMove = new JButton("도형이동"); toolBar.add(btnMove);
      btnCopy = new JButton("복사"); toolBar.add(btnCopy);

      // 각 버튼에 버튼리스너 추가
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
      // 라벨 설정
      lineColor = new JLabel("     색  "); toolBar.add(lineColor);
      // 라벨에 알맞는 라디오버튼 생성, 툴바에 라디오버튼 추가
      blackLine = new JRadioButton("검정"); toolBar.add(blackLine);
      redLine = new JRadioButton("빨강"); toolBar.add(redLine);
      greenLine = new JRadioButton("초록"); toolBar.add(greenLine);
      blueLine = new JRadioButton("파랑"); toolBar.add(blueLine);
      tempLine1 = new JRadioButton("temp");
      // 라벨에 맞는 버튼그룹 생성하고, 버튼그룹에 버튼 추가
      groupLine = new ButtonGroup();
      groupLine.add(blackLine); groupLine.add(redLine); groupLine.add(blackLine); groupLine.add(greenLine); groupLine.add(blueLine); groupLine.add(tempLine1);
      // 버튼리스너 추가
      blackLine.addActionListener(btnListener);
      redLine.addActionListener(btnListener);
      greenLine.addActionListener(btnListener);
      blueLine.addActionListener(btnListener);
     
      
      // 위와 동일
      thickness = new JLabel("     두께  "); toolBar.add(thickness);
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
         
         // 각 버튼의 타입에 맞게 버튼타입을 저장한다.
         
         // 사각형 버튼
         if (e.getSource() == btnRect) {
            buttonType = "사각형";
            System.out.println("사각형 클릭됨");
         // 타원 버튼
         } else if (e.getSource() == btnOval) {
            buttonType = "타원";
            System.out.println("타원 클릭됨");
         // 선분 버튼
         } else if (e.getSource() == btnLine) {
            buttonType = "선분";
            System.out.println("선분 클릭됨");
         // 그룹화 버튼
         } else if (e.getSource() == btnGroup) {
            buttonType = "그룹화";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("그룹화 클릭됨");
         // 도형이동 버튼
         } else if (e.getSource() == btnMove) {
            buttonType = "도형이동";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("도형이동 클릭됨");
         // 복사 버튼
         } else if (e.getSource() == btnCopy) {
            buttonType = "복사";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("복사 클릭됨");
         // 그룹풀기 버튼
         } else if (e.getSource() == btnUngroup) {
            buttonType = "그룹풀기";
            tempLine1.setSelected(true); tempLine2.setSelected(true);
            System.out.println("그룹풀기 클릭됨");
         } else if (e.getSource() == btnErase) {
             buttonType = "지우개";
             tempLine1.setSelected(true); tempLine2.setSelected(true);
             System.out.println("지우개 클릭됨");
          }else if (e.getSource() == btnFirst) {
              buttonType = "초기화";
              System.out.println("초기화 클릭됨");
           }
         
         // 색 라디오버튼
         
         // 각 버튼에 맞게 선 상태를 지정해준다.
         // 검정 0  빨강 1  초록 2  파랑 3
         
         // 검정
         if (e.getSource() == blackLine) {
            lineStatus = 0;
            System.out.println("검정 선 클릭됨");
         // 빨강
         } else if (e.getSource() == redLine) {
            lineStatus = 1;
            System.out.println("빨간 선 클릭됨");
         // 초록
         } else if (e.getSource() == greenLine) {
            lineStatus = 2;
            System.out.println("초록 선 클릭됨");
         // 파랑
         } else if (e.getSource() == blueLine) {
            lineStatus = 3;
            System.out.println("파랑 선 클릭됨");
         }
         
         // 선 두께 라디오버튼
         
         // 각 버튼에 맞게 선 두께 상태를 지정해준다.
         
         // 5 얇은선
         if (e.getSource() == thinLine) {
            thicknessStatus = 0;
            System.out.println("5 얇은 선 클릭됨");
         // 10 굵은선
         } else if (e.getSource() == thickLine) {
            thicknessStatus = 1;
            System.out.println("10 굵은 선 클릭됨");
         // 15 굵은선
         } else if (e.getSource() == thickLine2) {
             thicknessStatus = 2;
             System.out.println("15 굵은 선 클릭됨");
          }
      }
      
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      // TODO Auto-generated method stub
   
   }

   @Override
   public void mousePressed(MouseEvent e) {
      // 시작점을 저장한다.
      start = e;
      // 도형을 누르면 그 도형을 tempFigure에 저장한다.
      tempFigure = findFigure(e.getX(), e.getY());
      System.out.println("도형 선택됨");
   }
   
   
   @Override
   public void mouseReleased(MouseEvent e) {
      // 끝나는 점을 저장한다.
      end = e;
      // 버튼 타입이 사각형일 때
      if (buttonType.equals("사각형")) {
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
      // 버튼 타입이 타원일 때
      } else if (buttonType.equals("타원")) {
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
      // 버튼 타입이 선분일 때
      } else if (buttonType.equals("선분")) {
         myFigure = new MyLine(start.getX(), start.getY(), end.getX(), end.getY());
         myFigure.setFigureType(2);
         myFigure.setLineStatus(lineStatus);
         myFigure.setThicknessStatus(thicknessStatus);
         add(myFigure);
         repaint();
         
         
      // 버튼 타입이 도형이 아니면
         
      // 버튼 타입이 그룹화일 때
      }  else if (buttonType.equals("그룹화")) {
         // 새로운 도형 그룹을 만든다.
         figureGroup = new MyGroup(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
         figureGroup.setFigureType(5);
         // 그룹 안에 있는 도형들을 그룹리스트와 그룹 클래스의 도형 리스트에 추가한다.
         addFiguretoGroup(start.getX(), start.getY(), end.getX(), end.getY());
         // 도형리스트에 그룹을 추가한다.
         add(figureGroup);
         // 그룹 안에 있는 도형들은 도형리스트에서 삭제한다.
         for (int i=0; i<count; i++) {
             figureList.remove(figuretoDelete[i]);
          }
         repaint();
      // 버튼 타입이 도형이동일 때
      } else if (buttonType.equals("도형이동")) {
         // 위에서 정해준 tempFigure의 위치를 변경한다.
         tempFigure.changePosition(end.getX() - start.getX(), end.getY() - start.getY());
         // 그룹화하면서 추가한 그룹리스트의 그룹들이 tempFigure이면
         for (MyGroup g : groupList) {
            if (tempFigure == g) {
               // 각 그룹들도 위치를 변경한다.
               g.changePosition(end.getX() - start.getX(), end.getY() - start.getY());
            }
         }
         repaint();
      // 복사일 때
      } else if (buttonType.equals("복사")) {
         // 각 도형 타입에 맞게 새로운 위치에 새로운 도형을 생성한다.
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
         // 선택한 도형(tempFigure)의 선 색 상태와 선 두께 상태를 설정해주고,
         // 도형 리스트에 추가한다.  
             myFigure.setLineStatus(tempFigure.getLineStatus());
            myFigure.setThicknessStatus(tempFigure.getThicknessStatus());
            add(myFigure);
            repaint();
          }  else if (buttonType.equals("그룹풀기")) {
            //선택한 것이 그룹이면
            if (tempFigure instanceof MyGroup) {
              MyGroup group = (MyGroup)tempFigure;
              ArrayList<MyFigure> addfigure = group.get();
                // 도형 리스트에 추가한다.
                for (MyFigure figure : addfigure) 
                    figureList.add(figure);      
                // 그룹을 리스트에서 제거한다.
                figureList.remove(group);
            }
            //다시 그리기
           repaint(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
          }else if (buttonType.equals("초기화")) {
         //List에 있는 도형들과 그룹들을 삭제한다. 
         figureList.clear();
         repaint();
         figureGroup.clear(figureGroup);
          repaint();
         
          //하얀 직사각형을 크기에 맞게 생성한다.
        myFigure = new MyFillRectangle(0, 0, 1000, 800);
        myFigure.setFigureType(3);
          myFigure.setLineStatus(4);
          add(myFigure);
         repaint();
          }else if (buttonType.equals("지우개")) {
          // 색깔이 있는 도형들과 선분을 하얀색 선을 이용하여 지운다.
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
      // 그룹화하고 그룹 안에 있는 도형들은 도형리스트에서 삭제했지만, 도형은 계속 남아있어야하므로
      // 지운 도형들을 다 그려준다.
      for (int i=0; i<count; i++) {
         figuretoDelete[i].draw(g);
      }
      // 도형 리스트에 있는 도형들도 다 그려준다.
      for (MyFigure f : figureList) {
         f.draw(g);
      }
   }
   
   public void add(MyFigure f) { // 도형리스트에 도형들을 추가하는 함수
      figureList.add(f);
   }
   
   public MyFigure findFigure(int x, int y) { // 선택된 도형을 찾는 함수
      for (MyFigure f : figureList) {
         // 도형이 누른 위치에 있으면 그 도형을 반환한다.
         if (f.contains(x, y)) {
            return f;
         }
      }
      // 그 위치에 도형이 없으면 null 반환한다.
      return null;
   }
   
   public void addFiguretoGroup(int x, int y, int w, int h) { // 그룹화할 때  그룹클래스의 도형리스트에 그룹에 속한 도형을 추가하는 함수
      for (MyFigure f : figureList) {
         // 도형이 드래그 한 도형 안에 포함되어 있다면
         if (f.contains(x, y, w, h)) {
            // 그룹리스트에 도형을 추가한다.
            figureGroup.add(f);
            // 이 도형은 도형리스트에서 삭제될 도형이므로 삭제될 도형 배열에 추가한다.
            figuretoDelete[count++] = f;
            System.out.println("도형 추가됨");
         }
      }
   }
}