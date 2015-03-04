package owlAPI;

import java.io.File;
import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;

import static org.junit.Assert.assertNotNull;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
//import org.semanticweb.owlapi.model.OWLObjectAssertionAxiom;

import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

import org.json.*;


import de.derivo.sparqldlapi.Query;
import de.derivo.sparqldlapi.QueryEngine;
import de.derivo.sparqldlapi.QueryResult;
import de.derivo.sparqldlapi.exceptions.QueryEngineException;
import de.derivo.sparqldlapi.exceptions.QueryParserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by serge on 14/12/2014.
 */
public class Ontology {

    /**
     * An example which shows how to interact with a reasoner. In this example
     * Pellet is used as the reasoner. You must get hold of the pellet libraries
     * from pellet.owldl.com.
     *
     * @throws Exception
     *         exception
     */
    public static String shouldUseReasoner() throws Exception {
        // Create our ontology manager in the usual way.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("ontology.owl");
        OWLOntology ont = manager.loadOntologyFromOntologyDocument(file);
//        OWLOntology ont = loadPizza(manager);

        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory. Create a reasoner factory. In this case, we will use HermiT,
        // but we could also use FaCT++ (http://code.google.com/p/factplusplus/)
        // or Pellet(http://clarkparsia.com/pellet) Note that (as of 03 Feb
        // 2010) FaCT++ and Pellet OWL API 3.0.0 compatible libraries are
        // expected to be available in the near future). For now, we'll use
        // HermiT HermiT can be downloaded from http://hermit-reasoner.com Make
        // sure you get the HermiT library and add it to your class path. You
        // can then instantiate the HermiT reasoner factory: Comment out the
        // first line below and uncomment the second line below to instantiate
        // the HermiT reasoner factory. You'll also need to import the
        // org.semanticweb.HermiT.Reasoner package.
//        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        // We'll now create an instance of an OWLReasoner (the implementation
        // being provided by HermiT as we're using the HermiT reasoner factory).
        // The are two categories of reasoner, Buffering and NonBuffering. In
        // our case, we'll create the buffering reasoner, which is the default
        // kind of reasoner. We'll also attach a progress monitor to the
        // reasoner. To do this we set up a configuration that knows about a
        // progress monitor. Create a console progress monitor. This will print
        // the reasoner progress out to the console.
        // ConsoleProgressMonitor progressMonitor = new
        // ConsoleProgressMonitor();
        // Specify the progress monitor via a configuration. We could also
        // specify other setup parameters in the configuration, and different
        // reasoners may accept their own defined parameters this way.
        // OWLReasonerConfiguration config = new SimpleConfiguration(
        // progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now

//        reasoner.precomputeInferences();


        // We can determine if the ontology is actually consistent (in this
        // case, it should be).

        //PASSED TRUE
//        boolean consistent = reasoner.isConsistent();
//        System.out.println("Consistent: " + consistent);

        // We can easily get a list of unsatisfiable classes. (A class is
        // unsatisfiable if it can't possibly have any instances). Note that the
        // getUnsatisfiableClasses method is really just a convenience method
        // for obtaining the classes that are equivalent to owl:Nothing. In our
        // case there should be just one unsatisfiable class - "mad_cow" We ask
        // the reasoner for the unsatisfiable classes, which returns the bottom
        // node in the class hierarchy (an unsatisfiable class is a subclass of
        // every class).

//        Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
        // This node contains owl:Nothing and all the classes that are
        // equivalent to owl:Nothing - i.e. the unsatisfiable classes. We just
        // want to print out the unsatisfiable classes excluding owl:Nothing,
        // and we can used a convenience method on the node to get these

        //PASSED TRUE
//        Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
//        if (!unsatisfiable.isEmpty()) {
//            System.out.println("The following classes are unsatisfiable: ");
//            for (OWLClass cls : unsatisfiable) {
//                System.out.println("    " + cls);
//            }
//        } else {
//            System.out.println("There are no unsatisfiable classes");
//        }

        // Now we want to query the reasoner for all descendants of vegetarian.
        // Vegetarians are defined in the ontology to be animals that don't eat
        // animals or parts of animals.
        OWLDataFactory fac = manager.getOWLDataFactory();
        // Get a reference to the vegetarian class so that we can as the
        // reasoner about it. The full IRI of this class happens to be:
        // <http://owl.man.ac.uk/2005/07/sssw/people#vegetarian>
        OWLClass organizationClass = fac.getOWLClass(IRI
                .create("http://swrc.ontoware.org/ontology#Graduate"));
//        OWLClass organizationClass = fac.getOWLClass(IRI
//                .create("http://www.w3.org/2002/07/owl#Thing"));
        // Now use the reasoner to obtain the subclasses of vegetarian. We can
        // ask for the direct subclasses of vegetarian or all of the (proper)
        // subclasses of vegetarian. In this case we just want the direct ones
        // (which we specify by the "true" flag).
        NodeSet<OWLClass> subClses = reasoner.getSubClasses(organizationClass, true);
        // The reasoner returns a NodeSet, which represents a set of Nodes. Each
        // node in the set represents a subclass of vegetarian pizza. A node of
        // classes contains classes, where each class in the node is equivalent.
        // For example, if we asked for the subclasses of some class A and got
        // back a NodeSet containing two nodes {B, C} and {D}, then A would have
        // two proper subclasses. One of these subclasses would be equivalent to
        // the class D, and the other would be the class that is equivalent to
        // class B and class C. In this case, we don't particularly care about
        // the equivalences, so we will flatten this set of sets and print the
        // result
        Set<OWLClass> clses = subClses.getFlattened();
        System.out.println("Subclasses of root: ");
        for (OWLClass cls : clses) {
            System.out.println("    " + cls);
        }

        // In this case, we should find that the classes, cow, sheep and giraffe
        // are vegetarian. Note that in this ontology only the class cow had
        // been stated to be a subclass of vegetarian. The fact that sheep and
        // giraffe are subclasses of vegetarian was implicit in the ontology
        // (through other things we had said) and this illustrates why it is
        // important to use a reasoner for querying an ontology. We can easily
        // retrieve the instances of a class. In this example we'll obtain the
        // instances of the class pet. This class has a full IRI of
        // <http://owl.man.ac.uk/2005/07/sssw/people#pet> We need to obtain a
        // reference to this class so that we can ask the reasoner about it.
        OWLClass person = fac.getOWLClass(IRI
                .create("http://swrc.ontoware.org/ontology#Graduate"));
        // Ask the reasoner for the instances of pet
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
                person, true);
        // The reasoner returns a NodeSet again. This time the NodeSet contains
        // individuals. Again, we just want the individuals, so get a flattened
        // set.
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
        System.out.println("Instances of person: ");
        for (OWLNamedIndividual ind : individuals) {
            System.out.println("    " + ind);
        }

