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


  var isEdit = false;
  
  var menuBtn = document.getElementById("btn-group");
  menuBtn.addEventListener('click', function(e) {
    e.preventDefault();

    if(isEdit) {
      toggleClass(document.getElementById("card"), "flipped");
      removeClass(document.getElementById("btn-container"), "edit");
      toggleClass(document.getElementById("plus-icon"), "hidden");
      toggleClass(document.getElementById("plus-icon"), "rotate");
      toggleClass(document.getElementById("check-icon"), "hidden");

      user.textContent = document.getElementById("myname-edit").value;

      var obj = {a:1,b:2,c:{d:3, e:4}};

      JSON.stringify(obj, null, "    ");
      
      isEdit = false;
    } else {
      toggleClass(document.getElementById("btn-container"), "active");
      toggleClass(document.getElementById("plus-icon"), "rotate");
    }
  });

  var editBtn = document.getElementById("edit-btn");
  var myName = document.getElementById("myname-edit");

  var user = document.getElementById("profile-name");

  editBtn.addEventListener('click', function(e) {
    e.preventDefault();

    if (!isEdit) {
      toggleClass(document.getElementById("card"), "flipped");
      removeClass(document.getElementById("btn-container"), "active");
      addClass(document.getElementById("btn-container"), "edit");
      toggleClass(document.getElementById("plus-icon"), "hidden");
      toggleClass(document.getElementById("check-icon"), "hidden");
      isEdit = true;
      
      document.getElementById("myname-edit").focus();
      document.getElementById("myname-edit").value = user.textContent;

    } else {
      toggleClass(document.getElementById("card"), "flipped");
      removeClass(document.getElementById("btn-container"), "edit");
      toggleClass(document.getElementById("plus-icon"), "hidden");
      toggleClass(document.getElementById("plus-icon"), "rotate");
      toggleClass(document.getElementById("check-icon"), "hidden");
    }
  });

}(this, this.document));