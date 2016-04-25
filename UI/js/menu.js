(function (window, document) {

/*
Side menu in mobile and tablet version
*/
var layout   = document.getElementById('layout'),
menu     = document.getElementById('menu'),
menuLink = document.getElementById('menuLink');

menuLink.addEventListener("click", function (e) {
  e.preventDefault();

  toggleClass(layout, "active");
  toggleClass(menu, "active");
  toggleClass(menuLink, "active");
});

/*
FAB logic
*/
var isEdit = false;

var fab = document.getElementById("fab");
var accept = document.getElementById("accept");
var decline = document.getElementById("decline");
var textInputName = document.getElementById("text-input-name");
var myName = document.getElementById("myname-edit");
var pencilIcon = document.getElementById("pencil-icon");

var user = document.getElementById("profile-name");

var delay;

fab.addEventListener("click", function(e) {
  e.preventDefault();

  if(!isEdit) {
    addClass(fab, "active");
    addClass(accept, "active");
    addClass(decline, "active");
    addClass(textInputName, "active");
    addClass(pencilIcon, "active");

    isEdit = true;
    delay = setTimeout(focusDelay, 280);
    document.getElementById("myname-edit").value = user.textContent;
  }
});

function focusDelay() {
  document.getElementById("myname-edit").focus();
}

accept.addEventListener("click", function(e) {
  e.preventDefault();

  if(isEdit && !document.getElementById("myname-edit").value) {
    return false;
  }

  if(isEdit) {
    finishEdit();
    
    if(user.textContent !== document.getElementById("myname-edit").value) {
      sendAlert("\"" + user.textContent + "\" is now known as \"" + document.getElementById("myname-edit").value + "\"");
    }

    user.textContent = document.getElementById("myname-edit").value;
  }
});

decline.addEventListener("click", function(e) {
  e.preventDefault();

  if(isEdit) {
    finishEdit();
  }
});

function finishEdit() {
  removeClass(fab, "active");
  removeClass(accept, "active");
  removeClass(decline, "active");
  removeClass(textInputName, "active");
  removeClass(pencilIcon, "active");

  isEdit = false;
  
  clearTimeout(delay);
}

function sendAlert(value) {
  var message = createAlertMessage(value, "success");
  var messages = document.getElementsByClassName("chat-list")[0];

  messages.appendChild(message);

  document.body.scrollTop = document.body.scrollHeight - document.body.clientHeight;
}

function createAlertMessage(value, type) {
  var message = createAlertMessageContainer(type);
  var author = createAlertAuthor("Server");
  var doubleDot = createDoubleDot();
  var text = createText(value);
  var date = createDate();

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

function createAlertAuthor(author) {
  var div = document.createElement("div");
  div.className = "author";
  div.textContent = author;
  return div;
}

}(this, this.document));