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

            if (!Array.isArray(institutions) || institutions.length === 0) {
                console.log('Нет доступных данных.');
                return;
            }

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



let selectedModule = null;

// Обработка нажатия на школу
let selectedElementId = null;
const educationColumn = document.getElementById('educationColumn');
educationColumn.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV') {
        const divs = educationColumn.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        selectedElementId = event.target.id;

        if (selectedModule !== null){
            updateObjectList();
        }
    }
});

// Обработка нажатия на объект
let selectedObjectId = null;
const objectColumn = document.getElementById('objectColumn');
objectColumn.addEventListener('click', function(event) {
if (event.target.tagName === 'DIV') {
        const divs = objectColumn.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        selectedObjectId = event.target.id;
    }
});

// Обработка нажатия на селектор столбца 2
const selector = document.querySelector('.selector');
selector.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV') {
        const divs = selector.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        const newModule = event.target.id;

        selectedModule = newModule;

        if(selectedElementId !== null){
            updateObjectList();
        }
    }
});



// Обработка логики отображения модулей столбца 2
function closeAllModules() {
    document.getElementById('moduleAddAdministrator').style.display = 'none';
    document.getElementById('moduleAddTeacher').style.display = 'none';
    document.getElementById('moduleAddEducation').style.display = 'none';
    document.getElementById('moduleAddClass').style.display = 'none';
}

const teacherSelect = document.getElementById('teacherSelect');
document.getElementById('addObjectButton').addEventListener('click', function() {
    if (!selectedModule) {
        alert('Пожалуйста, выберите объект для добавления.');
        return;
    }

    closeAllModules();
    if (selectedModule === 'administrationSelector') {
        document.getElementById('moduleAddAdministrator').style.display = 'block';
    } else if (selectedModule === 'teachersSelector') {
        document.getElementById('moduleAddTeacher').style.display = 'block';
    } else if (selectedModule === 'classSelector') {
        const schoolId = selectedElementId;
        fetch(`/getTeachers?schoolId=${schoolId}`)
            .then(response => response.json())
            .then(teachers => {
                teacherSelect.innerHTML = '';
                teachers.forEach(teacher => {
                    const option = document.createElement('option');
                    option.value = teacher.id;
                    const fullName = `${teacher.firstName} ${teacher.lastName}` +
                                                 (teacher.patronymic ? ` ${teacher.patronymic}` : '');
                    option.textContent = fullName.trim();
                    teacherSelect.appendChild(option);
                });
            });
        document.getElementById('moduleAddClass').style.display = 'block';
    }
});