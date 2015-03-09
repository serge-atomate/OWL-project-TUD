package controllers;

import owlAPI.Ontology;
import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.lang3.text.WordUtils;

//import org.json.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import views.html.*;

/**
 * Created by serge on 07/01/2015.
 */

public class Individuals extends Controller {


    public static Result index(String individual) throws Exception  {
        System.out.println("Individual: " + individual);

        if(!individual.equals("")) {
            ArrayList<String> results = new ArrayList<String>();

            results = Ontology.getIndividual(individual);

//            return ok(results.toString());

            List<String> dataItems = new ArrayList<String>(Arrays.asList(results.get(0).replace("{ \"data\": [ {", "").replace("} ]", "").replace("http://www.aifb.uni-karlsruhe.de/", "").replace(">", "").split(";")));
            List<String> objectItems = new ArrayList<String>(Arrays.asList(results.get(1).replace("\"objects\": [ {", "").replace("} ] }", "").replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", "").split(";")));

            List<String> datas = new ArrayList<String>();
            List<String> objects = new ArrayList<String>();

            String name = "";

            for (String dataItem : dataItems) {

                String[] dataParts = dataItem.split(":", 2);

                if(dataParts.length!=1 && !dataParts[1].equals("\"\"") && !dataParts[1].equals("\"  \"")) {

//                    System.out.println(dataParts[1]);

                    // add name as separate var
                    if(dataParts[0].replace("\"", "").equals("name") || dataParts[0].replace("\"", "").equals("title")) {
                        name = dataParts[1].replace("\"", "");
                        continue;
                    }

                    String outputHtml = "<ul><li class=\"lst\">";

                    if(dataParts[1].contains("OWL/id")) {
                        // internal links
                        outputHtml += "<a class=\"cls\" href=\"/individ?q=" + dataParts[1].replace("\"", "") + "\">" + dataParts[1].replace("\"", "") + "</a>";
                    } else if(dataParts[1].contains("http://")) {
                        // external links
                        outputHtml += "<a class=\"cls\" target=\"_blank\" href=\"" + dataParts[1].replace("\"", "") + "\">" + dataParts[1].replace("\"", "") + "</a>";
                    } else {
                        // just text
                        outputHtml += dataParts[1].replace("\"", "");
                    }
                    outputHtml += "</li></ul>";

                    datas.add("<li class=\"lst\"><span>" + WordUtils.capitalize(dataParts[0].replace("\"", ""))+":</span>"+outputHtml+"</li>");

                }
            }

            for (String objectItem : objectItems) {
                String[] objParts = objectItem.split(":", 2);
                System.out.println("Size: " + objParts.length);

                String outputHtml = "";

                if (objParts.length > 1 && !objParts[1].equals("\"\"")) {

                    String[] options = objParts[1].replace("\"", "").split("±");

//                    if(options.length>=1) {
                        outputHtml = "<ul>";
                        for (String opt : options) {
                            System.out.println("OPT: " + opt);
                            // split again to take name and id separately
                            String[] parts = opt.split("=");
                            System.out.println("Size: " + parts.length);
                            if(parts.length > 1) {
                                System.out.println("parts: " + parts[0] + "////" + parts[1]);
                                outputHtml += "<li class=\"lst\"><a class=\"cls\" href=\"/individ?q=" + parts[1] + "\">" + parts[0] + "</a></li>";
                            } else {
                                outputHtml += "<li class=\"lst\"><a class=\"cls\" href=\"#\">" + parts[0] + "</a></li>";
                            }
                        }
                        outputHtml += "</ul>";
//                    } else {
//                        outputHtml = "<a class=\"cls\" href=\"/individ?q="+objParts[1].replace("\"", "")+"\">"+objParts[1].replace("\"", "")+"</a>";
//                    }

                    objects.add("<li class=\"lst\"><span>" + WordUtils.capitalize(objParts[0].replace("\"", "")) + ":</span>"+outputHtml+"</li>");
                } else {

//                    String[] options = objParts[0].replace("\"", "").split("±");
//                    outputHtml = "<ul>";
//                    for (String opt : options) {
//                        System.out.println("OPT: " + opt);
//                        // split again to take name and id separately
//                        String[] parts = opt.split("=");
//                        System.out.println("Size: " + parts.length);
//                        if(parts.length > 1) {
//                            System.out.println("parts: " + parts[0] + "////" + parts[1]);
//                            outputHtml += "<li class=\"lst\"><a class=\"cls\" href=\"/individ?q=" + parts[1] + "\">" + parts[0] + "</a></li>";
//                        } else {
//                            outputHtml += "<li class=\"lst\"><a class=\"cls\" href=\"#\">" + parts[0] + "</a></li>";
//                        }
//                    }
//                    outputHtml += "</ul>";
//                    objects.add("<li class=\"lst\"><span>" + WordUtils.capitalize(objParts[0].replace("\"", "")) + ":</span>"+outputHtml+"</li>");
                }
            }

            return ok(views.html.individ.render(datas, objects, name));

        } else {
            return ok("{[]}");
        }

    }


    public static Result individuals(String cls) throws Exception  {
        System.out.println("Cls: " + cls);

        if(!cls.equals("owl:Nothing")) {
            List<String> results = new ArrayList<String>();
//        String results = "";
            results = Ontology.classesIndividuals(cls);

            String jsonObj = "{\"individuals\" : ";
            jsonObj += results.toString();
            jsonObj += "}";

            return ok(jsonObj);
        } else {
            return ok("{\"classes\" : []}");
        }

    }
}