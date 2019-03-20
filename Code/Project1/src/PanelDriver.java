import javax.swing.JFrame;

public class PanelDriver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Circles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CirclePanel panel = new CirclePanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
