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
Test
*/
var isEdit = false;

var fab = document.getElementById("fab");
// var close = document.getElementById("close");
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
    // addClass(close, "active");
    addClass(accept, "active");
    addClass(decline, "active");
    addClass(textInputName, "active");
    addClass(pencilIcon, "active");

    isEdit = true;
    delay = setTimeout(focusDelay, 250);
    document.getElementById("myname-edit").value = user.textContent;
  }
});

accept.addEventListener("click", function(e) {
  e.preventDefault();

  if(isEdit) {
    removeClass(fab, "active");
    // removeClass(close, "active");
    removeClass(accept, "active");
    removeClass(decline, "active");
    removeClass(textInputName, "active");
    removeClass(pencilIcon, "active");

    user.textContent = document.getElementById("myname-edit").value;

    isEdit = false;
    clearTimeout(delay);
  }
});

function focusDelay() {
  document.getElementById("myname-edit").focus();
}

}(this, this.document));