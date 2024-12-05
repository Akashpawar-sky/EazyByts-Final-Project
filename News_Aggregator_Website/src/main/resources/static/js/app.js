// Backend API endpoint
const apiUrl = "http://localhost:8080/api/articles"; // Adjust based on your backend setup

// Function to fetch articles
async function fetchArticles() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Failed to fetch articles");

        const articles = await response.json();
        displayArticles(articles);
    } catch (error) {
        console.error("Error fetching articles:", error);
        alert("Could not load news articles. Please try again later.");
    }
}

// Function to display articles on the page
function displayArticles(articles) {
    const newsContainer = document.getElementById("news-container");
    newsContainer.innerHTML = ""; // Clear previous content

    articles.forEach(article => {
        const card = document.createElement("div");
        card.className = "col-md-4 news-card";

        card.innerHTML = `
            <div class="card h-100">
                <img src="${article.imageUrl || 'placeholder.jpg'}" class="card-img-top" alt="${article.title}">
                <div class="card-body">
                    <h5 class="news-title">${article.title}</h5>
                    <p class="news-description">${article.description}</p>
                    <a href="${article.url}" target="_blank" class="btn btn-primary">Read More</a>
                </div>
            </div>
        `;

        newsContainer.appendChild(card);
    });
}

// Load articles when the page is loaded
window.onload = fetchArticles;
