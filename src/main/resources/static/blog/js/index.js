$(window).scroll(function(){
   var scrollTop = $(this).scrollTop();
   console.log(scrollTop);
   if(scrollTop>50){
      $('.navbar').addClass('bg-warning');
   }
   else{
      $('.navbar').removeClass('bg-warning');
   }
})
