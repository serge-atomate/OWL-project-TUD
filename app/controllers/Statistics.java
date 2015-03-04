package controllers;

import owlAPI.Ontology;
import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

import views.html.*;

public class Statistics extends Controller {

    public static Result index() throws Exception {
        return ok(views.html.statistics.render());
    }

}
