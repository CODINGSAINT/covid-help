//urls
var register = "api/public/register";
var registerAdmin = "api/public/register/admin";
var pendingUsers= "api/admin/approve/users";
var approveUser= "api/admin/approve/users/email";
var removeUser= "api/admin/reject/users/email";
var addNeedsURL ="api/users/needs";
var addSingleItemURL="api/users/need";
var pendingNeedsURL ="api/users/needs";

var myPendingNeedsURL ="api/users/needs/me";
var updRequirementByIDURL="api/users/requirements"
var me="/api/users/me";
//Objects

var requirements="<div class='col-md-4 col-xs-12 col-lg-3 mb-2'><div class='card border-left-success shadow h-100 py-2 '> <div class='card-body'> "+
			"<div class='row no-gutters align-items-center'> <div class='col mr-2'> <div class='text-xs font-weight-bold text-success  mb-1'>"+
			" <span class='flatNumber'><span class='postedBy'>POSTEDBY</span> From FLATNUMBER posted on </span>  on <span class='date'>DATE</span> </div><div class='h5 mb-0 font-weight-bold text-gray-800'>"
			+" <table class='table'>"+
			    "<tr>"
			        +"<td><span class='item'>ITEM</span></td><td><span class='quantity'>AMOUNT</span></td>"
			        + "</tr><tr>"+"<td colspan='2'>Picked By <span class='pickedBy'>PICKEDBY</span></td>"
			        +"</tr></table>"+
			        "<span class='text-xs  font-weight-bold mr-2'> Select me to help or close requirement</span><input type='checkbox' class='_currentNeeds' value='NEEDID'/>"+

			"</div></div><div class='col-auto'> <i class='fas fa-clipboard-list fa-2x text-gray-300'></i> </div></div></div></div></div>";
var need =  {
    item: '',
    quantity:''
};
//utils

function initPage(){
messageHide();
}
function reload(){
location.reload();
}
// Form To Json
function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function (n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

//show success Message
function successMessage(message) {
	$("#successContainer").html(message);
	$("#success").show();

}

//show error Message
function errorMessage(errorsMessage) {
var error="";
$.each(errorsMessage.errors, function( index, value ) {
 error= value.message+"<br>";
});

	$("#errorContainer").html(error);
	$("#error").show();

}

function messageHide() {

	$("#error").hide();
	$("#errorContainer").html("");
	$("#successContainer").html("");
	$("#success").hide();
}

//Login
//signup

function signup() {
	messageHide();
	var $form = $("#registerForm");
	var data = getFormData($form);

	$.ajax({
		cache: false,
		url: register,
		data: JSON.stringify(data),
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			console.log(data);
			console.log(data.message);
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
				successMessage(data.message);
			}
		}
	});
}


function pendingforApprovalUsers(){
var tRow="<tr><th scope='row'>NTH</th><td>NAME</td> <td>FLAT</td><td>EMAIL</td> <td><button class='btn btn-sm btn-success pull-right ' type='button' onclick='approve(userEmail);'>Approve</button><button class='btn btn-sm btn-danger pull-right ' type='button' onclick='remove(userEmailRemoval);'>Remove</button></td></tr>";
messageHide();
		$.ajax({
		cache: false,
		url: pendingUsers,
		type: "GET",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			console.log(data);
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
			var rows="";

				row =0;
                $.each(data.message, function(key, item)
                {
                row++;
                   rows=rows+tRow.replace("NTH",row).replace("NAME",item.name).replace("FLAT",item.flatNumber).replace("EMAIL",item.email).replace("userEmail","\""+item.email+"\"").replace("userEmailRemoval","\""+item.email+"\"") ;
                });
			}
			$("#pendingUsersBody").html(rows);


		}
	});
}
function approve(email){


$.ajax({
		url: approveUser+"/"+email.trim(),
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
				successMessage(data.message);
                        				Swal.fire({
                                          title: '<strong>Help Neighbour</strong>',
                                          icon: 'info',
                                          html: data.message,
                                          showCloseButton: true,
                                          focusConfirm: false,
                                          confirmButtonText:
                                            '<i class="fa fa-thumbs-up"></i> Great!',
                                          confirmButtonAriaLabel: 'User Approved!',

                                        }).then(function() {
                                             location.reload()
                                          });
			}
		}
	});

}

function remove(email){


$.ajax({
		url: removeUser+"/"+email.trim(),
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
					Swal.fire({
                                              title: '<strong>Help Neighbour</strong>',
                                              icon: 'info',
                                              html: data.message,
                                              showCloseButton: true,
                                              focusConfirm: false,
                                              confirmButtonText:
                                                '<i class="fa fa-thumbs-up"></i> Great!',
                                              confirmButtonAriaLabel: 'User Removed!',

                                            }).then(function() {
                                                 location.reload()
                                              });
			}
		}
	});

}
function signupAdmin() {
	messageHide();
	var $form = $("#registerForm");
	var data = getFormData($form);

	$.ajax({
		cache: false,
		url: registerAdmin,
		data: JSON.stringify(data),
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
				successMessage(data.message);
			}
		}
	});
}


//Add Need

function addRequirement() {

var item=$("#item").val();
var quantity=$("#quantity").val();
var need= {
    item: item,
    quantity: quantity
};1
var userNeeds = JSON.stringify(need);

	$.ajax({
		cache: false,
		url: addSingleItemURL,
		data: userNeeds,
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',
		success: function (data) {
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
				successMessage(data.message);
			}
		}
	});
}

