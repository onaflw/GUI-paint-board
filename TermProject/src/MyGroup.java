import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MyGroup extends MyFigure {
   private ArrayList<MyFigure> figureList = new ArrayList<>(); // ���� ����Ʈ
   
   public MyGroup(int x, int y, int w, int h) {
      super(x, y, w, h);
   }
   public void add(MyFigure f) {
      figureList.add(f);
   }
   public void clear(MyGroup f) {
      figureList.clear();
      this.figureList= new ArrayList<>();
   }
   
   public ArrayList<MyFigure> get() {
      return figureList;
   }
   @Override public void draw(Graphics g) {
      // �׷�ȭ�� �� ���̴� �׷��� ����� �������Ѵ�.
      setSize();
      // �׷� ������ �簢������ ���Ͽ� �׸���.
      Graphics2D g2 = (Graphics2D)g;
      g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, 0));
      g.setColor(Color.gray);
      g.drawRect(m_x-5, m_y-5, m_width+10, m_height+10);
      g.setColor(Color.black);
   }
   public void setSize() { // ����� �������ϴ� �Լ�
      // ��������Ʈ�� arraylist�̹Ƿ� �����Ͱ� �������� ���� �� ���� ������
      // �񱳸� ���� ù��° ������ ��ҵ��� �����Ѵ�.
      int first_x = figureList.get(0).getX();
      int first_y = figureList.get(0).getY();
      int first_width = figureList.get(0).getWidth();
      int first_height = figureList.get(0).getHeight();
      // ����
      int end_x = 0, end_y = 0;
      
      for (MyFigure f : figureList) {
         if (first_x >= f.getX()) {
            m_x = f.getX();
         } else {
            m_x = first_x;
         }
         if (first_y >= f.getY()) {
            m_y = f.getY();
         } else {
            m_y = first_y;
         }
         if (first_x+first_width <= f.getX()+f.getWidth()) {
            end_x = f.getX() + f.getWidth();
         }
         if (first_y+first_height <= f.getY()+f.getHeight()) {
            end_y = f.getY() + f.getHeight();
         }
         // �ʺ�� ���̴� (����-������)
         m_width = end_x - m_x;
         m_height = end_y - m_y;
      }
   }
   public void changePosition(int x, int y) { // ��ġ�� �����ϴ� �Լ�
      // �׷�ȭ �� �� ������ ��ġ�� �����Ѵ�.
      m_x += x;
      m_y += y;
      // �׷� �ȿ� ���ִ� �������� ��ġ�� �����Ѵ�.
      for (MyFigure f : figureList) {
         f.changePosition(x, y);
      }
   }
}