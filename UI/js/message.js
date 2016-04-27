/*
Message
*/
function createMessage(elem) {
	var message = createMessageContainer(elem.isMy);
	var li = document.createElement("li");
	if(elem.removed) {
		message.appendChild(createRemovedMessage(li));
		addClass(message, "removed");
		return message;
	}
	var changed;
	if(elem.changed) {
		changed = createChangedLabel();
	}
	var author = createAuthor(elem.author);
	var doubleDot = createDoubleDot();
	var editbtn;
	if(elem.isMy) { 
		editbtn = createEditbtn(); 
	}
	var text = createText(elem.text);
	var date = createDate(elem.timestamp);

	var li = document.createElement("li");
	li.appendChild(author);
	li.appendChild(doubleDot);
	if(elem.isMy) {
		li.appendChild(editbtn);
	}
	li.appendChild(text);
	li.appendChild(date);
	if(elem.changed) {
		li.appendChild(changed);
	}

	message.appendChild(li);

	return message;
}

function createRemovedMessage(li) {
	var label = createDeleteLabel();

	li.appendChild(label);

	return li;	
}

function createMessageContainer(isMy) {
	var div = document.createElement("div");
	if(!isMy) {
		div.className = "msg";
	} else {
		div.className = "msg my";
	}
	return div;
}

function createAuthor(author) {
	var div = document.createElement("div");
	div.className = "author";
	div.textContent = author;
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

function createDate(date) {
	var div = document.createElement("div");
	div.className = "date";
	div.textContent = formatDate(new Date(date));
	return div;
}

function formatDate(date) {
	var hour = date.getHours();
	var minute = date.getMinutes();
	var amPM = (hour > 11) ? "pm" : "am";
	if(hour > 12) { hour -= 12; } 
	else if(hour === 0) { hour = "12"; }
	if(minute < 10) { minute = "0" + minute; }
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

function createChangedLabel() {
	var div = document.createElement("div");
	div.className = "changed";
	div.textContent = "Edited";
	return div;
}

function createDeleteLabel() {
	var div = document.createElement("div");
	div.className = "changed";
	div.textContent = "Deleted by " + author;
	return div;
}

/*
Alert
*/
function createAlertMessage(elem) {
	var message = createAlertMessageContainer(elem.type);
	var author = createAuthor(elem.author);
	var doubleDot = createDoubleDot();
	var text = createText(elem.text);
	var date = createDate(elem.timestamp);

	var li = document.createElement("li");
	li.appendChild(author);
	li.appendChild(doubleDot);
	li.appendChild(text);
	li.appendChild(date);

	message.appendChild(li);

	return message;
}

function createAlertMessageContainer(type) {
	var div = document.createElement("div");
	div.className = "msg alert alert-" + type;
	return div;
}