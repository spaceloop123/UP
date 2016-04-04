(function (window, document) {

  var layout   = document.getElementById('layout'),
  menu     = document.getElementById('menu'),
  menuLink = document.getElementById('menuLink');

  menuLink.onclick = function (e) {
    e.preventDefault();

    toggleClass(layout, "active");
    toggleClass(menu, "active");
    toggleClass(menuLink, "active");
  };



  var menuBtn = document.getElementById("btn-group");
  menuBtn.addEventListener('click', function(e) {
    e.preventDefault();

    toggleClass(document.getElementById("btn-container"), "active");
    toggleClass(document.getElementById("plus-icon"), "rotate");
  });



  var editBtn = document.getElementById("edit-btn");
  editBtn.addEventListener('click', function(e) {
    e.preventDefault();

    toggleClass(document.getElementById("card"), "flipped");
    removeClass(document.getElementById("btn-container"), "active");
    addClass(document.getElementById("btn-container"), "edit");
    toggleClass(document.getElementById("plus-icon"), "hidden");
    toggleClass(document.getElementById("check-icon"), "hidden");
  });

}(this, this.document));