//all pending needs
function updateRequirements(action){
    //action->pick|fulfilled

    var checkedVals = $('._currentNeeds:checkbox:checked').map(function() {
        return this.value;
    }).get();
    var requirements=checkedVals.join(",");
    var updRequirement={
        action : action,
        requirements: requirements
    }
    	$.ajax({
    		cache: false,
    		url: updRequirementByIDURL,
    		data: JSON.stringify(updRequirement),
    		type: "POST",
    		headers: {
    			'Accept': 'application/json',
    			'Content-Type': 'application/json'
    		},
    		dataType: 'json',
    		success: function (data) {
    			if (data.errors) {
    				errorMessage(data.errors);
    			} else if (data.message) {
    				successMessage(data.message);
    				Swal.fire({
                      title: '<strong>Help Neighbour</strong>',
                      icon: 'info',
                      html:
                        data.message,
                      showCloseButton: true,
                      focusConfirm: false,
                      confirmButtonText:
                        '<i class="fa fa-thumbs-up"></i> Great!',
                      confirmButtonAriaLabel: 'Thumbs up, great!',

                    }).then(function() {
                         location.reload()
                      });



    			}
    		}
    	});

}

function addNeeds() {



var data = {};
var items= [];
$("#needsTable tbody tr.userNeed").each(function() {
    var item=$(this).find("input.item").val();
    var quantity=$(this).find("input.quantity").val();
    console.log(item,quantity);

  items.push({"item":item,
               "quantity":quantity
                });

});
var userNeeds = JSON.stringify(items);



	$.ajax({
		cache: false,
		url: addNeedsURL,
		data: userNeeds,
		type: "POST",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
				successMessage(data.message);
			}
		}
	});
}

//all pending needs

function myPendingNeeds(){
processPendingNeeds(myPendingNeedsURL);
}
function pendingNeeds(){
processPendingNeeds(pendingNeedsURL);
}
function processPendingNeeds(url){
messageHide();
		$.ajax({
		cache: false,
		url: url,
		type: "GET",
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			console.log(data);
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {


			var allRequirements="";
			$(data.message).each(function(index, element) {
			    var requirementDiv=requirements;

                requirementDiv=requirementDiv.replace("DATE", element.date[0] +"/"+element.date[1]+"/"+element.date[2]);
                    requirementDiv=requirementDiv.replace("NEEDID", element.needId);

			    requirementDiv=requirementDiv.replace("FLATNUMBER", element.flatNumber);
                requirementDiv=requirementDiv.replace("ITEM", element.item);
                requirementDiv=requirementDiv.replace("AMOUNT", element.quantity);
                requirementDiv=requirementDiv.replace("POSTEDBY", element.name);
                requirementDiv=requirementDiv.replace("PICKEDBY",element.pickedBy === null ? "No One , Please Help" : element.pickedBy);


                allRequirements=allRequirements+requirementDiv;
            });
            $(".allReq").html(allRequirements)


			}
			//$("#pendingItemBody").html(rows);


		}
	});
}

function fetchRequiredData( url){
var requiredData='';
$.ajax({
		url: url,
		type: "GET",
		async: false,
        cache: false,
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			console.log(data);
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
			requiredData=data;
			}
			//$("#pendingItemBody").html(rows);


		}
	});
return requiredData;
}
function share(socialMediaType){

var message='';
var messageType=$("#whatsappStatusId").val();
if(messageType=='all'){
    var allPendingNeeds =fetchRequiredData(pendingNeedsURL);
    console.log(allPendingNeeds.message)
    console.log(Object.keys(allPendingNeeds.message).length)
    message= "Our  neighbours require "+Object.keys(allPendingNeeds.message).length+" help , please check at "+location;
    sendWhatsAppMessage(message);
}else if(messageType=='myNeeds'){
$.ajax({
		url: myPendingNeedsURL,
		type: "GET",

		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		dataType: 'json',

		success: function (data) {
			console.log(data);
			if (data.errors) {
				errorMessage(data.errors);
			} else if (data.message) {
			allPendingNeeds=data;

			}
			//$("#pendingItemBody").html(rows);
 var items =[];
    var name=""
     var flat=""

    console.log(Object.keys(allPendingNeeds.message).length)
    $.each(allPendingNeeds.message, function(key,value) {

      if(value.fullfilled==false){
      items.push(value.item);
      name=value.name;
      flat=value.flatNumber;
      }

    });
    message= name +" from "+flat+" requires "+items +" Please check. "
    console.log(message)


		}
	});


}
}
function sendWhatsAppMessage(message ){
  var win = window.open('https://api.whatsapp.com/send?&text='+message, '_blank');
  if (win) {
      //Browser has allowed it to be opened
      win.focus();
  } else {
      //Browser has blocked it
      alert('Please allow popups for this website');
  }
}


function userDetails(){
$.ajax({
 		cache: false,
 		url: me,
 		type: "GET",
 		headers: {
 			'Accept': 'application/json',
 			'Content-Type': 'application/json'
 		},
 		dataType: 'json',

 		success: function (data) {
 			console.log(data);
 			$(".userName").html(data.name);
 		}
 	});
}
function setCookie(cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires="+ d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for(var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

const LOGGED_COOKIE = "quarkus-credential";
function checkCookie() {
  var username = getCookie(LOGGED_COOKIE);
  if (username != "") {
   alert("Welcome again " + username);
  }
}
 function logout() {
            console.log("logging out")
            document.cookie = LOGGED_COOKIE + '=; Max-Age=0'
            window.location.href = "/";
        }



//approve users
//add items
//update items