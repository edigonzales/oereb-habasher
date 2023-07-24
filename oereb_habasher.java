///usr/bin/env jbang "$0" "$@" ; exit $?

import static java.lang.System.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class oereb_habasher {

    public static void main(String... args) throws IOException, InterruptedException, ExecutionException {
        List<String> egrids = Files.readAllLines(Paths.get("egrid_sh.csv"));

        HttpClient httpClient = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.NORMAL)
                .build();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (String egrid : egrids) {
            executorService.submit(() -> {
                //String url = "https://oereb.geo.sh.ch/oereb/extract/reduced/xml/"+egrid;
                //String url = "https://oereb.geo.sh.ch/oereb/extract/reduced/xml/"+egrid+"?WITHIMAGES=true&GEOMETRY=true";
                String url = "https://oereb.sh.opengis.ch/extract/xml/?GEOMETRY=true&WITHIMAGES=true&EGRID="+egrid;
                //String url = "https://geo.so.ch/api/oereb/extract/xml/?EGRID="+egrid+"&GEOMETRY=true&WITHIMAGES=true";                
                try {
                    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
                    out.println(url);
                    requestBuilder.GET().uri(new URI(url));
                    HttpRequest request = requestBuilder.timeout(Duration.ofMinutes(2L)).build();
            
                    var response = httpClient.send(request, BodyHandlers.ofString());
                    out.println(egrid + ": " + response.statusCode());
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
}
