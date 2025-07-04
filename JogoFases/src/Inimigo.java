import java.awt.*;
import java.util.Random;

public class Inimigo extends Personagem {
    private int velocidadeX, velocidadeY;
    private Random rand;
    
    public Inimigo(int x, int y, int velocidadeBase) {
        super(x, y, 35, 30, Color.RED);
        this.rand = new Random();
        this.velocidadeX = (rand.nextBoolean() ? 1 : -1) * velocidadeBase;
        this.velocidadeY = (rand.nextBoolean() ? 1 : -1) * velocidadeBase;
    }
    
    @Override
    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.RED);
        g2d.fillOval(x + 5, y + 12, 25, 12);
        
        int[] cabeca_x = {x, x + 8, x + 12, x + 8};
        int[] cabeca_y = {y + 15, y + 8, y + 15, y + 22};
        g2d.fillPolygon(cabeca_x, cabeca_y, 4);
        
        g2d.fillRect(x + 8, y + 15, 5, 8);
        
        g2d.setColor(Color.DARK_GRAY);

        int[] asa1_x = {x + 10, x + 8, x + 15, x + 12};
        int[] asa1_y = {y + 5, y + 12, y + 8, y + 15};
        g2d.fillPolygon(asa1_x, asa1_y, 4);

        int[] asa2_x = {x + 20, x + 25, x + 22, x + 18};
        int[] asa2_y = {y + 8, y + 5, y + 12, y + 15};
        g2d.fillPolygon(asa2_x, asa2_y, 4);

        g2d.setColor(Color.RED);
        int[] cauda_x = {x + 30, x + 35, x + 30};
        int[] cauda_y = {y + 15, y + 18, y + 21};
        g2d.fillPolygon(cauda_x, cauda_y, 3);
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + 8, y + 22, 3, 4);
        g2d.fillRect(x + 12, y + 22, 3, 4);
        g2d.fillRect(x + 18, y + 22, 3, 4);
        g2d.fillRect(x + 22, y + 22, 3, 4);
        
        g2d.fillRect(x + 9, y + 26, 1, 2);
        g2d.fillRect(x + 13, y + 26, 1, 2);
        g2d.fillRect(x + 19, y + 26, 1, 2);
        g2d.fillRect(x + 23, y + 26, 1, 2);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + 4, y + 12, 3, 3);
        g2d.fillOval(x + 4, y + 17, 3, 3);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + 5, y + 13, 1, 1);
        g2d.fillOval(x + 5, y + 18, 1, 1);

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x + 15, y + 16, 2, 2);
        g2d.fillOval(x + 20, y + 18, 2, 2);
        g2d.fillOval(x + 18, y + 14, 2, 2);
        
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + 5, y + 12, 25, 12);
    }
    
    public void mover(int larguraJanela, int alturaJanela) {
        x += velocidadeX;
        y += velocidadeY;

        if (x <= 0 || x >= larguraJanela - largura) {
            velocidadeX = -velocidadeX;
        }
        if (y <= 0 || y >= alturaJanela - altura) {
            velocidadeY = -velocidadeY;
        }
        
        x = Math.max(0, Math.min(x, larguraJanela - largura));
        y = Math.max(0, Math.min(y, alturaJanela - altura));
        
        if (rand.nextInt(100) < 2) {
            velocidadeX = (rand.nextBoolean() ? 1 : -1) * Math.abs(velocidadeX);
            velocidadeY = (rand.nextBoolean() ? 1 : -1) * Math.abs(velocidadeY);
        }
    }
}