        // Again, it's worth noting that not all of the individuals that are
        // returned were explicitly stated to be pets. Finally, we can ask for
        // the property values (property assertions in OWL speak) for a given
        // individual and property. Let's get the property values for the
        // individual Mick, the full IRI of which is
        // <http://owl.man.ac.uk/2005/07/sssw/people#Mick> Get a reference to
        // the individual Mick


        //======================= check individual's by property objects
        OWLNamedIndividual mick = fac.getOWLNamedIndividual(IRI
//                .create("http://swrc.ontoware.org/ontology#Organization"));
                .create("http://www.aifb.uni-karlsruhe.de/Personen/viewPersonOWL/id2105instance"));
//                .create("http://www.aifb.uni-karlsruhe.de/Publikationen/viewExternerAutorOWL/id299instance"));
        // Let's get the pets of Mick Get hold of the has_pet property which has
        // a full IRI of <http://owl.man.ac.uk/2005/07/sssw/people#has_pet>
        OWLObjectProperty hasPet = fac.getOWLObjectProperty(IRI
//                .create("http://swrc.ontoware.org/ontology#year"));
                .create("http://swrc.ontoware.org/ontology#publication"));
        // Now ask the reasoner for the has_pet property values for Mick
        NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner
                        .getObjectPropertyValues(mick, hasPet);
        Set<OWLNamedIndividual> values = petValuesNodeSet.getFlattened();
        System.out.println("The publication property values for individual are: ");
        for (OWLNamedIndividual ind : values) {
            System.out.println("    " + ind);
        }

        //======================= Print name of the individual
        OWLDataProperty hasAge = fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#name"));

        for (OWLLiteral l : reasoner.getDataPropertyValues(mick, hasAge)) {
            if (l.getDatatype().isString()) {
                System.out.println("Asserted value: " + l.getLiteral());
            }
        }

