document.addEventListener('DOMContentLoaded', () => {
    const habitForm = document.getElementById('habit-form');
    const habitsList = document.getElementById('habits-list');
    const progressBarFill = document.getElementById('progress-bar-fill');
    const progressText = document.getElementById('progress-text');
    document.getElementById('image').addEventListener('change', handleImagePreview);

    const userId = 6;
    let habits = [];

    const difficultyMap = {
        "Easy": 1,
        "Moderate": 2,
        "Hard": 3
    };

    async function fetchHabits() {
        try {
            const response = await fetch(`/habits/user-habits?userId=${userId}`);
            if (!response.ok) throw new Error('Failed to fetch habits');
            habits = await response.json();
            renderHabits();
            updateProgress();
        } catch (error) {
            console.error("Error fetching habits:", error);
            alert('Failed to load habits. Please try again.');
        }
    }

    function renderHabits() {
        habitsList.innerHTML = '';
        habits.forEach(habit => {
            const habitItem = document.createElement('li');
            habitItem.classList.add('habit-item');
            habitItem.innerHTML = `
            <div class="habit-content">
                <h3>${habit.title}</h3>
                <p>${habit.description}</p>
                <p><strong>Tags:</strong> ${habit.tags.join(', ')}</p>
                <p><strong>Difficulty:</strong> ${habit.difficulty}</p>
                <p><strong>Duration:</strong> ${habit.durationInDays} days</p>
                ${
                habit.images && habit.images.length > 0
                    ? habit.images.map(url => `<img src="${url}" alt="${habit.title}" class="habit-image">`).join('')
                    : ''
            }
            </div>
            <div class="habit-actions">
                <button class="complete-btn ${habit.completed ? 'disabled' : ''}" data-id="${habit.id}">
                    ${habit.completed ? 'Completed' : 'Mark as Complete'}
                </button>
                <button class="update-btn" data-id="${habit.id}">Update</button>
                <button class="delete-btn" data-id="${habit.id}">Delete</button>
            </div>
        `;
            habitsList.appendChild(habitItem);

            // Event listeners for actions
            habitItem.querySelector('.complete-btn').addEventListener('click', markHabitAsComplete);
            habitItem.querySelector('.update-btn').addEventListener('click', populateUpdateForm);
            habitItem.querySelector('.delete-btn').addEventListener('click', deleteHabit);
        });
    }

    function handleImagePreview(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                const previewContainer = document.getElementById('image-preview');
                previewContainer.innerHTML = `<img src="${event.target.result}" alt="Preview" class="habit-image">`;
            };
            reader.readAsDataURL(file);
        }
    }

    habitForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(habitForm);

        formData.append('userId', userId);

        console.log([...formData.entries()]);

        const endpoint = habitId ? `/habits/habit/update/${habitId}` : '/habits/create';
        const method = habitId ? 'PATCH' : 'POST';

        try {
            const response = await fetch(endpoint, {
                method,
                body: formData,
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error("Error response:", errorData);
                throw new Error('Failed to save the habit');
            }

            alert(habitId ? 'Habit updated successfully!' : 'Habit created successfully!');
            habitForm.reset();
            fetchHabits();
        } catch (error) {
            console.error("Error saving habit:", error);
            alert('Failed to save the habit. Please try again.');
        }
    });

    function updateProgress() {
        const completed = habits.filter(habit => habit.completed).length;
        const total = habits.length;
        const progress = total === 0 ? 0 : Math.round((completed / total) * 100);

        progressBarFill.style.width = `${progress}%`;
        progressText.textContent = `${completed} of ${total} habits completed (${progress}%)`;
    }

    habitForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(habitForm);

        formData.append('userId', userId);

        const difficulty = formData.get('difficulty');
        formData.set('difficulty', difficultyMap[difficulty]);

        const startDate = formData.get('start-date');
        const endDate = formData.get('end-date');
        if (!startDate || !endDate) {
            alert('Please provide both Start Date and End Date.');
            return;
        }

        formData.set('startDate', startDate + "T00:00:00"); // Add time component
        formData.set('endDate', endDate + "T00:00:00");

        const habitId = formData.get('id');
        const endpoint = habitId ? `/habits/habit/update/${habitId}` : '/habits/create';
        const method = habitId ? 'PATCH' : 'POST';

        try {
            const response = await fetch(endpoint, {
                method,
                body: formData,
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error("Error response:", errorData);
                throw new Error('Failed to save the habit');
            }

            alert(habitId ? 'Habit updated successfully!' : 'Habit created successfully!');
            habitForm.reset();
            fetchHabits();
        } catch (error) {
            console.error("Error saving habit:", error);
            alert('Failed to save the habit. Please try again.');
        }
    });

    async function markHabitAsComplete(e) {
        const habitId = e.target.dataset.id;
        try {
            const response = await fetch(`/habits/complete/${habitId}`, {method: 'PUT'});
            if (!response.ok) throw new Error('Failed to mark habit as complete');
            alert('Habit marked as complete!');
            fetchHabits();
        } catch (error) {
            console.error("Error marking habit as complete:", error);
            alert('Failed to mark habit as complete.');
        }
    }

    function populateUpdateForm(e) {
        const habitId = e.target.dataset.id;
        const habit = habits.find(h => h.id === parseInt(habitId, 10));

        document.getElementById('habit-id').value = habit.id;
        document.getElementById('title').value = habit.title;
        document.getElementById('description').value = habit.description;
        document.getElementById('start-date').value = habit.startDate.split('T')[0];
        document.getElementById('end-date').value = habit.endDate.split('T')[0];
        document.getElementById('tags').value = habit.tags.join(', ');
        document.getElementById('difficulty').value = habit.difficulty === 1 ? 'Easy' : habit.difficulty === 2 ? 'Moderate' : 'Hard';
    }

    async function deleteHabit(e) {
        const habitId = e.target.dataset.id;
        try {
            const response = await fetch(`/habits/delete/${habitId}`, {method: 'DELETE'});
            if (!response.ok) throw new Error('Failed to delete habit');
            alert('Habit deleted successfully!');
            fetchHabits();
        } catch (error) {
            console.error("Error deleting habit:", error);
            alert('Failed to delete habit.');
        }
    }

    fetchHabits();
});