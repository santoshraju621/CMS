<div id="uploadModal" class="modal fade" role="dialog" data-backdrop="false" style="z-index: 10000; overflow-y: scroll;">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Upload Files</h4>
            </div>
            <div class="modal-body">
                <div class="fileinput fileinput-new">
                    <h3>Upload a File</h3>
                    <form method="post" id="upload-form" name="upload-form" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" class="form-horizontal">
                        <input type="file" class="filestyle" name="file" data-input="false">
                        <br />
                        <input type="submit" value="Upload" class="btn btn-default btn-file" style="margin-top: 15px;"/>
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div> 
