$('.navbar').removeClass('navbar-light');
$('.navbar').addClass('navbar-dark');
$(window).scroll(function(){
      var scrollTop = $(this).scrollTop();
      console.log(scrollTop);
      if(scrollTop>50){
         $('.navbar').addClass('bg-primary');
      }
      else{
         $('.navbar').removeClass('bg-primary');
      }
})

//press search button to search
function search(){
   var keyword = $("#searchbox").val();
   if(keyword && keyword !="")
   {
     window.location.href="/search/" + keyword;
   }
}
//press the enter button on the keyboard to search
$(function () {
  $("#searchbox").keypress(function (e) {
    var key = e.which; //get key value
    if (key == 13) {
      var keyword = $(this).val();
      if (keyword && keyword != "") {
        window.location.href = "/search/" + keyword;
      }
    }
  });
});
