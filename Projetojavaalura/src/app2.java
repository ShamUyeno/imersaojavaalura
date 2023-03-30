import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;


public class app2 {
    public static void main(String[] args) throws Exception {
        
        // conexao http e buscar os filmes mais populares
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient(); 
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
        
        // extrair somente os dados que interessa (titulo, poster, pontuaçao)
        var parse = new jsonParse();
        List<Map<String, String>> listaDeFilmes = parse.parse(body);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();       

        //exibir e manuiular os dados
         
        for (Map<String, String> filme : listaDeFilmes){
            String urlImagem = filme.get("image");
            String urlImagemMaior = urlImagem.replaceFirst("(@?\\.)([0-9A-Z,_]+).jpg$", "");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textofigurinha;
            InputStream imagemEmoji;
            if (classificacao >= 9.0) {
                textofigurinha = "EU INDICO!";
                imagemEmoji = new FileInputStream(new File("sobreposicao/otimo.png"));
            }else if (classificacao >= 8.0) {
                textofigurinha = "ASSISTA JA!";
                imagemEmoji = new FileInputStream(new File("sobreposicao/bom.png"));
            } else {
                textofigurinha = "ERRRRR!";
                imagemEmoji = new FileInputStream(new File("sobreposicao/ruim.png"));
            }

            InputStream inputStream = new URL(urlImagemMaior).openStream();
            String nomeFilme = "figurinhas/"+ titulo + ".png";
            String nomeArquivo = nomeFilme.replace(":", "-");

            var geradora = new geradoradefigurinha();
            geradora.cria(inputStream, nomeArquivo, textofigurinha, imagemEmoji); 

            System.out.println(filme.get("title"));
            System.out.println();
        }

    }
}