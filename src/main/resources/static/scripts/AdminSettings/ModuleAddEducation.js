// Модуль добавления школы
document.getElementById('addSchoolButton').addEventListener('click', function() {
    const modal = document.getElementById('moduleAddEducation');
    fetch('/getRegions')
        .then(response => response.json())
        .then(regions => {
            const regionSelect = document.getElementById('regionSelect');
            regionSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.disabled = true;
            defaultOption.selected = true;
            defaultOption.textContent = 'Выберите регион';
            regionSelect.appendChild(defaultOption);

            regions.forEach(region => {
                const option = document.createElement('option');
                option.value = region.id;
                option.textContent = region.name;
                regionSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Ошибка при получении регионов:', error);
            const regionSelect = document.getElementById('regionSelect');
            regionSelect.innerHTML = '<option value="">Ошибка загрузки регионов</option>';
        });
    modal.style.display = 'block';
});

function fetchSettlements(region) {
    if (!region) {
        document.getElementById('settlementSelect').innerHTML = '<option value="">Сначала выберите регион</option>';
        return;
    }

    // Замените на ваш фактический schoolId
    fetch(`/getSettlements?region=${region}`)
        .then(response => response.json())
        .then(settlements => {
            const settlementSelect = document.getElementById('settlementSelect');
            settlementSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.disabled = true;
            defaultOption.selected = true;
            defaultOption.textContent = 'Выберите поселение';
            settlementSelect.appendChild(defaultOption);

            settlements.forEach(settlement => {
                const option = document.createElement('option');
                option.value = settlement.id;
                option.textContent = settlement.name;
                settlementSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Ошибка при получении поселений:', error);
            document.getElementById('settlementSelect').innerHTML = '<option value="">Ошибка загрузки поселений</option>';
        });
}

// Закрытие модального окна при клике за его пределами
window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddEducation');
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
    const regionId = document.getElementById('regionSelect').value;
    const settlementId = document.getElementById('settlementSelect').value

    if (schoolName && schoolAddress && regionId && settlementId) {
                const data = {
                    name: schoolName,
                    address: schoolAddress,
                    email: schoolEmail,
                    phoneNumber: schoolPhoneNumber,
                    regionId: regionId, // Добавляем регион
                    settlementId: settlementId // Добавляем поселение
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
                    const modal = document.getElementById('moduleAddEducation');
                    modal.style.display = 'none';
                    document.getElementById('schoolName').value = '';
                    document.getElementById('schoolAddress').value = '';
                    document.getElementById('schoolEmail').value = '';
                    document.getElementById('schoolPhoneNumber').value = '';
                    document.getElementById('regionSelect').selectedIndex = 0;
                    document.getElementById('settlementSelect').innerHTML = '<option value="">Сначала выберите регион</option>';

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


const deleteSchoolButton = document.getElementById('deleteSchoolButton');
deleteSchoolButton.addEventListener('click', function() {
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