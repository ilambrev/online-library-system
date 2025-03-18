const dataList = document.querySelector('#authors');

async function getAllAuthors() {
    const host = 'http://localhost:8080';
    const url = '/api/authors/all';

    const response = await fetch(host + url);
    const json = await response.json();
    const authorsNames = Object.values(json)
        .map(a => a.firstName + ' ' + a.lastName)
        .sort((n1, n2) => n1.localeCompare(n2));

    return authorsNames;
}

async function autocomplete() {
    const authors = await getAllAuthors();

    authors.forEach(a => {
        const option = document.createElement('option');
        option.value = a;
        dataList.appendChild(option);
    });
}

autocomplete();