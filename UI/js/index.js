/*
Global variables
*/
var user = document.getElementById("profile-name");
var msgInputArea = document.getElementById("msg-input-area");

/*
Send created message by pressing ctrl+enter
*/
msgInputArea.addEventListener('keydown', function(e) {
	if(e.keyCode == 13 && e.ctrlKey) {
		
		sendMessage(this.value);
		this.value = "";

		return false;
	}
});

function sendMessage(value) {
	if(!value)
		return;

	var message = createMessage(value);
	var messages = document.getElementsByClassName("chat-list")[0];

	messages.appendChild(message);

	document.body.scrollTop = document.body.scrollHeight - document.body.clientHeight;
}

function createMessage(text) {
	var message = createMessageContainer();
	var author = createAuthor();
	var doubleDot = createDoubleDot();
	var editButton = createEditButton();
	var text = createText();
	var date = createDate();


	var li = document.createElement("li");
	li.appendChild(author);
	li.appendChild(doubleDot);
	li.appendChild(editButton);
	li.appendChild(text);
	li.appendChild(date);
	
	message.appendChild(li);

	return message;
}

function createMessageContainer() {
	var div = document.createElement("div");
	div.className = "msg";
	div.id = "my-msg";
	return div;
}

function createAuthor() {
	var div = document.createElement("div");
	div.className = "author";
	div.textContent = user.textContent;
	return div;
}

function createDoubleDot() {
	var span = document.createElement("span");
	span.className = "double-dot";
	span.textContent = ":";
	return span;
}

function createText() {
	var div = document.createElement("div");
	div.className = "text";
	div.textContent = msgInputArea.value;
	return div;
}

function createDate() {
	var div = document.createElement("div");
	div.className = "date";

	var date = new Date();
	div.textContent = formatDate(date);

	return div;
}

function formatDate(date) {
	var hour = date.getHours();
	var minute = date.getMinutes();
	var amPM = (hour > 11) ? "pm" : "am";
	if(hour > 12) {
		hour -= 12;
	} else if(hour == 0) {
		hour = "12";
	}
	if(minute < 10) {
		minute = "0" + minute;
	}
	return hour + ":" + minute + amPM;
}

function createEditButton() {
	var div = document.createElement("div");
	div.id = "edit-message-button";
	
	var i = document.createElement("i");
	i.className = "fa fa-pencil";

	div.appendChild(i);

	return div;
}