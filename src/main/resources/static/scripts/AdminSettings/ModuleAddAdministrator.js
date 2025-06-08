window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddAdministrator');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

document.getElementById('submitAdminButton').addEventListener('click', function() {
    const adminFirstName = document.getElementById('adminFirstName').value;
    const adminLastName = document.getElementById('adminLastName').value;
    const adminPatronymic = document.getElementById('adminPatronymic').value;
    const adminLogin = document.getElementById('adminLogin').value;
    const adminPassword = document.getElementById('adminPassword').value;
    const adminEmail = document.getElementById('adminEmail').value;
    const adminPhoneNumber = document.getElementById('adminPhoneNumber').value;
    const administrationTypeId = document.getElementById('administrationSelect').value;

    const universityId = selectedElementId;

    if (adminFirstName && adminLastName && adminLogin && adminPassword && administrationTypeId) {
        const data = {
            firstName: adminFirstName,
            lastName: adminLastName,
            patronymic: adminPatronymic,
            login: adminLogin,
            password: adminPassword,
            email: adminEmail,
            phoneNumber: adminPhoneNumber,
            universityId: universityId,
            administrationsTypeId: administrationTypeId
        };

        fetch('/addAdministrator', {
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
            throw new Error('Ошибка при добавлении администратора');
        })
        .then(data => {
            const modal = document.getElementById('moduleAddAdministrator');
            modal.style.display = 'none';

            document.getElementById('adminFirstName').value = '';
            document.getElementById('adminLastName').value = '';
            document.getElementById('adminPatronymic').value = '';
            document.getElementById('adminLogin').value = '';
            document.getElementById('adminPassword').value = '';
            document.getElementById('adminEmail').value = '';
            document.getElementById('adminPhoneNumber').value = '';

            updateObjectList();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить администратора');
        });
    } else {
        alert('Пожалуйста, заполните все обязательные поля.');
    }
});