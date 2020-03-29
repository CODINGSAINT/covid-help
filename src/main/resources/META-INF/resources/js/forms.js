//urls
var register = "api/public/register";
var registerAdmin = "api/public/register/admin";
var pendingUsers= "api/admin/approve/users";
var approveUser= "api/admin/approve/users/email";
var removeUser= "api/admin/reject/users/email";
var addNeedsURL ="api/users/need";
var pendingNeedsURL ="api/users/needs";
//Objects
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
function errorMessage(message) {
	$("#errorContainer").html(message);
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
			}
		}
	});
	reload();
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
				successMessage(data.message);
			}
		}
	});
	reload();
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

function pendingNeeds(){
var tRow="<tr  scope='row' ><td>NTH</td><td>FLAT</td><td>DATE</td><td>NAME</td><td>MOBILE</td><td>ITEM</td><td>AMOUNT</td>  <td>Picked By</td><td>Full filled By</td> <td><button class='btn btn-sm btn-success pull-right ' type='button' onclick='approve(userEmail);'>Approve</button><button class='btn btn-sm btn-danger pull-right ' type='button' onclick='remove(userEmailRemoval);'>Remove</button></td></tr>";
messageHide();
		$.ajax({
		cache: false,
		url: pendingNeedsURL,
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
                   rows=rows+tRow.replace("NTH",row).
                                    replace("NAME",item.name).
                                    replace("FLAT",item.flatNumber).
                                    replace("DATE",item.date).

                                    replace("MOBILE",item.date).
                                    replace("ITEM",item.item).
                                    replace("AMOUNT",item.quantity).
                                    replace("Full filled By",item.fullFilledBy).
                                    replace("Picked By",item.pickedBy).
                                    replace("EMAIL",item.email).replace("userEmail","\""+item.email+"\"").replace("userEmailRemoval","\""+item.email+"\"") ;
                });
			}
			$("#pendingItemBody").html(rows);


		}
	});
}
//approve users
//add items
//update items