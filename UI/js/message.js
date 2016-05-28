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

function createElem(divType, className, innerText) {
	var div = document.createElement(divType);
	div.className = className;
	div.innerText = innerText;
	return div;
}

function createAuthor(author) {
	return createElem("div", "author", author);
}

function createDoubleDot() {
	return createElem("span", "double-dot", ":");
}

function createText(value) {
	return createElem("div", "text", value);
}

function createChangedLabel() {
	return createElem("div", "changed", "Edited");
}

function createDeleteLabel() {
	return createElem("div", "changed", "Deleted by " + author);
}

function createDate(date) {
	return createElem("div", "date", formatDate(new Date(date)));
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