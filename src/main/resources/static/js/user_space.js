document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        height: 'auto',
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

    const form = document.getElementById('photo-upload-form');
    const fileInput = document.getElementById('photo');

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        const file = fileInput.files[0];

        if (!file) {
            alert('Please choose a photo first.');
            return;
        }

        console.log('Selected file:', file);

        new Compressor(file, {
            quality: 0.6,
            success(compressedFile) {
                console.log('Compressed file:', compressedFile);

                const formData = new FormData();
                formData.append('photo', compressedFile);

                uploadPhoto(formData);
            },
            error(err) {
                console.error('Compression error:', err.message);
            },
        });
    });

    async function uploadPhoto(formData) {
        try {
            const response = await fetch('/upload', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                const errorResponse = await response.json();
                throw new Error(errorResponse.message || 'Failed to upload photo');
            }

            const result = await response.json();
            console.log('Upload successful:', result);

            // Оновлюємо фото з поверненим URL
            updateAchievementsPhoto(result.url);
            alert('Photo uploaded successfully!');
        } catch (error) {
            console.error('Upload failed:', error.message);
            alert(error.message || 'Error uploading photo. Please try again later.');
        }
    }

    function updateAchievementsPhoto(url) {
        const photoContainer = document.getElementById('photo-and-achievements');
        let achievementImage = document.getElementById('achievement-photo');

        if (!achievementImage) {
            // Створюємо новий <img>, якщо його немає
            achievementImage = document.createElement('img');
            achievementImage.id = 'achievement-photo';
            achievementImage.alt = 'Uploaded Photo';
            achievementImage.style.maxWidth = '100%';
            photoContainer.appendChild(achievementImage);
        }

        achievementImage.src = url;
    }
});
