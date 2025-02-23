function updateHeader(content) {
    document.getElementById('secondColumnHeader').innerText = content;
}

document.querySelector('.logout-button').addEventListener('click', function() {
    fetch('/logout', {
        method: 'GET',
        credentials: 'include'
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    })
    .catch(error => console.error('Ошибка при выходе:', error));
});

function updateSchoolList() {
    fetch('/getSchools')
        .then(response => {
            if (!response.ok) {
                throw new Error('Сетевая ошибка');
            }
            return response.json();
        })
        .then(institutions => {
            const educationColumn = document.getElementById('educationColumn');
            educationColumn.innerHTML = '';

            institutions.forEach(institution => {
                const schoolDiv = document.createElement('div');
                schoolDiv.innerText = institution.name;
                schoolDiv.id = institution.id;
                educationColumn.appendChild(schoolDiv);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}
updateSchoolList();


let selectedElementId = null;

// Обработка нажатия на школу
const educationColumn = document.getElementById('educationColumn');
educationColumn.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV') {
        const divs = educationColumn.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        selectedElementId = event.target.id;
    }
});


// Обработка нажатия на селектор столбца 2
const selector = document.querySelector('.selector');
selector.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV') {
        const divs = selector.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');
    }
});