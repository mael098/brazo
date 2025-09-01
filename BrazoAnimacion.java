import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrazoAnimacion extends JPanel implements ActionListener {
    private static final int L1 = 100; // Longitud del primer eslabón
    private static final int L2 = 80;  // Longitud del segundo eslabón
    private double theta1 = 0; // Ángulo del primer eslabón (radianes)
    private double theta2 = 0; // Ángulo del segundo eslabón (radianes)
    private Timer timer;

    public BrazoAnimacion() {
        timer = new Timer(30, this); // Actualiza cada 30 ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        int x0 = getWidth() / 2;
        int y0 = getHeight() / 2;

        // Primer eslabón
        int x1 = x0 + (int) (L1 * Math.cos(theta1));
        int y1 = y0 + (int) (L1 * Math.sin(theta1));
        g2.setColor(Color.BLUE);
        g2.drawLine(x0, y0, x1, y1);

        // Segundo eslabón (rotación relativa al primero)
        double totalAngle = theta1 + theta2;
        int x2 = x1 + (int) (L2 * Math.cos(totalAngle));
        int y2 = y1 + (int) (L2 * Math.sin(totalAngle));
        g2.setColor(Color.RED);
        g2.drawLine(x1, y1, x2, y2);

        // Dibuja los puntos de las articulaciones
        g2.setColor(Color.BLACK);
        g2.fillOval(x0 - 5, y0 - 5, 10, 10);
        g2.fillOval(x1 - 5, y1 - 5, 10, 10);
        g2.fillOval(x2 - 5, y2 - 5, 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Animación: incrementa los ángulos
        theta1 += 0.02;
        theta2 += 0.03;
        repaint();
    }

      public static void main(String[] args) {
        JFrame frame = new JFrame("Animación Brazo 2 Grados de Libertad");
        BrazoAnimacion panel = new BrazoAnimacion();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setVisible(true);
    }
}
