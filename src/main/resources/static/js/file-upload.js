const uploadFileInput = document.getElementById("imageFile");
const errorMessage = document.getElementById("error");

uploadFileInput.addEventListener("change", function() {
    const file = this.files[0];
    if (file && file )
        if (file && file.size > 5 * 1024 * 1024) {
            errorMessage.hidden = false;
            this.value = "";
        } else {
            errorMessage.hidden = true;
        }
});