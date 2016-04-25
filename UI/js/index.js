/*
Global variables
*/
var user = document.getElementById("profile-name");
var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");
/*
Run
*/
function run() {
	var msgBody = document.getElementsByClassName('msg-body')[0];
	msgBody.addEventListener('click', editClickEvent);
	// load();
}
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
			switchBtnIcon();
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
		switchBtnIcon();
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
	
	var pencil = document.createElement("i");
	pencil.className = "fa fa-pencil";
	var trash = document.createElement("i");
	trash.className = "fa fa-trash";

	div.appendChild(pencil);
	div.appendChild(trash);

	return div;
}

/*
Edit message
*/
var isEditMessage = false;

function editClickEvent(e) {
	var elem = e.target;
	if(elem.className === "fa fa-pencil" && !isEditMessage) {
		var li = elem.offsetParent.parentElement;
		var text = getText(li);

		if(!isEditMessage) {
			switchBtnIcon();
			isEditMessage = true;

			msgInputArea.value = text;
			document.getElementById("msg-input-area").focus();

			addClass(li.parentElement, "active");
		} else {
			switchBtnIcon();
			isEditMessage = false;
			
			removeClass(li.parentElement, "active");
		}
	} else if(elem.className === "fa fa-trash" && !isEditMessage) {
		var li = elem.offsetParent.parentElement;
		addClass(li.parentElement, "delete");
		deleteMessage(li.parentElement);
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
			if(!isChangedDivExists(li)) {
				var div = document.createElement("div");
				div.className = "changed";
				div.textContent = "Edited";
				li.appendChild(div);	
			}
			removeClass(messages.childNodes[i], "active");
		}
	}
}

function isChangedDivExists(li) {
	for (var j = li.childNodes.length - 1; j >= 0; j--) {
		if(li.childNodes[j].className === "changed") {
			return true;
		}
	}
	return false;
}

function switchBtnIcon() {
	if(!isEditMessage) {
		removeClass(document.getElementById("paper-plane"), "fa-paper-plane");
		addClass(document.getElementById("paper-plane"), "fa-pencil");

		removeClass(document.getElementById("msg-input-btn"), "plane");	
		addClass(document.getElementById("msg-input-btn"), "pencil");
		
	} else {
		removeClass(document.getElementById("paper-plane"), "fa-pencil");
		addClass(document.getElementById("paper-plane"), "fa-paper-plane");		

		removeClass(document.getElementById("msg-input-btn"), "pencil");	
		addClass(document.getElementById("msg-input-btn"), "plane");	
	}
}

/*
Delete message
*/
var cancelBtn = document.getElementById("cancel");
var deleteBtn = document.getElementById("delete");

function deleteMessage(div) {
	addClass(document.getElementById("overflow"), "active");
	addClass(document.getElementById("dialog"), "active");
	document.getElementById("dialog").focus();
}

cancelBtn.addEventListener("click", function(e) {
	e.preventDefault();

	var messages = document.getElementsByClassName("chat-list")[0];
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg delete") {
			removeClass(messages.childNodes[i], "delete");
			break;
		}
	}

	removeClass(document.getElementById("overflow"), "active");
	removeClass(document.getElementById("dialog"), "active");
});

deleteBtn.addEventListener("click", function(e) {
	e.preventDefault();

	var messages = document.getElementsByClassName("chat-list")[0];
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg delete") {
			var li = messages.childNodes[i].firstElementChild;
			addClass(li.parentElement, "deleted");

			while (li.firstChild) {
				li.removeChild(li.firstChild);
			}

			var div = document.createElement("div");
			div.className = "changed";
			div.textContent = "Deleted by " + user.textContent;

			li.appendChild(div);

			removeClass(messages.childNodes[i], "delete");
		}
	}

	removeClass(document.getElementById("overflow"), "active");
	removeClass(document.getElementById("dialog"), "active");
});

function createDotContainer() {
	var div = document.createElement("div");
	div.className = "dot-container";
	
	var dot;
	for (var i = 0; i < 3; i++) {
		dot = document.createElement("div");
		dot.className = "dot";
		div.appendChild(dot);
	}

	return div;
}