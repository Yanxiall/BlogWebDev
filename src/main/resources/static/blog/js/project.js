//press search button to search
function search(){
   var keyword = $("#searchbox").val();
   if(keyword && keyword !="")
   {
     window.location.href="/blog/search/" + keyword;
   }
}
//press the enter button on the keyboard to search
$(function () {
  $("#searchbox").keypress(function (e) {
    var key = e.which; //get key value
    if (key == 13) {
      var keyword = $(this).val();
      if (keyword && keyword != "") {
        window.location.href = "/blog/search/" + keyword;
      }
    }
  });
});