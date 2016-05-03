/*
Send message
*/
var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");

var isEditMessage = false;

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

function sendMessage(value) {
	if(!value) {
		return;
	}

	var message = newMessage(value, author, false, false, true);
	messageList.push(message);
	renderMessage(message);

	update();
}

function sendAlertMessage(text, author, type) {
	if(!text) {
		return;
	}

	var message = newAlertMessage(text, author, type);
	messageList.push(message);
	renderMessage(message);
	
	update();
}

/*
Edit message
*/
function addClickEvent() {
	var msgBody = document.getElementsByClassName('msg-body')[0];
	msgBody.addEventListener('click', editClickEvent);
}

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
		console.log("delete");
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
	var count = 0;
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg my active") {
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
				messageList[messageList.length - count - 1].changed = true;
				messageList[messageList.length - count - 1].text = text;
				update();
			}
			removeClass(messages.childNodes[i], "active");
		}
		count++;
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
	addClass(document.getElementById("overflow-background"), "active");
	addClass(document.getElementById("dialog"), "active");
	document.getElementById("dialog").focus();
}

cancelBtn.addEventListener("click", function(e) {
	e.preventDefault();

	var messages = document.getElementsByClassName("chat-list")[0];
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg my delete") {
			removeClass(messages.childNodes[i], "delete");
			break;
		}
	}

	removeClass(document.getElementById("overflow-background"), "active");
	removeClass(document.getElementById("dialog"), "active");
});

deleteBtn.addEventListener("click", function(e) {
	e.preventDefault();

	var messages = document.getElementsByClassName("chat-list")[0];
	var count = 0;
	for (var i = messages.childNodes.length - 1; i >= 0; i--) {
		if(messages.childNodes[i].className === "msg my delete") {
			var li = messages.childNodes[i].firstElementChild;
			addClass(li.parentElement, "removed");

			while (li.firstChild) {
				li.removeChild(li.firstChild);
			}

			var div = document.createElement("div");
			div.className = "changed";
			div.textContent = "Deleted by " + author;

			li.appendChild(div);

			messageList[messageList.length - count - 1].removed = true;
			update();

			removeClass(messages.childNodes[i], "delete");
		}
		count++;
	}

	removeClass(document.getElementById("overflow-background"), "active");
	removeClass(document.getElementById("dialog"), "active");
});