        OWLObjectProperty hasProperty = fac.getOWLObjectProperty(IRI
//                .create("http://swrc.ontoware.org/ontology#author"));
                .create("http://swrc.ontoware.org/ontology#publication"));
//        NodeSet<OWLObjectPropertyExpression> allProperties = reasoner.getEquivalentObjectProperties(hasPet);
        // get owl:topObjectProperty
//        NodeSet<OWLObjectPropertyExpression> allProperties = reasoner.getSuperObjectProperties(hasProperty, true);
        // get owl:bottomObjectProperty
//        NodeSet<OWLObjectPropertyExpression> allProperties = reasoner.getDisjointObjectProperties(hasProperty, true);

        NodeSet<OWLObjectPropertyExpression> allProperties = reasoner.getSuperObjectProperties(hasProperty, true);

        System.out.println("allProperties: " + allProperties);
        Set<OWLObjectPropertyExpression> allProp = allProperties.getFlattened();
//        Set<OWLObjectPropertyExpression> allProp = allProperties.getEntities();
        for (OWLObjectPropertyExpression val : allProp) {
            System.out.println("    " + val);
        }

//        Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> all = reasoner.getDataPropertyValues(ont);
//        for (OWLLiteral l : reasoner.getDataPropertyValues(mick)) {
//            if (l.getDatatype().isString()) {
//                System.out.println("Asserted value: " + l.getLiteral());
//            }
//        }

//        for (OWLLiteral l : reasoner.getDataPropertyValues(mick)) {
//            if (l.getDatatype().isString()) {
//                System.out.println("Asserted value: " + l.getLiteral());
//            }
//        }

//        Set<OWLDataProperty> allEquivalentProperties = fac.getEquivalentDataProperties(IRI.create("http://swrc.ontoware.org/ontology#name"));
//        for (OWLDataProperty property : allEquivalentProperties) {
//            System.out.println("    " + property);
//        }

//        Set<OWLObjectAssertionAxiom> set = fac.getObjectPropertyAssertionAxioms(mick);

/*
//Prepare the expression for the query
        OWLDataProperty p = fac.getOWLDataProperty("http://swrc.ontoware.org/ontology#name");
        OWLClassExpression ex =
                fac.getOWLDataHasValue(p, fac.getOWLLiteral("Claudio Giuliano"));

//Print out the results, John is inside
        Set<OWLNamedIndividual> result = reasoner.getInstances(ex, true).getFlattened();
        for (OWLNamedIndividual owlNamedIndividual : result) {
            System.out.println(owlNamedIndividual);
        }
        /*
        // Notice that Mick has a pet Rex, which wasn't asserted in the
        // ontology. Finally, let's print out the class hierarchy. Get hold of
        // the top node in the class hierarchy (containing owl:Thing) Now print
        // the hierarchy out

        */
/*
        final OWLAnnotationProperty comment = fac.getRDFSComment();

//Create a walker
        OWLOntologyWalker walker =
                new OWLOntologyWalker(Collections.singleton(ont));

//Define what's going to visited
        OWLOntologyWalkerVisitorEx<Object> visitor =
                new OWLOntologyWalkerVisitorEx<Object>(walker) {

                    //In your case you visit the annotations made with rdfs:comment
                    //over the object properties assertions
                    @Override
                    public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
                        //Print them
                        System.out.println("== "+axiom.getAnnotations(comment));
                        return null;
                    }
                };

//Walks over the structure - triggers the walk
        walker.walkStructure(visitor);
*/


//        Node<OWLClass> topNode = reasoner.getTopClassNode();
//        print(topNode, reasoner, 0);

