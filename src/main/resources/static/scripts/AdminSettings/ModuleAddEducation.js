// Модуль добавления школы
document.getElementById('addSchoolButton').addEventListener('click', function() {
    // Показываем модальное окно при нажатии на кнопку
    const modal = document.getElementById('modulAddEducation');
    modal.style.display = 'block';
});

// Закрытие модального окна при нажатии на кнопку закрытия
document.querySelector('.close-button').addEventListener('click', function() {
    const modal = document.getElementById('modulAddEducation');
    modal.style.display = 'none';
});

// Закрытие модального окна при клике за его пределами
window.addEventListener('click', function(event) {
    const modal = document.getElementById('modulAddEducation');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

//Добавление школы
document.getElementById('submitSchoolButton').addEventListener('click', function() {
    const schoolName = document.getElementById('schoolName').value;
    const schoolAddress = document.getElementById('schoolAddress').value;
    const schoolEmail = document.getElementById('schoolEmail').value;
    const schoolPhoneNumber = document.getElementById('schoolPhoneNumber').value;

    if (schoolName && schoolAddress) {
        const data = {
            name: schoolName,
            address: schoolAddress,
            email: schoolEmail,
            phoneNumber: schoolPhoneNumber
        };

        fetch('/addEducationalInstitution', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Ошибка при добавлении');
        })
        .then(data => {
            const modal = document.getElementById('modulAddEducation');
            modal.style.display = 'none';
            document.getElementById('schoolName').value = '';
            document.getElementById('schoolAddress').value = '';
            document.getElementById('schoolEmail').value = '';
            document.getElementById('schoolPhoneNumber').value = '';

            updateSchoolList();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить школу');
        });
    } else {
        alert('Пожалуйста, заполните обязательные поля.');
    }
});


const deleteButton = document.getElementById('deleteSchoolButton');
deleteButton.addEventListener('click', function() {
    if (selectedElementId) {
        const elementToDelete = document.getElementById(selectedElementId);
        if (elementToDelete) {
            fetch(`/deleteEducationalInstitution?id=${selectedElementId}`, {
                method: 'DELETE',
            })
            .then(response => {
                if (response.ok) {
                    updateSchoolList();
                } else {
                    throw new Error('Ошибка при удалении');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Не удалось удалить элемент.');
            });
        } else {
            alert('Элемент не найден.');
        }
    } else {
        alert('Пожалуйста, выберите элемент для удаления.');
    }
});