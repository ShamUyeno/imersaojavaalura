import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;


//import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;



public class geradoradefigurinha {
    

    public void cria(InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws Exception{

    //leitura da imagem
    //InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
    BufferedImage imagemOriginal = ImageIO.read(inputStream); 

    //cria uma nova imagem (transparencia e tamanho novo)
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();
    int novaaltura = altura + 200;
    BufferedImage novaImagem = new BufferedImage(largura, novaaltura, BufferedImage.TRANSLUCENT);

    //copiar imagem para novo imagem (memoria)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0,0, null);

        BufferedImage imagemSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoImagemSobreposicaoY = novaaltura - imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, 0, posicaoImagemSobreposicaoY, null);

    // Configurar a fonte
    Font fonte = new Font("Impact", Font.BOLD, 120 );
    graphics.setColor(Color.green);
    graphics.setFont(fonte);

    // Escrever a frase na nova imagem centralizada
    FontMetrics FontMetrics = graphics.getFontMetrics();
    Rectangle2D retangulo = FontMetrics.getStringBounds(texto, graphics);
    int larguratexto = (int) retangulo.getWidth();
    int posicaotextoX = (largura - larguratexto)/2;
    int posicaotextoY = novaaltura - 50;
    graphics.drawString(texto, posicaotextoX, posicaotextoY);

   FontRenderContext fontRenderContext = graphics.getFontRenderContext();
   var textLayout = new TextLayout(texto, fonte, fontRenderContext);
        
   Shape outline = textLayout.getOutline(null); 
   AffineTransform transform = graphics.getTransform();
   transform.translate(posicaotextoX, posicaotextoY);
   graphics.setTransform(transform);

   var outlineStroke = new BasicStroke(largura * 0.004f);
   graphics.setStroke(outlineStroke);

   graphics.setColor(Color.RED);
   graphics.draw(outline);
   graphics.setClip(outline);     

    // escrever nova imagem 
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));
    }

//    public static void main(String[] args) throws Exception {
//        var geradora = new geradoradefigurinha();
//        geradora.cria();
//    }

}
