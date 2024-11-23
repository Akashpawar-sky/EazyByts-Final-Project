// Fetch menu items and display on the home page
document.addEventListener("DOMContentLoaded", () => {
    const menuItemsContainer = document.getElementById("menu-items");
    
    fetch("http://localhost:8080/api/menu/menu") // Update with your API URL
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                const menuItem = document.createElement("div");
                menuItem.classList.add("menu-item");
                menuItem.innerHTML = `
                    <img src="${item.image}" alt="${item.name}" width="150">
                    <h3>${item.name}</h3>
                    <p>$${item.price}</p>
                `;
                menuItemsContainer.appendChild(menuItem);
            });
        })
        .catch(err => console.error(err));
});

// Handle form submissions for register and login
document.getElementById("register-form")?.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch("http://localhost:8080/api/register", {
        method: "POST",
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: { "Content-Type": "application/json" }
    }).then(res => {
        if (res.ok) alert("Registration successful!");
    });
});

document.getElementById("login-form")?.addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch("http://localhost:8080/api/login", {
        method: "POST",
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: { "Content-Type": "application/json" }
    }).then(res => {
        if (res.ok) alert("Login successful!");
    });
});
