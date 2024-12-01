// Fetch menu items and display on the home page
document.addEventListener("DOMContentLoaded", () => {
    const menuItemsContainer = document.getElementById("menu-items");

    fetch("http://localhost:8080/api/menu/menu")
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                const menuItem = document.createElement("div");
                menuItem.classList.add("menu-item", "col-md-4");
                menuItem.innerHTML = `
                    <div class="card">
                        <img src="${item.imageUrl}" class="card-img-top" alt="${item.name}">
                        <div class="card-body">
                            <h5 class="card-title">${item.name}</h5>
                            <p class="card-text">$${item.price}</p>
                        </div>
                    </div>
                `;
                menuItemsContainer.appendChild(menuItem);
            });
        })
        .catch(err => console.error("Failed to load menu items:", err));
});


// Handle form submissions for register and login
document.getElementById("register-form")?.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch("/users/register", { // Corrected URL
        method: "POST",
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: { "Content-Type": "application/json" }
    }).then(res => {
        if (res.ok) {
            alert("Registration successful!");
            window.location.href = "/users/login"; // Redirect to login page
        }
    });
});

document.getElementById("login-form")?.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch("/users/login", { // Corrected URL
        method: "POST",
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: { "Content-Type": "application/json" }
    }).then(res => {
        if (res.ok) {
            alert("Login successful!");
            window.location.href = "/"; // Redirect to homepage
        }
    });
});
