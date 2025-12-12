import java.awt.*;
import javax.swing.*;

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
        setBackground(new Color(240, 245, 250));
        setPreferredSize(new Dimension(800, 600));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Configuración de renderizado de alta calidad
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Dibujar grid de fondo
        dibujarGrid(g2);
        
        // Punto base del brazo
        int x0 = 150;
        int y0 = getHeight() - 100;
        
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
        
        // Dibujar sombras primero
        dibujarSombras(g2, x0, y0, x1, y1, x2, y2, x3, y3, x4, y4);
        
        // Dibujar los segmentos del brazo con efecto 3D
        dibujarSegmento3D(g2, x0, y0, x1, y1, 12, new Color(51, 102, 187), "Hombro");
        dibujarSegmento3D(g2, x1, y1, x2, y2, 11, new Color(187, 51, 68), "Codo");
        dibujarSegmento3D(g2, x2, y2, x3, y3, 10, new Color(34, 153, 84), "Muñeca");
        dibujarSegmento3D(g2, x3, y3, x4, y4, 9, new Color(255, 140, 0), "Mano");
        
        // Dibujar los dedos con efecto 3D
        dibujarDedos(g2, x4, y4, anguloAcumulado3);
        
        // Dibujar las articulaciones con efecto metálico
        dibujarArticulacion3D(g2, x0, y0, 16, "Base");
        dibujarArticulacion3D(g2, x1, y1, 14, null);
        dibujarArticulacion3D(g2, x2, y2, 13, null);
        dibujarArticulacion3D(g2, x3, y3, 12, null);
        dibujarArticulacion3D(g2, x4, y4, 11, null);
        
        // Dibujar panel de información mejorado
        dibujarPanelInfo(g2);
    }
    
    private void dibujarDedos(Graphics2D g2, int xBase, int yBase, double anguloBase) {
        // Dedo 1 (índice)
        double anguloDedo1Total = anguloBase + anguloDedo1;
        int x1d1 = xBase + (int)(L_DEDO * Math.cos(anguloDedo1Total));
        int y1d1 = yBase - (int)(L_DEDO * Math.sin(anguloDedo1Total));
        dibujarSegmento3D(g2, xBase, yBase, x1d1, y1d1, 6, new Color(218, 112, 214), null);
        
        // Dedo 2 (medio)
        double anguloDedo2Total = anguloBase + anguloDedo2 + 0.2;
        int x1d2 = xBase + (int)(L_DEDO * Math.cos(anguloDedo2Total));
        int y1d2 = yBase - (int)(L_DEDO * Math.sin(anguloDedo2Total));
        dibujarSegmento3D(g2, xBase, yBase, x1d2, y1d2, 6, new Color(64, 224, 208), null);
        
        // Dedo 3 (anular)
        double anguloDedo3Total = anguloBase + anguloDedo3 - 0.2;
        int x1d3 = xBase + (int)(L_DEDO * Math.cos(anguloDedo3Total));
        int y1d3 = yBase - (int)(L_DEDO * Math.sin(anguloDedo3Total));
        dibujarSegmento3D(g2, xBase, yBase, x1d3, y1d3, 6, new Color(255, 215, 0), null);
        
        // Pulgar
        double anguloPulgarTotal = anguloBase + anguloPulgar + Math.PI/2;
        int x1p = xBase + (int)((L_DEDO * 0.8) * Math.cos(anguloPulgarTotal));
        int y1p = yBase - (int)((L_DEDO * 0.8) * Math.sin(anguloPulgarTotal));
        dibujarSegmento3D(g2, xBase, yBase, x1p, y1p, 6, new Color(255, 182, 193), null);
        
        // Dibujar puntas de los dedos con efecto 3D
        dibujarArticulacion3D(g2, x1d1, y1d1, 6, null);
        dibujarArticulacion3D(g2, x1d2, y1d2, 6, null);
        dibujarArticulacion3D(g2, x1d3, y1d3, 6, null);
        dibujarArticulacion3D(g2, x1p, y1p, 6, null);
    }
    
    // Método para dibujar grid de fondo
    private void dibujarGrid(Graphics2D g2) {
        g2.setColor(new Color(220, 230, 240));
        g2.setStroke(new BasicStroke(0.5f));
        
        // Líneas verticales
        for (int x = 0; x < getWidth(); x += 50) {
            g2.drawLine(x, 0, x, getHeight());
        }
        
        // Líneas horizontales
        for (int y = 0; y < getHeight(); y += 50) {
            g2.drawLine(0, y, getWidth(), y);
        }
    }
    
    // Método para dibujar sombras
    private void dibujarSombras(Graphics2D g2, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        g2.setColor(new Color(0, 0, 0, 20));
        g2.setStroke(new BasicStroke(14, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int offsetX = 3;
        int offsetY = 3;
        
        g2.drawLine(x0 + offsetX, y0 + offsetY, x1 + offsetX, y1 + offsetY);
        g2.drawLine(x1 + offsetX, y1 + offsetY, x2 + offsetX, y2 + offsetY);
        g2.drawLine(x2 + offsetX, y2 + offsetY, x3 + offsetX, y3 + offsetY);
        g2.drawLine(x3 + offsetX, y3 + offsetY, x4 + offsetX, y4 + offsetY);
    }
    
    // Método para dibujar segmento con efecto 3D
    private void dibujarSegmento3D(Graphics2D g2, int x1, int y1, int x2, int y2, int grosor, Color colorBase, String etiqueta) {
        // Calcular colores para gradiente
        Color colorClaro = colorBase.brighter();
        Color colorOscuro = colorBase.darker();
        
        // Dibujar contorno oscuro (sombra del segmento)
        g2.setColor(colorOscuro);
        g2.setStroke(new BasicStroke(grosor + 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(x1, y1, x2, y2);
        
        // Dibujar segmento principal con gradiente
        GradientPaint gradient = new GradientPaint(x1, y1, colorClaro, x2, y2, colorBase);
        g2.setPaint(gradient);
        g2.setStroke(new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(x1, y1, x2, y2);
        
        // Dibujar brillo superior
        g2.setColor(new Color(255, 255, 255, 80));
        g2.setStroke(new BasicStroke(grosor * 0.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int offsetBrillo = (int)(grosor * 0.25);
        double angulo = Math.atan2(y2 - y1, x2 - x1);
        int dx = (int)(offsetBrillo * Math.sin(angulo));
        int dy = -(int)(offsetBrillo * Math.cos(angulo));
        g2.drawLine(x1 + dx, y1 + dy, x2 + dx, y2 + dy);
        
        // Dibujar etiqueta si existe
        if (etiqueta != null) {
            g2.setColor(new Color(60, 60, 60));
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            int xMid = (x1 + x2) / 2 + 10;
            int yMid = (y1 + y2) / 2 - 10;
            g2.drawString(etiqueta, xMid, yMid);
        }
    }
    
    // Método para dibujar articulación con efecto metálico 3D
    private void dibujarArticulacion3D(Graphics2D g2, int x, int y, int radio, String etiqueta) {
        // Sombra de la articulación
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillOval(x - radio + 2, y - radio + 2, radio * 2, radio * 2);
        
        // Base metálica oscura
        g2.setColor(new Color(80, 80, 90));
        g2.fillOval(x - radio, y - radio, radio * 2, radio * 2);
        
        // Gradiente radial (simulado con círculos concéntricos)
        for (int i = radio; i > 0; i--) {
            int alpha = 30 + (int)((radio - i) * 150.0 / radio);
            g2.setColor(new Color(150, 150, 160, alpha));
            g2.fillOval(x - i, y - i, i * 2, i * 2);
        }
        
        // Anillo exterior metálico
        g2.setColor(new Color(100, 100, 110));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x - radio, y - radio, radio * 2, radio * 2);
        
        // Brillo superior
        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillOval(x - radio/3, y - radio/2, radio/2, radio/2);
        
        // Centro de la articulación (tornillo)
        g2.setColor(new Color(60, 60, 70));
        g2.fillOval(x - 3, y - 3, 6, 6);
        g2.setColor(new Color(40, 40, 50));
        g2.drawLine(x - 2, y, x + 2, y);
        g2.drawLine(x, y - 2, x, y + 2);
        
        // Dibujar etiqueta si existe
        if (etiqueta != null) {
            g2.setColor(new Color(80, 80, 80));
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.drawString(etiqueta, x - radio - 20, y + radio + 15);
        }
    }
    
    // Método para dibujar panel de información mejorado
    private void dibujarPanelInfo(Graphics2D g2) {
        // Panel semitransparente
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(10, 10, 220, 150, 15, 15);
        
        // Borde del panel
        g2.setColor(new Color(100, 150, 200));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(10, 10, 220, 150, 15, 15);
        
        // Título del panel
        g2.setColor(new Color(40, 80, 120));
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("INFORMACIÓN DEL BRAZO", 20, 30);
        
        // Línea separadora
        g2.setColor(new Color(150, 180, 210));
        g2.drawLine(20, 35, 210, 35);
        
        // Información de ángulos con iconos de color
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        
        dibujarInfoAngulo(g2, 20, 55, new Color(51, 102, 187), "Hombro", anguloHombro);
        dibujarInfoAngulo(g2, 20, 75, new Color(187, 51, 68), "Codo", anguloCodo);
        dibujarInfoAngulo(g2, 20, 95, new Color(34, 153, 84), "Muñeca", anguloMuneca);
        dibujarInfoAngulo(g2, 20, 115, new Color(255, 140, 0), "Mano", anguloMano);
        
        // Footer del panel
        g2.setFont(new Font("Arial", Font.ITALIC, 10));
        g2.setColor(new Color(100, 100, 100));
        g2.drawString("Brazo Robótico v2.0", 20, 145);
    }
    
    // Método auxiliar para dibujar información de ángulo
    private void dibujarInfoAngulo(Graphics2D g2, int x, int y, Color color, String nombre, double angulo) {
        // Indicador de color
        g2.setColor(color);
        g2.fillRoundRect(x, y - 8, 15, 12, 5, 5);
        g2.setColor(color.darker());
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y - 8, 15, 12, 5, 5);
        
        // Texto
        g2.setColor(new Color(50, 50, 50));
        String texto = String.format("%s: %d°", nombre, Math.round(Math.toDegrees(angulo)));
        g2.drawString(texto, x + 22, y);
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
