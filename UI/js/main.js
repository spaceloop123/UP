/*
Global variables
*/
var messageList = [];
var author = "None";

var authorKey = "author";
var messagesKey = "chat history";

/*
Run
*/
function run() {
	addClickEvent();
	
	loadFromLocalStorage();

	render(messageList);
}

/*
Load
*/
function loadFromLocalStorage() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	messageList = loadMessages() ||  [
	// newMessage("Hello and welcome", "Server", false, false, false)
	newAlertMessage("Hello and welcome. Please, start chat now...", "Server", "success")
	];

	author = loadAuthor() || "Guest";
}

function loadMessages() {
	var item = localStorage.getItem(messagesKey);

	return item && JSON.parse(item);
}

function loadAuthor() {
	var item = localStorage.getItem(authorKey);
	
	return item && JSON.parse(item);
}

/*
Save
*/
function saveMessages(list) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem(messagesKey, JSON.stringify(list));
}

function saveAuthor(value) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem(authorKey, JSON.stringify(value));
}

/*
Render
*/
function render(list) {
	for(var i = 0; i < list.length; i++) {
		renderMessage(list[i]);
	}

	renderAuthor(author);
}

function renderAuthor(author) {
	document.getElementById("profile-name").textContent = author;
}

function renderMessage(elem){
	var messages = document.getElementsByClassName("chat-list")[0];
	var message;
	if(elem.type == undefined) {
		message = createMessage(elem);
	} else {
		message = createAlertMessage(elem);
	}

	messages.appendChild(message);
}

/*
New message
*/
function newMessage(text, author, changed, removed, isMy) {
	return {
		id: '' + uniqueId(),
		author: author,
		text: text,
		timestamp: new Date().getTime(),
		removed: !!removed,
		changed: !!changed,
		isMy: !!isMy
	};
}

function newAlertMessage(text, author, type) {
	return {
		id: '' + uniqueId(),
		author: author,
		text: text,
		type: type,
		timestamp: new Date().getTime()
	};
}

function uniqueId() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random);
}

/*
Update
*/
function update() {
	saveMessages(messageList);
	saveAuthor(author);

	document.body.scrollTop = document.body.scrollHeight - document.body.clientHeight;
}