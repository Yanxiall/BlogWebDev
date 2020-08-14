$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/category/list',
        //the datatype which is sent to the server
        datatype: "json",
        //the columns which shows in the website
        colModel: [
            {label: 'categoryId', name: 'categoryId', index: 'categoryId', width: 50, hidden: true, key: true},
            {label: 'categoryName', name: 'categoryName', index: 'categoryName', sortable: false, width: 50},
            {label: 'categoryIcon', name: 'categoryIcon', index: 'categoryIcon', sortable: false, width: 50,formatter:imgFormatter},
            {label: 'createTime', name: 'createTime', index: 'createTime', sortable: false, width: 60}
        ],
        height: 485,
        rowNum: 5,
        rowList: [5, 10, 15],
        styleUI: 'Bootstrap',
        loadtext: 'loading...',
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        //data which the server sends
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        //parameter names which is sent to the server
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    $("#ChangeIcon").click(function(){
      var rand = parseInt(Math.random() * 20 + 1);
      $("#categoryIconImg").attr("src", '/admin/dist/img/category/' + rand + ".png");
      $("#CategoryIcon").val('/admin/dist/img/category/' + rand + ".png")
    });
});
//Add category
function CategoryAdd(){
 reset();
 $(".modal-title").html('Add Category');
 $("#myModal").modal('show');
}
function imgFormatter(cellvalue) {
    return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"64\" width=\"64\" alt='icon'/></a>";
}
//Modify Category
function CategoryModify(){
  var categoryId = GetSelectedRow();
  if(categoryId == null){
     return;
  }
  $("#CategoryId").val(categoryId);
  $(".modal-title").html('Modify Category');
  $("#myModal").modal('show');
  $.ajax({
    type: "GET",
    url:"/admin/category/info?categoryId=" + categoryId,
    dataType:"json",
    success: function(result){
      if(result.resultCode == 200 && result.data != null){
         $("#CategoryId").val(result.data.CategoryId);
         $("#CategoryName").val(result.data.categoryName);
         $("#categoryIconImg").attr("src",result.data.categoryIcon);
         $("#CategoryIcon").val(result.data.categoryIcon)
      }
    }
  });
}
function reset() {
    $("#CategoryName").val('');
    $("#categoryIconImg").attr("src", '/admin/dist/img/img-upload.png');
    $("#CategoryIcon").val('');
}
//reload JaGrid to update the page
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}
$("#saveButton").click(function (){
    var categoryId = GetSelectedRowWithoutAlert();
    url= "/admin/category/add";
    if(categoryId != null){
     url= "/admin/category/modify";
     $("#CategoryId").val(categoryId);
    }
    $.ajax({
        type: "POST",
        url: url,
        data: $("#categoryForm").serialize(),
        dataType:"json",
        success: function(result)
        {
              if(result.resultCode == 200)
              {
                   $('#myModal').modal('hide');
                  swal("Save success!",{
                  icon:"success",
                  });
                  reload();
              }
               else
              {
                 $('#myModal').modal('hide');
                 swal(result.message,{
                       icon:"error",
                 });
              }
        }
    });
})

function CategoryDelete(){
   reset();
   var ids = GetSelectedMultiRows();
   if(ids == null){
        return;
     }
   swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
   }).then((willDelete) =>
   {
      if (willDelete) {
       $.ajax({
            type: "POST",
            url: "/admin/category/delete",
            contentType: "application/json",
            data: JSON.stringify(ids),
            dataType:"json",
            success: function(result)
            {
                  if(result.resultCode == 200)
                  {
                       swal("Poof! Delete success!", {
                             icon: "success",
                           });
                       reload();
                  }
                   else
                  {
                      swal(result.message,{
                           icon:"error",
                      });
                  }
            }
        });
       } else {
           swal("Delete canceled!");
         }
   });
}