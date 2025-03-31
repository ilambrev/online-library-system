const inputFields = Array.from(document.querySelectorAll('input[type="radio"]'));
const editButton = document.querySelector('#edit-btn');
const closeButton = document.querySelector('#close-btn');
const saveButton = document.querySelector('#save-btn');
const cancelButton = document.querySelector('#cancel-btn');
const fieldsValues = inputFields.map(f => f.value);
const radioChecked = inputFields.find(f => f.type === 'radio' && f.checked === true);

inputFields.forEach(f => f.disabled = true);

editButton.addEventListener('click', (e) => {
    e.preventDefault();
    inputFields.forEach(f => f.disabled = false);
    saveButton.style.display = 'block';
    cancelButton.style.display = 'block';
    editButton.style.display = 'none';
    closeButton.style.display = 'none';
});

cancelButton.addEventListener('click', (e) => {
    e.preventDefault();
    inputFields.map((f, i) => f.value = fieldsValues[i]);
    inputFields.forEach(f => {
        f.disabled = true;
        f.checked = false;
    });
    radioChecked.checked = true;
    saveButton.style.display = 'none';
    cancelButton.style.display = 'none';
    editButton.style.display = 'block';
    closeButton.style.display = 'block';
});