document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');

    // Ініціалізація календаря
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        height: 'auto', // Робимо календар компактним
        headerToolbar: {
            left: 'prev,next',
            center: 'title',
            right: 'today',
        },
        events: [
            { title: 'Community Clean-up', start: '2024-12-10' },
            { title: 'Recycling Workshop', start: '2024-12-15' },
        ],
    });

    calendar.render();

    // Завантаження фото
    const form = document.getElementById('photo-upload-form');
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        const fileInput = document.getElementById('photo');
        const formData = new FormData();
        formData.append('photo', fileInput.files[0]);

        try {
            const response = await fetch('/api/upload', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                alert('Photo uploaded successfully!');
            } else {
                alert('Failed to upload photo.');
            }
        } catch (error) {
            alert('Error uploading photo. Please try again later.');
        }
    });
});
