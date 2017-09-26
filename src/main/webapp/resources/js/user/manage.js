$(document).ready(function () {
    $(".sidebar-menu > .active").removeClass("active")
    $(".menu-user-manage").addClass("active");
});

$(document).ready(function () {
    var table = $(".table").DataTable({
        paging: false,
        info: false
    });

    function fillTable(offset, limit) {
        $.getJSON("manage/users", { offset: offset, limit: limit }, function (data) {
            table.clear();

            $.each(data, function (index, user) {
                table.row.add([
                    "<span class='table-user-name' data-photo='" + user.photo + "'>" + user.name + "</span>",
                    user.email,
                    $(new Date(user.registerDate)).dateFormat("yyyy-MM-dd hh:mm:ss"),
                    "<i class='fa fa-user-circle-o " + user.userRole.toLowerCase().replace("_", "-") + "' data-role='" + user.userRole + "'></i>"
                ]);
            });

            table.draw(false);

            if (typeof $.table_extra != "undefined") {
                $.table_extra();
            }
        })
    }

    $.getJSON("manage/userCount", function (data) {
        var offset = 0, limit = 25, user_count = data;

        $("#user_count").text(user_count);
        $("#start_user_index").text(offset + 1);
        $("#end_user_index").text(offset + limit < user_count ? offset + limit : user_count);

        for (var i = 1, length = Math.ceil(user_count / limit); i <= length; i++) {
            $("#table_next").before("<li class='paginate_button index'><a class='index' href='#' tabindex='0'>" + i + "</a></li>");
        }

        $("#pagination a").click(function (event) {
            event.preventDefault();
        });

        $("#table_previous").click(function (event) {
            if ($(this).hasClass("disabled")) {
                return;
            }

            $("#pagination > li.index.active").prev().children("a").click();
        });

        $("#table_next").click(function (event) {
            if ($(this).hasClass("disabled")) {
                return;
            }

            $("#pagination > li.index.active").next().children("a").click();
        });

        $("#pagination").on("click", "a.index", function (event) {
            $("#pagination > li.index").removeClass("active");

            var $parent = $(this).parent().addClass("active");

            $("#table_previous").removeClass("disabled");
            $("#table_next").removeClass("disabled");

            if ($parent.prev().hasClass("previous")) {
                $("#table_previous").addClass("disabled");
            }

            if ($parent.next().hasClass("next")) {
                $("#table_next").addClass("disabled");
            }

            var index = parseInt($(this).text());
            offset = (index - 1) * limit;
            $("#start_user_index").text(offset + 1);
            $("#end_user_index").text(offset + limit < user_count ? offset + limit : user_count);

            fillTable(offset, limit);
        });

        try {
            var page_index = parseInt($.UrlUtils.getParament("page"));
            if (page_index < 1 || page_index > Math.ceil(user_count / limit)) {
                throw "out of range";
            }

            $("#pagination li.index:nth-child(" + (page_index + 1) + ")").children("a").click();
        } catch (error) {
            $("#pagination li.index:nth-child(2)").children("a").click();
        }
    });
});