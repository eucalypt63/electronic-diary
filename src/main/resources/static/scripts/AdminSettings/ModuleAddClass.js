window.addEventListener('click', function(event) {
    const modal = document.getElementById('moduleAddClass');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});