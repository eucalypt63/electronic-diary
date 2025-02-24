window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddTeacher');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

document.getElementById('submitTeacherButton').addEventListener('click', function() {
    const teacherFirstName = document.getElementById('teacherFirstName').value;
    const teacherLastName = document.getElementById('teacherLastName').value;
    const teacherPatronymic = document.getElementById('teacherPatronymic').value;
    const teacherLogin = document.getElementById('teacherLogin').value;
    const teacherPassword = document.getElementById('teacherPassword').value;
    const teacherEmail = document.getElementById('teacherEmail').value;
    const teacherPhoneNumber = document.getElementById('teacherPhoneNumber').value;

    const universityId = selectedElementId;

    if (teacherFirstName && teacherLastName && teacherLogin && teacherPassword) {
        const data = {
            firstName: teacherFirstName,
            lastName: teacherLastName,
            patronymic: teacherPatronymic,
            login: teacherLogin,
            password: teacherPassword,
            email: teacherEmail,
            phoneNumber: teacherPhoneNumber,
            universityId: universityId
        };

        fetch('/addTeacher', {
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
            throw new Error('Ошибка при добавлении учителя');
        })
        .then(data => {
            const modal = document.getElementById('moduleAddTeacher');
            modal.style.display = 'none';
            document.getElementById('teacherFirstName').value = '';
            document.getElementById('teacherLastName').value = '';
            document.getElementById('teacherPatronymic').value = '';
            document.getElementById('teacherLogin').value = '';
            document.getElementById('teacherPassword').value = '';
            document.getElementById('teacherEmail').value = '';
            document.getElementById('teacherPhoneNumber').value = '';

            updateObjectList();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить учителя');
        });
    } else {
        alert('Пожалуйста, заполните обязательные поля.');
    }
});