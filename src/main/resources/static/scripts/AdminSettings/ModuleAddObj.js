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

                // Формируем текст для отображения в зависимости от типа объекта
                if (selectedModule === 'classSelector') {
                    displayText = object.name; // Имя для классов
                } else {
                    // ФИО для остальных объектов
                    displayText = `${object.firstName} ${object.lastName}`;
                    if (object.patronymic) {
                        displayText += ` ${object.patronymic}`; // Добавляем отчество, если оно есть
                    }
                }

                objectDiv.innerText = displayText; // Устанавливаем текст для отображения
                objectDiv.id = object.id; // Предполагается, что у объекта есть поле id
                objectColumn.appendChild(objectDiv);
            });
        })
       .catch(error => {
           console.log('Нет доступных данных:', error);
       });
}