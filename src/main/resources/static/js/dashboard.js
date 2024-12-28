document.addEventListener('DOMContentLoaded', function () {
    const userId = 6; // Example user ID, replace with dynamic value if needed
    const createGoalBtn = document.getElementById('create-goal-btn');
    const goalFormContainer = document.getElementById('goal-form-container');
    const cancelGoalBtn = document.getElementById('cancel-goal-btn');
    const goalForm = document.getElementById('goal-form');
    const goalDescriptionInput = document.getElementById('goal-description');
    const goalTargetDateInput = document.getElementById('goal-target-date');

    function getDashboardData() {
        fetch(`/dashboard/show-statistic?userId=${userId}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('user-name').textContent = `Welcome, ${data.userName}`;
                document.getElementById('completed-habits').textContent = data.completedHabits;
                document.getElementById('events-participated').textContent = data.eventsParticipated;
                document.getElementById('current-goal').textContent = data.currentGoal;
                document.getElementById('days-active-last-month').textContent = data.daysActiveLastMonth;
                document.getElementById('last-activity-date').textContent = data.lastActivityDate;
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    }

    function getUserGoals() {
        fetch(`/dashboard/goals?userId=${userId}`)
            .then(response => response.json())
            .then(goals => {
                const goalList = document.getElementById('goal-list');
                goalList.innerHTML = '';
                goals.forEach(goal => {
                    const goalItem = document.createElement('li');
                    goalItem.classList.add('goal-item');
                    goalItem.innerHTML = `
                        <div>
                            <strong>${goal.description}</strong> - <span class="goal-status">${goal.status}</span>
                        </div>
                        <div class="goal-actions">
                            <button class="mark-complete-btn" data-id="${goal.id}">Mark as Completed</button>
                            <button class="delete-btn" data-id="${goal.id}">Delete</button>
                        </div>
                    `;
                    goalList.appendChild(goalItem);

                    // Add event listeners
                    goalItem.querySelector('.mark-complete-btn').addEventListener('click', () => markGoalAsCompleted(goal.id));
                    goalItem.querySelector('.delete-btn').addEventListener('click', () => deleteGoal(goal.id));
                });
            })
            .catch(error => console.error('Error fetching goals:', error));
    }

    function markGoalAsCompleted(goalId) {
        fetch(`/dashboard/goals/${goalId}/complete`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to mark goal as completed');
                }
                return response.json();
            })
            .then(() => {
                alert('Goal marked as completed!');
                getUserGoals(); // Refresh the goal list
            })
            .catch(error => console.error('Error marking goal as completed:', error));
    }

    function deleteGoal(goalId) {
        fetch(`/dashboard/goals/${goalId}`, {method: 'DELETE'})
            .then(() => getUserGoals())
            .catch(error => console.error('Error deleting goal:', error));
    }

    createGoalBtn.addEventListener('click', function () {
        goalFormContainer.style.display = 'block';
    });

    cancelGoalBtn.addEventListener('click', function () {
        goalFormContainer.style.display = 'none';
    });

    goalForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const description = goalDescriptionInput.value.trim();
        const targetDate = goalTargetDateInput.value;

        fetch('/dashboard/goals', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({userId, description, targetDate}),
        })
            .then(() => {
                goalFormContainer.style.display = 'none'; // Hide the form
                getUserGoals(); // Refresh goals list
                window.scrollTo({top: 0, behavior: 'smooth'}); // Scroll to top
            })
            .catch(error => console.error('Error creating goal:', error));
    });

    getDashboardData();
    getUserGoals();
});