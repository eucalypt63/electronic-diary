window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddClass');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

document.getElementById('submitClassButton').addEventListener('click', function() {
    const className = document.getElementById('className').value;
    const teacherSelect = document.getElementById('teacherSelect');
    const selectedTeacherId = teacherSelect.options[teacherSelect.selectedIndex].value;

    if (className && selectedTeacherId) {
        const data = {
            name: className,
            teacherId: selectedTeacherId
        };

        fetch('/addClass', {
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
            throw new Error('Ошибка при добавлении класса');
        })
        .then(data => {
            const modal = document.getElementById('moduleAddClass');
            modal.style.display = 'none';
            document.getElementById('className').value = '';
            teacherSelect.selectedIndex = 0;

            updateObjectList();
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить класс');
        });
    } else {
        alert('Пожалуйста, заполните обязательные поля.');
    }
});