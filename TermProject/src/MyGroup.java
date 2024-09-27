import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MyGroup extends MyFigure {
   private ArrayList<MyFigure> figureList = new ArrayList<>(); // 도형 리스트
   
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
      // 그룹화할 때 보이는 그룹의 사이즈를 재조정한다.
      setSize();
      // 그룹 도형을 사각형으로 정하여 그린다.
      Graphics2D g2 = (Graphics2D)g;
      g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, 0));
      g.setColor(Color.gray);
      g.drawRect(m_x-5, m_y-5, m_width+10, m_height+10);
      g.setColor(Color.black);
   }
   public void setSize() { // 사이즈를 재조정하는 함수
      // 도형리스트가 arraylist이므로 이전것과 다음것을 비교할 수 없기 때문에
      // 비교를 위해 첫번째 도형의 요소들을 저장한다.
      int first_x = figureList.get(0).getX();
      int first_y = figureList.get(0).getY();
      int first_width = figureList.get(0).getWidth();
      int first_height = figureList.get(0).getHeight();
      // 끝점
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
         // 너비와 높이는 (끝점-시작점)
         m_width = end_x - m_x;
         m_height = end_y - m_y;
      }
   }
   public void changePosition(int x, int y) { // 위치를 변경하는 함수
      // 그룹화 된 이 도형의 위치를 변경한다.
      m_x += x;
      m_y += y;
      // 그룹 안에 들어가있는 도형들의 위치도 변경한다.
      for (MyFigure f : figureList) {
         f.changePosition(x, y);
      }
   }
}