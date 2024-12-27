document.addEventListener('DOMContentLoaded', () => {
    const difficultyStars = document.querySelectorAll('#difficulty span');
    const tagsContainer = document.getElementById('tags-container');
    const newTagInput = document.getElementById('new-tag');
    const addTagBtn = document.getElementById('add-tag-btn');
    const startDateInput = document.getElementById('start-date');
    const endDateInput = document.getElementById('end-date');
    const durationDisplay = document.getElementById('duration');

    // Initialize date pickers
    flatpickr('.date-picker', {
        dateFormat: "Y-m-d",
        onChange: calculateDuration
    });

    // Handle difficulty rating
    difficultyStars.forEach(star => {
        star.addEventListener('click', () => {
            difficultyStars.forEach(s => s.classList.remove('active'));
            for (let i = 0; i < star.dataset.value; i++) {
                difficultyStars[i].classList.add('active');
            }
        });
    });

    // Add new tag
    addTagBtn.addEventListener('click', () => {
        const tagValue = newTagInput.value.trim();
        if (tagValue) {
            const tagElement = document.createElement('span');
            tagElement.textContent = tagValue;
            tagElement.classList.add('tag');
            tagsContainer.appendChild(tagElement);
            newTagInput.value = '';
        }
    });

    // Calculate duration
    function calculateDuration() {
        const startDate = new Date(startDateInput.value);
        const endDate = new Date(endDateInput.value);

        if (startDate && endDate && startDate <= endDate) {
            const duration = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24)); // Days
            durationDisplay.textContent = `Duration: ${duration} days`;
        } else {
            durationDisplay.textContent = "Invalid dates. Please select valid dates.";
        }
    }

    // Submit form
    document.getElementById('habit-form').addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            title: document.getElementById('habit-title').value,
            difficulty: [...difficultyStars].filter(s => s.classList.contains('active')).length,
            tags: [...tagsContainer.querySelectorAll('.tag')].map(tag => tag.textContent),
            startDate: startDateInput.value,
            endDate: endDateInput.value,
            description: document.getElementById('description').value,
        };

        try {
            const response = await fetch('/habits/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                alert('Habit created successfully!');
            } else {
                alert('Error creating habit. Try again.');
            }
        } catch (err) {
            console.error(err);
            alert('An unexpected error occurred.');
        }
    });
});
