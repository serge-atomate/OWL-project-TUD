package controllers;

import owlAPI.Ontology;
import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

import views.html.*;

/**
 * Created by serge on 28/12/2014.
 */

public class Classes extends Controller {

    public static Result index(String cls) throws Exception  {
        System.out.println("Cls: " + cls);
        if(!cls.equals("owl:Nothing")) {
            List<String> results = new ArrayList<String>();
//        String results = "";
            results = Ontology.rootClasses(cls);

            String jsonObj = "{\"classes\" : ";
            jsonObj += results.toString();
            jsonObj += "}";
//        jsonObj += ", \"individuals\": " + (listInd.toString()) + " } ";

            return ok(jsonObj);
        } else {
            return ok("{\"classes\" : []}");
        }
//        return ok(views.html.classes.render(results));
    }

}
