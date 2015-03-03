package controllers;

import owlAPI.Ontology;
import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

import views.html.*;

public class Application extends Controller {

    public static Result index() throws Exception {

        String results = "";
//         results = Ontology.loadOntology();
//         results = Ontology.getClassesOntology();
//         results = Ontology.walkerOntology();

        results = Ontology.shouldUseReasoner();

        return ok(views.html.homepage.render());
    }

    public static Result classes() throws Exception {
        List<String> results = new ArrayList<String>();
        results = Ontology.rootClasses("");
        return ok(views.html.classes.render(results));
    }


    public static Result query() throws Exception {
        String results = "";
        results = Ontology.queryOnt();
        return ok(results);
    }

}
