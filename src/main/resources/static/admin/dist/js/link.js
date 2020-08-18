$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/friendlink/list',
        //the datatype which is sent to the server
        datatype: "json",
        //the columns which shows in the website (the names should be the same as that in database)
        colModel: [
            {label: 'Link Id', name: 'linkId', index: 'linkId', width: 50, hidden: true, key: true},
            {label: 'Link Name', name: 'linkName', index: 'linkName', sortable: false, width: 40},
            {label: 'Link Description', name: 'linkDescription', index: 'linkDescription', sortable: false, width: 50},
            {label: 'Link Url', name: 'linkUrl', index: 'linkUrl', sortable: false, width: 80},
            {label: 'Link Rank', name: 'linkRank', index: 'linkRank', sortable: false, width: 40},
            {label: 'Create Time', name: 'createTime', index: 'createTime', sortable: false, width: 60}
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
//Add link
function LinkAdd(){
 reset();
 $(".modal-title").html('Add Link');
 $("#LinkModal").modal('show');
}

//Modify link
function LinkModify(){
  reset();
  var linkId = GetSelectedRow();
  if(linkId == null){
     return;
  }
  $("#LinkId").val(linkId);
  $(".modal-title").html('Modify Link');
  $("#LinkModal").modal('show');
  $.ajax({
    type: "GET",
    url:"/admin/link/info?LinkId=" + linkId,
    dataType:"json",
    success: function(result){
      if(result.resultCode == 200 && result.data != null){
         $("#LinkId").val(result.data.linkId);
         $("#LinkType").val(result.data.linkType);
         $("#WebsiteName").val(result.data.linkName);
         $("#URL").val(result.data.linkUrl);
         $("#WebsiteDescription").val(result.data.linkDescription);
         $("#Rank").val(result.data.linkRank);
      }
    }
  });
}
function reset() {
    $("#LinkId").val('');
    $("#WebsiteName").val('');
    $("#URL").val('');
    $("#WebsiteDescription").val('');
    $("#Rank").val(0);
    $('#edit-error-msg').css("display", "none");
    $("#LinkType option:first").prop("selected", 'selected');
}
//reload JqGrid to update the page
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        //page: page
    }).trigger("reloadGrid");
}
$("#saveButton").click(function (){
    var linkId = GetSelectedRowWithoutAlert();
    url= "/admin/friendlink/add";
    if(linkId != null){
     url= "/admin/friendlink/modify";
     $("#LinkId").val(linkId);
    }
    var linkurl = $("#URL").val();
    var linkrank = $("#Rank").val();
    console.log(linkrank);
    if(!validURL(linkurl))
    {
      $('#edit-error-msg').css("display", "block");
      $('#edit-error-msg').html("please input valid url");
      return;
    }
    if(linkrank < 0){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("please input valid rank");
        return;
    }

    $.ajax({
        type: "POST",
        url: url,
        data: $("#LinkForm").serialize(),
        dataType:"json",
        success: function(result)
        {
              if(result.resultCode == 200)
              {
                  $('#LinkModal').modal('hide');
                  swal("Save success!",{
                  icon:"success",
                  });
                  reload();
              }
               else
              {
                 $('#LinkModal').modal('hide');
                 swal(result.message,{
                       icon:"error",
                 });
              }
        }
      });
})

function LinkDelete(){
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
            url: "/admin/link/delete",
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