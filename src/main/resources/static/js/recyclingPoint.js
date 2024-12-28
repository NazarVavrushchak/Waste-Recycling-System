const map = L.map('map').setView([50.4501, 30.5234], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 }).addTo(map);

const placesList = document.getElementById('places-list');

// Функція для отримання даних із бекенду
async function fetchPlaces() {
    try {
        // Замінити URL на ваш бекенд-ендпоінт
        const response = await fetch('/recycling-point/getAll');
        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }

        const places = await response.json();
        renderPlaces(places); // Рендеримо отримані дані
    } catch (error) {
        console.error('Error fetching places:', error);
    }
}

// Функція для рендеру місць
function renderPlaces(places) {
    placesList.innerHTML = ''; // Очищаємо список

    places.forEach(place => {
        // Додаємо елемент списку
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>${place.name}</strong> - ${place.address}<br>
            <em>Types of waste: ${place.wasteType.join(', ')}</em>
        `;
        placesList.appendChild(listItem);

        // Додаємо маркер на карту
        L.marker([place.latitude, place.longitude])
            .addTo(map)
            .bindPopup(`
                <strong>${place.name}</strong><br>
                ${place.address}<br>
                <em>Types of waste: ${place.wasteType.join(', ')}</em>
            `);
    });
}

// Викликаємо функцію для отримання даних
fetchPlaces();