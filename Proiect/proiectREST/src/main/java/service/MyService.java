package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Path("/MyService/{cuvant}/{limba_sursa}/{limba_destinatie}")
public class MyService {

    private Parser parser;

    @Context
    private ServletContext context;

    @PostConstruct
    public void init() throws MalformedURLException, URISyntaxException {
        File file = new File(context.getResource("/WEB-INF/traducere.xml").toURI());

        if(!file.exists())
            parser = null;
        else
            parser = new Parser(file);
    }

    @GET
    public String goGet(@PathParam("cuvant") String cuvant,
                        @PathParam("limba_sursa") String limbaSursa,
                        @PathParam("limba_destinatie") String limbaDestinatie) throws FileNotFoundException {

        if(cuvant == null || cuvant.isEmpty())
            return "Parametrul pentru cuvant lipseste";

        if(limbaSursa == null || limbaSursa.isEmpty())
            return "Parametrul pentru limba sursa lipseste";

        if(limbaDestinatie == null || limbaDestinatie.isEmpty())
            return "Parametrul pentru limba destinatie lipseste";

        if(parser == null)
            return "Fisierul XML nu a putut sa fie deschis";

        String traducere = parser.parseXML( cuvant, limbaSursa, limbaDestinatie);

        if(traducere == null)
            return "Traducerea cuvantului nu a fost gasita";

        return traducere;

    }
}
