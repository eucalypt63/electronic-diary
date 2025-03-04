function updateObjectList() {
    const objectColumn = document.getElementById('objectColumn');
    objectColumn.innerHTML = '';

    const objectColumn3 = document.getElementById('objectColumnThree');
    objectColumn3.innerHTML = '';

    document.getElementById('thirdColumnHeader').innerText = "Выберите объект";

    if (selectedElementId) {
        let url = '';

        switch (selectedModule) {
            case 'classSelector':
                url = '/getClasses';
                break;
            case 'teachersSelector':
                url = '/getTeachers';
                break;
            case 'administrationSelector':
                url = '/getAdministrators';
                break;
            default:
                return;
        }


        url += `?schoolId=${selectedElementId}`;


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

                    objectDiv.addEventListener('dblclick', () => {
                        let url;
                        switch (selectedModule) {
                            case 'classSelector':
                                url = 'classPage';
                                break;
                            case 'teachersSelector':
                                url = 'profileTeacher';
                                break;
                            case 'administrationSelector':
                                url = 'profileAdministrator';
                                break;
                            default:
                                return;
                        }
                        window.location.href = `${url}?id=${object.id}`;
                    });

                    objectColumn.appendChild(objectDiv);
                });
            })
       .catch(error => {
           console.log('Нет доступных данных:', error);
       });
    }
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
                if (userRole == "Main admin"){
                    url = '/deleteAdministrator';
                } else {
                    alert('Доступ ограничен');
                    return;
                }
                break;
            default:
                return;
        }

        url += `?id=${selectedObjectId}`;

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