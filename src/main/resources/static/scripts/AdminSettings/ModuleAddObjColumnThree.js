function updateColumnThreeList() {
    let url = '';
    const objectColumn = document.getElementById('objectColumnThree');
    objectColumn.innerHTML = '';

    switch (selectedModule) {
        case 'classSelector':
            url = '/getStudentsOfClass';
            document.getElementById('thirdColumnHeader').innerText = "Ученики";
            break;
        case 'studentsSelector':
            url = '/getStudentParents';
            document.getElementById('thirdColumnHeader').innerText = "Родители";
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
    if (selectedObjectColumnThreeId) {
        let url = '';

        switch (selectedModule) {
            case 'classSelector':
                url = '/deleteSchoolStudent';
                break;
            case 'studentsSelector':
                url = '/deleteStudentParents';
                break;
            default:
                return;
        }

        url += `?id=${selectedObjectColumnThreeId}`;


        fetch(url, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                updateColumnThreeList();
                selectedObjectColumnThreeId = null;
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