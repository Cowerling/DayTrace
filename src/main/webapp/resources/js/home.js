$(document).ready(function () {
    var $content_wrapper = $(".content-wrapper");

    $(".map").css("height", $content_wrapper.height() + "px");

    $(window).resize(function (event) {
        $(".map").css("height", $content_wrapper.height() + "px");
    });
    $(".sidebar-menu > .active").removeClass("active")
    $(".menu-map").addClass("active");
});

$(document).ready(function () {
    var map = new ol.Map({
        target: "map",
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([37.41, 8.82]),
            zoom: 4
        })
    });
});