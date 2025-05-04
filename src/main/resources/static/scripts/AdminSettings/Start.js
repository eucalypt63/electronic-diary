let userRole = null;
document.addEventListener("DOMContentLoaded", function () {
    updateSchoolList();
});

function updateSchoolList() {
    fetch('/getRole', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.text())
        .then(role => {
            userRole = role;
            if (role !== "Main admin") {
                const educationModule = document.getElementById("moduleAddEducation");
                if (educationModule) {
                    educationModule.remove();
                }

                const administratorModule = document.getElementById("moduleAddAdministrator");
                if (administratorModule) {
                    administratorModule.remove();
                }
            }
            const endpoint = (role === "Main admin") ? '/getSchools' : '/getSchoolByAuthorizationAdminId';
            return fetch(endpoint);
        })
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

                schoolDiv.addEventListener('dblclick', () => {
                    window.location.href = `EducationPage?id=${institution.id}`;//
                });

                educationColumn.appendChild(schoolDiv);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}

function updateHeader(content) {
    document.getElementById('secondColumnHeader').innerText = content;
}

let selectedElementId = null;
let selectedModule = null;
let selectedObjectId = null;
let selectedObjectColumnThreeId = null;

// Обработка нажатия на школу
const educationColumn = document.getElementById('educationColumn');
educationColumn.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV' && selectedElementId !== event.target.id) {
        const divs = educationColumn.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        selectedElementId = event.target.id;

        selectedObjectColumnThreeId = null;
        selectedObjectId = null;

        if (selectedModule !== null){
            updateObjectList();
        }
    }
});

// Обработка нажатия на объект столбца 2
const objectColumn = document.getElementById('objectColumn');
objectColumn.addEventListener('click', function(event) {
if (event.target.tagName === 'DIV' && selectedObjectId !== event.target.id) {
    const divs = objectColumn.querySelectorAll('div');
    divs.forEach(div => div.classList.remove('active'));
    event.target.classList.add('active');

    selectedObjectId = event.target.id;

    selectedObjectColumnThreeId = null;

    if (selectedModule == "classSelector")
    {
        updateColumnThreeList();
    }
}
});

// Обработка нажатия на объект столбца 3
const objectColumnThree = document.getElementById('objectColumnThree');
objectColumnThree.addEventListener('click', function(event) {
if (event.target.tagName === 'DIV' && selectedObjectColumnThreeId !== event.target.id) {
    const divs = objectColumnThree.querySelectorAll('div');
    divs.forEach(div => div.classList.remove('active'));
    event.target.classList.add('active');

    selectedObjectColumnThreeId = event.target.id;
}
});

// Обработка нажатия на селектор столбца 2
const selector = document.querySelector('.selector');
selector.addEventListener('click', function(event) {
    if (event.target.tagName === 'DIV' && selectedModule !== event.target.id) {
        selectedObjectId = null

        const divs = selector.querySelectorAll('div');
        divs.forEach(div => div.classList.remove('active'));
        event.target.classList.add('active');

        selectedObjectColumnThreeId = null;
        selectedObjectId = null;

        selectedModule = event.target.id;
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
    document.getElementById('moduleAddStudent').style.display = 'none';
}

document.getElementById('addObjectButton').addEventListener('click', function() {
    if (!selectedModule) {
        alert('Пожалуйста, выберите объект для добавления.');
        return;
    }

    closeAllModules();
    if (selectedModule === 'administrationSelector') {
        if (userRole == "Main admin"){
            document.getElementById('moduleAddAdministrator').style.display = 'block';
        } else {
            alert('Доступ ограничен');
        }
    } else if (selectedModule === 'teachersSelector') {
        document.getElementById('moduleAddTeacher').style.display = 'block';
    } else if (selectedModule === 'classSelector') {
        const schoolId = selectedElementId;
        const teacherSelect = document.getElementById('teacherSelect');

        teacherSelect.innerHTML = '';

        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.disabled = true;
        defaultOption.selected = true;
        defaultOption.textContent = 'Выберите учителя';
        teacherSelect.appendChild(defaultOption);

        fetch(`/getTeachersToClass?schoolId=${schoolId}`)
            .then(response => response.json())
            .then(teachers => {
            teachers.forEach(teacher => {
                const option = document.createElement('option');
                option.value = teacher.id;
                const fullName = `${teacher.firstName} ${teacher.lastName}` +
                    (teacher.patronymic ? ` ${teacher.patronymic}` : '');
                option.textContent = fullName.trim();
                teacherSelect.appendChild(option);
            });
            teacherSelect.disabled = false;

            });
        teacherSelect.selectedIndex = 0;
        document.getElementById('moduleAddClass').style.display = 'block';
    }
});

document.getElementById('addObjectButtonС3').addEventListener('click', function() {
    if (!selectedObjectId) {
        alert('Пожалуйста, выберите объект для добавления.');
        return;
    }

    closeAllModules();
    if (selectedModule === 'classSelector') {
        document.getElementById('moduleAddStudent').style.display = 'block';
    }
});