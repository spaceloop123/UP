var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");

var isEditMessage = false;

msgInputArea.addEventListener('keydown', function(e) {
	if(e.keyCode == 13 && e.ctrlKey) {
		if(this.value === "") 
			return false;
		
		if(!isEditMessage) {
			addMessage(this.value, function() {
				render(Application);
			});
		} else {
			switchBtnIcon();
			isEditMessage = false;

			editMessage(this.value);
		}

		this.value = "";

		return false;
	}
});

msgInputBtn.addEventListener('click', function() {
	if(msgInputArea.value === "") 
		return true;

	if(!isEditMessage) {
		addClass(this, "active");

		addMessage(msgInputArea.value, function() {
			render(Application);
		});
		
		removeClass(this, "active");
	} else {
		switchBtnIcon();
		isEditMessage = false;

		editMessage(msgInputArea.value);	
	}
	
	msgInputArea.value = "";

	return false;
}, true);