        return "|";
    }

    ///==============================================================///

    static OWLOntology loadOntologyFromFile(OWLOntologyManager manager)
            throws OWLOntologyCreationException {

        File file = new File("ontology.owl");
//        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

        return manager.loadOntologyFromOntologyDocument(file);
    }


    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static ArrayList rootClasses(String classItem) throws Exception {

        System.out.println("Class: " + classItem);

        ArrayList<String> jsonObj = new ArrayList<String>();
//        String jsonObj = "{ \"classes\": ";
        JSONArray listCls = new JSONArray();
        JSONArray listInd = new JSONArray();

        String uri = "";
        if(classItem == null || classItem == "") {
            uri = "http://www.w3.org/2002/07/owl#Thing";
        } else {
            uri = "http://swrc.ontoware.org/ontology#"+classItem;
        }

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
//        reasoner.precomputeInferences();

        OWLDataFactory fac = manager.getOWLDataFactory();

        OWLClass organizationClass = fac.getOWLClass(IRI
                .create(uri));

        NodeSet<OWLClass> subClses = reasoner.getSubClasses(organizationClass, true);

        Set<OWLClass> clses = subClses.getFlattened();
//        System.out.println("Subclasses of Organization: ");
        for (OWLClass cls : clses) {
//            System.out.println("    " + cls);
//
//            Pattern pattern = Pattern.compile("(#[A-Za-z*])\\w+");
//            Matcher matcher = pattern.matcher(cls.toString());
//            if (matcher.find())
//            {
//                System.out.println(matcher.group(1));
//                classes.add(cls.toString());
//            }

            jsonObj.add("\""+cls.toString().replace("<http://swrc.ontoware.org/ontology#", "").replace(">", "")+"\"");

//            listCls.put(cls.toString().replace("<http://swrc.ontoware.org/ontology#", "").replace(">", ""));

        }

//        jsonObj += listCls.toString();

//        OWLClass individs = fac.getOWLClass(IRI.create(uri));
//
//        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
//                individs, true);
//
//        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
//        System.out.println("Instances of class: ");
//        for (OWLNamedIndividual ind : individuals) {
//            System.out.println("    " + ind);
////            listInd.put(ind);
////            jsonObj.add(ind);
//        }

//        jsonObj += ", \"individuals\": " + (listInd.toString()) + " } ";

        System.out.println("Array List: "+jsonObj);

        return jsonObj;
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //public static String classesIndividuals(String classItem) throws Exception {
    public static ArrayList classesIndividuals(String classItem) throws Exception {

        System.out.println("Class: " + classItem);

        ArrayList<String> jsonObj = new ArrayList<String>();
    //        String jsonObj = "{ \"classes\": ";
        JSONArray listCls = new JSONArray();
        JSONArray listInd = new JSONArray();

        String uri = "";
        if(classItem == null || classItem == "") {
            uri = "http://www.w3.org/2002/07/owl#Thing";
        } else {
            uri = "http://swrc.ontoware.org/ontology#"+classItem;
        }

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
        reasoner.precomputeInferences();

        OWLDataFactory fac = manager.getOWLDataFactory();

        OWLClass individs = fac.getOWLClass(IRI.create(uri));

        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
                individs, true);

        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        OWLDataProperty hasName = fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#name"));

        System.out.println("Instances of class: ");
        int i = 0;
        for (OWLNamedIndividual ind : individuals) {
            i++;
            System.out.println(i+".    " + ind);
            if(i>20) {
                break;
            }

            //======================= Print name of the individual
            for (OWLLiteral l : reasoner.getDataPropertyValues(ind, hasName)) {
                if (l.getDatatype().isString()) {
                    System.out.println("Asserted value: " + l.getLiteral());
//                    jsonObj.add("\""+l.getLiteral()+"\"");
                    jsonObj.add("{\"id\":\""+ind.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", "")+"\", \"name\":\""+l.getLiteral()+"\"}");
//                    {"firstName":"John", "lastName":"Doe"},
                }
            }
    //            listInd.put(ind);
//            jsonObj.add("\""+ind.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", "")+"\"");
        }

        //        jsonObj += ", \"individuals\": " + (listInd.toString()) + " } ";

//        System.out.println("Array List: "+jsonObj);

        return jsonObj;
    }

    public static ArrayList getIndividual(String individual) throws Exception {

        ArrayList<String> json = new ArrayList<String>();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
//        reasoner.precomputeInferences();

        OWLDataFactory fac = manager.getOWLDataFactory();

        OWLNamedIndividual individ = fac.getOWLNamedIndividual(IRI
                .create("http://www.aifb.uni-karlsruhe.de/" + individual));

        //======================= Get DATA PROPERTY of the individual
//        OWLDataProperty hasName = fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#name"));

        List<String> listDataProperty = new ArrayList();
        listDataProperty.add("abstract");
        listDataProperty.add("address");
        listDataProperty.add("booktitle");
        listDataProperty.add("chapter");
        listDataProperty.add("date");

        listDataProperty.add("edition");
        listDataProperty.add("email");
        listDataProperty.add("eventTitle");
        listDataProperty.add("fax");

        listDataProperty.add("hasPrice");
        listDataProperty.add("homepage");
        listDataProperty.add("howpublished");
        listDataProperty.add("isbn");
        listDataProperty.add("journal");

        listDataProperty.add("keywords");
        listDataProperty.add("location");
        listDataProperty.add("month");
        listDataProperty.add("name");
        listDataProperty.add("note");

        listDataProperty.add("number");
        listDataProperty.add("pages");
        listDataProperty.add("phone");
//        listDataProperty.add("photo");
        listDataProperty.add("price");

        listDataProperty.add("series");
        listDataProperty.add("source");
        listDataProperty.add("title");
        listDataProperty.add("type");
        listDataProperty.add("volume");

        listDataProperty.add("year");

        String dataStr = "";

        for (String property : listDataProperty) {
            String value = "\""+property+"\":\"";
            for (OWLLiteral l : reasoner.getDataPropertyValues(individ, fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#"+property)))) {
                if (l.getDatatype().isString()) {
//                    System.out.println("Asserted value: " + l.getLiteral());
                    value += StringEscapeUtils.unescapeJava(l.getLiteral().replace("\n", " ").replace("\r", " "));
                }
            }
            dataStr += value+"\";";
        }
        json.add("{ \"data\": [ {"+dataStr.substring(0, dataStr.length()-1)+"} ]");
//        json.add("{\"id\":\"" + individual + "\", \"data\": [ {"+dataStr.substring(0, dataStr.length()-1)+"} ]");

        //======================= Get OBJECT PROPERTY of the individual

        List<String> listOBJProperty = new ArrayList();
        listOBJProperty.add("affiliation");
        listOBJProperty.add("atEvent");
        listOBJProperty.add("author");
        listOBJProperty.add("carriedOutBy");
        listOBJProperty.add("carriesOut");

        listOBJProperty.add("cite");
        listOBJProperty.add("citeBy");
        listOBJProperty.add("cooperateWith");
        listOBJProperty.add("dealtWithIn");
        listOBJProperty.add("describesProject");

        listOBJProperty.add("developedBy");
        listOBJProperty.add("develops");
        listOBJProperty.add("editor");
        listOBJProperty.add("employs");
        listOBJProperty.add("financedBy");

        listOBJProperty.add("finances");
        listOBJProperty.add("givenBy");
        listOBJProperty.add("hasPartEvent");
        listOBJProperty.add("hasParts");
        listOBJProperty.add("head");

        listOBJProperty.add("headOf");
        listOBJProperty.add("headOfGroup");
        listOBJProperty.add("institution");
        listOBJProperty.add("isAbout");
        listOBJProperty.add("isWorkedOnBy");

        listOBJProperty.add("member");
        listOBJProperty.add("memberOfPC");
        listOBJProperty.add("organization");
        listOBJProperty.add("organizerOrChairOf");
        listOBJProperty.add("participant");

        listOBJProperty.add("product");
        listOBJProperty.add("projectInfo");
        listOBJProperty.add("publication");
        listOBJProperty.add("publisher");
        listOBJProperty.add("publishes");

        listOBJProperty.add("Root");
        listOBJProperty.add("RootRelation");
        listOBJProperty.add("school");
        listOBJProperty.add("student");
        listOBJProperty.add("studiesAt");

        listOBJProperty.add("supervises");
        listOBJProperty.add("supervisor");
        listOBJProperty.add("technicalReport");
        listOBJProperty.add("worksAtProject");

        String objStr = "";

        for (String propertyObj : listOBJProperty) {
            String value = "\""+propertyObj+"\":\"";
            int i=0;
            for (OWLNamedIndividual l : reasoner.getObjectPropertyValues(individ, fac.getOWLObjectProperty(IRI
                    .create("http://swrc.ontoware.org/ontology#" + propertyObj))).getFlattened()) {
                i++;
                // get title/name for objects
//                if(propertyObj.equals("publication")) {
                    ArrayList<String> nameResults = new ArrayList<String>();
                    nameResults = getNameTitle(l.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", ""), fac, reasoner);
//                    System.out.println(l.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", ""));
//                    System.out.println(nameResults.get(0));
//                }
                if(i>=1) {
                    //add sepparator if more elements
                    value += nameResults.get(0)+"="+l+"±";
                } else {
                    value += nameResults.get(0)+"="+l;
                }
            }
            //delete the last separator if more elements added
            if(value.substring(value.length() - 1).equals(";")){
                value = value.substring(0, value.length()-1);
            }
            objStr += StringEscapeUtils.unescapeJava(value) + "\";";
        }
        json.add("\"objects\": [ {"+objStr.substring(0, objStr.length()-1)+"} ] }");

        return json;
    }

    // get Title/Name of the individ
    public static ArrayList getNameTitle(String individual, OWLDataFactory fac, OWLReasoner reasoner) throws Exception {

        ArrayList<String> name = new ArrayList<String>();

        OWLNamedIndividual individ = fac.getOWLNamedIndividual(IRI
                .create("http://www.aifb.uni-karlsruhe.de/" + individual));

        //======================= Get DATA PROPERTY of the individual

        List<String> listDataProperty = new ArrayList();
        listDataProperty.add("name");
        listDataProperty.add("title");

        for (String property : listDataProperty) {
            for (OWLLiteral l : reasoner.getDataPropertyValues(individ, fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#"+property)))) {
                if (l.getDatatype().isString() && !l.getLiteral().equals("")) {
//                    System.out.println("Asserted value: |" + l.getLiteral()+"|");
                    name.add(StringEscapeUtils.unescapeJava(l.getLiteral().replace("\n", " ").replace("\r", " ")));
                }
            }
        }

        return name;
    }


    public static String queryOnt() throws Exception {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
        reasoner.precomputeInferences();

        try {
            QueryEngine queryEng = QueryEngine.create(manager, reasoner);

            long startTime = System.currentTimeMillis();

            // Create a query object from it's string representation
//            String q = "SELECT ?subject\n" +
//                    "WHERE { DirectSubClassOf(?subject, <http://swrc.ontoware.org/ontology#Person>) }";


            //// !!!!!!!!!!!!!!! GET ALL DataProperty from Ontology
//            String q = "SELECT ?subject\n" +
//                    "WHERE { DataProperty(?subject) }";

            //// !!!!!!!!!!!!!!! GET ALL ObjectProperty from Ontology
//            String q = "SELECT ?subject\n" +
//                    "WHERE { ObjectProperty(?subject) }";

            //// !!!!!!!!!!!!!!! GET ALL ObjectProperty from Ontology
            String q = "SELECT ?subject\n" +
                    "WHERE { SubClassOf(?subject, <http://swrc.ontoware.org/ontology#Student>) }";


//            Query query = Query.create("SELECT ?a ?b\n" +
//                    "WHERE { DirectSubClassOf(?a, ?b) }");
            Query query = Query.create(q);

            System.out.println("Excecute the query:");
            System.out.println("-------------------------------------------------");

            // Execute the query and generate the result set
            QueryResult result = queryEng.execute(query);

            if(query.isAsk()) {
                System.out.print("Result: ");
                if(result.ask()) {
                    System.out.println("yes");
                }
                else {
                    System.out.println("no");
                }
            }
            else {
                if(!result.ask()) {
                    System.out.println("Query has no solution.\n");
                }
                else {
                    System.out.println("Results:");
                    System.out.print(result);
                    System.out.println("-------------------------------------------------");
                    System.out.println("Size of result set: " + result.size());
                }
            }

            System.out.println("-------------------------------------------------");
            System.out.println("Finished in " + (System.currentTimeMillis() - startTime) / 1000.0 + "s\n");

        }
        catch(UnsupportedOperationException exception) {
            System.out.println("Unsupported reasoner operation.");
        }
//        catch(OWLOntologyCreationException e) {
//            System.out.println("Could not load the wine ontology: " + e.getMessage());
//        }
        finally {
            if (reasoner != null) {
                reasoner.dispose();
            }
        }

        return "|";
    }




    public static String queryPublicationsbyYear() throws Exception {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
//        reasoner.precomputeInferences();

        OWLDataFactory fac = manager.getOWLDataFactory();

        try {
            QueryEngine queryEng = QueryEngine.create(manager, reasoner);

            String q = "SELECT ?subject\n" +
                    "WHERE { SubClassOf(?subject, <http://swrc.ontoware.org/ontology#Publication>) }";

            Query query = Query.create(q);
            // Execute the query and generate the result set
            QueryResult result = queryEng.execute(query);

            if(!result.ask()) {
                System.out.println("Query has no solution.\n");
            }
            else {
                System.out.println("Results:");
                System.out.print(result);
                System.out.println("-------------------------------------------------");
                String s = result.toString();
                System.out.println("Type: "+result.getClass().getName());
                System.out.println("Result: "+s);

                Pattern p = Pattern.compile("([A-Z])\\w+");
                Matcher m = p.matcher(s);

                ArrayList<String>  v = new ArrayList<String>();

                while ( m.find() ) {

                    System.out.println("------");
		            System.out.println(s.substring(m.start(), m.end()));
                    String word = s.substring(m.start(), m.end());
                    // skip Nothing class
                    if(word.equals("Nothing")) {
                        continue;
                    }
                    v.add(word);
                }

                System.out.println("-------------------------------------------------");
                int n = v.size();


                ArrayList<String> pub = new ArrayList<String>();

                for(int i = 0; i < n ; i++) {
                    System.out.println(v.get(i));

                    // till here, have all classes, now get individs and count them for each
                    String uri = "http://swrc.ontoware.org/ontology#"+v.get(i);

                    OWLClass individs = fac.getOWLClass(IRI.create(uri));

                    NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
                            individs, true);

                    Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

                    OWLDataProperty hasYear = fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#year"));

                    System.out.println(i+". Instances of class: "+v.get(i));
                    int j = 0;
                    for (OWLNamedIndividual ind : individuals) {
                        j++;
                        System.out.println(j+".    " + ind);

                        //======================= Print year of the individual
                        for (OWLLiteral l : reasoner.getDataPropertyValues(ind, hasYear)) {
                            if (l.getDatatype().isString()) {
                                System.out.println("Asserted value: " + l.getLiteral());

                            }
                        }
                    }

                    System.out.println("Total: " + j);

                    // Add Totals individs per Class
                    pub.add(j + "=" + v.get(i));

//                    if(i>2) {
//                        break;
//                    }

                }

                for(int k = 0; k < pub.size() ; k++) {
                    System.out.println(pub.get(k));
                }



                System.out.println("-------------------------------------------------");
                System.out.println("Size of result set: " + result.size());
            }

        }
        catch(UnsupportedOperationException exception) {
            System.out.println("Unsupported reasoner operation.");
        }
        finally {
            if (reasoner != null) {
                reasoner.dispose();
            }
        }

        return "|";
    }

/*
    // for Statistics get all individs for Publication and count by Year
    public static ArrayList countByYear() throws Exception {

        ArrayList<String> jsonObj = new ArrayList<String>();
        JSONArray listCls = new JSONArray();
        JSONArray listInd = new JSONArray();

        String uri = "";
        if(classItem == null || classItem == "") {
            uri = "http://www.w3.org/2002/07/owl#Thing";
        } else {
            uri = "http://swrc.ontoware.org/ontology#"+classItem;
        }

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        OWLOntology ont = loadOntologyFromFile(manager);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
        reasoner.precomputeInferences();

        OWLDataFactory fac = manager.getOWLDataFactory();

        OWLClass individs = fac.getOWLClass(IRI.create(uri));

        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
                individs, true);

        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        OWLDataProperty hasName = fac.getOWLDataProperty(IRI.create("http://swrc.ontoware.org/ontology#name"));

        System.out.println("Instances of class: ");
        int i = 0;
        for (OWLNamedIndividual ind : individuals) {
            i++;
            System.out.println(i+".    " + ind);
            if(i>20) {
                break;
            }

            //======================= Print name of the individual
            for (OWLLiteral l : reasoner.getDataPropertyValues(ind, hasName)) {
                if (l.getDatatype().isString()) {
                    System.out.println("Asserted value: " + l.getLiteral());
//                    jsonObj.add("\""+l.getLiteral()+"\"");
                    jsonObj.add("{\"id\":\""+ind.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", "")+"\", \"name\":\""+l.getLiteral()+"\"}");
//                    {"firstName":"John", "lastName":"Doe"},
                }
            }
            //            listInd.put(ind);
//            jsonObj.add("\""+ind.toString().replace("<http://www.aifb.uni-karlsruhe.de/", "").replace(">", "")+"\"");
        }

        //        jsonObj += ", \"individuals\": " + (listInd.toString()) + " } ";

//        System.out.println("Array List: "+jsonObj);

        return jsonObj;
    }
*/
}
