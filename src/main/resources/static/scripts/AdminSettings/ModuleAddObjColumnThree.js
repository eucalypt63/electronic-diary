function updateColumnThreeList() {
    let url = '';
    const objectColumn = document.getElementById('objectColumnThree');
    objectColumn.innerHTML = '';

    switch (selectedModule) {
        case 'classSelector':
            url = '/getStudentsOfClass';
            break;
        case 'studentsSelector':
            url = '/getStudentParents';
            break;
        default:
            return;
    }

    if (selectedObjectId) {
        url += `?ObjectId=${selectedObjectId}`;
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
                    displayText = `${object.lastName} ${object.firstName}`;
                    if (object.patronymic) {
                        displayText += ` ${object.patronymic}`; // Инфо о ученике класса
                    }
                } else if (selectedModule === 'studentsSelector') {
                    displayText = `${object.lastName} ${object.firstName}`;
                    if (object.patronymic) {
                        displayText += ` ${object.patronymic}`; // Инфо о родителях ученика
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

const deleteObjectButtonC3 = document.getElementById('deleteObjectButtonС3');
deleteObjectButtonC3.addEventListener('click', function() {
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