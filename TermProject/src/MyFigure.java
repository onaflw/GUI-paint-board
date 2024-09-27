import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MyFigure {
      protected int m_x, m_y, m_width, m_height; // x, y, 너비, 높이
      protected int lineStatus = 0, thicknessStatus = 0, fillingColorStatus = 0;
      protected int figureType = 0;
      
      public MyFigure(int x, int y, int w, int h) { // 생성자
         // 파라메터에 맞게 바운드를 저장한다.
         m_x = x;
         m_y = y;
         m_width = w;
         m_height = h;
      }
      public void draw(Graphics g) {
         // 각 선 상태에 맞게 색을 설정해준다.
         if (lineStatus == 0) {
            g.setColor(Color.black);
         } else if (lineStatus == 1) {
            g.setColor(Color.red);
         } else if (lineStatus == 2) {
            g.setColor(Color.green);
         } else if (lineStatus == 3) {
            g.setColor(Color.blue);
         } else if (lineStatus == 4) {
           g.setColor(Color.white);
        } 
         
         // 각 선 두께에 맞게 두께를 설정해준다.
         Graphics2D g2 = (Graphics2D)g;
         if (thicknessStatus == 0) {
            g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));
         } else if (thicknessStatus == 1) {
            g2.setStroke(new BasicStroke(13, BasicStroke.CAP_ROUND, 0));
         } else if (thicknessStatus == 2) {
               g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, 0));
            }
         
      }
      public boolean contains(int x, int y) { // 도형이동 할 때 => 선택된 위치에 있는지 판별하는 함수
         return m_x <= x && x <= m_x + m_width &&
               m_y <= y && y <= m_y + m_height;
      }
      public boolean contains(int x, int y, int w, int h) { // 그룹화할 때 => 선택된 위치에 있는지 판별하는 함수
         return m_x >= x && m_y >= y &&
               m_x+m_width <= w && m_y+m_height <= h;
      }
      public void changePosition(int x, int y) { // 위치를 변경하는 함수
         m_x += x;
         m_y += y;
      }
      public int getX() { // x 반환 함수
         return m_x;
      }
      public int getY() { // y 반환 함수
         return m_y;
      }
      public int getWidth() { // 너비 반환 함수
         return m_width;
      }
      public int getHeight() { // 높이 반환 함수
         return m_height;
      }
      public void setLineStatus(int ls) { // 선 상태를 설정하는 함수
         lineStatus = ls;
      }
      public void setThicknessStatus(int ts) { // 선 두께를 설정하는 함수
         thicknessStatus = ts;
      }
      public void setFigureType(int ft) { // 도형 타입을 설정하는 함수
         figureType = ft;
      }
      public int getFigureType() {
         return figureType;
      }
      public int getLineStatus() {
         return lineStatus;
      }
      public int getThicknessStatus() {
         return thicknessStatus;
      }
   }
   
   // 지우개
   class MyEraser extends MyFigure {
      public MyEraser(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.setColor(Color.white);
         g.drawLine(m_x, m_y, m_width, m_height);
      }
      public MyEraser getFigureClass() {
         return this;
      }
   }
   // 사각형 클래스
   class MyRectangle extends MyFigure {
      public MyRectangle(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.drawRect(m_x, m_y, m_width, m_height);
      }
      public MyRectangle getFigureClass() {
         return this;
      }
   }

   // 타원 클래스
   class MyOval extends MyFigure {
      public MyOval(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.drawOval(m_x, m_y, m_width, m_height);
      }
   }

   // 선분 클래스
   class MyLine extends MyFigure {
      public MyLine(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.drawLine(m_x, m_y, m_width, m_height);
      }
   }

   // 채운 사각형 클래스
   class MyFillRectangle extends MyFigure {
      public MyFillRectangle(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.fillRect(m_x, m_y, m_width, m_height);
      }
   }

   // 채운 타원 클래스
   class MyFillOval extends MyFigure {
      public MyFillOval(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.fillOval(m_x, m_y, m_width, m_height);
      }
   }