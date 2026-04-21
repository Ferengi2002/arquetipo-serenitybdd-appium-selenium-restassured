package tasks.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmartAgentAPI {

    // Se recomienda usar variables de entorno para mayor seguridad (nivel experto)
    private static String getApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        return (key != null) ? key : "SIN_KEY";
    }
    
    private static String getApiUrl() {
        return "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + getApiKey();
    }

    public static String getXPathFromAI(String objetivo, String htmlClickeable) {
        String apiUrl = getApiUrl();
        if (getApiKey().equals("SIN_KEY")) {
            System.err.println("ERROR: No se encontró la variable de entorno GEMINI_API_KEY");
            return null;
        }
        try {
            String prompt = "Actua como un QA Engineer automatizador. Aqui tienes una lista de elementos HTML de la pagina web actual:\n\n"
                    + htmlClickeable + "\n\n"
                    + "OBJETIVO: Necesito hacer clic en el elemento que represente: '" + objetivo + "'.\n"
                    + "Analiza el HTML y devuelve UNICAMENTE el selector XPath exacto para hacer clic en el elemento correcto. "
                    + "Tu respuesta debe ser un texto plano con el XPath y NADA MAS. No incluyas comillas iniciales o finales, no des explicaciones, ni uses formato markdown (prohibido usar ```xpath o similares). Solo el XPath puro.";

            // Limpiamos caracteres que puedan romper el JSON
            String escapedPrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

            String jsonBody = "{"
                    + "\"contents\": [{"
                    + "  \"parts\":[{\"text\": \"" + escapedPrompt + "\"}]"
                    + "}]"
                    + "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Extraemos la respuesta limpia (usamos substrings para no requerir librerías de JSON extras)
            int textIndex = responseBody.indexOf("\"text\": \"");
            if (textIndex == -1) {
                System.out.println("Error en IA: " + responseBody);
                return null; 
            }
            
            String xpath = responseBody.substring(textIndex + 9);
            xpath = xpath.substring(0, xpath.indexOf("\""));
            xpath = xpath.replace("\\n", "").trim();

            System.out.println("[GEMINI AI] He decidido que el XPath para '" + objetivo + "' es: " + xpath);
            return xpath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
