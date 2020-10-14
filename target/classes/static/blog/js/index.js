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
$(window).scroll(function(){
  var HTMLtimerId,HTMLpercent;
  HTMLpercent = 0;
  HTMLtimerId = setInterval(function() {
     // increment progress bar
     HTMLpercent += 5;
     $('#HTML').css('width', HTMLpercent + '%');
     if (HTMLpercent >= 90) {
      clearInterval(HTMLtimerId);
      $('#HTML').removeClass('progress-bar-striped active');
    }
  }, 100)
  var JavascripttimerId,Javascriptpercent;
  Javascriptpercent = 0;
  JavascripttimerId = setInterval(function() {
     // increment progress bar
     Javascriptpercent += 5;
     $('#Javascript').css('width', Javascriptpercent + '%');
     if ( Javascriptpercent >= 30) {
        clearInterval(JavascripttimerId);
        $('#Javascript').removeClass('progress-bar-striped active');
      }
    }, 100)
 var BootstraptimerId,Bootstrappercent;
     // reset progress bar
     Bootstrappercent = 0;
     BootstraptimerId = setInterval(function() {

       // increment progress bar
       Bootstrappercent += 5;
       $('#Bootstrap').css('width', Bootstrappercent + '%');
       if ( Bootstrappercent >= 80) {
         clearInterval(BootstraptimerId);

         $('#Bootstrap').removeClass('progress-bar-striped active');
       }
 }, 100)
 var JavatimerId,Javapercent;
      // reset progress bar
      Javapercent = 0;
      JavatimerId = setInterval(function() {

        // increment progress bar
        Javapercent += 5;
        $('#Java').css('width', Javapercent + '%');
        if ( Javapercent >= 50) {
          clearInterval(JavatimerId);

          $('#Java').removeClass('progress-bar-striped active');
        }
 }, 100)
 var CplustimerId,Cpluspercent;
        // reset progress bar
        Cpluspercent = 0;
        CplustimerId = setInterval(function() {

          // increment progress bar
         Cpluspercent += 5;
          $('#Cplus').css('width', Cpluspercent + '%');
          if ( Cpluspercent >= 70) {
            clearInterval(CplustimerId);

            $('#Cplus').removeClass('progress-bar-striped active');
          }
 }, 100)
 var pythontimerId,pythonpercent;
         // reset progress bar
 pythonpercent = 0;
 pythontimerId = setInterval(function() {
 // increment progress bar
 pythonpercent += 5;
 $('#python').css('width', pythonpercent + '%');
  if ( pythonpercent >= 60) {
        clearInterval(pythontimerId);
      $('#python').removeClass('progress-bar-striped active');
  }
 }, 100)
 var MySQLtimerId,MySQLpercent;
          // reset progress bar
          MySQLpercent = 0;
          MySQLtimerId = setInterval(function() {

            // increment progress bar
           MySQLpercent += 5;
            $('#MySQL').css('width', MySQLpercent + '%');
            if ( MySQLpercent >= 70) {
              clearInterval(MySQLtimerId);

              $('#MySQL').removeClass('progress-bar-striped active');
            }
   }, 100)
  var BigDatatimerId,BigDatapercent;
           // reset progress bar
           BigDatapercent = 0;
           BigDatatimerId = setInterval(function() {

             // increment progress bar
            BigDatapercent += 5;
             $('#BigData').css('width', BigDatapercent + '%');
             if ( BigDatapercent >= 30) {
               clearInterval(BigDatatimerId);

               $('#BigData').removeClass('progress-bar-striped active');
             }
    }, 100)
    })