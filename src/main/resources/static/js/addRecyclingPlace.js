document.getElementById('add-place-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);

    const requestData = {
        name: formData.get('name'),
        address: formData.get('address'),
        wasteType: Array.from(formData.getAll('wasteType')),
        latitude: parseFloat(formData.get('latitude')),
        longitude: parseFloat(formData.get('longitude')),
        openingTime: formData.get('openingTime'),
        closingTime: formData.get('closingTime'),
        workingDays: Array.from(formData.getAll('workingDays')),
    };

    try {
        const userId = 6; // ID користувача
        const response = await fetch(`/recycling-point/create?userId=${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        });

        if (response.ok) {
            // Додаємо нове місце в локальне сховище
            const places = JSON.parse(localStorage.getItem('places')) || [];
            places.push({
                name: requestData.name,
                address: requestData.address,
                coords: [requestData.latitude, requestData.longitude],
            });
            localStorage.setItem('places', JSON.stringify(places));

            // Редірект на recyclingPoint.html
            alert('New Recycling Place Added Successfully!');
            window.location.href = '/recyclingPoint.html';
        } else {
            const error = await response.json();
            console.error(error);
            alert(JSON.stringify(error));
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
    }
});