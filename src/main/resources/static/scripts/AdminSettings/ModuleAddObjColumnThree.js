function updateColumnThreeList() {
    const objectColumn = document.getElementById('objectColumnThree');
    objectColumn.innerHTML = '';

    let url = 'getStudentsOfClass';
    document.getElementById('thirdColumnHeader').innerText = "Ученики";

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

                displayText = `${object.lastName} ${object.firstName}`;
                if (object.patronymic) {
                    displayText += ` ${object.patronymic}`;
                }

                objectDiv.innerText = displayText;
                objectDiv.id = object.id;

                objectDiv.addEventListener('dblclick', () => {
                    window.location.href = `profileSchoolStudent?id=${object.id}`;
                });


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
        let url = '/deleteSchoolStudent';

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