window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddStudent');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

document.getElementById('submitStudentButton').addEventListener('click', function() {
    const firstName = document.getElementById('studentFirstName').value;
    const lastName = document.getElementById('studentLastName').value;
    const patronymic = document.getElementById('studentPatronymic').value;
    const email = document.getElementById('studentEmail').value;
    const phoneNumber = document.getElementById('studentPhoneNumber').value;
    const login = document.getElementById('studentLogin').value;
    const password = document.getElementById('studentPassword').value;
    const classSelect = document.getElementById('classSelect');
    const selectedClassId = classSelect.options[classSelect.selectedIndex].value;

    if (firstName && lastName && selectedClassId && login && password) {
        const data = {
            firstName,
            lastName,
            patronymic,
            email,
            phoneNumber,
            login,
            password,
            classRoomId: selectedClassId
        };

        fetch('/addSchoolStudent', { // Замените на соответствующий URL
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
            throw new Error('Ошибка при добавлении студента');
        })
        .then(data => {
            const modal = document.getElementById('moduleAddStudent');
            modal.style.display = 'none';
            document.getElementById('studentFirstName').value = '';
            document.getElementById('studentLastName').value = '';
            document.getElementById('studentPatronymic').value = '';
            document.getElementById('studentEmail').value = '';
            document.getElementById('studentPhoneNumber').value = '';
            document.getElementById('studentLogin').value = '';
            document.getElementById('studentPassword').value = '';
            classSelect.selectedIndex = 0;

            updateObjectList();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить студента');
        });
    } else {
        alert('Пожалуйста, заполните обязательные поля.');
    }
});