function updateObjectList() {
    let url = '';
    const objectColumn = document.getElementById('objectColumn');
    objectColumn.innerHTML = '';

    switch (selectedModule) {
        case 'classSelector':
            url = '/getClasses';
            break;
        case 'teachersSelector':
            url = '/getTeachers';
            break;
        case 'studentsSelector':
            url = '/getSchoolStudents';
            break;
        case 'administrationSelector':
            url = '/getAdministrators';
            break;
        default:
            return;
    }

    if (selectedElementId) {
        url += `?schoolId=${selectedElementId}`;
    }

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Сетевая ошибка');
            }
            return response.json();
        })
        .then(objects => {
            objects.forEach(object => {
                const objectDiv = document.createElement('div');
                let displayText;

                if (selectedModule === 'classSelector') {
                    displayText = object.name;
                } else {
                    displayText = `${object.lastName} ${object.firstName}`;
                    if (object.patronymic) {
                        displayText += ` ${object.patronymic}`;
                    }
                }

                objectDiv.innerText = displayText;
                objectDiv.id = object.id;
                objectColumn.appendChild(objectDiv);
            });
        })
       .catch(error => {
           console.log('Нет доступных данных:', error);
       });
}

const deleteObjectButton = document.getElementById('deleteObjectButton');
deleteObjectButton.addEventListener('click', function() {
    if (selectedObjectId) {
        let url = '';

        switch (selectedModule) {
            case 'classSelector':
                url = '/deleteClass';
                break;
            case 'teachersSelector':
                url = '/deleteTeacher';
                break;
            case 'studentsSelector':
                url = '/deleteSchoolStudent';
                break;
            case 'administrationSelector':
                url = '/deleteAdministrator';
                break;
            default:
                return;
        }

        if (selectedElementId) {
            url += `?id=${selectedObjectId}`;
        }

        fetch(url, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                updateObjectList();
                selectedObjectId = null;
            } else {
                throw new Error('Ошибка при удалении');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось удалить элемент.');
        });
    } else {
        alert('Пожалуйста, выберите элемент для удаления.');
    }
});