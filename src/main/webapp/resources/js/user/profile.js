$(document).ready(function () {
    var location_server_url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js";

    $.getScript(location_server_url, function () {
        $("#location").text(remote_ip_info.country + ", " + remote_ip_info.province + ", " + remote_ip_info.city);
    });

    $(".select2").select2();

    $("#inputBirthday").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true
    });

    $("[data-mask]").inputmask();

    $("input").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%'
    });

    $("form").validate({
        errorPlacement: function(error, element) {
            if (element.hasClass("agree")) {
                error.appendTo(element.parent().parent().parent()).addClass("text-red");
            } else {
                error.appendTo(element.parent()).addClass("text-red");
            }
        },
        rules: {
            agree: "required"
        },
        messages: {
            agree: "Must agree the terms"
        }
    });
});