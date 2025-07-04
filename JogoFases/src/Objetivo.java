import java.awt.*;

class Objetivo extends Personagem {
    
    public Objetivo(int x, int y) {
        super(x, y, 70, 55, Color.RED); 
    }
    
    @Override
    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        Color[] cores = {
            Color.RED,
            Color.ORANGE, 
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            new Color(75, 0, 130),
            new Color(148, 0, 211)
        };
 
        int arcoBaseX = x + 5;
        int arcoBaseY = y + 10;
        int arcoBaseLargura = 60;
        int arcoBaseAltura = 35;
        
        for (int i = 0; i < cores.length; i++) {
            g2d.setColor(cores[i]);
            int arcoLargura = arcoBaseLargura - (i * 2);
            int arcoAltura = arcoBaseAltura - (i * 1);
            int arcoX = arcoBaseX + i;
            int arcoY = arcoBaseY + i/2;
            
            g2d.fillArc(arcoX, arcoY, arcoLargura, arcoAltura, 0, 180);
        }
        
        g2d.setColor(Color.WHITE);

        int nuvemY = arcoBaseY + arcoBaseAltura - 8;
        g2d.fillOval(arcoBaseX + 2, nuvemY, 8, 6);
        g2d.fillOval(arcoBaseX + 4, nuvemY - 2, 6, 6);
        g2d.fillOval(arcoBaseX + 6, nuvemY, 8, 6);

        g2d.fillOval(arcoBaseX + arcoBaseLargura - 16, nuvemY, 8, 6);
        g2d.fillOval(arcoBaseX + arcoBaseLargura - 14, nuvemY - 2, 6, 6);
        g2d.fillOval(arcoBaseX + arcoBaseLargura - 12, nuvemY, 8, 6);
        
        g2d.setColor(Color.YELLOW);

        desenharEstrela(g2d, arcoBaseX + 8, y + 5, 3); 
        desenharEstrela(g2d, arcoBaseX + arcoBaseLargura - 8, y + 7, 3); 
        desenharEstrela(g2d, arcoBaseX + arcoBaseLargura/2, y + 2, 3);
        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawArc(arcoBaseX, arcoBaseY, arcoBaseLargura, arcoBaseAltura, 0, 180);
    }
   
    private void desenharEstrela(Graphics2D g2d, int cx, int cy, int tamanho) {
        int[] x_pontos = new int[10];
        int[] y_pontos = new int[10];
        
        for (int i = 0; i < 10; i++) {
            double angulo = Math.PI * i / 5.0;
            int raio = (i % 2 == 0) ? tamanho : tamanho / 2;
            x_pontos[i] = (int) (cx + raio * Math.cos(angulo - Math.PI/2));
            y_pontos[i] = (int) (cy + raio * Math.sin(angulo - Math.PI/2));
        }
        
        g2d.fillPolygon(x_pontos, y_pontos, 10);
    }
}