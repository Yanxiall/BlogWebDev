
$(function () {
      var blogEditor;
      blogEditor = editormd("blog-editormd", {
      width: "100%",
      height: 640,
      syncScrolling: "single",
      path: "/admin/plugins/editor.md-master/lib/",
      toolbarModes: "full",
      /**configure image upload*/
      imageUpload: true,
      imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"], //the format of images
      imageUploadURL: "/admin/blogs/md/uploadfile",
      onload: function (obj) {
            }
    });
  $(".select2").select2();
  $('#BlogTag').tagsInput({
  width: "100%",
  height: "38px",
  defaultText: "BlogTag",
});
  $("#ChangeCoverimg").click(function(){
     var rand = parseInt(Math.random() * 40 + 1);
     $("#RandomCoverImg").attr("src", '/admin/dist/img/rand/' + rand + ".jpg");
   });
   new AjaxUpload("#UploadCoverimg",{
       // Location of the server-side upload script
                  action: '/admin/upload/',
                  // File upload name
                  name: 'file',
                  // Submit file as soon as it's selected
                  autoSubmit: true,
                  // The type of data that you're expecting back from the server.
                  // html and xml are detected automatically.
                  // Only useful when you are using json data as a response.
                  // Set to "json" in that case.
                  responseType: "json",
                  // Callback to fire before file is uploaded
                  // You can return false to cancel upload
                  onSubmit: function(file, extension){
                      if(!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                        alert("only jpg,png,gif formats are supported!");
                        return false;
                      }
                  },
                  // Fired when file upload is completed
                  // WARNING! DO NOT USE "FALSE" STRING AS A RESPONSE!
                  onComplete: function(file, response){
                    if(response.resultCode == 200 ){
                       console.log("good");
                       alert("upload success");
                       console.log(response.data);
                       $("#RandomCoverImg").attr("src",response.data);
                       return true;
                    }
                    else{
                        alert("error");
                    }
                  }
   });
   $("#SaveBlogButton").click(function(){
        var BlogTitle = $("#BlogTitle").val();
        var BlogTag = $("#BlogTag").val();
        var BlogSuburl = $("#BlogSuburl").val();
        var blogCategoryId = $("#blogCategoryId").val();
        var blogContent = blogEditor.getMarkdown();
        var RandomCoverImg = $("#RandomCoverImg")[0].src;
        var PostStatus = $("input[name='PostStatus']:checked").val();
        var EnableComment =  $("input[name='EnableComment']:checked").val();
        var data = {
        "BlogTitle":BlogTitle,"BlogTag":BlogTag,"BlogSuburl":BlogSuburl,"blogCategoryId":blogCategoryId,
        "blogContent":blogContent,"RandomCoverImg":RandomCoverImg,"PostStatus":PostStatus,"EnableComment":EnableComment
        };
        $.ajax
                 ({
                     type: "POST",
                     url: "/admin/edit/save",
                     data:data,
                     dataType:"json",
                     success: function(result)
                     {
                        if(result.resultCode == 200)
                        {
                        swal("save success", {
                               icon: "success"
                                 }).then(function () {
                               window.location.href = "/admin/blog";
                               });
                         }
                        else
                        {
                          swal(result.message, {
                              icon: "error",
                                          });
                        }
                     },
                     error:function()
                     {
                        swal("request failed", {
                                icon: "error",
                        });
                     }
                 });
   });
   $("#ReturnBlogList").click(function(){
   console.log("come into return");
   window.location.href = "/admin/blog";
   });

});