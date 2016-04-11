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
var close = document.getElementById("close");
var textInputName = document.getElementById("text-input-name");
var myName = document.getElementById("myname-edit");

var user = document.getElementById("profile-name");

fab.addEventListener("click", function(e) {
  e.preventDefault();

  addClass(fab, "active");
  addClass(close, "active");
  addClass(textInputName, "active");

  isEdit = true;

  document.getElementById("myname-edit").focus();
  document.getElementById("myname-edit").value = user.textContent;
});

close.addEventListener("click", function(e) {
  e.preventDefault();

  removeClass(fab, "active");
  removeClass(close, "active");
  removeClass(textInputName, "active");

  user.textContent = document.getElementById("myname-edit").value;

  isEdit = false;
});

}(this, this.document));