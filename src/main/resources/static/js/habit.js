document.addEventListener("DOMContentLoaded", () => {
    // Отримуємо елементи
    const habitImage = document.querySelector(".habit-image img");
    const tagsContainer = document.querySelector(".tags");
    const tags = document.querySelectorAll(".tag");

    if (habitImage) {
        habitImage.addEventListener("click", () => {
            alert("You clicked on the habit image!");
        });
    }

    if (tagsContainer) {
        tagsContainer.addEventListener("click", (event) => {
            const clickedTag = event.target;

            if (clickedTag.classList.contains("tag")) {
                // Знімаємо активний клас з усіх тегів
                tags.forEach(tag => tag.classList.remove("active-tag"));

                clickedTag.classList.add("active-tag");
                console.log("Selected tag:", clickedTag.textContent);
            }
        });
    }
});
