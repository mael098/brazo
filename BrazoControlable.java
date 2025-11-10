import javax.swing.*;
import java.awt.*;

public class BrazoControlable extends JPanel {
    // Longitudes de los segmentos
    private static final int L_HOMBRO = 120;
    private static final int L_CODO = 100;
    private static final int L_MUNECA = 60;
    private static final int L_MANO = 40;
    private static final int L_DEDO = 20;
    
    // Ángulos de las articulaciones (en radianes)
    private double anguloHombro = 0;
    private double anguloCodo = 0;
    private double anguloMuneca = 0;
    private double anguloMano = 0;
    private double anguloDedo1 = 0;
    private double anguloDedo2 = 0;
    private double anguloDedo3 = 0;
    private double anguloPulgar = 0;
    
    // Incremento para el movimiento
    private static final double INCREMENTO = 0.1;
    
    public BrazoControlable() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 400));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(6));
        
        // Punto base del brazo
        int x0 = 100;
        int y0 = getHeight() - 50;
        
        // Calcular posiciones usando matrices de rotación
        
        // Hombro (primer segmento)
        int x1 = x0 + (int)(L_HOMBRO * Math.cos(anguloHombro));
        int y1 = y0 - (int)(L_HOMBRO * Math.sin(anguloHombro));
        
        // Codo (segundo segmento - rotación acumulada)
        double anguloAcumulado1 = anguloHombro + anguloCodo;
        int x2 = x1 + (int)(L_CODO * Math.cos(anguloAcumulado1));
        int y2 = y1 - (int)(L_CODO * Math.sin(anguloAcumulado1));
        
        // Muñeca (tercer segmento)
        double anguloAcumulado2 = anguloAcumulado1 + anguloMuneca;
        int x3 = x2 + (int)(L_MUNECA * Math.cos(anguloAcumulado2));
        int y3 = y2 - (int)(L_MUNECA * Math.sin(anguloAcumulado2));
        
        // Mano
        double anguloAcumulado3 = anguloAcumulado2 + anguloMano;
        int x4 = x3 + (int)(L_MANO * Math.cos(anguloAcumulado3));
        int y4 = y3 - (int)(L_MANO * Math.sin(anguloAcumulado3));
        
        // Dibujar los segmentos del brazo
        g2.setColor(Color.BLUE);
        g2.drawLine(x0, y0, x1, y1);
        
        g2.setColor(Color.RED);
        g2.drawLine(x1, y1, x2, y2);
        
        g2.setColor(Color.GREEN);
        g2.drawLine(x2, y2, x3, y3);
        
        g2.setColor(Color.ORANGE);
        g2.drawLine(x3, y3, x4, y4);
        
        // Dibujar los dedos
        dibujarDedos(g2, x4, y4, anguloAcumulado3);
        
        // Dibujar las articulaciones
        g2.setColor(Color.BLACK);
        g2.fillOval(x0 - 6, y0 - 6, 12, 12);
        g2.fillOval(x1 - 6, y1 - 6, 12, 12);
        g2.fillOval(x2 - 6, y2 - 6, 12, 12);
        g2.fillOval(x3 - 6, y3 - 6, 12, 12);
        g2.fillOval(x4 - 6, y4 - 6, 12, 12);
        
        // Dibujar información de ángulos
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("Hombro: " + Math.round(Math.toDegrees(anguloHombro)) + "°", 10, 20);
        g2.drawString("Codo: " + Math.round(Math.toDegrees(anguloCodo)) + "°", 10, 35);
        g2.drawString("Muñeca: " + Math.round(Math.toDegrees(anguloMuneca)) + "°", 10, 50);
        g2.drawString("Mano: " + Math.round(Math.toDegrees(anguloMano)) + "°", 10, 65);
    }
    
    private void dibujarDedos(Graphics2D g2, int xBase, int yBase, double anguloBase) {
        g2.setStroke(new BasicStroke(3));
        
        // Dedo 1 (índice)
        double anguloDedo1Total = anguloBase + anguloDedo1;
        int x1d1 = xBase + (int)(L_DEDO * Math.cos(anguloDedo1Total));
        int y1d1 = yBase - (int)(L_DEDO * Math.sin(anguloDedo1Total));
        g2.setColor(Color.MAGENTA);
        g2.drawLine(xBase, yBase, x1d1, y1d1);
        
        // Dedo 2 (medio)
        double anguloDedo2Total = anguloBase + anguloDedo2 + 0.2;
        int x1d2 = xBase + (int)(L_DEDO * Math.cos(anguloDedo2Total));
        int y1d2 = yBase - (int)(L_DEDO * Math.sin(anguloDedo2Total));
        g2.setColor(Color.CYAN);
        g2.drawLine(xBase, yBase, x1d2, y1d2);
        
        // Dedo 3 (anular)
        double anguloDedo3Total = anguloBase + anguloDedo3 - 0.2;
        int x1d3 = xBase + (int)(L_DEDO * Math.cos(anguloDedo3Total));
        int y1d3 = yBase - (int)(L_DEDO * Math.sin(anguloDedo3Total));
        g2.setColor(Color.YELLOW);
        g2.drawLine(xBase, yBase, x1d3, y1d3);
        
        // Pulgar
        double anguloPulgarTotal = anguloBase + anguloPulgar + Math.PI/2;
        int x1p = xBase + (int)((L_DEDO * 0.8) * Math.cos(anguloPulgarTotal));
        int y1p = yBase - (int)((L_DEDO * 0.8) * Math.sin(anguloPulgarTotal));
        g2.setColor(Color.PINK);
        g2.drawLine(xBase, yBase, x1p, y1p);
        
        // Dibujar puntas de los dedos
        g2.setColor(Color.BLACK);
        g2.fillOval(x1d1 - 2, y1d1 - 2, 4, 4);
        g2.fillOval(x1d2 - 2, y1d2 - 2, 4, 4);
        g2.fillOval(x1d3 - 2, y1d3 - 2, 4, 4);
        g2.fillOval(x1p - 2, y1p - 2, 4, 4);
    }
    
    // Métodos para controlar las articulaciones
    public void moverHombro(boolean positivo) {
        anguloHombro += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverCodo(boolean positivo) {
        anguloCodo += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverMuneca(boolean positivo) {
        anguloMuneca += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverMano(boolean positivo) {
        anguloMano += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverDedo1(boolean positivo) {
        anguloDedo1 += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverDedo2(boolean positivo) {
        anguloDedo2 += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverDedo3(boolean positivo) {
        anguloDedo3 += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public void moverPulgar(boolean positivo) {
        anguloPulgar += positivo ? INCREMENTO : -INCREMENTO;
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Brazo Robótico Controlable con Mano");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            BrazoControlable brazo = new BrazoControlable();
            
            // Panel de controles
            JPanel panelControles = new JPanel(new GridLayout(8, 3, 5, 5));
            panelControles.setBorder(BorderFactory.createTitledBorder("Controles"));
            
            // Controles del hombro
            panelControles.add(new JLabel("Hombro:"));
            JButton hombroMas = new JButton("+");
            JButton hombroMenos = new JButton("-");
            hombroMas.addActionListener(e -> brazo.moverHombro(true));
            hombroMenos.addActionListener(e -> brazo.moverHombro(false));
            panelControles.add(hombroMenos);
            panelControles.add(hombroMas);
            
            // Controles del codo
            panelControles.add(new JLabel("Codo:"));
            JButton codoMas = new JButton("+");
            JButton codoMenos = new JButton("-");
            codoMas.addActionListener(e -> brazo.moverCodo(true));
            codoMenos.addActionListener(e -> brazo.moverCodo(false));
            panelControles.add(codoMenos);
            panelControles.add(codoMas);
            
            // Controles de la muñeca
            panelControles.add(new JLabel("Muñeca:"));
            JButton munecaMas = new JButton("+");
            JButton munecaMenos = new JButton("-");
            munecaMas.addActionListener(e -> brazo.moverMuneca(true));
            munecaMenos.addActionListener(e -> brazo.moverMuneca(false));
            panelControles.add(munecaMenos);
            panelControles.add(munecaMas);
            
            // Controles de la mano
            panelControles.add(new JLabel("Mano:"));
            JButton manoMas = new JButton("+");
            JButton manoMenos = new JButton("-");
            manoMas.addActionListener(e -> brazo.moverMano(true));
            manoMenos.addActionListener(e -> brazo.moverMano(false));
            panelControles.add(manoMenos);
            panelControles.add(manoMas);
            
            // Controles dedo 1 (índice)
            panelControles.add(new JLabel("Índice:"));
            JButton dedo1Mas = new JButton("+");
            JButton dedo1Menos = new JButton("-");
            dedo1Mas.addActionListener(e -> brazo.moverDedo1(true));
            dedo1Menos.addActionListener(e -> brazo.moverDedo1(false));
            panelControles.add(dedo1Menos);
            panelControles.add(dedo1Mas);
            
            // Controles dedo 2 (medio)
            panelControles.add(new JLabel("Medio:"));
            JButton dedo2Mas = new JButton("+");
            JButton dedo2Menos = new JButton("-");
            dedo2Mas.addActionListener(e -> brazo.moverDedo2(true));
            dedo2Menos.addActionListener(e -> brazo.moverDedo2(false));
            panelControles.add(dedo2Menos);
            panelControles.add(dedo2Mas);
            
            // Controles dedo 3 (anular)
            panelControles.add(new JLabel("Anular:"));
            JButton dedo3Mas = new JButton("+");
            JButton dedo3Menos = new JButton("-");
            dedo3Mas.addActionListener(e -> brazo.moverDedo3(true));
            dedo3Menos.addActionListener(e -> brazo.moverDedo3(false));
            panelControles.add(dedo3Menos);
            panelControles.add(dedo3Mas);
            
            // Controles pulgar
            panelControles.add(new JLabel("Pulgar:"));
            JButton pulgarMas = new JButton("+");
            JButton pulgarMenos = new JButton("-");
            pulgarMas.addActionListener(e -> brazo.moverPulgar(true));
            pulgarMenos.addActionListener(e -> brazo.moverPulgar(false));
            panelControles.add(pulgarMenos);
            panelControles.add(pulgarMas);
            
            // Layout principal
            frame.setLayout(new BorderLayout());
            frame.add(brazo, BorderLayout.CENTER);
            frame.add(panelControles, BorderLayout.EAST);
            
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
