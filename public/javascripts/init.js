function getClasses(element,value) {

    $('#loader').show();

    $('a.currentCls').removeClass('currentCls');
    element.parent( "li" ).addClass('selected');
    element.addClass("currentCls");

    // check if subclasses have been found already
    if(element.parent( "li" ).find('ul.subclass').length != 0) {
        $('#loader').hide();
        // go find again individuals
        getIndividuals(value);

    } else {

        $.ajax({
            type: "GET",
            url: "/classes/cls",
            //dataType: "json",
            data: { q: value }
        }).done(function( results ) {

            var obj = $.parseJSON(results);

            //console.log(obj.classes);

            var items = [];
    //        items.push("<ul class='list-unstyled'>");
            $.each( obj, function( key, val ) {

//                console.log(val);
                if(val.length > 0 && $.inArray( "owl:Nothing", val )==-1) {
                    $.each( val, function( key2, val2 ) {
                        items.push( "<li class='lst' id='cls-" + key2 + "'><a onclick='getClasses($(this),\"" + val2 + "\")' class='cls'>" + val2 + "</a></li>" );
                    });
                } else {
    //                items.push("<li>No classes found</li>");
                }

            });
    //        items.push("</ul>");

    //        $( "<div/>", {
    //            "class": "col-md-2",
    //            html: items.join( "" )
    //        }).appendTo( ".subclasses" );
            if(items.length !== 0) {
                $( "<ul/>", {
                    "class": "list-unstyled subclass",
                    html: items.join( "" )
                }).appendTo( ".selected" );
            }

            $('#loader').hide();

            $("li.selected" ).removeClass('selected');

            getIndividuals(value);

           // $(".list-unstyled").html(results);

        }).fail(function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }

}


function getIndividuals(value) {

    $('#loaderInds').show();
    $(".individuals").html('');

    $.ajax({
        type: "GET",
        url: "/individuals",
        data: { q: value }
    }).done(function( results ) {

        var obj = $.parseJSON(results);

//        console.log(obj.individuals);

        var items = [];
//        items.push("<ul class='list-unstyled'>");
        $.each( obj, function( key, val ) {

            if(val.length > 0 && $.inArray( "owl:Nothing", val )==-1) {
                $.each( val, function( key2, val2 ) {
//            console.log($.type(val2));
//            console.log(val2);
                    items.push( "<li id='indiv-" + key2 + "'><a href='/individ?q=" + val2.id + "'>" + val2.name + "</a></li>" );
                    if(key2>=20) {
                        return false;
                    }
                });
            } else {
                items.push("<li>No individuals found</li>");
            }

        });
//        items.push("</ul>");

        $( "<ul/>", {
            "class": "list-unstyled",
            html: items.join( "" )
        }).appendTo( ".individuals" );

        $('#loaderInds').hide();

       // $(".list-unstyled").html(results);

    }).fail(function( jqXHR, textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}