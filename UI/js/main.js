/*
Global variables
*/
var Application = {
	mainUrl : 'http://localhost:8080/chat',
	messageList:[],
	token : 'TN11EN'
};

var gAuthor = document.getElementById("profile-name").innerText;

/*
Run
*/
function run() {
	logic();

	loadMessages(function(){
		render(Application);
	});
}

/*
New message
*/
function newMessage(text, edited, removed) {
	return {
		id: '' + uniqueId(),
		author: gAuthor,
		text: text,
		timestamp: new Date().getTime(),
		edited: !!edited,
		removed: !!removed
	};
}

function uniqueId() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random);
}

/*
Main functions for work with messages
*/
function loadMessages(done) {
	var url = Application.mainUrl + '?token=' + Application.token;

	ajax('GET', url, null, function(responseText){
		var response = JSON.parse(responseText);

		Application.messageList = response.messages;
		Application.token = response.token;
		done();
	});
}

function addMessage(text, done) {
	if(text == '' || text == null)
		return;

	var message = newMessage(text, false, false);

	ajax('POST', Application.mainUrl, JSON.stringify(message), function(){
		Application.messageList.push(message);
		done();
	});
}

function deleteMessage(id, done) {
	var index = indexById(Application.messageList, id);
	var message = Application.messageList[index];
	var messageToDelete = {
		id:message.id
	};

	ajax('DELETE', Application.mainUrl, JSON.stringify(messageToDelete), function(){
		message.remove = !message.remove;
		done();
	});	
}

function editMessage(id, done) {
	var index = indexById(Application.messageList, id);
	var message = Application.messageList[index];
	var messageToPut = {
		id:message.id, 
		text:message.text
	};

	ajax('PUT', Application.mainUrl, JSON.stringify(messageToPut), function(){
		message.edited = !message.edited;
		done();
	});
}

/*
Render
*/
function render(root) {
	var items = document.getElementsByClassName('chat-list')[0];
	var messagesMap = root.messageList.reduce(function(accumulator, task) {
		accumulator[task.id] = task;

		return accumulator;
	},{});
	var notFound = updateList(items, messagesMap);
	removeFromList(items, notFound);
	appendToList(items, root.messageList, messagesMap);
}

function renderMessage(element, message){
	element.setAttribute('data-msg-id', message.id);
	if (message.removed) {
		addClass(element, "removed");
	}
}

function updateList(element, itemMap) {
	var children = element.children;
	var notFound = [];
	
	for(var i = 0; i < children.length; i++) {
		var child = children[i];
		var id = child.attributes['data-msg-id'].value;
		var item = itemMap[id];
		
		if(item == null) {
			notFound.push(child);
			continue;
		}
		
		renderMessage(child, item);
		itemMap[id] = null;
	}
	
	return notFound;
}

function appendToList(element, items, itemMap) {
	for(var i = 0; i < items.length; i++) {
		var item = items[i];
		
		if(itemMap[item.id] == null) {
			continue;
		}
		itemMap[item.id] = null;
		
		var child = createMessage(item);
		
		renderMessage(child, item);
		element.appendChild(child);
	}
}

function removeFromList(element, children) {
	for(var i = 0; i < children.length; i++) {
		element.removeChild(children[i]);
	}
}

/*
Ajax
*/

function output(value){
	var output = document.getElementById('output');

	output.innerText = JSON.stringify(value, null, 2);
}

function defaultErrorHandler(message) {
	console.error(message);
	output(message);
}

function isError(text) {
	if(text == "")
		return false;
	
	try {
		var obj = JSON.parse(text);
	} catch(ex) {
		return true;
	}

	return !!obj.error;
}

function ajax(method, url, data, continueWith, continueWithError) {
	var xhr = new XMLHttpRequest();

	continueWithError = continueWithError || defaultErrorHandler;
	xhr.open(method || 'GET', url, true);

	xhr.onload = function () {
		if (xhr.readyState !== 4)
			return;

		if(xhr.status != 200) {
			continueWithError('Error on the server side, response ' + xhr.status);
			return;
		}

		if(isError(xhr.responseText)) {
			continueWithError('Error on the server side, response ' + xhr.responseText);
			return;
		}

		continueWith(xhr.responseText);
	};    

	xhr.ontimeout = function () {
		ontinueWithError('Server timed out !');
	};

	xhr.onerror = function (e) {
		var errMsg = 'Server connection error !\n'+
		'\n' +
		'Check if \n'+
		'- server is active\n'+
		'- server sends header "Access-Control-Allow-Origin:*"\n'+
		'- server sends header "Access-Control-Allow-Methods: PUT, DELETE, POST, GET, OPTIONS"\n';

		continueWithError(errMsg);
	};

	xhr.send(data);
}

window.onerror = function(err) {
	output(err.toString());
};