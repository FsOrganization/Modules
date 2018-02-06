var username = "";
function sendMessage(message){
	//Encapsulate the contents of #chatbox div
	var prevMessage = $("#chatbox").html();
	if($("#chatbox").html() != 0){
		prevMessage = prevMessage + '<br/>';
	}
	//htmlEntities => Convert special character into HTML readable string
	$("#chatbox").html(prevMessage + "<label class='bot'>Chatbot:</label> " + htmlEntities($.trim(message)));
}

function getUsername(){
	//Call the function with string parameter
	sendMessage("Hello! What is your name?!");
}

function autoMessage(message){
	//Statement if "username" variable is empty
	if(username == 0){
		//"username" variable contains "message" variable
		username = message;
		//Call the function with string parameter
		sendMessage("Nice to meet you " + ucwords(username) + ", how are you doing?");
	}

	if(message.indexOf("how are you") >= 0){
		sendMessage("Thanks, Im good!");
	}

	if(message.indexOf("time") >= 0){
		var date = new Date();
		var h = date.getHours();
		var m = date.getMinutes();
		var time = h + ":" + m;
		sendMessage("Current time is " + time); 
	}
}

//Opening for jQuery script
$(function(){
	//Call the function
	getUsername();

	//Read the character code of the key when pressed on the input field
	$("#textarea").keypress(function(event){
		//Statement when "Enter" button is pressed
		if(event.which == 13){
			//if checkbox "#enter" is checked
			if($("#enter").prop("checked")){
				//Trigger the #send button
				$("#send").click();
				//Prevent the action on the element
				event.preventDefault();
			}
		}
	});

	//Function when the #send button clicked
	$("#send").click(function(){
		//Encapsulate the string
		var username = "You:";
		//Encapsulate the value of #textarea input field
		var newMessage = $("#textarea").val();
		//Encapsulate the contents of #chatbox div
		var prevMessage = $("#chatbox").html();

		//If the #chatbox is empty, no line break on the first line
		if($("#chatbox").html() != 0){
			prevMessage = prevMessage + '<br/>';
		}
		//Statement if input field #textarea is not empty
		if($("#textarea").val() != ""){
			//Clear the input field #textarea
			$("#textarea").val("");
			//htmlEntities => Convert special character into HTML readable string
			$("#chatbox").html(prevMessage + '<label class="username">' + htmlEntities($.trim(username)) + '</label> ' + htmlEntities($.trim(newMessage)));
			//Automatically scoll at the bottom of the #chatbox when the user submit new message
			$("#chatbox").scrollTop($("#chatbox").prop("scrollHeight"));
		}

		autoMessage(newMessage);

	});
});