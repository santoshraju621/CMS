//PAGE LOAD
$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: contextRoot + "/page/all",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (pages, status) {
        $.each(pages, function (index, page) {
            var dropdown = buildDropDown(page);
            $('#navList').append(dropdown);
        });
    });

});

function buildDropDown(page) {
    return "<li>  \n\
                <a href='" +contextRoot +"/page/show/"+page.id +"'>" +page.title +"</a>  \n\
                </li>";
}