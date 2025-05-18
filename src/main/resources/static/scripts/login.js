async function submitLoginForm(event) {
    event.preventDefault();
    const formData = new FormData(document.getElementById('loginForm'));
    const response = await fetch('/login', {
        method: 'POST',
        body: formData
    });
    //----------
    if (response.ok) {
        const userRole = await response.text();
        if (userRole === "Main admin") {
            window.location.href = '/adminSettings';
        } else if (userRole === "Local admin" ){
            window.location.href = '/adminSettings';
        } else if (userRole === "School student" ){
            window.location.href = '/profileSchoolStudent?id=101'; // Получить id пользователя
        }
    } else {
        const errorMessage = await response.text();
        document.querySelector('.error-message span').innerText = errorMessage;
    }
}

document.getElementById('loginForm').addEventListener('submit', submitLoginForm);