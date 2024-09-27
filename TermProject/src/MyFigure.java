import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MyFigure {
      protected int m_x, m_y, m_width, m_height; // x, y, �ʺ�, ����
      protected int lineStatus = 0, thicknessStatus = 0, fillingColorStatus = 0;
      protected int figureType = 0;
      
      public MyFigure(int x, int y, int w, int h) { // ������
         // �Ķ���Ϳ� �°� �ٿ�带 �����Ѵ�.
         m_x = x;
         m_y = y;
         m_width = w;
         m_height = h;
      }
      public void draw(Graphics g) {
         // �� �� ���¿� �°� ���� �������ش�.
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
         
         // �� �� �β��� �°� �β��� �������ش�.
         Graphics2D g2 = (Graphics2D)g;
         if (thicknessStatus == 0) {
            g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));
         } else if (thicknessStatus == 1) {
            g2.setStroke(new BasicStroke(13, BasicStroke.CAP_ROUND, 0));
         } else if (thicknessStatus == 2) {
               g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, 0));
            }
         
      }
      public boolean contains(int x, int y) { // �����̵� �� �� => ���õ� ��ġ�� �ִ��� �Ǻ��ϴ� �Լ�
         return m_x <= x && x <= m_x + m_width &&
               m_y <= y && y <= m_y + m_height;
      }
      public boolean contains(int x, int y, int w, int h) { // �׷�ȭ�� �� => ���õ� ��ġ�� �ִ��� �Ǻ��ϴ� �Լ�
         return m_x >= x && m_y >= y &&
               m_x+m_width <= w && m_y+m_height <= h;
      }
      public void changePosition(int x, int y) { // ��ġ�� �����ϴ� �Լ�
         m_x += x;
         m_y += y;
      }
      public int getX() { // x ��ȯ �Լ�
         return m_x;
      }
      public int getY() { // y ��ȯ �Լ�
         return m_y;
      }
      public int getWidth() { // �ʺ� ��ȯ �Լ�
         return m_width;
      }
      public int getHeight() { // ���� ��ȯ �Լ�
         return m_height;
      }
      public void setLineStatus(int ls) { // �� ���¸� �����ϴ� �Լ�
         lineStatus = ls;
      }
      public void setThicknessStatus(int ts) { // �� �β��� �����ϴ� �Լ�
         thicknessStatus = ts;
      }
      public void setFigureType(int ft) { // ���� Ÿ���� �����ϴ� �Լ�
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
   
   // ���찳
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
   // �簢�� Ŭ����
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

   // Ÿ�� Ŭ����
   class MyOval extends MyFigure {
      public MyOval(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.drawOval(m_x, m_y, m_width, m_height);
      }
   }

   // ���� Ŭ����
   class MyLine extends MyFigure {
      public MyLine(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.drawLine(m_x, m_y, m_width, m_height);
      }
   }

   // ä�� �簢�� Ŭ����
   class MyFillRectangle extends MyFigure {
      public MyFillRectangle(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.fillRect(m_x, m_y, m_width, m_height);
      }
   }

   // ä�� Ÿ�� Ŭ����
   class MyFillOval extends MyFigure {
      public MyFillOval(int x, int y, int w, int h) {
         super(x, y, w, h);
      }
      @Override public void draw(Graphics g) {
         super.draw(g);
         g.fillOval(m_x, m_y, m_width, m_height);
      }
   }