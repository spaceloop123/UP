/*
Global variables
*/
var user = document.getElementById("profile-name");
var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");
/*
Send created message by pressing ctrl+enter
*/
msgInputArea.addEventListener('keydown', function(e) {
	if(e.keyCode == 13 && e.ctrlKey) {
		if(this.value === "") 
			return false;
		
		if(!isEditMessage) {
			sendMessage(this.value);
		} else {
			isEditMessage = false;
			
			editMessage(this.value);
		}

		this.value = "";

		return false;
	}
});

msgInputBtn.addEventListener('click', function() {
	if(msgInputArea.value === "") 
		return true;

	if(!isEditMessage) {
		addClass(this, "active");

		sendMessage(msgInputArea.value);
		
		removeClass(this, "active");
	} else {
		isEditMessage = false;

		editMessage(msgInputArea.value);	
	}
	
	msgInputArea.value = "";

	return false;
}, true);

/*
Send message
*/
function sendMessage(value) {
	if(!value)
		return;

	var message = createMessage(value);
	var messages = document.getElementsByClassName("chat-list")[0];

	messages.appendChild(message);

	document.body.scrollTop = document.body.scrollHeight - document.body.clientHeight;
}
/*
Create message
*/
function createMessage(value) {
	var message = createMessageContainer();
	var author = createAuthor();
	var doubleDot = createDoubleDot();
	var editbtn = createEditbtn();
	var text = createText(value);
	var date = createDate();

	var li = document.createElement("li");
	li.appendChild(author);
	li.appendChild(doubleDot);
	li.appendChild(editbtn);
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

function createText(value) {
	var div = document.createElement("div");
	div.className = "text";
	div.textContent = value;
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
	} else if(hour === 0) {
		hour = "12";
	}
	if(minute < 10) {
		minute = "0" + minute;
	}
	return hour + ":" + minute + amPM;
}

function createEditbtn() {
	var div = document.createElement("div");
	div.className = "edit-mes";
	
	var i = document.createElement("i");
	i.className = "fa fa-pencil";

	div.appendChild(i);

	return div;
}

/*
Edit message
*/
function run() {
	var msgBody = document.getElementsByClassName('msg-body')[0];
	msgBody.addEventListener('click', editClickEvent);
}

var isEditMessage = false;

function editClickEvent(e) {
	var elem = e.target;
	if(elem.className === "fa fa-pencil" && !isEditMessage) {
		var li = elem.offsetParent.parentElement;
		var text = getText(li);

		if(!isEditMessage) {
			isEditMessage = true;

			msgInputArea.value = text;
			document.getElementById("msg-input-area").focus();

			addClass(li.parentElement, "active");
		} else {
			isEditMessage = false;

			removeClass(li.parentElement, "active");
		}
	}
}

function getText(li) {
	var text = "";
	for (var i = li.childNodes.length - 1; i >= 0; i--) {
		if(li.childNodes[i].className === "text") {
			text = li.childNodes[i].innerHTML;
			break;
		}
	}	
	return text;
}

function editMessage(text) {
	var messages = document.getElementsByClassName("chat-list")[0];
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg active") {
			var li = messages.childNodes[i].firstElementChild;
			for (var j = li.childNodes.length - 1; j >= 0; j--) {
				if(li.childNodes[j].className === "text") {
					li.childNodes[j].innerHTML = text;
					break;
				}
			}
			removeClass(messages.childNodes[i], "active");
		}
	}
}