
function getIndividuals(element,value) {

    $('a.currentCls').removeClass('currentCls');
    element.parent( "li" ).addClass('selected');
    element.addClass("currentCls");

    $('.unavailable').show();
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

        $('.unavailable').hide();
        $('#loaderInds').hide();

       // $(".list-unstyled").html(results);

    }).fail(function( jqXHR, textStatus ) {
        alert( "Request failed: " + textStatus );
        $('.unavailable').hide();
        $('#loaderInds').hide();
    });
}