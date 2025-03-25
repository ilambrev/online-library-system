const host = 'http://localhost:8080';
const dataListAuthors = document.querySelector('#authors');
const dataListPublishers = document.querySelector('#publishers');
const inputAuthor = document.querySelector('#authorName');
const inputAuthorHidden = document.querySelector('#hidden-author-input');
const authorsIds = {};

inputAuthor.addEventListener('input', (e) => {
    let inputValue = e.currentTarget.value;
    if (authorsIds.hasOwnProperty(inputValue)) {
        inputValue = authorsIds[inputValue];
    }
    inputAuthorHidden.value = inputValue;
});

async function getAllAuthors() {
    const url = '/api/authors/all';

    const response = await fetch(host + url);
    const json = await response.json();
    const authorsNames = Object.values(json)
        .map(a => [`${a.id}`, `${a.firstName} ${a.lastName}`])
        .sort((n1, n2) => n1[1].localeCompare(n2[1]));

    return authorsNames;
}

async function getAllPublishers() {
    const url = '/api/publishers/all';

    const response = await fetch(host + url);
    const json = await response.json();
    const publishersNames = Object.values(json)
        .map(p => p.name)
        .sort((n1, n2) => n1[1].localeCompare(n2[1]));

    return publishersNames;
}

async function autocomplete() {
    const authors = await getAllAuthors();
    const publishers = await getAllPublishers();

    const authorsOptions = authors.reduce((acc, curr) => {
        authorsIds[curr[1]] = curr[0];
        const option = document.createElement('option');
        option.value = curr[1];
        acc.push(option);

        return acc;
    }, []);

    const publishersOptions = publishers.reduce((acc, curr) => {
        const option = document.createElement('option');
        option.value = curr;
        acc.push(option);

        return acc;
    }, []);

    dataListAuthors.append(...authorsOptions);
    dataListPublishers.append(...publishersOptions);
}

autocomplete();