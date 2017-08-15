$(document).ready(function () {
    $("input").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%'
    });

    $.extend($.validator.messages, {
        required: "This field required",
        email: "Invalid email address",
        equalTo: "Password must be the same"
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