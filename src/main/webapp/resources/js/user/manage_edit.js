(function ($) {
    $.table_extra = function () {
        if ($(".table").hasClass("edit")) {
            $(".table span.table-user-name")
                .wrap("<a class='user-edit' href='#' data-toggle='modal' data-target='#user_edit_modal'></a>")
                .prepend("<i class='fa fa-fw fa-gear text-danger'></i>");
        }

        $("a.user-edit").click(function (event) {
            event.preventDefault();

            var request_user_name = $(this).children().text();
            var photo = $(this).children().data("photo");

            $("#origin_name").val(request_user_name);
            $("#inputName").val(request_user_name);
            $("#inputRole").val($(this).parent().next().next().next().children().data("role")).trigger("change");
            $("#inputEmail").val($(this).parent().next().text());
            $("#editedUserPhoto").attr("src", "../resources/image/user/" + (photo != null ? photo : "sample-160x160.png"));
            $("#page").val($("#pagination > li.index.active").text());

            $.getJSON("manage/userProfileAndMedals", { requestUserName: request_user_name }, function (data) {
                $("#inputAlias").val(data.profile.alias);
                $("#inputGender").val(data.profile.userGender).trigger("change");
                $("#inputBirthday").datepicker("setDate" , data.profile.birthday != null ? $(new Date(data.profile.birthday)).dateFormat("yyyy-MM-dd") : null);
                $("#inputPhone").val(data.profile.phone);
                $("#inputBrief").text(data.profile.brief);

                $("#origin_medals").val(data.medals);
                $("#inputMedals").val(data.medals).trigger("change");
            });
        });
    }
})(jQuery);

$(document).ready(function () {
    $("#user_edit_modal").modal({
        backdrop: false,
        show: false
    });

    $(".select2.allow-null").select2({
        placeholder: "Select",
        allowClear: true
    });

    $("select.select2:not(.allow-null)").select2();

    $("#inputBirthday").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true
    });

    $("[data-mask]").inputmask();

    $("div.image-preview").imageSelect();

    $("#user_edit_form").validate({
        errorPlacement: function(error, element) {
            error.appendTo(element.parent()).addClass("text-red");
        }
    });

    $("#user_edit_submit").click(function (event) {
        $("#user_edit_form").submit();
    });
});