<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" rel="stylesheet" />
        <style>
            .modal-open {
                overflow-y: scroll;
            }
        </style>

        <title>Admin</title>

    </head>
    <body>

        <jsp:include page="/jsp/navbar.jsp" />

        <!--buttons-->
        <div class="container">

            <div class="row">
                <div class="col-md-4">

                </div>

                <div class="col-md-4 text-center">

                    <a class="btn btn-default btn-lg" style="width: 150px; margin: 5px;" data-toggle="modal" data-target="#postModal">Posts</a>
                    <br />
                    <a class="btn btn-default btn-lg" style="width: 150px; margin: 5px;" data-toggle="modal" data-target="#pageModal">Pages</a>
                    <br />
                    <a class="btn btn-default btn-lg" style="width: 150px; margin: 5px;" data-toggle="modal" data-target="#categoryModal">Categories</a>
                    <br />
                    <a class="btn btn-default btn-lg" style="width: 150px; margin: 5px;" data-toggle="modal" data-target="#userModal">Users</a>
                    <br />
                    <a class="btn btn-danger btn-lg" style="width: 150px; margin: 5px;" href="${pageContext.request.contextPath}/logout">Logout</a>

                </div>

                <div class="col-md-4">

                </div>
            </div>
        </div>
        <!--end buttons-->       

        <!--post-->
        <div id="postModal" class="modal fade" role="dialog" style="overflow-y: scroll;">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <div id="modalPost" class="modal-body" style='display: block;'>

                        <table class="table">
                            <tr>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioPost" id="radioPostList" checked>List Posts</th>
                                <th><input onclick="javascript:radioCheck(); tinyMCE.get('postAddContent').focus();" type="radio" name="radioPost" id="radioPostAdd">Add Post</th>
                            </tr>
                        </table>

                        <div id="modalPostList" style="display: block;">
                            <table id="postList" class="table table-bordered">
                            </table>
                        </div>

                        <div id="modalPostAdd" style="display: none;">
                            <table class="table table-bordered">

                                <form class="form-horizontal" id="create-post-form" action="${pageContext.request.contextPath}/post/" method="POST">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="title">Title: </label>  
                                        <div class="col-md-10">
                                            <div id="add-errors-title"></div>
                                            <input id="postAddTitle" name="title" type="text" placeholder="Enter Title" class="form-control input-md">
                                            <span class="help-block">This will be the headline of your post</span>  
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="content">Content</label>
                                        <div class="col-md-10">
                                            <div id="add-errors-content"></div>
                                            <textarea class="form-control tmce" id="postAddContent" name="content" rows="10" cols="50"></textarea>
                                            <span class="help-block">Enter your post's content here.  Use the tools above for formatting, adding images, and inserting links to other pages.</span> 
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="categoryId">Category</label>
                                        <div class="col-md-10">
                                            <div id="add-errors-categoryId"></div>
                                            <select id="postAddCategoryId" name="categoryId" class="form-control">
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="tags">Tags</label>
                                        <div class="col-md-10">
                                            <select id="postAddTags" name="tags" class="form-control s2" multiple="multiple">
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="published">Publish Date: </label>  
                                        <div class="col-md-10">
                                            <div id="add-errors-published"></div>
                                            <input id="postAddPublished" name="published" type="date" placeholder="MM/DD/YYYY" class="form-control input-md">
                                            <span class="help-block">This will be when your post is viewable by the public</span>  
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="expiration">Expire Date: </label>  
                                        <div class="col-md-10">
                                            <div id="add-errors-expiration"></div>
                                            <input id="postAddExpiration" name="expiration" type="date" placeholder="" class="form-control input-md">
                                            <span class="help-block">This will be when your post will no longer be viewable</span>  
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="submit"></label>
                                        <div class="col-md-4">
                                            <button id="postAddSubmit" name="submit" class="btn btn-default">Submit</button>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-4" id="add-errors"></div>
                                </form>
                            </table>

                        </div>
                    </div>

                    <div  id="modalPostEdit" class="modal-body" style="display: none;">
                        <div>
                            <table class="table table-bordered">

                                <form class="form-horizontal" id="edit-post-form" action="${pageContext.request.contextPath}/post/" method="PUT">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="title">Title: </label>  
                                        <div class="col-md-10">
                                            <div id="edit-errors-title"></div>
                                            <input id="postEditTitle" name="title" type="text" placeholder="Enter Title" class="form-control input-md">
                                            <span class="help-block">This will be the headline of your post</span>  
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="content">Content</label>
                                        <div class="col-md-10">
                                            <div id="edit-errors-content"></div>
                                            <textarea class="form-control tmce" id="postEditContent" name="content" rows="10" cols="50"></textarea>
                                            <span class="help-block">Enter your post's content here.  Use the tools above for formatting, adding images, and inserting links to other pages.</span> 
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="categoryId">Category</label>
                                        <div class="col-md-10">
                                            <div id="edit-errors-categoryId"></div>
                                            <select id="postEditCategoryId" name="categoryId" class="form-control">
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="tags">Tags</label>
                                        <div class="col-md-10">
                                            <select id="postEditTags" name="tags" class="form-control s2" multiple="multiple">
                                            </select>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="published">Publish Date: </label>  
                                        <div class="col-md-10">
                                            <div id="edit-errors-published"></div>
                                            <input id="postEditPublished" name="published" type="date" placeholder="MM/DD/YYYY" class="form-control input-md">
                                            <span class="help-block">This will be when your post is viewable by the public</span>  
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="expiration">Expire Date: </label>  
                                        <div class="col-md-10">
                                            <div id="edit-errors-expiration"></div>
                                            <input id="postEditExpiration" name="expiration" type="date" placeholder="" class="form-control input-md">
                                            <span class="help-block">This will be when your post will no longer be viewable</span>  
                                        </div>
                                    </div>

                                    <input type='hidden' id='postEditId' />

                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="submit"></label>
                                        <div class="col-md-4">
                                            <button id="postEditSubmit" name="submit" class="btn btn-default">Submit</button>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-4" id="add-errors"></div>
                                </form>
                            </table>

                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--end post-->

        <!--page-->
        <div id="pageModal" class="modal fade" role="dialog"  style="overflow-y: scroll;">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <div class="modal-body" id='modalPage' style='display: block;'>

                        <table class="table">
                            <tr>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioPage" id="radioPageList" checked>List Pages</th>
                                <th><input onclick="javascript:radioCheck(); tinyMCE.get('pageAddContent').focus();" type="radio" name="radioPage" id="radioPageAdd">Add Page</th>
                            </tr>
                        </table>

                        <div id="modalPageList" style="display: block;">
                            <table id="pageList" class="table table-bordered">
                            </table>
                        </div>

                        <div id="modalPageAdd" style="display: none;">
                            <table class="table table-bordered">
                                <form id="create-page-form" class="form-horizontal" action="${pageContext.request.contextPath}/page" method="post">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="title">Title: </label>  
                                        <div class="col-md-10">
                                            <div id="modalpage-title-adderror"class="text-warning"></div>
                                            <input id="pageAddTitle" name="modalpage-title" type="text" placeholder="Enter Title" class="form-control input-md">
                                        </div>
                                    </div>

                                    <!-- Textarea -->
                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="content">Content:</label>
                                        <div class="col-md-10">
                                            <div id="modalpage-content-adderror"class="text-warning"></div>
                                            <textarea class="form-control tmce" id="pageAddContent" name="modalpage-content" rows="150" cols="300" placeholder="Enter content"></textarea>
                                        </div>
                                    </div>


                                    <input type="submit" id="add-page-button" class="btn btn-default">
                                </form>
                            </table>

                        </div>
                    </div>

                    <div class="modal-body" id="modalPageEdit" style="display: none;">

                        <div>
                            <table class="table table-bordered">
                                <form id="edit-page-form" class="form-horizontal" action="${pageContext.request.contextPath}/page" method="put">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="title">Title: </label>  
                                        <div class="col-md-10">
                                            <input id="pageEditTitle" name="title" type="text" placeholder="Enter Title" class="form-control input-md">
                                        </div>
                                    </div>

                                    <!-- Textarea -->
                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="content">Content:</label>
                                        <div class="col-md-10">
                                            <textarea class="form-control tmce" id="pageEditContent" name="content" rows="150" cols="300" placeholder="Enter content"></textarea>
                                        </div>
                                    </div>

                                    <input type='hidden' id='pageEditId' />

                                    <input type="submit" id="edit-page-button" class="btn btn-default">
                                </form>
                            </table>

                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--end page-->

        <!--category-->
        <div id="categoryModal" class="modal fade" role="dialog"  style="overflow-y: scroll;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <div id="modalCategory" class="modal-body" style="display: block;">

                        <table class="table">
                            <tr>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioCategory" id="radioCategoryList" checked>List Categories</th>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioCategory" id="radioCategoryAdd">Add Category</th>
                            </tr>
                        </table>

                        <div id="modalCategoryList" style="display: block;">
                            <table id="categoryList" class="table table-bordered">
                            </table>
                        </div>

                        <div id="modalCategoryAdd" style="display: none;">
                            <table class="table table-bordered">
                                <form class="form-horizontal" id="create-category-form" action="${pageContext.request.contextPath}/post/category" method="POST">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="categoryAddName">New Category</label>  
                                        <div class="col-md-10">
                                            <input id="categoryAddName" name="categoryAddName" type="text" placeholder="Category" class="form-control input-md">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="categoryAddName"></label> 
                                        <div class="col-md-10">
                                            <button id="addCategoryButton" name="addCategoryButton" class="btn btn-primary">Add Category</button>
                                        </div>
                                    </div>

                                </form>
                            </table>

                        </div>
                    </div>

                    <div id="modalCategoryEdit" class="modal-body" style="display: none;">

                        <div>
                            <table class="table table-bordered">
                                <form class="form-horizontal" id="edit-category-form" action="${pageContext.request.contextPath}/post/category" method="PUT">

                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="categoryEditName">Category Name</label>  
                                        <div class="col-md-4">
                                            <input id="categoryEditName" name="categoryEditName" type="text" placeholder="Category" class="form-control input-md">
                                        </div>
                                    </div>

                                    <input type='hidden' id='categoryEditId' />

                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="categoryEditName"></label> 
                                        <div class="col-md-4">
                                            <button id="editCategoryButton" name="editCategoryButton" class="btn btn-primary">Edit Category</button>
                                        </div>
                                    </div>

                                </form>
                            </table>

                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--end category-->

        <!--user-->
        <div id="userModal" class="modal fade" role="dialog"  style="overflow-y: scroll;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <div id="modalUser" class="modal-body">

                        <table class="table">
                            <tr>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioUser" id="radioUserList" checked>List Users</th>
                                <th><input onclick="javascript:radioCheck();" type="radio" name="radioUser" id="radioUserAdd">Add User</th>
                            </tr>
                        </table>

                        <div id="modalUserList" style="display: block;">
                            <table id="userList" class="table table-bordered">
                            </table>
                        </div>

                        <div id="modalUserAdd" style="display: none;">
                            <table class="table table-bordered">
                                <form id="create-user-form" class="form-horizontal" action="${pageContext.request.contextPath}/user" method="post">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="username">Username: </label>  
                                        <div class="col-md-10">
                                            <input id="userAddUsername" name="username" type="text" class="form-control input-md">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="password">Password:</label>
                                        <div class="col-md-10">
                                            <input id="userAddPassword" type="password" name="password" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label" for="password">Check if Admin:</label>
                                        <div class="col-md-9">
                                            <input id="userAddAdmin" type="checkbox" name="admin" value="true" />
                                        </div>
                                    </div>

                                    <input type='hidden' id='userEditId' />

                                    <input type="submit" id="add-user-button" class="btn btn-default">
                                </form>
                            </table>

                        </div>
                    </div>

                    <div class="modal-body" id="modalUserEdit" style="display: block;">

                        <div>
                            <table class="table table-bordered">
                                <form id="edit-user-form" class="form-horizontal" action="${pageContext.request.contextPath}/user" method="put">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="username">Username: </label>  
                                        <div class="col-md-10">
                                            <input id="userEditUsername" name="username" type="text" class="form-control input-md">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="password">Password:</label>
                                        <div class="col-md-10">
                                            <input id="userEditPassword" type="password" name="password" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label" for="password">Check if Admin:</label>
                                        <div class="col-md-9">
                                            <input id="userEditAdmin" type="checkbox" name="admin" value="true" />
                                        </div>
                                    </div>

                                    <input type="submit" id="edit-user-button" class="btn btn-default">
                                </form>
                            </table>

                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--end user-->
        <!-- upload modal -->

        <jsp:include page="/jsp/uploadModal.jsp" />

        <!--end upload modal -->


        <script type="text/javascript">
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script type="text/javascript">
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
        </script>


        <script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/tinymce/tinymce.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-filestyle.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
        <script>
            tinymce.init({
                selector: '.tmce',
                relative_urls: false,
                height: 300,
                width: 500,
                toolbar: 'imageButton | undo redo | styleselect | bold italic | strikethrough | alignleft | aligncenter | alignright | indent | outdent | bullist | numlist',
                setup: function (editor) {
                    editor.addButton('imageButton', {
                        text: 'Add an Image',
                        icon: false,
                        onclick: function () {
                            $(uploadModal).modal('show');
                        }
                    });
                },
                content_css: [
                    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
                    '//www.tinymce.com/css/codepen.min.css'
                ]
            });
        </script>
        <script>
            $(document).ready(function () {
                $(".s2").select2({
                    tags: true
                });
            });
        </script>
        <script src="${pageContext.request.contextPath}/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/js/app-upload.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    </body>
</html>
