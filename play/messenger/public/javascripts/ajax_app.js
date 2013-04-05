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
			console.log("got current user", user);
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
				console.log("signup", user);
				onLogIn(user);
			}, function(result) {
				alert("Failed to sign up: "+result.responseText);
			});		
		});
		
		logoutBox.find(".logoutButton").click(function() {
			$.post("/ajax/logout");
			logoutBox.addClass("hidden");
			loginBox.removeClass("hidden");
			//TODO hide another application elements
		});
		
		
		
		function onLogIn(user) {
			loginBox.addClass("hidden");
			logoutBox.find(".username").html(user.name);
			logoutBox.removeClass("hidden");
		}
	}//end LoginArea
	
	var loginArea = new LoginArea($("#loginArea"));
})