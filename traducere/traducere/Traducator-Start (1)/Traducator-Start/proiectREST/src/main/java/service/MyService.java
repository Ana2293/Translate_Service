package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

@Path("/MyService/{cuvant}/{limbaSursa}/{limbaDestinatie}")
public class MyService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String goGet (@PathParam("cuvant") String cuvant,
                         @PathParam("limbaSursa") String limbaSursa,
                         @PathParam("limbaDestinatie") String limbaDestinatie) {
        if (cuvant == null || cuvant.trim().isEmpty())
            return "Paramentrul corespunzator cuvantului lipseste";
        if (limbaSursa == null || limbaSursa.trim().isEmpty())
            return "Parametrul corespunzator numelui limbii origine lipseste";
        if (limbaDestinatie == null || limbaDestinatie.trim().isEmpty())
            return "Parametrul corespunzator numelui limbii in care se va traduce lipseste";
        String traducereOnline = cuvant + limbaSursa + limbaDestinatie;
        return traducereOnline;
    }
    public static void main(String[] args) throws IOException {

        MyParsingService myParsingService = new MyParsingService(new File("C:\\Users\\Ana Maria\\Downloads\\traducere\\Traducator-Start (1)\\Traducator-Start\\proiectREST\\src\\main\\webapp\\WEB-INF\\words.xml"));

        String traducere = myParsingService.traducereXML("fleur", "french", "german");
        if(traducere != null)
            System.out.println(traducere);
        else
            System.out.println("cuvantul nu a fost gasit");
    }
}



