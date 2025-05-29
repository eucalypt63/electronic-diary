async function submitLoginForm(event) {
    event.preventDefault();
    const formData = new FormData(document.getElementById('loginForm'));
    const response = await fetch('/login', {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        const userInfoResponse = await fetch('/getAuthorizationUserInfo');
        if (userInfoResponse.ok) {
            const userInfo = await userInfoResponse.json();
            const role = userInfo.role;
            const id = userInfo.id;

            switch(role) {
                case "Main admin":
                case "Local admin":
                    window.location.href = '/adminSettings';
                    break;
                case "School student":
                    window.location.href = `/profileSchoolStudent?id=${id}`;
                    break;
                case "Administration":
                    window.location.href = `/profileAdministrator?id=${id}`;
                    break;
                case "Teacher":
                    window.location.href = `/profileTeacher?id=${id}`;
                    break;
                case "Parent":
                    window.location.href = `/profileParent?id=${id}`;
                    break;
                default:
                    console.error('Unknown role:', role);
                    document.querySelector('.error-message span').innerText = 'Неизвестная роль пользователя';
            }
        } else {
            const errorMessage = await userInfoResponse.text();
            document.querySelector('.error-message span').innerText = errorMessage;
        }
    } else {
        const errorMessage = await response.text();
        document.querySelector('.error-message span').innerText = errorMessage;
    }
}

document.getElementById('loginForm').addEventListener('submit', submitLoginForm);