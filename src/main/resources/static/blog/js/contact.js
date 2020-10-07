$("#submitContactMessage").click(function(){
        var contactName = $("#Name").val();
        var contactEmail =  $("#Email").val();
        var contactPhoneNumber = $("#Phone").val();
        var contactCompany = $("#Company").val();
        var contactMessage = $("#Message").val();
        if(isNull(contactName)){
             swal("Please enter your name!", {
                    icon: "error",
             });
             return;
        }
        if(isNull(contactEmail)){
             swal("Please enter your Email!", {
                    icon: "error",
                            });
             return;
        }
        if(!validEmail(contactEmail))
        {
             swal("please enter valid email", {
                    icon: "error",
             });
             return;
        }
        if(isNull(contactPhoneNumber)){
             swal("Please enter your phone number!", {
                   icon: "error",
             });
             return;
        }
         if(!validPhoneNumber(contactPhoneNumber))
         {
              swal("please enter valid phone number", {
                     icon: "error",
              });
              return;
         }
        if(isNull(contactCompany)){
             swal("Please enter your company name!", {
                    icon: "error",
             });
             return;
        }
        if(isNull(contactMessage)){
             swal("Please enter your message!", {
                   icon: "error",
             });
             return;
        }
        var data = {
        "contactName":contactName,"contactEmail":contactEmail,"contactPhoneNumber":contactPhoneNumber,"contactCompany":contactCompany,"contactMessage":contactMessage

        };

        $.ajax
                 ({
                     type: "POST",
                     url: "/save",
                     data:data,
                     dataType:"json",
                     success: function(result)
                     {
                        if(result.resultCode == 200)
                        {
                        swal("Submit success! I will contact you as soon as possible!", {
                               icon: "success"
                                 }).then(function () {
                               window.location.href = "/index";
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