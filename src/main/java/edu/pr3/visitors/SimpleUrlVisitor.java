package edu.pr3.visitors;

import edu.pr3.Patterns;
import edu.pr3.stats.CodeAnsStats;
import edu.pr3.stats.GeneralStats;
import edu.pr3.stats.HttpMetodsStats;
import edu.pr3.stats.RemoteAddresStats;
import edu.pr3.stats.ResourcesStats;
import edu.pr3.stats.Stats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import static edu.pr3.LogAnalyse.analyseDoc;

public class SimpleUrlVisitor {
    private SimpleUrlVisitor() {

    }

    public static List<Stats> analyseURL(String pathOrUrl) throws IOException, URISyntaxException {
        Matcher matcher;
        var codeAnsStats = new CodeAnsStats();
        var httpMetodsStats = new HttpMetodsStats();
        var resourcesStats = new ResourcesStats();
        var remoteAddresStats = new RemoteAddresStats();
        var generalStats = new GeneralStats();
        matcher = Patterns.URL.getPattern().matcher(pathOrUrl);
        if (matcher.matches()) {
            generalStats.addFile(matcher.group(1));
            var uri = new URI(pathOrUrl);
            var url = uri.toURL();
            try (var reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                analyseDoc(reader, resourcesStats, remoteAddresStats, httpMetodsStats, generalStats, codeAnsStats);
            }
        }
        return List.of(
            generalStats,
            resourcesStats,
            codeAnsStats,
            remoteAddresStats,
            httpMetodsStats
        );
    }

}
