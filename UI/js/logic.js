var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");

var isEditMessage = false;
var divForEdit;

msgInputArea.addEventListener('keydown', function(e) {
	if(e.keyCode == 13 && e.ctrlKey) {
		if(this.value === "") 
			return false;
		
		if(!isEditMessage) {
			addMessage(this.value, function() {
				render(Application);
			});
		} else {
			switchBtnIcon();
			isEditMessage = false;

			var index = indexById(Application.messageList, idFromElement(divForEdit));
			var message = Application.messageList[index];
			message.text = this.value;

			editMessage(message.id, function() {
				if (divForEdit.firstChild.getElementsByClassName("changed")[0] === undefined) {
					divForEdit.firstChild.appendChild(createEditedLabel());
				}
				divForEdit.getElementsByClassName("text")[0].innerText = message.text;

				render(Application);
			});

			removeClass(divForEdit, "active");
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

		addMessage(msgInputArea.value, function() {
			render(Application);
		});
		
		removeClass(this, "active");
	} else {
		switchBtnIcon();
		isEditMessage = false;

		var index = indexById(Application.messageList, idFromElement(divForEdit));
		var message = Application.messageList[index];
		message.text = msgInputArea.value;

		editMessage(message.id, function() {
			if (divForEdit.firstChild.getElementsByClassName("changed")[0] === undefined) {
				divForEdit.firstChild.appendChild(createEditedLabel());
			}
			divForEdit.getElementsByClassName("text")[0].innerText = message.text;

			render(Application);
		});

		removeClass(divForEdit, "active");
	}
	
	msgInputArea.value = "";

	return false;
}, true);

function logic() {
	var appContainer = document.getElementsByClassName('chat-list')[0];
	appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type === 'click' && evtObj.target.classList.contains('fa-trash'))
		onRemoveBtnClick(evtObj.target.parentElement.parentElement);
	if(evtObj.type === 'click' && evtObj.target.classList.contains('fa-pencil'))
		onEditBtnClick(evtObj.target.parentElement.parentElement);
}

/*
Edit message
*/
function onEditBtnClick(li) {
	console.log(li);
	if(!isEditMessage) {
		switchBtnIcon();
		
		isEditMessage = true;
		divForEdit = li.parentElement;

		msgInputArea.value = li.getElementsByClassName("text")[0].innerText;
		document.getElementById("msg-input-area").focus();

		addClass(divForEdit, "active");
	} else {
		switchBtnIcon();

		isEditMessage = false;

		removeClass(divForEdit, "active");
	}
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
var divForDelete;

function onRemoveBtnClick(li) {
	addClass(document.getElementById("overflow-background"), "active");
	addClass(document.getElementById("dialog"), "active");
	document.getElementById("dialog").focus();	
	divForDelete = li.parentElement;
}

cancelBtn.addEventListener("click", function(e) {
	removeClass(document.getElementById("overflow-background"), "active");
	removeClass(document.getElementById("dialog"), "active");
});

deleteBtn.addEventListener("click", function(e) {
	var index = indexById(Application.messageList, idFromElement(divForDelete));
	var message = Application.messageList[index];

	var author = divForDelete.firstChild.getElementsByClassName("author")[0].innerText;

	deleteMessage(message.id, function() {
		divForDelete.removeChild(divForDelete.firstChild);

		var li = document.createElement("li");
		li = createRemovedMessage(li, author);
		divForDelete.appendChild(li);
		addClass(divForDelete, "removed");

		render(Application);
	});

	removeClass(document.getElementById("overflow-background"), "active");
	removeClass(document.getElementById("dialog"), "active");
});

function indexById(list, id){
	for(var i = 0; i< list.length; i++) {
		if(list[i].id == id) {
			return i;
		}
	}
	return -1;
}

function idFromElement(element){
	return element.attributes['data-msg-id'].value;
}