document.addEventListener('DOMContentLoaded', () => {
    const eventForm = document.getElementById('event-form');
    const eventsList = document.getElementById('events-list'); // Corrected DOM element id
    const thematicSelect = document.getElementById('thematic');
    const eventTypeSelect = document.getElementById('eventType');
    const locationTypeSelect = document.getElementById('locationType');
    const imagePreviewContainer = document.getElementById('image-preview');
    const userId = 6; // Example user ID

    // Static values for enums
    const Thematic = ['ECONOMIC', 'SOCIAL', 'ENVIRONMENTAL'];
    const EventType = ['PUBLIC', 'PRIVATE'];
    const LocationType = ['ONLINE', 'OFFLINE'];

    // Populate enums in the form
    function populateEnums() {
        populateSelect(thematicSelect, Thematic);
        populateSelect(eventTypeSelect, EventType);
        populateSelect(locationTypeSelect, LocationType);
    }

    function populateSelect(selectElement, values) {
        selectElement.innerHTML = '';
        values.forEach(value => {
            const option = document.createElement('option');
            option.value = value;
            option.textContent = value;
            selectElement.appendChild(option);
        });
    }

    // Fetch all events
    async function fetchEvents() {
        try {
            const response = await fetch(`/event/getAll?userId=${userId}`);
            if (!response.ok) throw new Error('Failed to fetch events');
            const events = await response.json();
            renderEvents(events);
        } catch (error) {
            console.error('Error fetching events:', error);
            alert('Failed to load events. Please try again later.');
        }
    }

    // Render events
    function renderEvents(events) {
        if (!eventsList) {
            console.error('Error: events-list element not found in the DOM.');
            return;
        }

        eventsList.innerHTML = ''; // Clear existing content

        if (events.length === 0) {
            eventsList.innerHTML = '<p>No events found.</p>';
            return;
        }

        events.forEach(event => {
            const eventItem = document.createElement('li');
            eventItem.classList.add('event-item');
            eventItem.innerHTML = `
                <div class="event-details">
                    <h3>${event.title}</h3>
                    <p><strong>Thematic:</strong> ${event.thematic}</p>
                    <p><strong>Type:</strong> ${event.eventType}</p>
                    <p><strong>Location:</strong> ${event.locationType}</p>
                    <p><strong>Start:</strong> ${event.startTime}</p>
                    <p><strong>Completed:</strong> ${event.completed ? 'Yes' : 'No'}</p>
                    ${event.images.length > 0 ? `<img src="${event.images[0]}" alt="${event.title}" class="event-image">` : ''}
                </div>
                <div class="event-actions">
                    <button class="complete-btn" data-id="${event.id}">${event.completed ? 'Completed' : 'Mark as Complete'}</button>
                    <button class="update-btn" data-id="${event.id}">Update</button>
                    <button class="delete-btn" data-id="${event.id}">Delete</button>
                </div>
            `;
            eventsList.appendChild(eventItem);

            // Add event listeners for actions
            eventItem.querySelector('.complete-btn').addEventListener('click', markEventAsComplete);
            eventItem.querySelector('.update-btn').addEventListener('click', populateUpdateForm);
            eventItem.querySelector('.delete-btn').addEventListener('click', deleteEvent);
        });
    }

    // Create or update event
    eventForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(eventForm);

        // Append userId explicitly
        formData.append('userId', userId);

        const eventId = formData.get('id');
        const endpoint = eventId ? `/event/update/${eventId}` : '/event/create';
        const method = eventId ? 'PATCH' : 'POST';

        try {
            const response = await fetch(endpoint, {
                method,
                body: formData,
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error("Error response:", errorData);
                throw new Error('Failed to save the event');
            }

            alert(eventId ? 'Event updated successfully!' : 'Event created successfully!');
            eventForm.reset();
            imagePreviewContainer.innerHTML = ''; // Clear image preview
            fetchEvents();
        } catch (error) {
            console.error("Error saving event:", error);
            alert('Failed to save the event. Please try again.');
        }
    });

    // Mark event as complete
    async function markEventAsComplete(e) {
        const eventId = e.target.dataset.id;
        try {
            const response = await fetch(`/event/complete/${eventId}`, { method: 'PUT' });
            if (!response.ok) throw new Error('Failed to mark event as complete');
            alert('Event marked as complete!');
            fetchEvents();
        } catch (error) {
            console.error('Error marking event as complete:', error);
            alert('Failed to mark event as complete.');
        }
    }

    // Populate form for update
    function populateUpdateForm(e) {
        const eventId = e.target.dataset.id;
        fetch(`/event/getAll?userId=${userId}`)
            .then(response => response.json())
            .then(events => {
                const event = events.find(e => e.id == eventId);
                if (event) {
                    document.getElementById('event-id').value = event.id;
                    document.getElementById('title').value = event.title;
                    document.getElementById('description').value = event.description;
                    document.getElementById('thematic').value = event.thematic;
                    document.getElementById('eventType').value = event.eventType;
                    document.getElementById('locationType').value = event.locationType;
                    document.getElementById('startTime').value = event.startTime.split('T')[0];
                }
            });
    }

    // Delete event
    async function deleteEvent(e) {
        const eventId = e.target.dataset.id;
        try {
            const response = await fetch(`/event/delete/${eventId}`, { method: 'DELETE' });
            if (!response.ok) throw new Error('Failed to delete event');
            alert('Event deleted successfully!');
            fetchEvents();
        } catch (error) {
            console.error('Error deleting event:', error);
            alert('Failed to delete event.');
        }
    }

    // Handle image preview
    document.getElementById('image').addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                imagePreviewContainer.innerHTML = `<img src="${event.target.result}" alt="Preview" class="event-image">`;
            };
            reader.readAsDataURL(file);
        }
    });

    // Initial actions
    populateEnums();
    fetchEvents();
});
