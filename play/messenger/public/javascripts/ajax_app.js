$(function() {
	var UNAUTHORIZED = 401;

	function LoginArea(element) {
		var that = this	
		this.element = element;
		
		var loginBox = $("#loginBoxTemplate").clone().addClass("hidden");
		var logoutBox = $("#logoutBoxTemplate").clone().addClass("hidden");
		element.append(loginBox);
		element.append(logoutBox);
		
		$.get("/ajax/currentUser").then(function(user) {
//			console.log("got current user", user);
			onLogIn(user);
		}, function(result) {
			if(result.status == UNAUTHORIZED)  {
				logoutBox.addClass("hidden");
				loginBox.removeClass("hidden");			
			}
		});
		
		function getCredentials() {
			var name = loginBox.find(".username").val();
			var password = loginBox.find(".password").val();
			return {name: name, password: password};
		}
		
		loginBox.find(".loginButton").click(function() {
			$.post("/ajax/login", getCredentials()).then(function(user) {
				onLogIn(user);
			}, function(result) {
				if(result.status == UNAUTHORIZED)  {
					alert("Can't log in. Username/password might be wrong");
				}
			});
		});
		
		loginBox.find(".signupButton").click(function() {
			$.post("/ajax/signup", getCredentials()).then(function(user) {
				onLogIn(user);
			}, function(result) {
				alert("Failed to sign up: "+result.responseText);
			});		
		});
		
		logoutBox.find(".logoutButton").click(function() {
			$.post("/ajax/logout");
			logoutBox.addClass("hidden");
			loginBox.removeClass("hidden");
			$(element).trigger("custom:logout");
		});
		
		
		
		function onLogIn(user) {
			loginBox.addClass("hidden");
			logoutBox.find(".username").html(user.name);
			logoutBox.removeClass("hidden");
			$(element).trigger("custom:login", [user]);
			
		}
	}//end LoginArea
	
	function MessageForm(element, user) {
		var that = this;
		var messageForm = $("#messageFormTemplate").clone();
		element.append(messageForm);
		messageForm.find(".senderName").html(user.name);
		var receiverSelect = messageForm.find(".receiverName");
		var messageArea = messageForm.find(".messageText");

		$.get("/ajax/users").then(function(users) {
			for(i in users) {
				if(!users.hasOwnProperty(i) || users[i].id == user.id) continue;
				receiverSelect.append("<option value='"+users[i].id+"'>"+users[i].name+"</option>");
			}
		});

		messageForm.find(".sendMessageButton").click(function() {
			var receiver = receiverSelect.val();	
			var message = messageArea.val();
			console.log("send", user, receiver, message);
			$.post("/ajax/message/send/"+user.id+"/"+receiver,
				message).then(function(response) {
					alert("Message sent");
					messageArea.val("");
				}, function(err) {
					alert("Failed to send: "+err);
				});
		});
	}//end MessageForm

	var loginArea = $("#loginArea")
	new LoginArea(loginArea);

	loginArea.on("custom:login", function(event, user) {
		console.log("event: login", user);
		new MessageForm($("#messageForm"), user);
	});

	loginArea.on("custom:logout", function(event) {
		console.log("event: logout");
		$("#messageForm").html("");
	});
	
})
