function showDeletePopup() {
    document.getElementById("delete-popup").style.display = "block";
}

function hideDeletePopup() {
    document.getElementById("delete-popup").style.display = "none";
}

function deleteRecord() {
    // send a DELETE request to your server to delete the record from the database
    // you can use fetch() or XMLHttpRequest() for this
    // once the record has been deleted, you can hide the delete popup
    hideDeletePopup();
}