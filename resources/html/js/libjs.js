window.onresize = function(){
    // reload the page when window resized
    location.reload();
}

window.onload = function(){

    // set content padding-top
    setContentPadding();

    // bind click animation on images
    bindClickImage();

    // randomize position of notes in the margin
    randomizePositionNotes();

    // draw svg lines between notes and paragraph
    drawLines();


};



function setContentPadding(){
    var tophheight = $(".top").height();
    $(".content").css("padding-top",tophheight + 80);
};

function getElementLeft(elm){
    var x = 0;
    x = elm.offsetLeft;
    elm = elm.offsetParent;

    while(elm != null){
        x = parseInt(x) + parseInt(elm.offsetLeft);
        elm = elm.offsetParent;
    }
    return x;
};

function getElementTop(elm){
    var y = 0;
    console.log("e: " + elm);
    y = elm.offsetTop;
    elm = elm.offsetParent;

    while(elm != null){
        y = parseInt(y) + parseInt(elm.offsetTop);
        elm = elm.offsetParent;
    }
    return y;
};

function bindClickImage(){
    $( "img" ).bind( "click", function() {
        h = 250;
        w = 300;
        t = getElementTop(this) - h + this.offsetHeight - 6;
        l = getElementLeft(this);

        $("#imgpopup").attr("src",$(this).attr("src"));
        $("#popup").css({"visibility" : "visible", "width" : w + "px", "height" : h + "px", "top" : t +"px", "left" : l + "px","z-index" : "50"});
        $(this).parent().css({"width" : "30px","height" : "20px" ,"border" : "4px solid rgb(0,0,255)","visibility" : "hidden"});

    });

    $("#imgpopup").bind( "click", function(){
        $("#imgpopup").attr("src","");
        $("#popup").css({"visibility" : "hide", "width" : 0 + "px", "height" : 0 + "px", "top" : 0 +"px", "left" : 0 + "px"});
         $(".border").css({"width" : "30px","height" : "20px" ,"border" : "4px solid rgb(0,0,255)","visibility" : "visible"});
    });
};

function randomizePositionNotes(){
    var notes = $(".marge .inner");

    for(var i=0; i < notes.length; i++){
        var n = $(notes[i]);
        // linked element
        var elem = n.parent().next();
        var elem_h = elem.height();

        // random height of the note (between -45 and elem.heigth+45)
        var h = Math.floor((Math.random() * (elem_h+45)) - 45);
        n.css("top",h + "px");
    }
};

function drawLines(){
    var notes = $(".marge .inner p");
    var body = $("body");


    // create new svg element
    var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute('width', body.width());
    svg.setAttribute('height', body.height());
    
    // create lines
    for(var i=0; i < notes.length; i++){
        var n = $(notes[i]);
        var n_top = getElementTop(n[0]) + 5;
        var n_left = getElementLeft(n[0]) - 25;

        // linked element
        var elem = n.parent().parent().next();
        var elem_top = getElementTop(elem[0]) + 10;
        var elem_left = getElementLeft(elem[0]) + elem.width() - 20;

        // create path svg element
        var line = document.createElementNS("http://www.w3.org/2000/svg", "path");
        var d = "M" + elem_left + " " + elem_top + " L" + n_left + " " + n_top;
        line.setAttributeNS(null,"d",d);
        line.setAttributeNS(null,"class","line");
        svg.appendChild(line);

    }

    // append svg element to the body
    $(".lines").append(svg);
}