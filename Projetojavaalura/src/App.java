import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;



public class App {
    public static void main(String[] args) throws Exception {
        
        // conexao http e buscar os filmes mais populares
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient(); 
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
        
        // extrair somente os dados que interessa (titulo, poster, pontuaçao)
        var parse = new jsonParse();
        List<Map<String, String>> listaDeFilmes = parse.parse(body);
        
        //exibir e manuiular os dados
        for (Map<String, String> filme : listaDeFilmes){
            System.out.println("Titulo: " + "\033[1m" + filme.get("title") + "\033[0m");
            System.out.println("Cartaz: " + filme.get("image"));
            double nota = Double.parseDouble(filme.get("imDbRating"));
    
        if (nota >= 9.0) {
                System.out.println("\u001b[30m \u001b[46m" + "Nota: " + " \u2B50 " + nota + " \u2B50" +"\033[0m");

            }else if (nota >= 8.0) {
                System.out.println("\u001b[30m \u001b[44m" + "Nota: " + " \uD83D\uDC4D " + nota + " \uD83D\uDC4D" +"\033[0m");

            } else {
                System.out.println("\u001b[30m \u001b[45m" + "Nota: " + " \uD83D\uDC4E " + nota + " \uD83D\uDC4E" +"\033[0m");
            }
        }

    }
}