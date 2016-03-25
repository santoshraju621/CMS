//PAGE LOAD
$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: contextRoot + "/post/select",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (posts, status) {
        $('#postAddCategoryId').append('<option></option>');
        $('#postAddTags').append('<option></option>');
        $.each(posts.tags, function (index, tag) {
            $('#postAddTags').append('<option value="' + tag.name + '">' + tag.name + '</option>');
        });
        $.each(posts.category, function (index, category) {
            $('#postAddCategoryId').append('<option value="' + category.id + '">' + category.name + '</option>');
        });

        $('#postEditCategoryId').append('<option></option>');
        $('#postEditTags').append('<option></option>');
        $.each(posts.tags, function (index, tag) {
            $('#postEditTags').append('<option value="' + tag.name + '">' + tag.name + '</option>');
        });
        $.each(posts.category, function (index, category) {
            $('#postEditCategoryId').append('<option value="' + category.id + '">' + category.name + '</option>');
        });

    });

});

//POSTS
$('#postModal').on('show.bs.modal', function (e) {

    var modal = $(this);
    $('#postList').empty();
    $('#modalPost').show();
    $('#modalPostEdit').hide();

    $.ajax({
        type: 'GET',
        url: contextRoot + "/post/list",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (posts, status) {

        $.each(posts, function (index, post) {
            var postRow = buildPostRow(post);
            $('#postList').append(postRow);
        });

    });

});

$(document).on('submit', '#create-post-form', function (e) {
    e.preventDefault();

    var postData = JSON.stringify({
        title: $('#postAddTitle').val(),
        content: $('#postAddContent').val(),
        categoryId: $('#postAddCategoryId').val(),
        tags: $('#postAddTags').val(),
        published: $('#postAddPublished').val(),
        expiration: $('#postAddExpiration').val()
    });

    $.ajax({
        type: 'POST',
        url: contextRoot + '/post',
        data: postData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (post, status) {

        $('#postAddTitle').val('');
        tinymce.get('postAddContent').setContent('');
        $('#postAddCategoryId').val('');
        $('#postAddTags').html('').select2({data: {id: null, text: null}});
        $('#postAddPublished').val('');
        $('#postAddExpiration').val('');

        $('#radioPostList').prop("checked", true);
        var postRow = buildPostRow(post);
        $('#postList').append(postRow);
        radioCheck();

    }).error(function (data, status) {

        var errors = data.responseJSON.errors;

        $.each(errors, function (index, validationError) {
            $('#add-errors-' + validationError.fieldName).append(validationError.message);
        });

    });

});

$(document).on('click', '.edit-post-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('post-id');

    $('#edit-errors').empty();

    $.ajax({
        type: 'GET',
        url: contextRoot + '/post/' + id,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (post, status) {

        $('#modalPost').hide();
        $('#modalPostEdit').show();

        $('#postEditTitle').val(post.title);
        tinymce.get('postEditContent').setContent(post.content);
        tinyMCE.get('postEditContent').focus();

        $('#postEditCategoryId').val(post.category.id);

        var tagArray = []; 

        $.each(post.tags, function (index, tag) {
            tagArray.push(tag.name);
        });

        $('#postEditTags').val(tagArray).trigger("change");

        $('#postEditPublished').val(post.publishedIso);
        $('#postEditExpiration').val(post.expirationIso);

        $('#postEditId').val(post.id);

    }).error(function (data, status) {

    });
});

$(document).on('submit', '#edit-post-form', function (e) {
    e.preventDefault();

    var id = $('#postEditId').val();

    var postData = JSON.stringify({
        title: $('#postEditTitle').val(),
        content: $('#postEditContent').val(),
        categoryId: $('#postEditCategoryId').val(),
        tags: $('#postEditTags').val(),
        published: $('#postEditPublished').val(),
        expiration: $('#postEditExpiration').val()
    });

    $.ajax({
        type: 'PUT',
        url: contextRoot + '/post/' + id,
        data: postData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (post, status) {

        $('#postAddTitle').val('');
        tinymce.get('postAddContent').setContent('');
        
        $('#postAddCategoryId').val('');
        $('#postAddTags').html('').select2({data: {id: null, text: null}});
        $('#postAddPublished').val('');
        $('#postAddExpiration').val('');

        $('#radioPostList').prop("checked", true);
        var postRow = buildPostRow(post);
        $('#post-row-' + post.id).replaceWith($(postRow));

        $('#modalPost').show();
        $('#modalPostEdit').hide();

    }).error(function (data, status) {

    });

});

$(document).on('click', '.delete-post-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('post-id');

    $.ajax({
        type: 'DELETE',
        url: contextRoot + '/post/' + id
    }).success(function (data, status) {

        $('#post-row-' + id).remove();

    });
});

//PAGES
$('#pageModal').on('show.bs.modal', function (e) {

    var modal = $(this);
    $('#pageList').empty();

    $('#modalPage').show();
    $('#modalPageEdit').hide();

    $.ajax({
        type: 'GET',
        url: contextRoot + "/page/all",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (pages, status) {

        $.each(pages, function (index, page) {
            var pageRow = buildPageRow(page);
            $('#pageList').append(pageRow);
        });

    });

});

$(document).on('submit', '#create-page-form', function (e) {
    e.preventDefault();

    var pageData = JSON.stringify({
        title: $('#pageAddTitle').val(),
        content: $('#pageAddContent').val()
    });

    $.ajax({
        type: 'POST',
        url: contextRoot + "/page",
        data: pageData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (page, status) {

        $('#pageAddTitle').val('');
        tinymce.get('pageAddContent').setContent('');
        $('#radioPageList').prop("checked", true);
        var pageRow = buildPageRow(page);
        $('#pageList').append(pageRow);
        radioCheck();

    }).error(function (data, status) {

        var errors = data.responseJSON.errors;
        $.each(errors, function (index, validationError) {
            $('#' + validationError.fieldName + '-adderror').replaceWith(validationError.message);

        });

    });

});

$(document).on('click', '.edit-page-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('page-id');

    $.ajax({
        type: 'GET',
        url: contextRoot + '/page/' + id,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (page, status) {

        $('#modalPage').hide();
        $('#modalPageEdit').show();

        $('#pageEditId').val(page.id);
        $('#pageEditTitle').val(page.title);
        tinymce.get('pageEditContent').setContent(page.content);
        tinyMCE.get('pageEditContent').focus();

    }).error(function (data, status) {

    });
});

$(document).on('submit', '#edit-page-form', function (e) {
    e.preventDefault();

    var id = $('#pageEditId').val();

    var pageData = JSON.stringify({
        title: $('#pageEditTitle').val(),
        content: $('#pageEditContent').val()
    });

    $.ajax({
        type: 'PUT',
        url: contextRoot + "/page/" + id,
        data: pageData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (page, status) {

        $('#pageEditTitle').val('');
        tinymce.get('pageEditContent').setContent('');
        var pageRow = buildPageRow(page);
        $('#page-row-' + page.id).replaceWith($(pageRow));

        $('#modalPage').show();
        $('#modalPageEdit').hide();


    }).error(function (data, status) {

        var errors = data.responseJSON.errors;
        $.each(errors, function (index, validationError) {
            $('#' + validationError.fieldName + '-adderror').replaceWith(validationError.message);

        });

    });

});

$(document).on('click', '.delete-page-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('page-id');

    $.ajax({
        type: 'DELETE',
        url: contextRoot + '/page/' + id
    }).success(function (data, status) {

        $('#page-row-' + id).remove();

    });
});

//CATEGORIES
$('#categoryModal').on('show.bs.modal', function (e) {

    var modal = $(this);
    $('#categoryList').empty();

    $('#modalCategory').show();
    $('#modalCategoryEdit').hide();

    $.ajax({
        type: 'GET',
        url: contextRoot + "/post/category/all",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (categories, status) {

        $.each(categories, function (index, category) {
            var categoryRow = buildCategoryRow(category);
            $('#categoryList').append(categoryRow);
        });

    });

});

$(document).on('submit', '#create-category-form', function (e) {
    e.preventDefault();

    var categoryData = JSON.stringify({
        name: $('#categoryAddName').val()
    });

    $.ajax({
        type: 'POST',
        url: contextRoot + '/post/category',
        data: categoryData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (category, status) {

        $('#categoryAddName').val('');
        $('#radioCategoryList').prop("checked", true);
        var categoryRow = buildCategoryRow(category);
        $('#categoryList').append(categoryRow);
        radioCheck();

    }).error(function (data, status) {

        var errors = data.responseJSON.errors;

        $.each(errors, function (index, validationError) {
            $('#add-errors-' + validationError.fieldName).append(validationError.message);
        });

    });

});

$(document).on('click', '.edit-category-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('category-id');

    $.ajax({
        type: 'GET',
        url: contextRoot + '/post/category/' + id,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (category, status) {

        $('#modalCategory').hide();
        $('#modalCategoryEdit').show();

        $('#categoryEditId').val(category.id);
        $('#categoryEditName').val(category.name);

    }).error(function (data, status) {

    });
});

$(document).on('submit', '#edit-category-form', function (e) {
    e.preventDefault();
    
    var id = $('#categoryEditId').val();

    var categoryData = JSON.stringify({
        name: $('#categoryEditName').val()
    });

    $.ajax({
        type: 'PUT',
        url: contextRoot + '/post/category/' +id,
        data: categoryData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (category, status) {

        $('#categoryEditId').val('');
        $('#categoryEditName').val('');
        
        var categoryRow = buildCategoryRow(category);
        $('#category-row-' + category.id).replaceWith($(categoryRow));

        $('#modalCategory').show();
        $('#modalCategoryEdit').hide();

    }).error(function (data, status) {

        var errors = data.responseJSON.errors;

        $.each(errors, function (index, validationError) {
            $('#add-errors-' + validationError.fieldName).append(validationError.message);
        });

    });

});

$(document).on('click', '.delete-category-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('category-id');

    $.ajax({
        type: 'DELETE',
        url: contextRoot + '/post/category/' + id
    }).success(function (data, status) {

        $('#category-row-' +id).remove();

    });
});

//USERS
$('#userModal').on('show.bs.modal', function (e) {

    var modal = $(this);
    $('#userList').empty();

    $('#modalUser').show();
    $('#modalUserEdit').hide();

    $.ajax({
        type: 'GET',
        url: contextRoot + "/user/all",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        }
    }).success(function (users, status) {

        $.each(users, function (index, user) {
            var userRow = buildUserRow(user);
            $('#userList').append(userRow);
        });

    });

});

$(document).on('submit', '#create-user-form', function (e) {
    e.preventDefault();

    var userData = JSON.stringify({
        username: $('#userAddUsername').val(),
        password: $('#userAddPassword').val()
    });

    $.ajax({
        type: 'POST',
        url: contextRoot + "/user",
        data: userData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (user, status) {

        $('#userAddUsername').val('');
        $('userAddPassword').val('');
        $('#radioUserList').prop("checked", true);
        var userRow = buildUserRow(user);
        $('#userList').append(userRow);
        radioCheck();

    }).error(function (data, status) {

        var errors = data.responseJSON.errors;
        $.each(errors, function (index, validationError) {
            $('#' + validationError.fieldName + '-adderror').replaceWith(validationError.message);

        });

    });

});

$(document).on('click', '.edit-user-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('user-id');

    $.ajax({
        type: 'GET',
        url: contextRoot + '/user/' + id,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (user, status) {

        $('#modalUser').hide();
        $('#modalUserEdit').show();

        $('#userEditId').val(user.id);
        $('#userEditUsername').val(user.username);
        $('#userEditPassword').val('');

    }).error(function (data, status) {

    });
});

$(document).on('submit', '#edit-user-form', function (e) {
    e.preventDefault();

    var id = $('#userEditId').val();

    var userData = JSON.stringify({
        username: $('#userEditUsername').val(),
        password: $('#userEditPassword').val()
    });

    $.ajax({
        type: 'PUT',
        url: contextRoot + "/user/" + id,
        data: userData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-type", "application/json");
        }
    }).success(function (user, status) {

        $('#userEditUsername').val('');
        $('#userEditPassword').val('');
        var userRow = buildUserRow(user);
        $('#user-row-' + user.id).replaceWith($(userRow));

        $('#modalUser').show();
        $('#modalUserEdit').hide();


    }).error(function (data, status) {

//        var errors = data.responseJSON.errors;
//        $.each(errors, function (index, validationError) {
//            $('#' + validationError.fieldName + '-adderror').replaceWith(validationError.message);
//
//        });

    });

});

$(document).on('click', '.delete-user-link', function (e) {
    e.preventDefault();

    var id = $(e.target).data('user-id');

    $.ajax({
        type: 'DELETE',
        url: contextRoot + '/user/' + id
    }).success(function (data, status) {

        $('#user-row-' + id).remove();

    });
});


//MISC
function buildPostRow(post) {
    return "<tr id='post-row-" + post.id + "'>  \n\
                <td>" + post.title + "</td> \n\
                <td> <a data-post-id='" + post.id + "' class='edit-post-link'>Edit</a>  </td>   \n\
                <td> <a data-post-id='" + post.id + "' class='delete-post-link'>Delete</a>  </td>   \n\
                </tr>";
}

function buildPageRow(page) {
    return "<tr id='page-row-" + page.id + "'>  \n\
                <td>" + page.title + "</td> \n\
                <td> <a data-page-id='" + page.id + "' class='edit-page-link'>Edit</a>  </td>   \n\
                <td> <a data-page-id='" + page.id + "' class='delete-page-link'>Delete</a>  </td>   \n\
                </tr>";
}

function buildCategoryRow(category) {
    return "<tr id='category-row-" + category.id + "'>  \n\
                <td>" + category.name + "</td> \n\
                <td> <a data-category-id='" + category.id + "' class='edit-category-link'>Edit</a>  </td>   \n\
                <td> <a data-category-id='" + category.id + "' class='delete-category-link'>Delete</a>  </td>   \n\
                </tr>";
}

function buildUserRow(user) {
    return "<tr id='user-row-" + user.id + "'>  \n\
                <td>" + user.username + "</td> \n\
                <td> <a data-user-id='" + user.id + "' class='edit-user-link'>Edit</a>  </td>   \n\
                <td> <a data-user-id='" + user.id + "' class='delete-user-link'>Delete</a>  </td>   \n\
                </tr>";
}

function radioCheck() {
    if (document.getElementById('radioPostList').checked) {
        document.getElementById('modalPostList').style.display = 'block';
        document.getElementById('modalPostAdd').style.display = 'none';
    } else {
        document.getElementById('modalPostAdd').style.display = 'block';
        document.getElementById('modalPostList').style.display = 'none';
    }

    if (document.getElementById('radioPageList').checked) {
        document.getElementById('modalPageList').style.display = 'block';
        document.getElementById('modalPageAdd').style.display = 'none';
    } else {
        document.getElementById('modalPageAdd').style.display = 'block';
        document.getElementById('modalPageList').style.display = 'none';
    }

    if (document.getElementById('radioCategoryList').checked) {
        document.getElementById('modalCategoryList').style.display = 'block';
        document.getElementById('modalCategoryAdd').style.display = 'none';
    } else {
        document.getElementById('modalCategoryAdd').style.display = 'block';
        document.getElementById('modalCategoryList').style.display = 'none';
    }

    if (document.getElementById('radioUserList').checked) {
        document.getElementById('modalUserList').style.display = 'block';
        document.getElementById('modalUserAdd').style.display = 'none';
    } else {
        document.getElementById('modalUserAdd').style.display = 'block';
        document.getElementById('modalUserList').style.display = 'none';
    }
}
