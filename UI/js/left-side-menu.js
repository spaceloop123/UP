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


    var editButton = document.getElementById("btn-group");
    editButton.onclick = function() {
        toggleClass(document.getElementById("btn-container"), "active");
        toggleClass(document.getElementById("plus-icon"), "rotate")
    }

    var fab = document.getElementById("fab");
    var fabSha = document.getElementById("fab-sha");
    var close = document.getElementById("close");
    fab.onclick = function() {
      addClass(fab, "expand");
      addClass(fabSha, "active");
      addClass(close, "active");
  }

  close.onclick = function() {
      removeClass(close, "active");
      removeClass(fab, "expand");
      removeClass(fabSha, "active");
  }

}(this, this.document));