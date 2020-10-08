$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/tags/list',
        //the datatype which is sent to the server
        datatype: "json",
        //the columns which shows in the website
        colModel: [
            {label: 'tagId', name: 'tagId', index: 'tagId', width: 50, hidden: true, key: true},
            {label: 'tagName', name: 'tagName', index: 'tagName', sortable: false, width: 50},
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
});
function TagAdd(){
 reset();
 $(".modal-title").html('Add Tag');
 $("#TagModal").modal('show');
}

function reset() {
    $("#TagName").val('');
}
//reload JaGrid to update the page
function reload() {
    var page = $("#TagJqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}
$("#saveButton").click(function (){
    var TagId = GetSelectedRowWithoutAlert();
    url= "/admin/tags/add";
    if(TagId!= null){
     $("#TagId").val(TagId);
    }
    $.ajax({
        type: "POST",
        url: url,
        data: $("#TagForm").serialize(),
        dataType:"json",
        success: function(result)
        {
              if(result.resultCode == 200)
              {
                   $('#TagModal').modal('hide');
                  swal("Save success!",{
                  icon:"success",
                  });
                  reload();
              }
               else
              {
                 $('#TagModal').modal('hide');
                 swal(result.message,{
                       icon:"error",
                 });
              }
        }
    });
})

function TagDelete(){
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
            url: "/admin/tags/delete",
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