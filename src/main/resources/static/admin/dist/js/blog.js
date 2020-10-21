$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/blog/list',
        //the datatype which is sent to the server
        datatype: "json",
        //the columns which shows in the website
        colModel: [
            {label: 'blogId', name: 'blogId', index: 'blogId', width: 40, hidden: true, key: true},
            {label: 'blogTitle', name: 'blogTitle', index: 'blogTitle', sortable: false, width: 50},
            {label: 'blogCoverImage', name: 'blogCoverImage', index: 'blogCoverImage', sortable: false, width: 60,formatter:imgFormatter},
            {label: 'blogStatus', name: 'blogStatus', index: 'blogStatus',sortable: false,width: 40,formatter:statusFormatter},
            {label: 'blogViews', name: 'blogViews', index: 'blogViews',sortable: false,width: 40,},
            {label: 'blogCategoryName', name: 'blogCategoryName', index: 'blogCategoryName',sortable: false,width: 60,},
            {label: 'createTime', name: 'createTime', index: 'createTime', sortable: false, width: 90}
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

    function imgFormatter(cellvalue) {
        return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"64\" width=\"64\" alt='icon'/></a>";
    }
    function statusFormatter(cellvalue) {
           if (cellvalue == 0) {
                 return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 70%;\">Draft</button>";
           }
           else if (cellvalue == 1) {
                return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 70%;\">Publish</button>";
           }
    }
});

//add blog
function BlogAdd(){
 window.location.href = "/admin/edit";
}

//reload JaGrid to update the page
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function BlogDelete(){
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
            url: "/admin/blog/delete",
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

function BlogModify(){
  var blogId = GetSelectedRow();
  if(blogId == null){
    return;
  }
  else{
  window.location.href = "/admin/blog/edit/" + blogId;
  }
}